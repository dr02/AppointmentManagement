package com.flm.appointment.dto;

import java.time.LocalDate;

import com.flm.appointment.constants.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientResponseDTO {
	private String patientId;
	private String patientName;
	private Gender gender;
	private String patientEmail;
	private String patientPhoneNumber;
	private LocalDate dateOfBirth;
	private PatientAddressResponseDTO patientAddress;
}
