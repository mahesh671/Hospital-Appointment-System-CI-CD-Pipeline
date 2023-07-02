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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import spring.orm.contract.DAO.DoctorsDAO;
import spring.orm.contract.DAO.SpecializationDAO;
import spring.orm.model.Specialization;

@Controller
public class SpecializationController {

	private SpecializationDAO specializationDAO;

	private DoctorsDAO doctorDAO;

	private static final Logger logger = LoggerFactory.getLogger(SpecializationController.class);

	@Autowired
	public SpecializationController(SpecializationDAO specializationDAO, DoctorsDAO doctorDAO) {
		super();
		this.specializationDAO = specializationDAO;
		this.doctorDAO = doctorDAO;
	}

	@GetMapping("/getspecdetails")
	public String specializationDetails(Model model) {
		// Calls the method to get all the specaializations
		logger.info("Entered to getSPecialization details method");
		List<Specialization> specializationModel = specializationDAO.getAllSpecializations();
		logger.info("{}", ((Specialization) specializationModel).getId());
		// Gives the total doctors count to display on cards
		model.addAttribute("dcount", doctorDAO.findCount());
		// Add specialization list to the model
		model.addAttribute("tests", specializationModel);
		return "/admin/specialization";

	}

	@RequestMapping(value = "admin/savespec", method = RequestMethod.POST)
	public String saveSpecialization(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {
		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		// Calls the method to add the Specialization
		specializationDAO.addSpecialization(s);
		logger.info("The addition of specialization has done");
		List<Specialization> specializationModel = specializationDAO.getAllSpecializations();
		logger.info("{}", ((Specialization) specializationModel).getId());
		// The Object tm is added to slist to retrieve in the jsp page
		model.addAttribute("slist", specializationModel);
		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/updatespec", method = RequestMethod.POST)
	public String updateSpecialization(@RequestParam String idInput, @RequestParam String titleInput,
			@RequestParam String descriptionInput, Model model) {
		logger.info("Entered the update specialization method");
		Specialization s = new Specialization(idInput, titleInput, descriptionInput);
		// Calls the method to update the specializations which are edited
		specializationDAO.updateSpec(s);
		// To display all the specializations
		List<Specialization> specializationModel = specializationDAO.getAllSpecializations();
		logger.info("{}", specializationModel.toString());
		// Add the specializations to the model
		model.addAttribute("slist", specializationModel);
		return "redirect:/admin/specialization";
	}

	@RequestMapping(value = "admin/getspec", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getSpecialization(@RequestParam("id") String id) {
		// Get all the specializations by the id
		Specialization specialization = specializationDAO.getSpecialization(id);
		logger.info("{}", specialization.getId());
		// Convert the specialization object to JSON
		Gson gson = new Gson();
		String json = gson.toJson(specialization);
		// Return the JSON response
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/admin/delspec", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteSpecialization(@RequestParam String id) {
		logger.info("Entered the delete specialization method");
		// Delete the specialization from the database
		specializationDAO.deleteSpecialization(id);
		logger.info("The record has been delete");
		// Return a success response
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}