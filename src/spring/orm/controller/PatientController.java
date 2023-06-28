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
import spring.orm.contract.DoctorsDAO;
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
	private SpecializationDAO specDAO;

	private DoctorsDAO doctorDAO;

	private PatientDAO patientDAO;

	private UserDAO userDAO;

	private TestDAO testDAO;

	private DiagnosticBillDAO diagnosticBillDAO;

	private DoctorOutputService doctorServiceDAO;

	private DocScheduleDAO doctorScheduleDAO;

	private PatientFamilyMembersService patientFamilyMemberService;

	@Autowired
	public PatientController(SpecializationDAO specDAO, DoctorsDAO doctorDAO, PatientDAO patientDAO, UserDAO userDAO,
			TestDAO testDAO, DiagnosticBillDAO diagnosticBillDAO, DoctorOutputService doctorServiceDAO,
			DocScheduleDAO doctorScheduleDAO, PatientFamilyMembersService patientFamilyMemberService) {
		super();
		this.specDAO = specDAO;
		this.doctorDAO = doctorDAO;
		this.patientDAO = patientDAO;
		this.userDAO = userDAO;
		this.testDAO = testDAO;
		this.diagnosticBillDAO = diagnosticBillDAO;
		this.doctorServiceDAO = doctorServiceDAO;
		this.doctorScheduleDAO = doctorScheduleDAO;
		this.patientFamilyMemberService = patientFamilyMemberService;
	}

	// Handles the base URL to retrieve patient dashboard view
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewPatientScreen(@SessionAttribute("patientSession") PatientSession patientSession) {
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
	public String patientTestReports(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {

		System.out.println("called?");
		List<TestModel> testModel = testDAO.getTests();
		//
		model.addAttribute("tests", testModel);
		//
		List<OutputPatientTestReports> reports = patientDAO.getPatientReportsById(patientSession.getId());
		model.addAttribute("reportUrls", reports);
		// System.out.println(reports.toString());
		System.out.println(reports);

		return "patient/patienttest";
	}

	// Handles the /gettestbydate POST request and retrieves patient test reports within a date range
	@RequestMapping(value = "/gettestbydate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTestByDate(@SessionAttribute("patientSession") PatientSession patientSession,
			@RequestParam String date1, @RequestParam String date2, Model model) {

		List<OutputPatientTestReports> testsList = diagnosticBillDAO.gettestdate(date1, date2, patientSession.getId()); // change
																													// with
																													// pid
		// data
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(testsList));

	}

	// Handles the /myfamily GET request and retrieves the list of family members for the patient

	@RequestMapping(value = "/myfamily", method = RequestMethod.GET)
	public String viewMyFamilyProfile(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		System.out.println("Inside get all fm"); //
		List<FamilyMembersInput> members = patientFamilyMemberService.getAllFamilyMembers(patientSession.getId());
		System.out.println(members);
		m.addAttribute("members", members);
		return "patient/myfamily";
	}

	// Handles the /savefm POST request and saves a family member for the patient
	@RequestMapping(value = "/savefm", method = RequestMethod.POST)
	public @ResponseBody String saveFamilyMember(@ModelAttribute FamilyMembersInput familyMember,
			@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		System.out.println("In SaveFm");
		System.out.println(patientSession.getId());
		int pfmbid = patientFamilyMemberService.addFamilyMember(familyMember, patientSession);
		System.out.println(pfmbid);
		return "patient/myfamily";

	}

}
