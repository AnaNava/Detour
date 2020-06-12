package net.atpco.detour.application;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
	private final Date timestamp = new Date();
	private final HttpStatus statusCode;
	private final String errorMsg;
	private final String errorDetails;
}
