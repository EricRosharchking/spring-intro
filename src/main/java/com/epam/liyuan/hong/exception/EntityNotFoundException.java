package com.epam.liyuan.hong.exception;

public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673227846663160292L;

	public EntityNotFoundException() {
		super("The Entity you are trying access does not exist");
	}

//	public EntityNotFoundException(String message) {
//		super(message);
//	}
//
//	public EntityNotFoundException(String message, Throwable cause) {
//		super(message, cause);
//	}
//
//	public EntityNotFoundException(Throwable cause) {
//		super(cause);
//	}
//
//	protected EntityNotFoundException(String message, Throwable cause, boolean enableSuppression,
//			boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}
}
