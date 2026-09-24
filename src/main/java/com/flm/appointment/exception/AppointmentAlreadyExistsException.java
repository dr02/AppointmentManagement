package com.flm.appointment.exception;

public class AppointmentAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3699513939323826538L;
	
	public AppointmentAlreadyExistsException(String message) {
		super(message);
	}
}
