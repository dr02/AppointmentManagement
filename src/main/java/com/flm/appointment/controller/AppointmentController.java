package com.flm.appointment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
	private final AppointmentService appointmentService;
	
	@GetMapping("/appointments/{doctorId}")
	public ResponseEntity<List<String>> getDoctorPatients(@PathVariable String doctorId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		List<String> doctorsPatient = appointmentService.getDoctorsPatient(doctorId, startDate, endDate);
		return ResponseEntity.ok(doctorsPatient);
	}
}
