package spring.orm.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

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

	private boolean isExpired;
	private String otp;

	// private final UserServices userService;

	private final RegistrationService rs;

	@Autowired
	public LoginController(RegistrationService rs) {
		// this.userService = userService;
		this.rs = rs;
	}

	@RequestMapping(value = "/")
	public String dcscreen() {
		return "login/home";
	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String getForgetPage() {
		return "login/forgetPage";
	}

	@RequestMapping(value = { "admin/change", "dcadmin/change", "patient/change" }, method = RequestMethod.GET)
	public String getChangePage() {
		return "login/changepass";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "login/registerPage";
	}

	@RequestMapping(value = "/forgetVal", method = RequestMethod.POST)
	public String sendMail(@RequestParam String uname, Model m) {
		// Code for sending an email with OTP to the user

		MailSend mailSender = new MailSend();
		UserPass user = rs.getUser(uname);
		String userEmail = user.getMail();
		otp = mailSender.generateOTP();
		System.out.println(user.getMail() + " " + user.getUsername());
		mailSender.sendOTPMail(userEmail, otp);

		LocalDateTime expiretime = LocalDateTime.now().plusMinutes(5);
		user.setOtp(otp);
		user.setotptime(expiretime);
		rs.updateUser(user);
		m.addAttribute("user", user);

		return "login/reset";
	}

	@RequestMapping(value = "/passwordset", method = RequestMethod.POST)
	public String otpValidate(@RequestParam String lcpass, @RequestParam String lotp, @RequestParam String uname) {
		// Code for validating the OTP and updating the user's password

		UserPass user = rs.getUser(uname);
		if (LocalDateTime.now().isBefore(user.getotptime())) {
			// userService.updateUser(lcpass, uname);
			if (user.getOtp().equals(lotp)) {
				user.setPassword(lcpass);
				rs.updateUser(user);
			}

			return "login/success";
		} else {
			System.out.println("OTP Expired");
			// Handle OTP expired scenario
			return "login/otpExpired";
		}
	}

	@RequestMapping(value = "/passwordchange", method = RequestMethod.POST)
	public String passwordChange(@RequestParam String lcpass, @RequestParam String opass, @RequestParam String uname) {
		// Code for changing the user's password

		UserPass user = rs.getUser(uname);
		if (user.getPassword().equals(opass)) {
			user.setPassword(lcpass);
			rs.updateUser(user);
			return "login/success";
		} else {
			System.out.println("Wrong Old Password");
			// Handle wrong old password scenario
			return "login/changepass";
		}
	}

	@RequestMapping(value = "/saveregister", method = RequestMethod.POST)
	public String saveRegister(@ModelAttribute RegistrationForm rf, Model model) {
		// Code for registering a new patient

		rs.registerPatient(rf);
		return "redirect:/";
	}

	@RequestMapping(value = "/checkUsernameAvailability", method = RequestMethod.GET)
	@ResponseBody
	public String checkUsernameAvailability(@RequestParam String username) {
		// Code for checking the availability of a username

		System.out.println("in check");
		boolean isUsernameAvailable = rs.isUsernameAvailable(username);

		if (isUsernameAvailable) {
			return "available";
		} else {
			return "taken";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String redirectToDashboard(@RequestParam("uname") String uname, @RequestParam("pass") String pass,
			HttpSession session, Model model) {
		// Code for validating the user's credentials and redirecting to the corresponding dashboard based on the user's
		// role

		UserPass user = rs.getUser(uname);
		if (user != null && user.getPassword().equals(pass)) {
			String role = user.getRole();
			switch (role) {
			case ROLE_ADMINISTRATOR:
				// Redirect to administrator dashboard
				return "redirect:/admin/";
			case ROLE_PATIENT:
				PatientModel patientModel = rs.getPatientDetails(user.getPatient().getPatn_id());
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

					// Set other relevant data in the patient session
					// ...

					// Store patient session in the HttpSession
					session.setAttribute("patientSession", patientSession);
					// Redirect to patient dashboard
					return "redirect:/patient/";
				}
			case ROLE_DIAGNOSTIC_CENTER:
				// Redirect to diagnostic center dashboard
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