package fr.dawan.evalnico.exceptions;

import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.dawan.evalnico.dto.ApiErrorDto;


@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= {TokenException.class})
	protected ResponseEntity<?> handleTokenException(TokenException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(401);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), 
				HttpStatus.UNAUTHORIZED, request);
		
	}
	
	
	
	@ExceptionHandler(value= {IllegalCreateException.class})
	protected ResponseEntity<?> handleIllegalCreateException(IllegalCreateException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(400);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
		
	}
	
	@ExceptionHandler(value= {InvalidDataException.class})
	protected ResponseEntity<?> handleInvalidDataException(InvalidDataException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(400);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
		
	}
	
	@ExceptionHandler(value= {IllegalArgumentException.class})
	protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(400);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
		
	}
	
	@ExceptionHandler(value= {NoDataException.class})
	protected ResponseEntity<?> handleNoDataException(NoDataException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(204);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NO_CONTENT , request);
		
	}
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class})
	protected ResponseEntity<?> handleConstraintViolationException(DataIntegrityViolationException ex, WebRequest request){
		
		ApiErrorDto error = new ApiErrorDto();
		error.setErrorCode(409);
		error.setMessage(ex.getMessage());
		error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT , request);
		
	}
	
	

}
