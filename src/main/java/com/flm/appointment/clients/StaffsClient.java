package com.flm.appointment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flm.appointment.dto.StaffDetailsDTO;

@FeignClient(name = "StaffManagement")
public interface StaffsClient {
	@GetMapping("/{staffId}")
	public ResponseEntity<StaffDetailsDTO> getStaffByStaffId(@PathVariable String staffId);
}
