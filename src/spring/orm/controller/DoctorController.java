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

	@Autowired
	private DoctorsDaoTemp docdao;

	@Autowired
	private SpecializationDao specdao;

	@Autowired
	private DoctorOutputService docoutput;

	@Autowired
	private DocScheduleDao docschdao;

	@RequestMapping(value = "admin/savedoc", method = RequestMethod.POST)
	public String savespec(@ModelAttribute DoctorInput d, @RequestParam CommonsMultipartFile docphoto,
			Model model) {

		System.out.println(d);
		int docid = docoutput.addDoc(d, docphoto);
		docschdao.addDoctorSchedule(d, docid);
		return "admin/redirect";
	}

	@RequestMapping(value = "admin/doctors", method = RequestMethod.GET)
	public String getDoctorpage(Model m) {

		m.addAttribute("docsche", docdao.getallDocSchedule());
		m.addAttribute("speclist", specdao.getAllSpec());

		return "admin/doctor";
	}

	@RequestMapping(value = "admin/updatedoc", method = RequestMethod.POST)
	public String updatedoc(@ModelAttribute DoctorUpdateModel d, @RequestParam CommonsMultipartFile docphoto,
			Model model) {
	
		System.out.println("controller" + d);
		int id = docoutput.updateDoc(d, docphoto);


		return "redirect:/admin/doctors";
	}

	@RequestMapping(value = "admin/getdoc", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DoctorTemp getdoc(@RequestParam("id") int id) {
		System.out.println("Entered");
		DoctorTemp s = docdao.getdoc(id);

		

		return s;
	}

}