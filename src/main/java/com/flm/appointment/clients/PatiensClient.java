package com.flm.appointment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flm.appointment.dto.RegisterPatientResponseDTO;

@FeignClient(name = "PatientManagement")
public interface PatiensClient {
	@GetMapping("/{id}")
	public ResponseEntity<RegisterPatientResponseDTO> getPatientByPatientId(@PathVariable("id") String patientId);
}
