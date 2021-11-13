package com.app.emedi.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String getErrorMessage(Exception message) {
		message.printStackTrace();
		return message.getMessage();
	}

}
