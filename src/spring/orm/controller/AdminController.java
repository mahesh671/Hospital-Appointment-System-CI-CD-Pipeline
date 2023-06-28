package spring.orm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

import spring.orm.contract.AdminDAO;
import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.model.Specialization;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.AdminFilter;
import spring.orm.model.input.ProfileUpdateForm;
import spring.orm.model.output.OutputDoctorProfit;
import spring.orm.model.output.OutputSpecializationProfit;
import spring.orm.services.UpdateProfileService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private SpecializationDAO specializationDAO;

	private AppointmentDAO appointmentDAO;

	private DoctorsDAO doctorDAO;

	private HttpSession httpSession;

	private UpdateProfileService updateProfileService;

	private PatientDAO patientDAO;

	private AdminDAO adminDAO;

	public AdminController(SpecializationDAO specdao, AppointmentDAO apdao, DoctorsDAO docdao, HttpSession httpSession,
			UpdateProfileService ups, PatientDAO pdao, AdminDAO ad, PatientProfileUpdateDAO pcudao) {
		super();
		this.specializationDAO = specdao;
		this.appointmentDAO = apdao;
		this.doctorDAO = docdao;
		this.httpSession = httpSession;
		this.updateProfileService = ups;
		this.patientDAO = pdao;
		this.adminDAO = ad;
		this.patientProfileUpdateDAO = pcudao;
	}

	private PatientProfileUpdateDAO patientProfileUpdateDAO;

	// TODO Auto-generated constructor stub
	@RequestMapping(value = "/adminprofitdata", method = RequestMethod.GET)
	public String doctorProfitData(Model model) {
		List<OutputDoctorProfit> doctorProfitList = adminDAO.fetchDoctorProfit();
		List<OutputSpecializationProfit> specializationProfitList = adminDAO.fetchSpecializationProfit();

		model.addAttribute("tndata", doctorProfitList);
		model.addAttribute("tmdata", specializationProfitList);

		return "admin/adminProfitData";
	}

	@RequestMapping(value = "/getpatient")
	public String getPatient() {
		return "admin/patientprofile";
	}

	@RequestMapping(value = "/adminDateWiseProfitdata", method = RequestMethod.GET)
	public String testDateWiseProfitData(@ModelAttribute AdminFilter fill, Model model) {

		System.out.println(fill.getFrom() + " " + fill.getTo());

		List<OutputDoctorProfit> doctorProfitList = adminDAO.fetchDoctorProfit(fill.getFrom(), fill.getTo());
		List<OutputSpecializationProfit> specializationProfitList = adminDAO.fetchSpecializationProfit(fill.getFrom(),
				fill.getTo());

		model.addAttribute("tndata", doctorProfitList);
		model.addAttribute("tmdata", specializationProfitList);

		return "admin/adminProfitDataWiseData";
	}

	@RequestMapping(value = "/adminreportspage")
	public String adminReports(Model model) {

		return "admin/adminreports";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String basePage(Model m) {
		m.addAttribute("dcount", doctorDAO.findCount());
		m.addAttribute("App", appointmentDAO.getTopAppointments());
		m.addAttribute("prof", appointmentDAO.getTopprof());
		m.addAttribute("cards", appointmentDAO.getDashreport());

		return "admin/dashboard";
	}

	@RequestMapping(value = "/specialization", method = RequestMethod.GET)
	public String getSpecialization(Model m) {
		System.out.println("special");
		List<Specialization> slist = specializationDAO.getAllSpecializations();
		m.addAttribute("slist", slist);
		return "admin/specialization";
	}

	@RequestMapping(value = "/getpatientprofile", method = RequestMethod.POST, produces = "application/json")

	public String getPatientProfile(@ModelAttribute ProfileUpdateForm profileUpdateForm,
			@RequestParam CommonsMultipartFile reportsInput) {
		System.out.println(profileUpdateForm.toString());
		updateProfileService.updateProfile(profileUpdateForm, reportsInput);
		appointmentDAO.updateAppStatusComp(profileUpdateForm.getAppnid());
		AppointmentEntity a = appointmentDAO.getAppointmentById(profileUpdateForm.getAppnid());
		patientDAO.updateLastvisitAndLastAppointmentInfo(profileUpdateForm.getPatientId(),
				a.getAppn_sch_date().toLocalDateTime().toLocalDate(), profileUpdateForm.getAppnid());

		return "admin/postredirect";
	}

	@RequestMapping(value = "/getpatientid", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getPatientId(Model m) {

		List<Integer> patientIDList = patientDAO.getPatientIds();

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(patientIDList));
	}

	@RequestMapping(value = "/getpatientbyid", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getPatientById(@RequestParam int patientId, Model m) {
		System.out.println("speciaty");
		List<Integer> patientIdList = patientProfileUpdateDAO.getAllappnids(patientId);

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(patientIdList));
	}

}