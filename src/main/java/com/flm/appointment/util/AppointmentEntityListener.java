package com.flm.appointment.util;

import org.springframework.stereotype.Component;

import com.flm.appointment.model.Appointment;
import com.flm.appointment.model.AppointmentStatus;

import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppointmentEntityListener {
	private final AppointmentIdGenerator appointmentIdGenerator;
	
	@PrePersist
	public void generateAppointmentId(Appointment appointment) {
		if(appointment != null) {
			Long newAppointmentId = appointmentIdGenerator.generateNextAppointmentId();
			
			if(appointment.getId() == null) {
				appointment.setId(newAppointmentId);
			}
			
			if(appointment.getAppointmentStatus() == null) {
				appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
			}
		}
	}
}
