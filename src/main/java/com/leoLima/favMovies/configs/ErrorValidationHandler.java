package com.leoLima.favMovies.configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@Slf4j
@RestControllerAdvice
public class ErrorValidationHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDTO> handle(MethodArgumentNotValidException ex) {
		List<FormErrorDTO> formErrorDTOList = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			FormErrorDTO error = new FormErrorDTO(e.getField(), msg);
			formErrorDTOList.add(error);
		});
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw);
//		ex.printStackTrace(pw);
//		log.error(sw.toString());
		return formErrorDTOList;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public FormErrorDTO handle(DataIntegrityViolationException ex) {
		
		
		
		FormErrorDTO formErrorDTO = new FormErrorDTO("id", ex.getCause().toString());
		
		
		
		
		
		return formErrorDTO;
	}
	
}
