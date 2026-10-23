package com.flm.appointment.dto;

import java.time.LocalDate;

import com.flm.appointment.constants.Specialization;
import com.flm.appointment.constants.StaffType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDetailsDTO {
	private String staffId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String role;
	private String gender;
	private LocalDate dateOfJoining;
	private int experienceInYears;
	private Specialization specialization;
	private StaffType staffType;
	private boolean isEmployeeActive;
	private boolean canLogin;
	private StaffAddressDTO staffAddressDTO;
}
