package com.flm.appointment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.flm.appointment.dao.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppointmentIdGenerator {
	private final AppointmentRepository appointmentRepository;
	
	public Long generateNextAppointmentId() {
		String newId = null;
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String currentTimestamp = current.format(formatter);
		
		String lastUsedIdSequence = appointmentRepository.lastUsedAppointmentIdSequence(currentTimestamp);
		if (lastUsedIdSequence != null && lastUsedIdSequence.length() >= 19) {
		String lastUsedIdTimestamp = lastUsedIdSequence.substring(0, 14);
		
		if(lastUsedIdTimestamp.equals(currentTimestamp)) {
			String idSuffix = lastUsedIdSequence.substring(14);
			int incrementedId = (Integer.parseInt(idSuffix))+1;
			newId = currentTimestamp+String.format("%05d", incrementedId);
		} else {
			newId = currentTimestamp+"00001";
			}
		
		} else {
			newId = currentTimestamp+"00001";
		}
		return Long.parseLong(newId);
	}
}
