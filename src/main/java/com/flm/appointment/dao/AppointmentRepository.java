package com.flm.appointment.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flm.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	@Query(value = "SELECT appointment_id FROM appointment WHERE appointment_id LIKE (?1,'%') ORDER BY appointment_id DESC LIMIT 1", nativeQuery = true)
	String lastUsedAppointmentIdSequence(String currentTimestamp);
	
	List<Appointment> findByDoctorIdAndAppointmentDateBetweenOrderByPatientIdDesc(String doctorId, LocalDate start, LocalDate end);
}
