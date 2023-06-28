package spring.orm.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.model.PatientSession;
import spring.orm.model.Specialization;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.BookedAppForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AppoutformFamily;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;
import spring.orm.model.output.OutputBookedAppointmnets;
import spring.orm.model.output.RescheduleAppointmentOutput;
import spring.orm.services.AppointmentService;
import spring.orm.services.DoctorOutputService;
import spring.orm.util.MailSend;

@Controller
@RequestMapping
public class AppointmentController {

	private SpecializationDAO specializationDAO;

	private DoctorOutputService doctorservice;

	private PatientDAO patientDAO;

	private DoctorsDAO doctorDAO;

	private AppointmentDAO appointmentDAO;

	private AppointmentService appointmentService;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	public AppointmentController(SpecializationDAO specializationDAO, DoctorOutputService doctorservice,
			PatientDAO patientDAO, DoctorsDAO doctorsDAO, AppointmentDAO appointmentDAO, AppointmentService a) {

		this.appointmentDAO = appointmentDAO;
		this.appointmentService = a;
		this.doctorDAO = doctorsDAO;
		this.doctorservice = doctorservice;
		this.patientDAO = patientDAO;
		this.specializationDAO = specializationDAO;
	}

	@RequestMapping(value = "admin/newappointment")
	public String getNewAppointment(Model m) {
		// Retrieve all specializations and add them to the model
		String basePath = System.getProperty("catalina.base");
		logger.info("Log base path: " + basePath);
		logger.info("Entered to new appointment page");
		List<Specialization> aplist = specializationDAO.getAllSpecializations();
		m.addAttribute("speclist", aplist);
		// Retrieve all patients and add them to the model

		m.addAttribute("patlist", patientDAO.getAllPatientInfo());
		System.out.println(patientDAO.getAllPatientInfo());
		return "admin/appointment";
	}

	@RequestMapping(value = { "patient/newappointment" })
	public String getNewPatApp(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		// Retrieve all specializations and add them to the model

		List<Specialization> aplist = specializationDAO.getAllSpecializations();
		m.addAttribute("speclist", aplist);

		// Retrieve the family appointments for the patient session and add them to the
		// model
		List<AppoutformFamily> apfamily = appointmentService.getFormFamily(patientSession.getId());
		m.addAttribute("fam", apfamily);
		return "patient/appointment";
	}

	@RequestMapping(value = "patient/appointments")
	public String getpatientBookedAppForm(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {

		// Retrieve all specializations and add them to the model
		List<Specialization> specdata = specializationDAO.getAllSpecializations();
		m.addAttribute("specdata", specdata);

		// Retrieve all doctors and add them to the model
		List<DoctorTemp> docdata = doctorDAO.getAllDoc();
		m.addAttribute("docdata", docdata);
		System.out.println(specdata);
		System.out.println(docdata);

		return "patient/bookedapp";
	}

	@RequestMapping(value = "patient/fetchBookData", method = RequestMethod.GET)
	public String getpatientBookAppData(@SessionAttribute("patientSession") PatientSession patientSession,
			@ModelAttribute BookedAppForm baf, Model m) {

		System.out.println(baf);

		// Fetch booked appointment data based on the BookedAppForm and patientSession
		// ID
		List<OutputBookedAppointmnets> data = appointmentDAO.fetchBookedAppointmentData(baf, patientSession.getId());
		m.addAttribute("data", data);
		System.out.println(data);
		return "patient/BookedAppData";
	}

	@RequestMapping(value = { "admin/fetchBySpecialization",
			"patient/fetchBySpecialization" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> fetchBySpecialization(@RequestParam String specialization,
			@RequestParam String appointmentDate) {
		// Convert the appointmentDate to Date object
		Date appointmentDated = Date.valueOf(appointmentDate);

		// Fetch doctors by specialization and appointment date
		List<DoctorList> dlist = doctorservice.getAllDocBySpecDate(specialization, appointmentDated);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dlist));

	}

	@RequestMapping(value = { "admin/fetchdoctor",
			"patient/fetchdoctor" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchDoctor(@RequestParam int id) {

		// Fetch doctor details by ID
		DoctorOutPutModel d = doctorservice.getDocbyID(id);

		// Update the doctor's consultation fee
		d.setDoctCfee(d.getDoctCfee() + d.getDoctCfee() * 0.1);

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(d));
	}

	@RequestMapping(value = { "admin/fetchtimeSlots", "admin/reschedule/fetchtimeSlots",
			"patient/reschedule/fetchtimeSlots",
			"patient/fetchtimeSlots" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchTimeSlots(@RequestParam int id, @RequestParam String date) {

		// Fetch available time slots for the given doctor ID and date
		List<String> d = doctorservice.getDocTimeSlots(id, Date.valueOf(date).toString());
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(d));
	}

	@RequestMapping(value = "admin/newappointment/create", method = RequestMethod.POST)
	public @ResponseBody void newAppointmentBooking(@ModelAttribute AppointmentForm appointment,
			HttpServletRequest request, HttpServletResponse response) {

		if (appointment.getBookingType().equals("NEW PATIENT")) {

			// Book an appointment with a new patient
			int app_id = appointmentService.bookAppointmentWithNewPatient(appointment);

		} else {
			System.out.println("harsha");
			System.out.println(appointment);
			int app_id = appointmentService.bookAppointment(appointment);

			// Book an appointment
			String userMail = appointmentService.getAppointmentByID(app_id).getMail();
			if (!userMail.equals("")) {
				try {
					MailSend.sendBookingEmail(request, response, appointmentService.getAppointmentByID(app_id),
							userMail);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("appn details" + appointment.toString());
	}

	@RequestMapping(value = "patient/newappointment/create", method = RequestMethod.POST)
	public @ResponseBody void newAppointmentPatientBooking(
			@SessionAttribute("patientSession") PatientSession patientSession,
			@ModelAttribute AppointmentForm appointment, HttpServletRequest request, HttpServletResponse response) {
		int patientId = patientSession.getId();
		Integer app_id;

		if (appointment.getBookingType().equals("SELF")) {
			System.out.println("harsha new");
			System.out.println(appointment);
			appointment.setExistingPatientid(String.valueOf(patientSession.getId()));
			// Book an appointment with an existing patient (self)
			app_id = appointmentService.bookAppointment(appointment);

		} else {
			System.out.println("harsha");
			System.out.println(appointment);
			// Book an appointment
			app_id = appointmentService.bookAppointment(appointment);

		}
		String userMail = patientSession.getEmail();
		try {
			MailSend.sendBookingEmail(request, response, appointmentService.getAppointmentByID(app_id), userMail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(appointment + "\n" + patientSession);

	}

	@RequestMapping(value = { "admin/cancel/{app_id}", "patient/cancel/{app_id}" })
	public String cancelAppointment(@PathVariable int app_id, Model m) {
		// Cancel an appointment

		appointmentService.cancelAppointment(app_id);

		return "redirect:../appointments";
	}

	@RequestMapping(value = "patient/reschedule/{app_id}")
	public String reschedulePatAppointment(@PathVariable int app_id, Model m) {
		// Get the appointment details for rescheduling

		RescheduleAppointmentOutput rescheduleAppointment = appointmentService.getAppointmentByIdOutput(app_id);
		System.out.println(rescheduleAppointment);
		m.addAttribute("app", rescheduleAppointment);
		return "admin/reschedule";
	}

	@RequestMapping(value = "admin/reschedule/{app_id}")
	public String rescheduleAppointment(@PathVariable int app_id, Model m) {
		// Extract the appointment details from the RescheduleAppointmentModel object
		RescheduleAppointmentOutput rescheduleAppointment = appointmentService.getAppointmentByIdOutput(app_id);
		rescheduleAppointment.setApp_id(app_id);
		System.out.println(rescheduleAppointment);
		m.addAttribute("app", rescheduleAppointment);
		return "admin/reschedule";
	}

	@RequestMapping(value = { "admin/reschedule/success", "patient/reschedule/success" }, method = RequestMethod.POST)
	public String rescheduleAppointment(@ModelAttribute RescheduleAppointmentModel rescheduleModel) {
		// Extract the appointment details from the RescheduleAppointmentModel object
		int appointmentId;
		try {
			appointmentId = (rescheduleModel.getAppointmentId());
		} catch (NumberFormatException e) {
			// Handle the case where appointmentId is not a valid integer
			// You can redirect to an error page or show an error message
			return "errorPage";
		}

		String rescheduleDate = rescheduleModel.getRescheduleDate();
		String slot = rescheduleModel.getSlot();
		System.out.println("in form submission" + rescheduleDate + " " + slot);

		// Update the appointment details in the database using the appointmentId
		appointmentService.reschduleAppointment(rescheduleModel);
		// Redirect to a success page or show a success message
		return "admin/success";
	}

	@RequestMapping(value = "admin/appointments")
	public String getBookedAppForm(Model model) {
		// Get the data for the booked appointment form

		List<Specialization> specializationData = specializationDAO.getAllSpecializations();

		List<DoctorTemp> doctorData = doctorDAO.getAllDoc();

		model.addAttribute("specdata", specializationData);
		model.addAttribute("docdata", doctorData);

		return "admin/bookedapp";
	}

	@RequestMapping(value = "admin/fetchBookData", method = RequestMethod.GET)
	public String getBookAppData(@ModelAttribute BookedAppForm bookedAppForm, Model model) {

		// Fetch booked appointment data based on the BookedAppForm
		List<OutputBookedAppointmnets> data = appointmentDAO.fetchBookedAppointmentData(bookedAppForm, null);
		model.addAttribute("data", data);

		return "admin/BookedAppData";
	}

}