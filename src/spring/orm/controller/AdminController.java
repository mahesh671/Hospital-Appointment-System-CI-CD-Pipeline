package spring.orm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import spring.orm.contract.AdminDao;
import spring.orm.contract.AppointmentDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.PatientDao;
import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.contract.SpecializationDao;
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

	@Autowired
	private SpecializationDao specdao;

	@Autowired
	private AppointmentDao apdao;
	@Autowired
	private DoctorsDaoTemp docdao;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private UpdateProfileService ups;
	@Autowired
	private PatientDao pdao;
	@Autowired
	private AdminDao ad;

	@Autowired
	private PatientProfileUpdateDAO pcudao;

	// TODO Auto-generated constructor stub
	@RequestMapping(value = "/adminprofitdata", method = RequestMethod.GET)
	public String doctorProfitData(Model model) {
		List<OutputDoctorProfit> tndata = ad.fetchDoctorProfit();
		List<OutputSpecializationProfit> tmdata = ad.fetchSpecializationProfit();

		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);

		return "admin/adminProfitData";
	}

	@RequestMapping(value = "/getpatient")
	public String getPatient() {
		return "admin/patientprofile";
	}

	@RequestMapping(value = "/adminDateWiseProfitdata", method = RequestMethod.GET)
	public String TestDateWiseProfitData(@ModelAttribute AdminFilter fill, Model model) {

		System.out.println(fill.getFrom() + " " + fill.getTo());

		List<OutputDoctorProfit> tndata = ad.fetchDoctorProfit(fill.getFrom(), fill.getTo());
		List<OutputSpecializationProfit> tmdata = ad.fetchSpecializationProfit(fill.getFrom(), fill.getTo());

		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);

		return "admin/adminProfitDataWiseData";
	}

	@RequestMapping(value = "/adminreportspage")
	public String adminreports(Model model) {

		return "admin/adminreports";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String basePage(Model m) {
		m.addAttribute("dcount", docdao.findCount());
		m.addAttribute("App", apdao.getTopapp());
		m.addAttribute("prof", apdao.getTopprof());
		m.addAttribute("cards", apdao.getDashreport());

		return "admin/dashboard";
	}

	@RequestMapping(value = "/specialization", method = RequestMethod.GET)
	public String getSpecialization(Model m) {
		System.out.println("special");
		List<Specialization> slist = specdao.getAllSpec();
		m.addAttribute("slist", slist);
		return "admin/specialization";
	}

	//
	// @RequestMapping(value = "/appointment")
	// public String getNewAppoint(Model m) {
	// m.addAttribute("speclist",specdao.getAllSpec());
	// return "admin/appointment";
	// }

	@RequestMapping(value = "/getpatientprofile", method = RequestMethod.POST, produces = "application/json")

	public String getpatientprofile(@ModelAttribute ProfileUpdateForm ppu,
			@RequestParam CommonsMultipartFile reportsInput) {
		System.out.println(ppu.toString());
		ups.UpdateProfile(ppu, reportsInput);
		apdao.updateAppStatusComp(ppu.getAppnid());
		AppointmentEntity a = apdao.getAppointmentById(ppu.getAppnid());
		pdao.updateLastvisitAndLastAppointment(ppu.getPatientId(), a.getAppn_sch_date().toLocalDateTime().toLocalDate(),
				ppu.getAppnid());

		return "admin/postredirect";
	}

	@RequestMapping(value = "/getpatientid", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getpatientid(Model m) {

		List<Integer> pidlist = pdao.getAllPatientids();

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(pidlist));
	}

	@RequestMapping(value = "/getpatientbyid", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getpatientbyid(@RequestParam int patientId, Model m) {
		System.out.println("speciaty");
		List<Integer> pidlist = pcudao.getAllappnids(patientId);

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(pidlist));
	}

}