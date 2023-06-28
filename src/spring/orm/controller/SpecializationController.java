package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.model.Specialization;

@Controller
public class SpecializationController {

	private SpecializationDAO specializationDAO;

	private DoctorsDAO doctorDAO;

	@Autowired
	public SpecializationController(SpecializationDAO specializationDAO, DoctorsDAO doctorDAO) {
		super();
		this.specializationDAO = specializationDAO;
		this.doctorDAO = doctorDAO;
	}

	@GetMapping("/getspecdetails")
	public String specializationDetails(Model model) {
		// Calls the method to get all the specaializations
		List<Specialization> testModel = specializationDAO.getAllSpecializations();
		// Gives the total doctors count to display on cards
		model.addAttribute("dcount", doctorDAO.findCount());
		// Add specialization list to the model
		model.addAttribute("tests", testModel);
		return "/admin/specialization";

	}

	@RequestMapping(value = "admin/savespec", method = RequestMethod.POST)
	public String saveSpecialization(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {
		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		// Calls the method to add the Specialization
		specializationDAO.addSpecialization(s);
		List<Specialization> testModel = specializationDAO.getAllSpecializations();
		// The Object tm is added to slist to retrieve in the jsp page
		model.addAttribute("slist", testModel);
		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/updatespec", method = RequestMethod.POST)
	public String updateSpecialization(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {
		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		// Calls the method to update the specializations which are edited
		specializationDAO.updateSpec(s);
		// To display all the specializations
		List<Specialization> testModel = specializationDAO.getAllSpecializations();
		// Add the specializations to the model
		model.addAttribute("slist", testModel);
		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/getspec", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getSpecialization(@RequestParam("id") String id) {
		// Get all the specializations by the id
		Specialization s = specializationDAO.getSpecialization(id);
		// Convert the specialization object to JSON
		Gson gson = new Gson();
		String json = gson.toJson(s);
		// Return the JSON response
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/admin/delspec", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteSpecialization(@RequestParam String id) {
		// Delete the specialization from the database
		specializationDAO.deleteSpecialization(id);
		// Return a success response
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}