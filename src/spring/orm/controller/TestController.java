package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import spring.orm.contract.TestDAO;
import spring.orm.model.TestModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;

@Controller
public class TestController {

	@Autowired
	private TestDAO testDAO;

	// Page mapping for "dcadmin/dcpatients"
	@RequestMapping(value = "dcadmin/dcpatients")
	public String dcPatientsPage() {
		return "dcadmin/DCPatients";
	}

	// Request mapping for getting test details
	@RequestMapping(value = "dcadmin/gettestdetails", method = RequestMethod.GET)
	public String testDetails(Model model) {
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testDAO.getTests();
		model.addAttribute("tests", testModel);
		return "dcadmin/testspage";

	}

	// Request mapping for deleting a test
	@RequestMapping(value = "dcadmin/deltest", method = RequestMethod.POST)
	public @ResponseBody String deleteTest(@RequestParam int test_id, Model model) {
		// Calls the method deltest in TestDao for delete operation
		testDAO.deleteTest(test_id);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testDAO.getTests();
		model.addAttribute("tests", testModel);
		return "dcadmin/testspage";
	}

	// Request mapping for saving a test
	@RequestMapping(value = "dcadmin/savetest", method = RequestMethod.POST)
	public String saveTest(@ModelAttribute TestInputModel t, Model model) {
		// Calls the method savetest to save the tests to DB
		testDAO.saveTest(t);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testDAO.getTests();
		model.addAttribute("tests", testModel);
		return "redirect:./gettestdetails";
	}

	// Request mapping for updating a test
	@RequestMapping(value = "dcadmin/updatetest", method = RequestMethod.POST)
	public String updateTest(@ModelAttribute TestModel t, Model model) {
		// Calls the method updatetest to update the edited details
		testDAO.updateTest(t);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testDAO.getTests();
		model.addAttribute("tests", testModel);
		return "redirect:gettestdetails";
	}

	// Request mapping for getting specific test details
	@RequestMapping(value = "dcadmin/gettest", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getSpecialization(@RequestParam int id) {
		// Calls the method gettestbyid to update the details
		TestModel tests = testDAO.getTestById(id);
		// Returns a response entity with a JSON representation of the object 's'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(tests));
	}

	/// Where we are using this call??

	// Request mapping for getting all test-patient details
	@RequestMapping(value = "dcadmin/getalltestpatientdetails", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> getAllTestPatientDetails(@RequestParam int test,
			@RequestParam String date1, @RequestParam String date2, Model model) {
		// Calls the method to get patients details by testwise and between dates
		List<testsPatientsModel> testsPatientsModel = testDAO.getAllTestPatientDetails(test, date1, date2);
		return testsPatientsModel;
	}

	// Request mapping for getting all tests
	@RequestMapping(value = "dcadmin/getalltests", method = RequestMethod.GET)
	public ResponseEntity<String> getalltests(Model model) {
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testModel = testDAO.getTests();
		// Returns a response entity with a JSON representation of the object 'tm'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(testModel));
	}

	// Request mapping for getting all test-patients
	@RequestMapping(value = "dcadmin/getalltestspatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody List<testsPatientsModel> getalltestpatients(Model model) {
		// Calls the method to get all the patients who booked that test
		List<testsPatientsModel> testsPatientsList = testDAO.getAllTestPatients();
		return testsPatientsList;
	}

	// Request mapping for getting test-wise patients
	@RequestMapping(value = "dcadmin/gettestwisepatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> gettestwisepatients(@RequestParam int test, Model model) {
		// Calls the method to get the patients according to testwise
		List<testsPatientsModel> testsPatientsList = testDAO.getTestWisePatients(test);
		return testsPatientsList;
	}

}