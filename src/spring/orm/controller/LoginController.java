package spring.orm.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.UserPass;
import spring.orm.model.input.RegistrationForm;
import spring.orm.services.RegistrationService;
import spring.orm.util.MailSend;

@Controller

public class LoginController {
	private static final String ROLE_ADMINISTRATOR = "ADMIN";
	private static final String ROLE_PATIENT = "Patient";
	private static final String ROLE_DIAGNOSTIC_CENTER = "DCADMIN";

	private String otp;

	private final RegistrationService registrationService;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	public LoginController(RegistrationService rs) {
		/*
		 * Constructor for the LoginController class that takes a RegistrationService object as a parameter
		 */
		this.registrationService = rs;
		// Assign the passed RegistrationService object to the 'rs' instance variable
	}

	@RequestMapping(value = "/")
	public String dcScreen() {
		// This method maps the root URL ("/") to display the login home page
		String retrievedCatalinaHome = System.getProperty("catalina.home");
		System.out.println(retrievedCatalinaHome);

		return "login/home";// Returns the name of the view template for the login home page
	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String getForgetPage() {
		// This method maps the "/forget" URL to display the forget password page

		return "login/forgetPage";
		// Returns the name of the view template for the forget password page

	}

	@RequestMapping(value = { "admin/change", "dcadmin/change", "patient/change" }, method = RequestMethod.GET)
	public String getChangePage() {
		/*
		 * This method maps multiple URLs ("/admin/change", "/dcadmin/change", "/patient/change") to display the change
		 * password page
		 */
		return "login/changepass";
		// Returns the name of the view template for the change password page
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		// This method maps the "/register" URL to display the registration page.

		return "login/registerPage";// Returns the name of the view template for the registration page.
	}

	@RequestMapping(value = "/forgetVal", method = RequestMethod.POST)
	public String sendMail(@RequestParam String uname, Model m) {
		/*
		 * This method maps the "/forgetVal" URL to handle a POST request for sending an email with OTP to the user.
		 * 
		 * Code for sending an email with OTP to the user
		 */
		MailSend mailSender = new MailSend(); // Create an instance of the MailSend class to send the email
		UserPass user = registrationService.getUser(uname); // Retrieve the user information based on the provided
															// username
		String userEmail = user.getMail(); // Get the email address of the user
		otp = mailSender.generateOTP(); // Generate an OTP (one-time password)
		System.out.println(user.getMail() + " " + user.getUsername()); // Print the user's email and username for
																		// debugging purposes
		mailSender.sendOTPMail(userEmail, otp); // Send the OTP via email to the user's email address

		LocalDateTime expiretime = LocalDateTime.now().plusMinutes(5); // Set the expiration time of the OTP to 5
																		// minutes from the current time
		user.setOtp(otp); // Set the generated OTP for the user
		user.setotptime(expiretime); // Set the expiration time of the OTP for the user
		registrationService.updateUser(user); // Update the user information in the registration service
		m.addAttribute("user", user); // Add the user object to the model attribute for further use in the view

		return "login/reset"; // Return the name of the view template for the password reset page
	}

	@RequestMapping(value = "/passwordset", method = RequestMethod.POST)
	public String otpValidate(@RequestParam String newPassword, @RequestParam String enteredOTP,
			@RequestParam String userName) {
		/*
		 * This method maps the "/passwordset" URL to handle a POST request for OTP validation and password update.
		 */
		UserPass user = registrationService.getUser(userName); // Retrieve the user information based on the provided
																// username
		if (LocalDateTime.now().isBefore(user.getotptime())) {
			// Check if the current time is before the OTP expiration time

			if (user.getOtp().equals(enteredOTP)) {
				// Validate if the entered OTP matches the stored OTP for the user

				user.setPassword(newPassword); // Update the user's password with the new password
				registrationService.updateUser(user); // Update the user information in the registration service
			}

			return "login/success"; // Return the name of the view template for the password reset success page
		} else {
			return "login/otpExpired"; // Return the name of the view template for the OTP expiration page
		}
	}

	@RequestMapping(value = { "admin/passwordchange", "admin/passwordchange",
			"admin/passwordchange" }, method = RequestMethod.POST)
	public String passwordChange(@RequestParam String newPassword, @RequestParam String oldPassword,
			@RequestParam String userName) {
		/*
		 * This method maps the "/passwordchange" URL to handle a POST request for password change.
		 */
		UserPass user = registrationService.getUser(userName); // Retrieve the user information based on the provided
																// username
		if (user.getPassword().equals(oldPassword)) {
			// Check if the entered old password matches the stored password for the user

			user.setPassword(newPassword); // Update the user's password with the new password
			registrationService.updateUser(user); // Update the user information in the registration service

			return "login/success"; // Return the name of the view template for the password change success page
		} else {
			System.out.println("Wrong Old Password");
			// Handle wrong old password scenario (e.g., display error message, log the
			// error, etc.)

			return "login/changepass"; // Return the name of the view template for the password change page
		}
	}

	@RequestMapping(value = "/saveregister", method = RequestMethod.POST)
	public String saveRegister(@ModelAttribute RegistrationForm registrationForm, Model model) {
		// Code for registering a new patient

		registrationService.registerPatient(registrationForm);
		return "redirect:/";
	}

	@RequestMapping(value = "/checkUsernameAvailability", method = RequestMethod.GET)
	@ResponseBody
	public String checkUsernameAvailability(@RequestParam String username) {
		// Code for checking the availability of a username
		boolean isUsernameAvailable = registrationService.isUsernameAvailable(username);

		if (isUsernameAvailable) {
			return "available";
		} else {
			return "taken";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String redirectToDashboard(@RequestParam("uname") String userName, @RequestParam("pass") String password,
			HttpSession session, Model model) {
		/*
		 * Code for validating the user's credentials and redirecting to the corresponding dashboard based on the user's
		 * role
		 */

		UserPass user = registrationService.getUser(userName);
		if (user != null && user.getPassword().equals(password)) {
			String role = user.getRole();
			switch (role) {
			case ROLE_ADMINISTRATOR:
				// Redirect to administrator dashboard
				ThreadContext.put("logFile", "adminLogFile");
				logger.info("Entered into Admin Page Successfully");
				ThreadContext.remove("logFile");
				return "redirect:/admin/";
			case ROLE_PATIENT:
				PatientModel patientModel = registrationService.getPatientDetails(user.getPatient().getPatn_id());
				if (patientModel != null) {

					// Create and store patient session
					PatientSession patientSession = new PatientSession();
					patientSession.setId(patientModel.getPatn_id());
					patientSession.setUsername(user.getUsername());
					patientSession.setName(patientModel.getPatn_name());
					patientSession.setAge(patientModel.getPatn_age());
					patientSession.setGender(patientModel.getPatn_gender().charAt(0));
					patientSession.setAccessPatientId(patientModel.getAccessPatientId());
					patientSession.setBloodGroup(patientModel.getPatn_bgroup());
					patientSession.setRegistrationDate(patientModel.getPatn_rdate());
					patientSession.setLastVisitDate(patientModel.getPatn_lastvisit());
					patientSession.setEmail(user.getMail());
					// patientSession.setLastAppointmentId(patientModel.getPatn_lastapp_id());

					/*
					 * Set other relevant data in the patient session
					 * 
					 * 
					 * Store patient session in the HttpSession
					 */
					ThreadContext.put("logFile", "patientLogFile");
					logger.info("Entered into Patient Page Successfully");
					ThreadContext.remove("logFile");
					session.setAttribute("patientSession", patientSession);
					/*
					 * Redirect to patient dashboard
					 */
					return "redirect:/patient/";
				}
			case ROLE_DIAGNOSTIC_CENTER:
				// Redirect to diagnostic center dashboard

				ThreadContext.put("logFile", "dcadminLogFile");
				logger.info("Entered into DcAdmin Page Successfully");
				logger.warn("Warning");
				ThreadContext.remove("logFile");
				return "redirect:/dcadmin/";
			}
		}
		model.addAttribute("error", "Incorrect username or password");
		return "login/home";
	}

	@RequestMapping(value = "patient/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// Clear the session
		session.removeAttribute("patientSession");
		// Redirect to the login page
		return "redirect:/";
	}
}