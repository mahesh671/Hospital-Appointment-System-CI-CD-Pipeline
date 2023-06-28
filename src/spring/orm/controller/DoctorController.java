package spring.orm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.DocScheduleDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.services.DoctorOutputService;

@Controller

public class DoctorController {

	// Injects the respective class objects

	private DoctorsDAO doctorDAO;

	private SpecializationDAO specializationDAO;

	private DoctorOutputService doctorOutputService;

	private DocScheduleDAO doctorScheduleDAO;

	@RequestMapping(value = "admin/savedoc", method = RequestMethod.POST)
	public String saveSpecialization(@ModelAttribute DoctorInput doctorInputModel,
			@RequestParam CommonsMultipartFile docphoto, Model model) {
		/*
		 * This method maps the "/admin/savedoc" URL to handle a POST request for saving doctor information.
		 */

		int docid = doctorOutputService.addDoc(doctorInputModel, docphoto);

		doctorScheduleDAO.addDoctorSchedule(doctorInputModel, docid); // Add the doctor's schedule information to the
																		// database

		return "admin/redirect"; // Return the name of the view template for the redirection page
	}

	@Autowired
	public DoctorController(DoctorsDAO doctorDAO, SpecializationDAO specializationDAO,
			DoctorOutputService doctorOutputService, DocScheduleDAO doctorScheduleDAO) {
		super();
		this.doctorDAO = doctorDAO;
		this.specializationDAO = specializationDAO;
		this.doctorOutputService = doctorOutputService;
		this.doctorScheduleDAO = doctorScheduleDAO;
	}

	@RequestMapping(value = "admin/doctors", method = RequestMethod.GET)
	public String getDoctorpage(Model m) {
		/*
		 * This method maps the "/admin/doctors" URL to handle a GET request for displaying the doctor page.
		 */
		m.addAttribute("docsche", doctorDAO.getallDocSchedule());
		m.addAttribute("speclist", specializationDAO.getAllSpecializations());
		return "admin/doctor"; // Return the name of the view template for the doctor page
	}

	@RequestMapping(value = "admin/updatedoc", method = RequestMethod.POST)
	public String updateDoctor(@ModelAttribute DoctorUpdateModel doctorUpdate,
			@RequestParam CommonsMultipartFile docphoto, Model model) {
		/*
		 * This method maps the "/admin/updatedoc" URL to handle a POST request for updating a doctor's information.
		 */
		int id = doctorOutputService.updateDoctor(doctorUpdate, docphoto);
		return "redirect:/admin/doctors";
	}

	@RequestMapping(value = "admin/getdoc", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DoctorTemp getDoctor(@RequestParam("id") int id) {
		DoctorTemp docList = doctorDAO.getDoctor(id);
		return docList;
	}

}