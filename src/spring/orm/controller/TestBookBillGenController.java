package spring.orm.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import spring.orm.contract.DAO.DiagnosticBillDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.DAO.TestDAO;
import spring.orm.contract.services.TestService;
import spring.orm.model.TestModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.patientsoutputmodel;
import spring.orm.model.output.testsCategoriesModel;
import spring.orm.util.MailSendHelper;

@Controller
public class TestBookBillGenController {

	private TestService testService;
	private DiagnosticBillDAO diagnosticBillDAO;
	private TestDAO testDAO;
	private HttpSession httpSession;
	private PatientDAO patientDAO;
	private static final Logger logger = LoggerFactory.getLogger(TestBookBillGenController.class);
	@Autowired
	public TestBookBillGenController(TestService testService, DiagnosticBillDAO diagnosticBillDAO, TestDAO testDAO,
			HttpSession httpSession, PatientDAO patientDAO) {
		super();
		this.testService = testService;
		this.diagnosticBillDAO = diagnosticBillDAO;
		this.testDAO = testDAO;
		this.httpSession = httpSession;
		this.patientDAO = patientDAO;
	}

	/**
	 * Displays the view for new Test Booking.
	 */
	@RequestMapping("/dcadmin/booktest")
	public String newTestBook(Model model) {
		// Return the view name for displaying test categories
		logger.info("Landed In new Test Booking Page");
		return "dcadmin/booktest";
	}

	/**
	 * Displays the view for viewing booked tests.
	 */
	@RequestMapping("/dcadmin/bookedtest")
	public String getBookedTests(Model model) {
		// Return the view name for displaying booked tests
		logger.info("Landed In Booked Tests Page");
		return "dcadmin/bookedtest";
	}

	/**
	 * Retrieves the list of test categories.
	 */
	@GetMapping("/dcadmin/gettestcat")
	public @ResponseBody ResponseEntity<String> getCategories(Model model) {
		// Get list of test categories from TestDAO
		logger.info("Loading Categories");
		List<testsCategoriesModel> categories = testDAO.getCategories();
		System.out.println("Retrieved test categories: " + categories);
		// Return the categories as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(categories));
	}

	/**
	 * Retrieves the list of all patients.
	 */
	@GetMapping("/dcadmin/getpatients")
	public @ResponseBody ResponseEntity<String> getPatients(Model model) {
		// Get list of patients from PatientDAO
		logger.info("Loading Patients");
		List<patientsoutputmodel> patients = testDAO.getPatients();
		System.out.println("Retrieved patients: " + patients);
		// Return the patients as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(patients));
	}

	/**
	 * Retrieves the tests based on selected category.
	 */
	@RequestMapping(value = "/dcadmin/gettestbycat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTestByCategory(@RequestParam String cat, Model model) {
		// Get tests of selected category from TestDAO
		
		List<TestModel> tests = testDAO.getTestByCategory(cat);
		logger.info("Loading respective tests of selected category"+" "+cat+":"+tests);
		// Return the tests as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(tests));
	}

	/**
	 * Books a test and stores the information in the database.
	 */
	@RequestMapping(value = "/dcadmin/bookdctest", method = RequestMethod.POST)
	public ResponseEntity<String> bookTest(Model model, @ModelAttribute BillInputModel bi) throws Exception {
		try {
			// Book the test by storing the information in the database
			diagnosticBillDAO.bookDcTest(bi);
			logger.info("Test booked successfully."+" "+bi.toString());
		} catch (Exception e) {
			// Return an error response if the test is already booked
			logger.info("Test Already Booked.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Test Already Booked");
		}
		return null;
	}

	/**
	 * Retrieves the price of a test.
	 */
	@RequestMapping(value = "/dcadmin/gettestprice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTestPrice(@RequestParam int test, Model model) {
		logger.info("Retrieving Test Price.");
		// Get the price of a test based on the provided test ID from TestDAO
		Object price = testDAO.getSelectedTestPrice(test);
		
		logger.info("Retrieved price for test " + test + ": " + price);
		// Return the price as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(price));
	}
	/**
	 * Stores the booked tests in the database.
	 */
	@RequestMapping(value = "/dcadmin/storedb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeToDatabase(Model model, @RequestParam int patient) {
		// Store the booked tests for provided patient , information in the database
		int billId = diagnosticBillDAO.storeToDatabase(patient);
		logger.info("Stored patient with ID " + patient + " in database. Bill ID: " + billId);
		// Return the bill ID of the tests as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(billId));
	}

	/**
	 * Displays the booked tests for a patient.
	 */
	@RequestMapping(value = "/dcadmin/displaytests", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> displayTests(Model model, @RequestParam int patient) {
		// Get the list of booked tests for the specified patient from DiagnosticBillDAO
		List<Object> bookedTests = diagnosticBillDAO.getTotalBills(patient);
		bookedTests.remove(bookedTests.size() - 1);
		
		logger.info("Retrieved booked tests for patient " + patient );
		// Return the booked tests as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(bookedTests));
	}

	/**
	 * Cancels a test for a patient.
	 */
	@RequestMapping(value = "/dcadmin/cancelTest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void cancelTest(Model model, @RequestParam int patient, @RequestParam String test) {
		// Cancel the specified test for the given patient in the database
		diagnosticBillDAO.cancelTest(patient, test);
		logger.info("Cancelled test " + test + " for patient " + patient);
	}

	/**
	 * Retrieves the total bills for a patient to make Payment.
	 */
	@RequestMapping(value = "/dcadmin/totalbills", method = RequestMethod.GET)
	public ResponseEntity<String> totalBills(@RequestParam int patient, Model model) {
		
		// Get the total bills for the specified patient from DiagnosticBillDAO
		List<Object> bills = diagnosticBillDAO.getTotalBills(patient);
		
		logger.info("Retrieved total bills for patient " + patient );
		// Return the total bills as a JSON response
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(bills));
	}

	/**
	 * Sends an email with test booking and payment information.
	 */
	@RequestMapping(value = "/dcadmin/mailsend2", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> mailSend(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String email, @RequestParam String content) {
		try {
			// Send the email with the provided parameters using MailSendHelper class
			MailSendHelper.sendEmailTestBooking(request, response, email, content);
			logger.info("Email sent successfully to " + email);
		} catch (MessagingException e) {
			logger.info("Exception occurred while sending email.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid Mail");
		} catch (ServletException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while sending mail");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while sending mail");
		}
		return null;
	}
	
	
}
