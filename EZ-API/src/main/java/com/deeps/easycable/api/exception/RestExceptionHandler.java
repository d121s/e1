package com.deeps.easycable.api.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import com.nimbusds.jwt.proc.BadJWTException;
//import com.nimbusds.jose.proc.BadJOSEException;
import com.fasterxml.jackson.core.JsonParseException;

import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.exception.SQLGrammarException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LogManager.getLogger();

	/*
	 * Fall Back Handler to Catch All Exception except the once defined below
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "Illegal State Exception";
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.CONFLICT,
				(bodyOfResponse + ":" + ex.getLocalizedMessage()), request.getDescription(false)), HttpStatus.CONFLICT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.
	 * springframework.web.bind.MethodArgumentNotValidException,
	 * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST,
				"Error occurred . Reason :" + errors.stream().map(i -> i.toString()).collect(Collectors.joining(",")),
				request.getDescription(false)), HttpStatus.BAD_REQUEST);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * ResponseEntityExceptionHandler#handleMissingServletRequestParameter(org.
	 * springframework.web.bind.MissingServletRequestParameterException,
	 * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String error = ex.getParameterName() + " parameter is missing";
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST, error, request.getDescription(false)),
				HttpStatus.BAD_REQUEST);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * ResponseEntityExceptionHandler#handleHttpMediaTypeNotSupported(org.
	 * springframework.web.HttpMediaTypeNotSupportedException,
	 * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" Media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.toString(),
				request.getDescription(false)), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.NOT_FOUND, error, request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.BAD_REQUEST, message, request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	/*
	 * This exception reports the result of constraint violations
	 */
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST,
				errors.stream().map(i -> i.toString()).collect(Collectors.joining(",")), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ SQLGrammarException.class,QueryException.class, HibernateException.class })
	public ResponseEntity<Object> handleSQLGrammerException(Exception ex, WebRequest request) {
		LOG.error("SQL Exception Handled :"+ex);
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST, "Invalid Request to DataBase",
				request.getDescription(false)), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ExceptionResponse> handleNullException(RuntimeException ex, WebRequest request) {
		LOG.error("A Null pointer Exception is Handled");
		String error = "Expected Data is null or Empty";

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, request.getDescription(false)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MandatoryDataMissingException.class)
	protected ResponseEntity<ExceptionResponse> handleMissingParamsException(RuntimeException ex, WebRequest request) {
		LOG.error("Raised Exception for missing Params");

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request.getDescription(false)),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(NotValidRequest.class)
	protected ResponseEntity<ExceptionResponse> handleInvalidParamsDataException(RuntimeException ex,
			WebRequest request) {
		LOG.error("Raised Exception for Invalid Params value");

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UIPermissonError.class)
	protected ResponseEntity<ExceptionResponse> handleUIPermissonException(RuntimeException ex, WebRequest request) {
		LOG.error("Raised Exception for incorrect user action");

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request.getDescription(false)),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	protected ResponseEntity<ExceptionResponse> handleIndexOutOfBoundException(RuntimeException ex,
			WebRequest request) {
		LOG.error("Index Out of Bound Exception is Handled");
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Expected Data unavailable",
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionResponse> handleIOException(RuntimeException ex, WebRequest request) {
		LOG.error("IOException handler executed");

		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Service not Reachable",
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex, WebRequest request) {
		LOG.error("NotFoundException is Handled");

		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ExceptionResponse> handleGremlinServiceException(RuntimeException ex, WebRequest request) {
		LOG.error("CustomException is Handled");
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ExceptionResponse> handleJsonParseException(RuntimeException ex, WebRequest request) {
		LOG.error("JsonParseException handler executed");
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "JsonParser Exception",
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(AlreadyExistsException.class)
	protected ResponseEntity<ExceptionResponse> handleAlreadyExistsExceptionException(RuntimeException ex,
			WebRequest request) {
		LOG.error(" Already Exists Exception is Handled");
		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.IM_USED, ex.getMessage(), request.getDescription(false)),
				HttpStatus.IM_USED);
	}/*

	@ExceptionHandler({ BadJWTException.class, BadJOSEException.class })
	protected ResponseEntity<ExceptionResponse> handleExpiredJwtToken(RuntimeException ex, WebRequest request) {
		LOG.error(" Expired JWT" + ex);
		return new ResponseEntity<>(
				new ExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getDescription(false)),
				HttpStatus.UNAUTHORIZED);
	}*/

	@ExceptionHandler(UnAuthorizedTenantException.class)
	protected ResponseEntity<ExceptionResponse> handleUnAuthorizedTenantException(RuntimeException ex,
			WebRequest request) {
		LOG.error(" UnAuthorized Tenant " + ex);
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNAUTHORIZED,
				"UnAzuthorized Tenant" + ex.getMessage(), request.getDescription(false)), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(SyncFailureException.class)
	protected ResponseEntity<ExceptionResponse> handleSyncFailureException(RuntimeException ex, WebRequest request) {
		LOG.error("A SyncFailureException is Handled");
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"Sync Failed" + ex.getMessage(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}