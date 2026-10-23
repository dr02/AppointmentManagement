package com.flm.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleAppointmentDTO {
	private LocalDate newDate;
	private LocalTime startTime;
	private LocalTime endTime;
}
