package com.flm.appointment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.appointment.dto.AppointmentResponseDTO;
import com.flm.appointment.dto.RescheduleAppointmentDTO;
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
	
	@GetMapping("/allFutureAppointments/{doctorId}")
	public ResponseEntity<List<AppointmentResponseDTO>> getFutureAppointmentsOfDcotor(@PathVariable String doctorId) {
		List<AppointmentResponseDTO> futureAppointmentsOfDoctor = appointmentService.getFutureAppointmentsOfDoctor(doctorId);
		return ResponseEntity.ok(futureAppointmentsOfDoctor); 
	}
	
	@GetMapping("/{date}")
	public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointmentsForAllDoctors(@PathVariable String date) {
		List<AppointmentResponseDTO> allAppointmentsForAllDoctors = appointmentService.getAllAppointmentsForAllDoctors(date);
		return ResponseEntity.ok(allAppointmentsForAllDoctors);
	}
	
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity<String> cancelAppointmentByAppointmentId(@PathVariable String appointmentId) {
		String response = appointmentService.cancelAppointment(appointmentId);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/rescheduleAppointment/{appointmentId}")
	public ResponseEntity<AppointmentResponseDTO> rescheduleAppointment(@PathVariable String appointmentId, @RequestBody RescheduleAppointmentDTO rescheduleAppointmentDTO) {
		AppointmentResponseDTO appointmentResponseDTO = appointmentService.rescheduleAppointment(appointmentId, rescheduleAppointmentDTO);
		return ResponseEntity.ok(appointmentResponseDTO);
	}
	
	@GetMapping("/getAppointmentDetails/{appointmentId}")
	public ResponseEntity<AppointmentResponseDTO> getAppointmentDetails(@PathVariable String appointmentId) {
		AppointmentResponseDTO appointmentResponseDTO = appointmentService.getAppointmentDetails(appointmentId);
		return ResponseEntity.ok(appointmentResponseDTO);
	}
	
	@GetMapping("/{doctorId}/{date}")
	public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointmentsOfDoctor(@PathVariable String doctorId, @PathVariable String date) {
		List<AppointmentResponseDTO> allAppointmentsOfDoctor = appointmentService.getAllAppointmentsOfDoctor(doctorId, date);
		return ResponseEntity.ok(allAppointmentsOfDoctor);
	}
}
