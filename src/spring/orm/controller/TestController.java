package spring.orm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.google.gson.Gson;

import spring.orm.contract.DAO.TestDAO;
import spring.orm.model.TestModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;
import spring.orm.services.TestServices;

@Controller
public class TestController {

	private TestDAO testDAO;

	private TestServices testServices;

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	public TestController(TestDAO testDAO, TestServices testServices) {
		super();
		this.testDAO = testDAO;
		this.testServices = testServices;
	}

	// Page mapping for "dcadmin/dcpatients"
	@RequestMapping(value = "dcadmin/dcpatients")
	public String dcPatientsPage() {
		logger.info("Entered to the DCPatients page");
		return "dcadmin/DCPatients";
	}

	// Request mapping for getting test details
	@RequestMapping(value = "dcadmin/gettestdetails", method = RequestMethod.GET)
	public String testDetails(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		// Calls the method gettests in TestDao to get tests
		int pageSize = 3; // Number of records to display per page
		logger.info("Entered to gettestdetails method");
		List<TestModel> testModel = testServices.getTests();
		int size = testModel.size();
		int totalPages = (int) Math.ceil(size / (double) pageSize);

		// Calculate the start and end indexes for the current page
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, size);

		List<TestModel> candidatesOnPage = testModel.subList(startIndex, endIndex);

		model.addAttribute("tests", candidatesOnPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		logger.info("Returns all the tests with details in list");
		logger.info(testModel.toString());
		// model.addAttribute("tests", testModel);
		return "dcadmin/testspage";

	}

	// Request mapping for deleting a test
	@RequestMapping(value = "dcadmin/deltest", method = RequestMethod.POST)
	public @ResponseBody String deleteTest(@RequestParam int test_id, Model model) {
		// Calls the method deltest in TestDao for delete operation
		logger.info("Entered to deletetest method");
		testServices.deleteTest(test_id);
		logger.info("The record has been deleted");
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testServices.getTests();
		logger.info(testModel.toString());
		model.addAttribute("tests", testModel);
		return "dcadmin/testspage";
	}

	// Request mapping for saving a test
	@RequestMapping(value = "dcadmin/savetest", method = RequestMethod.POST)
	public String saveTest(@ModelAttribute TestInputModel t, Model model) {
		// Calls the method savetest to save the tests to DB
		logger.info("Entered the savetest method");
		testDAO.saveTest(t);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testServices.getTests();
		logger.info(testModel.toString());
		model.addAttribute("tests", testModel);
		return "redirect:./gettestdetails";
	}

	// Request mapping for updating a test
	@RequestMapping(value = "dcadmin/updatetest", method = RequestMethod.POST)
	public String updateTest(@ModelAttribute TestModel t, Model model) {
		// Calls the method updatetest to update the edited details
		logger.info("Entered the Update test method");
		testServices.updateTest(t);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testServices.getTests();
		logger.info(testModel.toString());
		model.addAttribute("tests", testModel);
		return "redirect:gettestdetails";
	}

	// Request mapping for getting specific test details
	@RequestMapping(value = "dcadmin/gettest", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getSpecialization(@RequestParam int id) {
		// Calls the method gettestbyid to update the details
		TestModel tests = testServices.getTestById(id);
		logger.info("{}", tests.getTest_id());
		// Returns a response entity with a JSON representation of the object 's'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(tests));
	}

	// Request mapping for getting all test-patient details
	@RequestMapping(value = "dcadmin/getalltestpatientdetails", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> getAllTestPatientDetails(@RequestParam int test,
			@RequestParam String date1, @RequestParam String date2, Model model) {
		// Calls the method to get patients details by testwise and between dates
		List<testsPatientsModel> testsPatientsModel = testDAO.getAllTestPatientDetails(test, date1, date2);
		logger.info(testsPatientsModel.toString());
		return testsPatientsModel;
	}

	// Request mapping for getting all tests
	@RequestMapping(value = "dcadmin/getalltests", method = RequestMethod.GET)
	public ResponseEntity<String> getalltests(Model model) {
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testServices.getTests();
		logger.info(testModel.toString());
		// Returns a response entity with a JSON representation of the object 'tm'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(testModel));
	}

	// Request mapping for getting all test-patients
	@RequestMapping(value = "dcadmin/getalltestspatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody List<testsPatientsModel> getalltestpatients(Model model) {
		// Calls the method to get all the patients who booked that test
		List<testsPatientsModel> testsPatientsList = testDAO.getAllTestPatients();
		logger.info(testsPatientsList.toString());
		return testsPatientsList;
	}

	// Request mapping for getting test-wise patients
	@RequestMapping(value = "dcadmin/gettestwisepatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> gettestwisepatients(@RequestParam int test, Model model) {
		// Calls the method to get the patients according to testwise
		List<testsPatientsModel> testsPatientsList = testDAO.getTestWisePatients(test);
		logger.info(testsPatientsList.toString());
		return testsPatientsList;
	}

}