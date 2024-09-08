package br.edu.locadora.validation;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandlingForViolationsControllerAdvice {
	/*
	 * O metodo "onMethodArgumentNotValidexcetion" deve retornar um
	 * ValidationErrorResponse(Resposta do erro de validação) quando recebe um erro do
	 * tipo MethodArgumentNotValidException(Exceção do Argumento Inválido Do Método)
	*/
	ValidationErrorResponse onMethodArgumentNotValidexcetion(MethodArgumentNotValidException e) {
		
		//Instanciação do ValidationErrorResponse error
		ValidationErrorResponse error = new ValidationErrorResponse();
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			/*
			 * ERRADO:
			 * Para cada e.getBindingResult().getFieldErrors() error recebe uma nova violação
			 * cuja teve os parametros field e defaultMessage são obtidos de fielError(Campo de erro)
			*/
			error.setViolation(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		
		System.out.println("Violação do metodo de validação");
		return null;
	}
	ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		
		//Para cada violação do tipo ConstraintViolation em e.getConstraintViolations() algo acontece
		for (ConstraintViolation violation: e.getConstraintViolations()) {
			/*
			 * error recebe uma nova violation
			 * Essa nova violation recebe como os parametro fieldname e message
			 * violation.getPropertyPath().toString() e violation.getMessage() respectivamente
			*/ 
			error.setViolation(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
		}
		System.out.print("Violação de constraint");
		
		return error;
	}
}
