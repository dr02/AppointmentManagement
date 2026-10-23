package com.flm.appointment.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flm.appointment.builder.AppointmentBuilder;
import com.flm.appointment.builder.AppointmentDTOBuilder;
import com.flm.appointment.clients.PatiensClient;
import com.flm.appointment.clients.StaffsClient;
import com.flm.appointment.dao.AppointmentRepository;
import com.flm.appointment.dto.AppointmentRequestDTO;
import com.flm.appointment.dto.AppointmentResponseDTO;
import com.flm.appointment.dto.RegisterPatientResponseDTO;
import com.flm.appointment.dto.RescheduleAppointmentDTO;
import com.flm.appointment.dto.StaffDetailsDTO;
import com.flm.appointment.exception.AppointmentAlreadyExistsException;
import com.flm.appointment.exception.AppointmentNotFoundException;
import com.flm.appointment.model.Appointment;
import com.flm.appointment.model.AppointmentStatus;
import com.flm.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final PatiensClient patiensClient;
	private final StaffsClient staffsClient;

	@Override
	public List<String> getDoctorsPatient(String doctorId, LocalDate start, LocalDate end) {
		List<Appointment> appointmentsOfDoctorId = appointmentRepository.findByDoctorIdAndAppointmentDateBetweenOrderByPatientIdDesc(doctorId, start, end);
		return appointmentsOfDoctorId
				.stream()
				.map((appointment) -> appointment.getPatientId())
				.map(String::valueOf)
				.toList();
	}

	@Override
	public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO) {
		LocalTime startTime = appointmentRequestDTO.getStartTime();
		LocalTime endTime = appointmentRequestDTO.getEndTime();
		
		validateTimePeriod(startTime, endTime);
		validateDateIsNotPastDate(appointmentRequestDTO.getAppointmentDate());

		boolean conflict = appointmentRepository.isConflict(appointmentRequestDTO.getDoctorId(), appointmentRequestDTO.getAppointmentDate(), startTime, endTime);
		if (!conflict) throw new AppointmentAlreadyExistsException("Doctor already has an appointment during this time slot. Please choose a different time.");
		
		boolean patientAvailable = appointmentRepository.existsByPatientIdAndAppointmentDateAndStartTimeLessThanAndEndTimeGreaterThan(appointmentRequestDTO.getPatientId(), appointmentRequestDTO.getAppointmentDate(), endTime, startTime);
		if (patientAvailable) throw new AppointmentAlreadyExistsException("Patient already has an appointment during this time slot. Please choose a different time.");
		
		Appointment appointment = AppointmentBuilder.buildAppointmentFromAppointmentRequestDTO(appointmentRequestDTO);
		appointmentRepository.save(appointment);
		
		return AppointmentDTOBuilder.buildAppointmentResponseDTO(appointment);
	}

	@Override
	public List<AppointmentResponseDTO> getFutureAppointmentsOfDoctor(String doctorId) {
		long id = Long.parseLong(doctorId);
		List<Appointment> futureAppointments = appointmentRepository.getFutureAppointments(id);
		return futureAppointments
				.stream()
				.map(AppointmentDTOBuilder::buildAppointmentResponseDTO)
				.toList();
	}

	@Override
	public List<AppointmentResponseDTO> getAllAppointmentsForAllDoctors(String date) {
		LocalDate appointmentDate = LocalDate.parse(date);
		List<Appointment> allByAppointmentDate = appointmentRepository.findAllByAppointmentDate(appointmentDate);
		return allByAppointmentDate
				.stream()
				.map(AppointmentDTOBuilder::buildAppointmentResponseDTO)
				.toList();
	}

	@Override
	public String cancelAppointment(String appointmentId) {
		Appointment appointment = findAppointmentById(appointmentId);
		if(AppointmentStatus.CANCELLED.equals(appointment.getAppointmentStatus())) throw new IllegalStateException("Appointment is already cancelled.");
		
		appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
		appointmentRepository.save(appointment);
		return "SUCCESS";
	}

	@Override
	public AppointmentResponseDTO rescheduleAppointment(String appointmentId,
			RescheduleAppointmentDTO rescheduleAppointmentDTO) {
		Appointment appointment = findAppointmentById(appointmentId);
		
		validateDateIsNotPastDate(rescheduleAppointmentDTO.getNewDate());
		validateTimePeriod(rescheduleAppointmentDTO.getStartTime(), rescheduleAppointmentDTO.getEndTime());
		// conflict case
		
		appointment.setAppointmentDate(rescheduleAppointmentDTO.getNewDate());
		appointment.setStartTime(rescheduleAppointmentDTO.getStartTime());
		appointment.setEndTime(rescheduleAppointmentDTO.getEndTime());
		
		Appointment modifiedAppointment = appointmentRepository.save(appointment);
		return AppointmentDTOBuilder.buildAppointmentResponseDTO(modifiedAppointment);
	}
	
	@Override
	public AppointmentResponseDTO getAppointmentDetails(String appointmentId) {
		Appointment appointment = findAppointmentById(appointmentId);
		AppointmentResponseDTO appointmentResponseDTO = AppointmentDTOBuilder.buildAppointmentResponseDTO(appointment);
		
		RegisterPatientResponseDTO patientResponseDTO = patiensClient.getPatientByPatientId(appointmentResponseDTO.getPatientId()).getBody();
		StaffDetailsDTO staffDetailsDTO = staffsClient.getStaffByStaffId(appointmentResponseDTO.getDoctorId()).getBody();
		
		appointmentResponseDTO.setPatientName(patientResponseDTO.getPatientName());
		appointmentResponseDTO.setDoctorName(staffDetailsDTO.getFirstName()+" "+staffDetailsDTO.getLastName());
		return appointmentResponseDTO;
	}
	
	@Override
	public List<AppointmentResponseDTO> getAllAppointmentsOfDoctor(String doctorId, String date) {
		long id = Long.parseLong(doctorId);
		LocalDate appointmentDate = LocalDate.parse(date);
		List<Appointment> allAppointments = appointmentRepository.findAllByDoctorIdAndAppointmentDate(id, appointmentDate);
		return allAppointments
				.stream()
				.map(AppointmentDTOBuilder::buildAppointmentResponseDTO)
				.toList();
	}
	
	private Appointment findAppointmentById(String appointmentId) {
		long id = Long.parseLong(appointmentId);
		return findAppointmentById(id);
	}
	
	private Appointment findAppointmentById(long id) {
		return appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("No appointment with the ID: "+id));
	}
	
	private void validateTimePeriod(LocalTime startTime, LocalTime endTime) {
		if (!startTime.isBefore(endTime)) throw new RuntimeException("Start time must be before end time.");
	}
	
	private void validateDateIsNotPastDate(LocalDate appointmentDate) {
		if (appointmentDate.isBefore(LocalDate.now())) throw new RuntimeException("Cannot book appointments for past dates. Please select a future date.");
	}
}
