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
		/*
		 * This method handles exceptions related to missing or invalid request
		 * parameters or session attributes.
		 */
		redirectAttributes.addFlashAttribute("errorMessage", "Session attribute is required.");
		/*
		 * Add a flash attribute called "errorMessage" with the message "Session
		 * attribute is required" to be displayed in the redirected page.
		 */
		return "redirect:/";
		// Redirect the user to the root URL ("/") after handling the exception.
	}
}