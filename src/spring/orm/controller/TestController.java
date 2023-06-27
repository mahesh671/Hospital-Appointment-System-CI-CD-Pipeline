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
	private TestDAO testdao;
	
	
	// Page mapping for "dcadmin/dcpatients"
	@RequestMapping(value = "dcadmin/dcpatients")
	public String dcpatPage() {
		return "dcadmin/DCPatients";
	}
	
	
	 // Request mapping for getting test details
	@RequestMapping(value = "dcadmin/gettestdetails", method = RequestMethod.GET)
	public String testdetails(Model model) {
		// Calls the method gettests in TestDao to get tests
		List<TestModel> tm = testdao.gettests();
		model.addAttribute("tests", tm);
		return "dcadmin/testspage";

	}
	
	
	  // Request mapping for deleting a test
	@RequestMapping(value = "dcadmin/deltest", method = RequestMethod.POST)
	public @ResponseBody String deltest(@RequestParam int test_id, Model model) {
		//Calls the method deltest in TestDao for delete operation
		testdao.deltest(test_id);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> tm = testdao.gettests();
		model.addAttribute("tests", tm);
        return "dcadmin/testspage";
	}


    // Request mapping for saving a test
	@RequestMapping(value = "dcadmin/savetest", method = RequestMethod.POST)
	public String savetest(@ModelAttribute TestInputModel t, Model model) {
		//Calls the method savetest to save the tests to DB
		testdao.savetest(t);
		// Calls the method gettests in TestDao to get tests
		List<TestModel> tm = testdao.gettests();
		model.addAttribute("tests", tm);
		return "redirect:./gettestdetails";
	}
	
	

	 // Request mapping for updating a test
	@RequestMapping(value = "dcadmin/updatetest", method = RequestMethod.POST)
	public String updatetest(@ModelAttribute TestModel t, Model model) {
		//Calls the method updatetest to update the edited details
       testdao.updatetest(t);
        // Calls the method gettests in TestDao to get tests
       List<TestModel> tm = testdao.gettests();
       model.addAttribute("tests", tm);
       return "redirect:gettestdetails";
	}

	
	
	 // Request mapping for getting specific test details
	@RequestMapping(value = "dcadmin/gettest", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> getspec(@RequestParam int id) {
		//Calls the method gettestbyid to update the details
		TestModel s = testdao.gettestbyid(id);
		// Returns a response entity with a JSON representation of the object 's'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(s));
	}
	
	
	
	///Where we are using this call??
	
	
	 // Request mapping for getting all test-patient details
//	@RequestMapping(value = "dcadmin/getalltestpatientdetails", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public @ResponseBody List<testsPatientsModel> getalltestpatientdetails(@RequestParam int test,
//			@RequestParam String date1, @RequestParam String date2, Model model) {
//        //Calls the method to get patients details by testwise and between dates
//		List<testsPatientsModel> testspatientsmodel = testdao.getalltestpatientdetails(test, date1, date2);
//	    return testspatientsmodel;
//	}
//	
	
	// Request mapping for getting all tests
	@RequestMapping(value = "dcadmin/getalltests", method = RequestMethod.GET)
	public ResponseEntity<String> getalltests(Model model) {
		// Calls the method gettests in TestDao to get tests
		List<TestModel> testmodel = testdao.gettests();
		// Returns a response entity with a JSON representation of the object 'tm'
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(testmodel));
	}
	
	
	
	 // Request mapping for getting all test-patients
	@RequestMapping(value = "dcadmin/getalltestspatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody List<testsPatientsModel> getalltestpatients(Model model) {
      //Calls the method to get all the patients who booked that test
		List<testsPatientsModel> tpm = testdao.getalltestpatients();
		return tpm;
	}
	
	
	
	  // Request mapping for getting test-wise patients
	@RequestMapping(value = "dcadmin/gettestwisepatients", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> gettestwisepatients(@RequestParam int test, Model model) {
       //Calls the method to get the patients according to testwise
		List<testsPatientsModel> tpm = testdao.gettestwisepatients(test);
        return tpm;
	}
	
	@RequestMapping(value = "dcadmin/getalltestpatientdetails", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody List<testsPatientsModel> getalltestpatientdetails(@RequestParam int test,
			@RequestParam String date1, @RequestParam String date2, Model model) {

		List<testsPatientsModel> tpm = testdao.getalltestpatientdetails(test, date1, date2);
		// System.out.println(tpm.toString());
		return tpm;
	}

}