package com.flm.appointment.service;

import java.time.LocalDate;
import java.util.List;

import com.flm.appointment.dto.AppointmentRequestDTO;
import com.flm.appointment.dto.AppointmentResponseDTO;
import com.flm.appointment.dto.RescheduleAppointmentDTO;

public interface AppointmentService {
	List<String> getDoctorsPatient(String doctorId, LocalDate start, LocalDate end);
	AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO);
	List<AppointmentResponseDTO> getFutureAppointmentsOfDoctor(String doctorId);
	List<AppointmentResponseDTO> getAllAppointmentsForAllDoctors(String date);
	String cancelAppointment(String appointmentId);
	AppointmentResponseDTO rescheduleAppointment(String appointmentId, RescheduleAppointmentDTO rescheduleAppointmentDTO);
	AppointmentResponseDTO getAppointmentDetails(String appointmentId);
	List<AppointmentResponseDTO> getAllAppointmentsOfDoctor(String doctorId, String date);
}
