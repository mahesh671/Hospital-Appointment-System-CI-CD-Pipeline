package spring.orm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.orm.contract.DAO.AdminDAO;
import spring.orm.contract.DAO.AppointmentDAO;
import spring.orm.contract.DAO.DoctorsDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.DAO.PatientProfileUpdateDAO;
import spring.orm.contract.DAO.SpecializationDAO;
import spring.orm.contract.services.UpdateProfileServices;
import spring.orm.model.Specialization;
import spring.orm.model.input.AdminFilter;
import spring.orm.model.output.OutputDoctorProfit;
import spring.orm.model.output.OutputSpecializationProfit;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private SpecializationDAO specializationDAO;

	private AppointmentDAO appointmentDAO;

	private DoctorsDAO doctorDAO;

	private HttpSession httpSession;

	private UpdateProfileServices updateProfileService;

	private PatientDAO patientDAO;

	private AdminDAO adminDAO;
	
	private PatientProfileUpdateDAO patientProfileUpdateDAO;
	

	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);
	
@Autowired    //dependency injection, it eliminates manual configuations
	public AdminController(SpecializationDAO specdao, AppointmentDAO apdao, DoctorsDAO docdao, HttpSession httpSession,
			UpdateProfileServices ups, PatientDAO pdao, AdminDAO ad, PatientProfileUpdateDAO pcudao) {
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

	

	@RequestMapping(value = "/adminprofitdata", method = RequestMethod.GET)
	public String doctorProfitData(Model model) {
		logger.info("To calculate doctor profit in Admin's revenue reports page");
		List<OutputDoctorProfit> doctorProfitList = adminDAO.fetchDoctorProfit(); //fetched from DAO
		List<OutputSpecializationProfit> specializationProfitList = adminDAO.fetchSpecializationProfit();
//To calculate doctor profit in Admin's revenue reports page 
		model.addAttribute("tndata", doctorProfitList);
		model.addAttribute("tmdata", specializationProfitList); 
		//fetched data added to model
		logger.info("Doctors Profits List is fetched");
		logger.info("Specialization Profits List is fetched");
		return "admin/adminProfitData";
	}

	@RequestMapping(value = "/getpatient")
	public String getPatient() {
		//to get the patientsprofile
		return "admin/patientprofile";
	}

	@RequestMapping(value = "/adminDateWiseProfitdata", method = RequestMethod.GET)
	public String testDateWiseProfitData(@ModelAttribute AdminFilter fill, Model model) {
		logger.info("To calculate profit based on date in Admin's revenue reports page");
//from & to dates are taken as part of filters
		System.out.println(fill.getFrom() + " " + fill.getTo());
		logger.info("from date : {}", fill.getFrom());
		logger.info("to date : {}", fill.getTo());
		// Doctors & Specialization profits are displayed between selected dates
		List<OutputDoctorProfit> doctorProfitList = adminDAO.fetchDoctorProfit(fill.getFrom(), fill.getTo());
		List<OutputSpecializationProfit> specializationProfitList = adminDAO.fetchSpecializationProfit(fill.getFrom(),
				fill.getTo());

		model.addAttribute("tndata", doctorProfitList);
		model.addAttribute("tmdata", specializationProfitList);
		//Applied filters are added to model
		logger.info("doctors profit based on selected dates : {}", doctorProfitList);
		logger.info("specialization profit based on selected dates : {}", specializationProfitList);
		return "admin/adminProfitDataWiseData";
	}

	@RequestMapping(value = "/adminreportspage")
	public String adminReports(Model model) {
		// display of reports at admin side
		logger.info("Reports at Admin");
		return "admin/adminreports";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String basePage(Model model) {
		logger.info("Admin dashboard values");
		//Aggregate functions & output models are used to get the following data
		model.addAttribute("dcount", doctorDAO.findCount());
		model.addAttribute("App", appointmentDAO.getTopAppointments());
		model.addAttribute("prof", appointmentDAO.getTopprof());
		model.addAttribute("cards", appointmentDAO.getDashreport());
		// The data is to be displayed on the cards in admin dashboard
		return "admin/dashboard";
	}

	@RequestMapping(value = "/specialization", method = RequestMethod.GET)
	public String getSpecialization(Model model) {
		logger.info("getting Specialization in admin");
		// to get the Specialiation details
		List<Specialization> slist = specializationDAO.getAllSpecializations();
		//A list format is obtained
		model.addAttribute("slist", slist);
		logger.info("Specialization List : {}", slist);
		return "admin/specialization";
	}

}