package spring.orm.controller;

// import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.SpecializationDao;
// import antlr.collections.List;
import spring.orm.model.Specialization;

@Controller
public class SpecializationController {

	@Autowired
	private SpecializationDao specdao;
	@Autowired
	private DoctorsDaoTemp docdao;

	@GetMapping("/getspecdetails")
	public String specdetails(Model model) {
		System.out.println("Inside testdetails");
		List<Specialization> tm = specdao.getAllSpec();
		System.out.println("Inside testdetails");
		model.addAttribute("dcount", docdao.findCount());

		model.addAttribute("tests", tm);
		return "/admin/specialization";

	}

	@RequestMapping(value = "admin/savespec", method = RequestMethod.POST)
	public String savespec(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {

		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		System.out.println("entered savespec");
		specdao.addSpec(s);
		List<Specialization> tm = specdao.getAllSpec();
		System.out.println("Inside testdetails");

		model.addAttribute("slist", tm);

		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/updatespec", method = RequestMethod.POST)
	public String updatespec(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {

		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		System.out.println("entered savespec");
		specdao.updateSpec(s);
		List<Specialization> tm = specdao.getAllSpec();
		System.out.println("Inside testdetails");

		model.addAttribute("slist", tm);

		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/getspec", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getspec(@RequestParam("id") String id) {
		System.out.println("Entered");
		Specialization s = specdao.getSpecialization(id);

		Gson gson = new Gson();
		String json = gson.toJson(s);

		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/admin/delspec", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delspec(@RequestParam String id) {

		specdao.delSpec(id);
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}