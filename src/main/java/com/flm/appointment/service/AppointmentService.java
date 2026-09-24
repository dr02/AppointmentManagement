package com.flm.appointment.service;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
	List<String> getDoctorsPatient(String doctorId, LocalDate start, LocalDate end);
}
