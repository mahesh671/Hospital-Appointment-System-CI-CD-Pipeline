package spring.orm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

import spring.orm.contract.DocScheduleDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.SpecializationDao;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.services.DoctorOutputService;

@Controller

public class DoctorController {

	//Injects the respective class objects
	@Autowired
	private DoctorsDaoTemp docdao;

	@Autowired
	private SpecializationDao specdao;

	@Autowired
	private DoctorOutputService docoutput;

	@Autowired
	private DocScheduleDao docschdao;

	@RequestMapping(value = "admin/savedoc", method = RequestMethod.POST)
	public String savespec(@ModelAttribute DoctorInput d, @RequestParam CommonsMultipartFile docphoto, Model model) {
		/*
		 * This method maps the "/admin/savedoc" URL to handle a POST request for saving
		 * doctor information.
		 */

		int docid = docoutput.addDoc(d, docphoto);

		docschdao.addDoctorSchedule(d, docid); // Add the doctor's schedule information to the database

		return "admin/redirect"; // Return the name of the view template for the redirection page
	}

	@RequestMapping(value = "admin/doctors", method = RequestMethod.GET)
	public String getDoctorpage(Model m) {
		/*
		 * This method maps the "/admin/doctors" URL to handle a GET request for
		 * displaying the doctor page.
		 */
		m.addAttribute("docsche", docdao.getallDocSchedule());
		m.addAttribute("speclist", specdao.getAllSpec());
		return "admin/doctor"; // Return the name of the view template for the doctor page
	}

	@RequestMapping(value = "admin/updatedoc", method = RequestMethod.POST)
	public String updatedoc(@ModelAttribute DoctorUpdateModel d, @RequestParam CommonsMultipartFile docphoto,
			Model model) {
		/*
		 * This method maps the "/admin/updatedoc" URL to handle a POST request for
		 * updating a doctor's information.
		 */
		int id = docoutput.updateDoc(d, docphoto);
		return "redirect:/admin/doctors";
	}

	@RequestMapping(value = "admin/getdoc", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DoctorTemp getdoc(@RequestParam("id") int id) {
		DoctorTemp s = docdao.getdoc(id);
		return s;
	}

}