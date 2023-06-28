package spring.orm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.ProfileUpdateForm;
import spring.orm.model.output.PrescriptionOutputmodel;
import spring.orm.services.UpdateProfileService;

@Controller
public class PostConsultUpdateController {

	private PatientDAO patientDAO;

	private PatientProfileUpdateDAO profileUpdateDAO;

	private HttpSession httpSession;

	private UpdateProfileService updateProfileService;

	private AppointmentDAO appointmentDAO;

	private PatientProfileUpdateDAO patientProfileUpdateDAO;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	public PostConsultUpdateController(PatientDAO patientDAO, PatientProfileUpdateDAO profileUpdateDAO,
			HttpSession httpSession, UpdateProfileService updateProfileService, AppointmentDAO appointmentDAO,
			PatientProfileUpdateDAO patientProfileUpdateDAO) {
		super();
		this.patientDAO = patientDAO;
		this.profileUpdateDAO = profileUpdateDAO;
		this.httpSession = httpSession;
		this.updateProfileService = updateProfileService;
		this.appointmentDAO = appointmentDAO;
		this.patientProfileUpdateDAO = patientProfileUpdateDAO;
	}

	@RequestMapping(value = "patient/getallPrescription", method = RequestMethod.GET)
	public String getAllPrescription(Model model, @SessionAttribute("patientSession") PatientSession patientSession) {
		logger.info("Getting all prescription values in pcu");
		int p = patientSession.getId();// to maintain same session id when logged in
		List<PrescriptionOutputmodel> prescriptionList = profileUpdateDAO.getallPrescription(p); // merged
																									// PatientMedicalProfile
																									// &
																									// DiagnosticBillModel
																									// attributes

		model.addAttribute("pres", prescriptionList);
		System.out.println(prescriptionList.toString());
		logger.info("This is the following prescriptionList {}", prescriptionList.toString());
		return "patient/patpresdisplay"; // reflects at patients page , which displays in the format of table, it also
											// includes prescription image
	}

	@RequestMapping(value = "admin/getpatientprofile", method = RequestMethod.POST, produces = "application/json")

	public String getPatientProfile(@ModelAttribute ProfileUpdateForm profileUpdateForm,
			@RequestParam CommonsMultipartFile reportsInput) {
		logger.info("Here is the profileUpdateForm in admin after the button click: ", profileUpdateForm.toString());
		// System.out.println(profileUpdateForm.toString());
		logger.info("PrescriptionImage is read in the form of image(multipart file)");
		updateProfileService.updateProfile(profileUpdateForm, reportsInput); // fields along with image are passed
		appointmentDAO.updateAppStatusComp(profileUpdateForm.getAppnid());
		AppointmentEntity a = appointmentDAO.getAppointmentById(profileUpdateForm.getAppnid());
		patientDAO.updateLastvisitAndLastAppointmentInfo(profileUpdateForm.getPatientId(),
				a.getAppn_sch_date().toLocalDateTime().toLocalDate(), profileUpdateForm.getAppnid());

		return "admin/postredirect";
	}

	@RequestMapping(value = "admin/getpatientid", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getPatientId(Model m) {
		logger.info("To get Patient ID list in admin's patient profile update page");
		List<Integer> patientIDList = patientDAO.getPatientIds(); // Patient Ids will be displayed based on load page
		logger.info("The patients IDs are : {}", patientIDList.toString());
		// System.out.println("Patient ids list" + patientIDList);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(patientIDList));
	}

	@RequestMapping(value = "admin/getpatientbyid", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getPatientById(@RequestParam int patientId, Model m) {
		logger.info("To get Patient details by using ID in Admin's patient profile update page");
		List<Integer> patientIdList = patientProfileUpdateDAO.getAllAppointmentIds(patientId); // Appointment IDs list are
																						// displayed by mapping patient
																						// IDs
		logger.info("This Appointment IDs are returned for a particular selected patient in dropdown : {}",
				patientIdList);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(patientIdList));
	}

}