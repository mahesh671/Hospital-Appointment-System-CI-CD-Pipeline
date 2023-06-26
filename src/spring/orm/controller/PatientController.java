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

import spring.orm.contract.DiagnosticBillDao;
import spring.orm.contract.DocScheduleDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.PatientDao;
import spring.orm.contract.SpecializationDao;
import spring.orm.contract.TestDao;
import spring.orm.contract.UserDao;
import spring.orm.model.PatientSession;
import spring.orm.model.TestModel;
import spring.orm.model.input.FamilyMembersInput;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.services.DoctorOutputService;
import spring.orm.services.PatientFamilyMembersService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private SpecializationDao specdao;

	@Autowired
	private DoctorsDaoTemp docdao;
	@Autowired
	private PatientDao pdao;

	@Autowired
	private UserDao udao;
	@Autowired
	private TestDao tdao;
	@Autowired
	private DiagnosticBillDao dbo;

	@Autowired

	private DoctorOutputService docserv;
	@Autowired
	private DocScheduleDao docschdao;
	PatientSession ps;

	@Autowired
	private PatientFamilyMembersService pfms;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String basePage(@SessionAttribute("patientSession") PatientSession patientSession) {
		// System.out.println(PatientSession);
		return "patient/patient";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(@SessionAttribute("patientSession") PatientSession patientSession, Model model) {
		// Retrieve patient ID from the HttpSession

		int patientId = patientSession.getId();
		System.out.println(patientId);
		// Use the patient ID as needed
		// ...

		return "patient/profile";
	}

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

	@RequestMapping(value = "/gettestbydate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> gettestbydate(@SessionAttribute("patientSession") PatientSession patientSession,
			@RequestParam String date1, @RequestParam String date2, Model model) {

		List<OutputPatientTestReports> lm = dbo.gettestdate(date1, date2, patientSession.getId()); // change with pid
																									// data
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lm));

	}

	@RequestMapping(value = "/myfamily", method = RequestMethod.GET)
	public String viewMyprofile(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		System.out.println("Inside get all fm"); //
		List<FamilyMembersInput> members = pfms.getAllFamilyMembers(patientSession.getId());
		System.out.println(members);
		m.addAttribute("members", members);
		return "patient/myfamily";
	}

	@RequestMapping(value = "/getapptestcards", method = RequestMethod.GET)
	public @ResponseBody List<Object> getapptestcards(
			@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<Object> lo = pdao.getapptestcards(p);
		System.out.println(lo);
		return lo;
	}
///?? why this method and getviewtests
//	@RequestMapping(value = "/getTestDetails", method = RequestMethod.POST)
//	public ResponseEntity<Object> getTestDetails(@SessionAttribute("patientSession") PatientSession patientSession,
//			@RequestParam int testid) {
//
//		List<Object> lo = tdao.getviewtests(testid);
//		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
//
//	}

	@RequestMapping(value = "/getapptests", method = RequestMethod.GET)
	public ResponseEntity<String> getapptests(@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<Object> lo = pdao.getapptests(p);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
		// return "patient/myprofile";
	}

	@RequestMapping(value = "/getapps", method = RequestMethod.GET)
	public ResponseEntity<String> getapps(@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<Object> lo = pdao.getapps(p);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
		// return "patient/myprofile";
	}

	@RequestMapping(value = "/getOutParaGroup", method = RequestMethod.GET)
	public ResponseEntity<String> getParaGroup(@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<ParaGroupOutput> lo = pdao.getParaGroupParaout(p);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
		// return "patient/myprofile";
	}

	@RequestMapping(value = "/getPrescriptionView", method = RequestMethod.GET)
	public @ResponseBody List<PatientlastvisitOutput> getPrescriptionLastVisit(
			@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<PatientlastvisitOutput> lo = pdao.getlastvisit(p);
		return lo;
		// return "patient/myprofile";
	}

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
