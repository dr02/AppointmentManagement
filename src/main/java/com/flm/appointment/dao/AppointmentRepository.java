package com.flm.appointment.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flm.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	@Query(value = "SELECT appointment_id FROM appointment WHERE appointment_id LIKE (?1,'%') ORDER BY appointment_id DESC LIMIT 1", nativeQuery = true)
	String lastUsedAppointmentIdSequence(String currentTimestamp);
	
	List<Appointment> findByDoctorIdAndAppointmentDateBetweenOrderByPatientIdDesc(String doctorId, LocalDate start, LocalDate end);
	
	@Query("SELECT COUNT(*) > 0 as conflict_count FROM Appointment a WHERE a.doctorId=:doctorId AND a.appointmentDate=:appointmentDate AND :endTime <= a.startTime OR :startTime >= a.endTime")
	boolean isConflict(String doctorId, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime);
	
	boolean existsByPatientIdAndAppointmentDateAndStartTimeLessThanAndEndTimeGreaterThan(String patientId, LocalDate appointmentDate, LocalTime endTime, LocalTime startTime);
	
	@Query("SELECT a FROM Appointment a WHERE a.doctorId = :doctorId : AND a.appointmentDate >= CURDATE() AND a.startTime > CURTIME() ORDER BY a.appointmentDate, a.startTime ASC")
	List<Appointment> getFutureAppointments(@Param("doctorId") Long doctorId);
	
	List<Appointment> findAllByAppointmentDate(LocalDate appointmentDate);
	
	List<Appointment> findAllByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);
}
