package com.flm.appointment.builder;

import com.flm.appointment.dto.AppointmentRequestDTO;
import com.flm.appointment.model.Appointment;
import com.flm.appointment.model.AppointmentStatus;

public class AppointmentBuilder {

	public static Appointment buildAppointmentFromAppointmentRequestDTO(AppointmentRequestDTO appointmentRequestDTO) {
		return Appointment
				.builder()
				.patientId(Long.parseLong(appointmentRequestDTO.getPatientId()))
				.doctorId(Long.parseLong(appointmentRequestDTO.getDoctorId()))
				.appointmentDate(appointmentRequestDTO.getAppointmentDate())
				.startTime(appointmentRequestDTO.getStartTime())
				.endTime(appointmentRequestDTO.getEndTime())
				.appointmentStatus(AppointmentStatus.SCHEDULED)
				.notes(appointmentRequestDTO.getNotes())
				.build();
	}
}
