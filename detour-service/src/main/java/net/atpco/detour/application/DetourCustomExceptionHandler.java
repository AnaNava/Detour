package net.atpco.detour.application;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.AppException;

@Slf4j
@ControllerAdvice
public class DetourCustomExceptionHandler extends ResponseEntityExceptionHandler {

	// When a required parameter is missing from the request
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}

	// When the required parameters do not match expected type
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}

	// When the required parameters do not match format expected
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		String bodyOfResponse = ex.getMessage();
		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, "Bad input provided", bodyOfResponse);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}

	@ExceptionHandler(value = { AppException.class, RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		String bodyOfResponse = ex.getMessage();
		ErrorResponse apiError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot complete request at this time",
				bodyOfResponse);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		log.error(ex.getMessage(), ex);

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
		}
		String bodyOfResponse = ex.getMessage();
		ErrorResponse apiError = new ErrorResponse(status, "Cannot complete request",
				bodyOfResponse);
		return new ResponseEntity<>(apiError, headers, status);
	}

}
