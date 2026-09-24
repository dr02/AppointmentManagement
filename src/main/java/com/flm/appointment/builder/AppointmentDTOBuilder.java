package com.flm.appointment.builder;

import com.flm.appointment.dto.AppointmentResponseDTO;
import com.flm.appointment.model.Appointment;

public class AppointmentDTOBuilder {

	public static AppointmentResponseDTO buildAppointmentResponseDTO(Appointment appointment) {
		return buildAppointmentResponseDTO(appointment, null);
	}
	
	public static AppointmentResponseDTO buildAppointmentResponseDTO(Appointment appointment, String diagonsis) {
		return AppointmentResponseDTO
				.builder()
				.appointmentId(String.valueOf(appointment.getId()))
				.patientId(String.valueOf(appointment.getPatientId()))
				.doctorId(String.valueOf(appointment.getDoctorId()))
				.appointmentDate(appointment.getAppointmentDate())
				.startTime(appointment.getStartTime())
				.endTime(appointment.getEndTime())
				.status(appointment.getAppointmentStatus().toString())
				.notes(appointment.getNotes())
				.build();
	}
}
