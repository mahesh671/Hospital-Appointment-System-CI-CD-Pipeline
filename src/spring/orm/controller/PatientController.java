package spring.orm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import spring.orm.contract.DAO.DiagnosticBillDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.services.PatientFamilyMembersServices;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.input.FamilyMembersInput;
import spring.orm.model.input.ProfileInputModel;
import spring.orm.model.output.OutputPatientTestReports;

@Controller
@RequestMapping("/patient")
public class PatientController {

	private PatientDAO patientDAO;

	private DiagnosticBillDAO diagnosticBillDAO;

	private PatientFamilyMembersServices patientFamilyMemberService;

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	public PatientController(PatientDAO patientDAO, DiagnosticBillDAO diagnosticBillDAO,
			PatientFamilyMembersServices patientFamilyMemberService) {
		super();
		this.patientDAO = patientDAO;
		this.diagnosticBillDAO = diagnosticBillDAO;
		this.patientFamilyMemberService = patientFamilyMemberService;
	}

	// Handles the base URL to retrieve patient dashboard view
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewPatientScreen(@SessionAttribute("patientSession") PatientSession patientSession) {
		logger.info("Entered into viewPatientScreen");

		logger.info("Fetching the Patient Screen");
		return "patient/patient";
	}

	// Handles the /profile GET request and retrieves the patient profile information

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		logger.info("Entered into viewProfile");
		// Retrieve patient ID from the HttpSession

		int pid = patientSession.getId();
		logger.info("fetched the patient id");

		PatientModel patientModel = patientDAO.getPatientById(pid);
		logger.info("fetched the patient information through patient id");

		model.addAttribute("patient", patientModel);
		logger.info("Inserte the data into the model");

		logger.info("Fetching the Patient Profile Screen");
		return "patient/profile";
	}

	// Handles the /patienttest GET request and retrieves the list of conducted tests and patient test reports
	@RequestMapping(value = "/patienttest", method = RequestMethod.GET)
	public String patientTestReports(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		logger.info("Entered into patientTestReports");

		// fetches the patient test reports
		List<OutputPatientTestReports> reports = patientDAO.getPatientReportsById(patientSession.getId());
		logger.info("fetched the patient test reports");

		// inserts the patient test reports data into the model
		model.addAttribute("reportUrls", reports);
		logger.info("inserted the patient test reports data into the model");

		logger.info("Fetching the Patient Tests Screen");
		return "patient/patienttest";
	}

	// Handles the /gettestbydate POST request and retrieves patient test reports within a date range
	@RequestMapping(value = "/gettestbydate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTestByDate(@SessionAttribute("patientSession") PatientSession patientSession,
			@RequestParam String date1, @RequestParam String date2, Model model) {
		logger.info("Entered into getTestByDate");

		// fetches the patient test reports between the dates
		List<OutputPatientTestReports> testsList = diagnosticBillDAO.gettestdate(date1, date2, patientSession.getId());
		logger.info("fetched the patient test reports");

		logger.info("Fetching the Patient Tests data by date");
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(testsList));

	}

	// Handles the /myfamily GET request and retrieves the list of family members for the patient
	@RequestMapping(value = "/myfamily", method = RequestMethod.GET)
	public String viewMyFamilyProfile(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		logger.info("Entered into viewMyFamilyProfile");

		// fetches the family members data of the patient
		List<FamilyMembersInput> members = patientFamilyMemberService.getAllFamilyMembers(patientSession.getId());
		logger.info("fetched the family mmbers data of the patient");

		// inserts the patient family members data into the model
		m.addAttribute("members", members);
		logger.info("Inserted the family mmbers data of the patient into the model");

		logger.info("Fetching the myfamily Screen");
		return "patient/myfamily";
	}

	// Handles the /savefm POST request and saves a family member for the patient
	@RequestMapping(value = "/savefm", method = RequestMethod.POST)
	public @ResponseBody String saveFamilyMember(@ModelAttribute FamilyMembersInput familyMember,
			@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		logger.info("Entered into saveFamilyMember");

		// Saving the family member information into database and getting his/her patient id
		int patientFamilyMemberId = patientFamilyMemberService.addFamilyMember(familyMember, patientSession.getId());
		logger.info("Saved the family member information into database");

		logger.info("Fetching the myfamily Screen");
		return "patient/myfamily";

	}

	@RequestMapping(value = "/saveSettings", method = RequestMethod.POST)
	public String saveSettings(@ModelAttribute ProfileInputModel patientFamilyInputModel,
			@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		logger.info("Entered into saveSettings");
		int pid = patientSession.getId();
		logger.info("fetched the patient id");

		PatientModel patientModel = patientDAO.getPatientById(pid);
		logger.info("fetched the patient information through patient id");

		model.addAttribute("patient", patientModel);
		logger.info("Inserte the data into the model");
		patientDAO.updatePatientData(patientFamilyInputModel, patientSession);
		return "patient/profile";
	}

	@RequestMapping(value = "/editFamily", method = RequestMethod.GET)
	public String editPatientFamilyMember(@RequestParam int id,
			@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		logger.info("Entered into editPatientFamilyMember");

		FamilyMembersInput familyData = patientFamilyMemberService.getFamilyMemberInfo(patientSession.getId(), id);
		logger.info("fetched the family data");

		model.addAttribute("family", familyData);
		logger.info("inserted the data to the model");

		logger.info("fetching the profile page");
		return "patient/myprofile";

	}

	@RequestMapping(value = "/saveFamilyMember", method = RequestMethod.POST)
	public String savePatientFamilyMember(@ModelAttribute FamilyMembersInput fam, Model model,
			@SessionAttribute("patientSession") PatientSession patientSession) {
		logger.info("Entered into savePatientFamilyMember");
		patientFamilyMemberService.saveFamilyMemberInfo(fam);

		List<FamilyMembersInput> members = patientFamilyMemberService.getAllFamilyMembers(patientSession.getId());
		logger.info("fetched the family mmbers data of the patient");

		// inserts the patient family members data into the model
		model.addAttribute("members", members);
		logger.info("Inserted the family mmbers data of the patient into the model");

		logger.info("Fetching the myfamily Screen");

		return "patient/myfamily";
	}

	@RequestMapping(value = "/deleteFamilyMember", method = RequestMethod.GET)
	public String deleteFamilyMember(@RequestParam int id, Model model,
			@SessionAttribute("patientSession") PatientSession patientSession) {
		logger.info("Entered into deleteFamilyMember");
		patientFamilyMemberService.deleteFamilyMember(id);

		List<FamilyMembersInput> members = patientFamilyMemberService.getAllFamilyMembers(patientSession.getId());
		logger.info("fetched the family mmbers data of the patient");

		// inserts the patient family members data into the model
		model.addAttribute("members", members);
		logger.info("Inserted the family mmbers data of the patient into the model");

		logger.info("Fetching the myfamily Screen");

		return "patient/myfamily";
	}

}
