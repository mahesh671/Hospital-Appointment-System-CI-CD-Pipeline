package spring.orm.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import spring.orm.contract.AppointmentDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.PatientDao;
import spring.orm.contract.SpecializationDao;
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
import spring.orm.model.output.RescheduleAppOutput;
import spring.orm.services.AppointmentService;
import spring.orm.services.DoctorOutputService;
import spring.orm.util.MailSend;

@Controller
@RequestMapping
public class AppointmentController {

	@Autowired
	private SpecializationDao specdao;
	@Autowired
	private DoctorOutputService docserv;
	// @Autowired
	// private DocScheduleDao docschdao;

	@Autowired
	private PatientDao patdao;
	@Autowired
	private DoctorsDaoTemp docdao;

	@Autowired
	private AppointmentDao apd;
	@Autowired
	private AppointmentService appser;

	@RequestMapping(value = "admin/newappointment")
	public String getnewApp(Model m) {
		List<Specialization> aplist = specdao.getAllSpec();
		// System.out.println(aplist);
		m.addAttribute("speclist", aplist);
		m.addAttribute("patlist", patdao.getAllPatientModels());
		System.out.println(patdao.getAllPatientModels());
		return "admin/appointment";
	}

	@RequestMapping(value = { "patient/newappointment" })
	public String getnewPatApp(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {
		List<Specialization> aplist = specdao.getAllSpec();
		// System.out.println(aplist);
		m.addAttribute("speclist", aplist);
		List<AppoutformFamily> apfamily = appser.getFormFamily(patientSession.getId());
		m.addAttribute("fam", apfamily);
		return "patient/appointment";
	}

	@RequestMapping(value = "patient/appointments")
	public String getpatientBookedAppForm(@SessionAttribute("patientSession") PatientSession patientSession, Model m) {

		List<Specialization> specdata = specdao.getAllSpec();

		List<DoctorTemp> docdata = docdao.getAllDoc();

		m.addAttribute("specdata", specdata);
		m.addAttribute("docdata", docdata);
		System.out.println(specdata);
		System.out.println(docdata);

		return "patient/bookedapp";
	}

	@RequestMapping(value = "patient/fetchBookData", method = RequestMethod.GET)
	public String getpatientBookAppData(@SessionAttribute("patientSession") PatientSession patientSession,
			@ModelAttribute BookedAppForm baf, Model m) {

		System.out.println("called?");
		System.out.println(baf);
		List<OutputBookedAppointmnets> data = apd.fetchBookedAppData(baf, patientSession.getId());
		m.addAttribute("data", data);
		System.out.println(data);
		;
		return "patient/BookedAppData";
	}

	@RequestMapping(value = { "admin/fetchBySpecialization",
			"patient/fetchBySpecialization" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> fetchBySpecialization(@RequestParam String specialization,
			@RequestParam String appointmentDate) {
		Date appointmentDated = Date.valueOf(appointmentDate);
		// System.out.println("hello");
		List<DoctorList> dlist = docserv.getAllDocBySpecDate(specialization, appointmentDated);

		// System.out.println(appointmentDated + " " + appointmentDated.getDay());

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dlist));

	}

	@RequestMapping(value = { "admin/fetchdoctor",
			"patient/fetchdoctor" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchDoctor(@RequestParam int id) {
		DoctorOutPutModel d = docserv.getDocbyID(id);
		d.setDoctCfee(d.getDoctCfee() + d.getDoctCfee() * 0.1);
		// System.out.println(d);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(d));
	}

	@RequestMapping(value = { "admin/fetchtimeSlots", "admin/reschedule/fetchtimeSlots",
			"patient/reschedule/fetchtimeSlots",
			"patient/fetchtimeSlots" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchTimeSlots(@RequestParam int id, @RequestParam String date) {

		// System.out.println(Date.valueOf(date).toString());
		List<String> d = docserv.getDocTimeSlots(id, Date.valueOf(date).toString());
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(d));
	}

	@RequestMapping(value = "admin/newappointment/create", method = RequestMethod.POST)
	public @ResponseBody void newAppointmentBooking(@ModelAttribute AppointmentForm appointment,
			HttpServletRequest request, HttpServletResponse response) {

		if (appointment.getBookingType().equals("NEW PATIENT")) {
			System.out.println("harsha new");
			System.out.println(appointment);
			int app_id = appser.bookAppointmentWithNewPatient(appointment);

		} else {
			System.out.println("harsha");
			System.out.println(appointment);
			int app_id = appser.bookAppointment(appointment);
			String userMail = appser.getAppointmentByID(app_id).getMail();
			if (!userMail.equals("")) {
				try {
					MailSend.sendEmail(request, response, appser.getAppointmentByID(app_id), userMail);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("appn details" + appointment.toString());
		// appdao.bookAppointment(appointment);

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
			app_id = appser.bookAppointment(appointment);

		} else {
			System.out.println("harsha");
			System.out.println(appointment);
			app_id = appser.bookAppointment(appointment);

		}
		String userMail = patientSession.getEmail();
		try {
			MailSend.sendEmail(request, response, appser.getAppointmentByID(app_id), userMail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(appointment + "\n" + patientSession);

	}

	@RequestMapping(value = { "admin/cancel/{app_id}", "patient/cancel/{app_id}" })
	public String cancelAppointment(@PathVariable int app_id, Model m) {
		appser.cancelAppointment(app_id);

		return "redirect:../appointments";
	}

	@RequestMapping(value = "patient/reschedule/{app_id}")
	public String reschedulePatAppointment(@PathVariable int app_id, Model m) {
		RescheduleAppOutput rapp = appser.getAppointmentByIdOutput(app_id);
		System.out.println(rapp);
		m.addAttribute("app", rapp);
		return "admin/reschedule";
	}

	@RequestMapping(value = "admin/reschedule/{app_id}")
	public String rescheduleAppointment(@PathVariable int app_id, Model m) {
		RescheduleAppOutput rapp = appser.getAppointmentByIdOutput(app_id);
		rapp.setApp_id(app_id);
		System.out.println(rapp);
		m.addAttribute("app", rapp);
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
		appser.reschduleAppointment(rescheduleModel);
		// Redirect to a success page or show a success message
		return "admin/success";
	}

	@RequestMapping(value = "admin/appointments")
	public String getBookedAppForm(Model model) {

		List<Specialization> specdata = specdao.getAllSpec();

		List<DoctorTemp> docdata = docdao.getAllDoc();

		model.addAttribute("specdata", specdata);
		model.addAttribute("docdata", docdata);
		System.out.println(specdata);
		System.out.println(docdata);
		return "admin/bookedapp";
	}

	@RequestMapping(value = "admin/fetchBookData", method = RequestMethod.GET)
	public String getBookAppData(@ModelAttribute BookedAppForm baf, Model model) {
		System.out.println("called?");
		System.out.println(baf);
		List<OutputBookedAppointmnets> data = apd.fetchBookedAppData(baf, null);
		model.addAttribute("data", data);
		System.out.println(data);
		return "admin/BookedAppData";
	}

}