package spring.orm.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:user-roles.properties")

public class LoginController {
	@Value("${role.admin}")
	private String ROLE_ADMINISTRATOR;

	@Value("${role.patient}")
	private String ROLE_PATIENT;

	@Value("${role.diagnostic_center}")
	private String ROLE_DIAGNOSTIC_CENTER;

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

		logger.info("Landing screen loaded");
		return "login/home";// Returns the name of the view template for the login home page
	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String getForgetPage() {
		// This method maps the "/forget" URL to display the forget password page

		logger.info("Displaying forget password page");
		return "login/forgetPage";
		// Returns the name of the view template for the forget password page

	}

	@RequestMapping(value = { "admin/change", "dcadmin/change", "patient/change" }, method = RequestMethod.GET)
	public String getChangePage() {
		/*
		 * This method maps multiple URLs ("/admin/change", "/dcadmin/change", "/patient/change") to display the change
		 * password page
		 */
		logger.info("Displaying change password page");
		return "login/changepass";
		// Returns the name of the view template for the change password page
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		// This method maps the "/register" URL to display the registration page.
		logger.info("Displaying registration page");
		return "login/registerPage";// Returns the name of the view template for the registration page.
	}

	@RequestMapping(value = "/forgetVal", method = RequestMethod.POST)
	public String sendMail(@RequestParam String uname, Model m) {
		/*
		 * This method maps the "/forgetVal" URL to handle a POST request for sending an email with OTP to the user.
		 * 
		 * Code for sending an email with OTP to the user
		 */
		logger.info("Processing forget password request for username: {}", uname);
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
		logger.info("Validating OTP for username: {}", userName);
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
		logger.info("Processing password change request for username: {}", userName);
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
		try {
			// Log the registration of a new patient
			logger.info("Registering a new patient");

			// Register the new patient using the registration service
			registrationService.registerPatient(registrationForm);

			// Redirect to the home page after successful registration
			return "redirect:/";
		} catch (Exception e) {
			// An error occurred during the registration process
			logger.error("An error occurred while registering a new patient", e);

			// Add an error message to the model to display to the user
			model.addAttribute("error", "An error occurred during registration. Please try again later.");

			// Return the registration form view
			return "registrationForm";
		}
	}

	@RequestMapping(value = "/checkUsernameAvailability", method = RequestMethod.GET)
	@ResponseBody
	public String checkUsernameAvailability(@RequestParam String username) {
		try {
			// Log the username availability check
			logger.info("Checking username availability for: {}", username);

			// Check the availability of the username using the registration service
			boolean isUsernameAvailable = registrationService.isUsernameAvailable(username);

			if (isUsernameAvailable) {
				// Username is available
				return "available";
			} else {
				// Username is taken
				return "taken";
			}
		} catch (Exception e) {
			// An error occurred during the username availability check
			logger.error("An error occurred while checking username availability for: {}", username, e);
			return "error";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String redirectToDashboard(@ModelAttribute("userpass") UserPass userpass, HttpSession session, Model model) {
		String userName = userpass.getUsername();
		String password = userpass.getPassword();

		try {
			// Log the login request
			logger.info("Processing login request for username: {}", userName);

			// Retrieve user information from the registration service
			UserPass user = registrationService.getUser(userName);

			// Check if the user exists and the password is correct
			if (user != null && user.getPassword().equals(password)) {
				String role = user.getRole();

				// Handle login for the Administrator role
				if (ROLE_ADMINISTRATOR.equals(role)) {
					// Log the successful login and redirect to the administrator dashboard
					logger.info("User '{}' logged in as Administrator. Redirecting to administrator dashboard.",
							userName);
					return "redirect:/admin/";
				}

				// Handle login for the Patient role
				else if (ROLE_PATIENT.equals(role)) {
					// Retrieve patient details from the registration service
					PatientModel patientModel = registrationService.getPatientDetails(user.getPatient().getPatn_id());

					// Create the patient session and store it in the HttpSession
					PatientSession patientSession = registrationService.createPatientSession(user, patientModel);
					registrationService.storePatientSession(session, patientSession);

					// Log the successful login and redirect to the patient dashboard
					logger.info("User '{}' logged in as Patient. Redirecting to patient dashboard.", userName);
					return "redirect:/patient/";
				}

				// Handle login for the Diagnostic Center role
				else if (ROLE_DIAGNOSTIC_CENTER.equals(role)) {
					// Log the successful login and redirect to the diagnostic center dashboard
					logger.info(
							"User '{}' logged in as Diagnostic Center Admin. Redirecting to diagnostic center dashboard.",
							userName);
					return "redirect:/dcadmin/";
				}
			}

			// Login failed: incorrect username or password
			logger.warn("Login failed for username: {}. Incorrect username or password entered.", userName);
			model.addAttribute("error", "Incorrect username or password");
		} catch (Exception e) {
			// An error occurred during the login process
			logger.error("An error occurred during login for username: {}", userName, e);
			model.addAttribute("error", "An error occurred during login. Please try again later.");
		}

		// Redirect to the home page if login was unsuccessful
		return "login/home";
	}

	@RequestMapping(value = "patient/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// Clear the session
		session.removeAttribute("patientSession");
		logger.info("Logging out patient");
		// Redirect to the login page
		return "redirect:/";
	}
}