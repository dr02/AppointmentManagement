package com.flm.appointment.exception;

public class AppointmentNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4501635235131609258L;
	public AppointmentNotFoundException(String message) {
		super(message);
	}
}
