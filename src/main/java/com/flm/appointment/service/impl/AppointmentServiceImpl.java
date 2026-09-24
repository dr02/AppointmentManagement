package com.flm.appointment.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flm.appointment.dao.AppointmentRepository;
import com.flm.appointment.model.Appointment;
import com.flm.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
	private final AppointmentRepository appointmentRepository;

	@Override
	public List<String> getDoctorsPatient(String doctorId, LocalDate start, LocalDate end) {
		List<Appointment> appointmentsOfDoctorId = appointmentRepository.findByDoctorIdAndAppointmentDateBetweenOrderByPatientIdDesc(doctorId, start, end);
		return appointmentsOfDoctorId
				.stream()
				.map((appointment) -> appointment.getPatientId())
				.map(String::valueOf)
				.toList();
	}
	
	
}
