package com.flm.appointment.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentManagementGlobalExceptionHandler {
	@ExceptionHandler(AppointmentNotFoundException.class)
	public ResponseEntity<String> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatusCode.valueOf(404));
	}
	
	@ExceptionHandler(AppointmentAlreadyExistsException.class)
	public ResponseEntity<String> handleAppointmentAlreadyExistsException(AppointmentAlreadyExistsException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatusCode.valueOf(409));
	}
}
