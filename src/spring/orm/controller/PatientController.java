package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import spring.orm.contract.DiagnosticBillDAO;
import spring.orm.contract.DocScheduleDAO;
import spring.orm.contract.DoctorsDAOTemp;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.contract.TestDAO;
import spring.orm.contract.UserDAO;
import spring.orm.model.PatientSession;
import spring.orm.model.TestModel;
import spring.orm.model.input.FamilyMembersInput;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.services.DoctorOutputService;
import spring.orm.services.PatientFamilyMembersService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private SpecializationDAO specdao;

	@Autowired
	private DoctorsDAOTemp docdao;
	@Autowired
	private PatientDAO pdao;

	@Autowired
	private UserDAO udao;
	@Autowired
	private TestDAO tdao;
	@Autowired
	private DiagnosticBillDAO dbo;

	@Autowired

	private DoctorOutputService docserv;
	@Autowired
	private DocScheduleDAO docschdao;
	PatientSession ps;

	@Autowired
	private PatientFamilyMembersService pfms;

	// Handles the base URL to retrieve patient dashboard view
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String basePage(@SessionAttribute("patientSession") PatientSession patientSession) {
		// System.out.println(PatientSession);
		return "patient/patient";
	}

	// Handles the /profile GET request and retrieves the patient profile information

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		// Retrieve patient ID from the HttpSession

		int patientId = patientSession.getId();
		System.out.println(patientId);
		// Use the patient ID as needed
		// ...

		return "patient/profile";
	}

	// Handles the /patienttest GET request and retrieves the list of conducted tests and patient test reports
	@RequestMapping(value = "/patienttest", method = RequestMethod.GET)
	public String ptest(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {

		System.out.println("called?");
		List<TestModel> tm = tdao.gettests();
		//
		model.addAttribute("tests", tm);
		//
		List<OutputPatientTestReports> reports = pdao.getPatientReports(patientSession.getId());
		model.addAttribute("reportUrls", reports);
		// System.out.println(reports.toString());
		System.out.println(reports);

		return "patient/patienttest";
	}

	// Handles the /gettestbydate POST request and retrieves patient test reports within a date range
	@RequestMapping(value = "/gettestbydate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> gettestbydate(@SessionAttribute("patientSession") PatientSession patientSession,
			@RequestParam String date1, @RequestParam String date2, Model model) {

		List<OutputPatientTestReports> lm = dbo.gettestdate(date1, date2, patientSession.getId()); // change with pid
																									// data
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lm));

	}

	// Handles the /myfamily GET request and retrieves the list of family members for the patient

	@RequestMapping(value = "/myfamily", method = RequestMethod.GET)
	public String viewMyprofile(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		System.out.println("Inside get all fm"); //
		List<FamilyMembersInput> members = pfms.getAllFamilyMembers(patientSession.getId());
		System.out.println(members);
		m.addAttribute("members", members);
		return "patient/myfamily";
	}

	// Handles the /savefm POST request and saves a family member for the patient
	@RequestMapping(value = "/savefm", method = RequestMethod.POST)
	public @ResponseBody String savefm(@ModelAttribute FamilyMembersInput fm,
			@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		System.out.println("In SaveFm");
		System.out.println(patientSession.getId());
		int pfmbid = pfms.addfm(fm, patientSession);
		System.out.println(pfmbid);
		return "patient/myfamily";

	}

}
