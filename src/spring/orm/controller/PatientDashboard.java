package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import spring.orm.model.PatientSession;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.services.PatientServices;

@Controller
@RequestMapping("/patient")
public class PatientDashboard {

	@Autowired
	private PatientServices ps;

	// Display all the appointments and test details
	@RequestMapping(value = "/getapptests", method = RequestMethod.GET)
	public ResponseEntity<String> getapptests(@SessionAttribute("patientSession") PatientSession patientSession) {
		// Extract the patient ID from the session attribute
		int patientId = patientSession.getId();

		// Retrieve the appointments tests details for the patient using the patient ID
		List<Object> appointmentTests = ps.getapptests(patientId);

		// Return the response with the appointment tests in JSON format
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(appointmentTests));
	}

	// Get all Appointment Details
	@RequestMapping(value = "/getapps", method = RequestMethod.GET)
	public ResponseEntity<String> getapps(@SessionAttribute("patientSession") PatientSession patientSession) {
		// Extract the patient ID from the session attribute
		int patientId = patientSession.getId();

		// Retrieve the appointments details for the patient using the patient ID
		List<Object> appointments = ps.getapps(patientId);

		// Return the response with the appointments in JSON format
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(appointments));
	}

	// Get all Parameters data for respective patient
	@RequestMapping(value = "/getOutParaGroup", method = RequestMethod.GET)
	public ResponseEntity<String> getParaGroup(@SessionAttribute("patientSession") PatientSession patientSession) {
		// Extract the patient ID from the session attribute
		int patientId = patientSession.getId();

		// Retrieve the parameter groups for the patient using the patient ID
		List<ParaGroupOutput> paraGroups = ps.getParaGroupParaout(patientId);

		// Return the response with the parameter groups in JSON format
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(paraGroups));
	}

	@RequestMapping(value = "/getPrescriptionView", method = RequestMethod.GET)
	public @ResponseBody List<PatientlastvisitOutput> getPrescriptionLastVisit(
			@SessionAttribute("patientSession") PatientSession patientSession) {
		// Extract the patient ID from the session attribute
		int patientId = patientSession.getId();

		// Retrieve the last visit information for the patient using the patient ID
		List<PatientlastvisitOutput> lastVisits = ps.getlastvisit(patientId);

		// Return the last visit information
		return lastVisits;
	}

	@RequestMapping(value = "/getapptestcards", method = RequestMethod.GET)
	public @ResponseBody List<Object> getapptestcards(
			@SessionAttribute("patientSession") PatientSession patientSession) {
		// Extract the patient ID from the session attribute
		int patientId = patientSession.getId();

		// Retrieve the appointments and test details for the patient using the patient ID
		List<Object> testCards = ps.getapptestcards(patientId);

		// Return the test cards
		return testCards;
	}

}
