package spring.orm.controller;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class SessionAttributeExceptionHandler {

	@ExceptionHandler({ MissingServletRequestParameterException.class, ServletRequestBindingException.class })
	public String handleSessionAttributeException(Exception ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Session attribute is required.");
		return "redirect:/";
	}
}
