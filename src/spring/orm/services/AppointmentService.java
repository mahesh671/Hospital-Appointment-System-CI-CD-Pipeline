package spring.orm.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AppoutformFamily;
import spring.orm.model.output.MailAppOutputModel;
import spring.orm.model.output.RescheduleAppOutput;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentDAO apdao;

	@Autowired
	private DoctorsDAO docdao;

	@Autowired
	private PatientDAO patdao;

	public List<AppointmentEntity> getAllAppointments() {
		// Retrieve all appointments
		List<AppointmentEntity> alist = apdao.getAllAppointments();
		for (AppointmentEntity a : alist) {
			// Format date
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			a.setDateFormetted(dateFormat.format(a.getAppn_sch_date()));

			// Format time
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			a.setTimeFormetted(timeFormat.format(a.getAppn_sch_date()));
		}
		return alist;
	}

	public int bookAppointment(AppointmentForm app) {
		// Book an appointment
		DateTimeFormatter sqlformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println(LocalTime.parse(app.getSlots(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlformat));
		int app_id = apdao.bookAppointment(patdao.getPatientById(app.getExistingPatientid()),
				docdao.getdoc(app.getDoctor()),
				app.getAppointmentDate().toString() + " "
						+ LocalTime.parse(app.getSlots(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlformat),
				app.getAppnrefer(), app.getAppnfee());
		return app_id;
	}

	public RescheduleAppOutput getAppointmentByIdOutput(int app_id) {
		// Get appointment details by ID for rescheduling
		RescheduleAppOutput r = new RescheduleAppOutput();
		AppointmentEntity a = apdao.getAppointmentById(app_id);
		r.setApp_id(a.getAppn_id());
		r.setSlot(a.getAppn_sch_date().toLocalDateTime().toLocalTime());
		r.setApp_sch_date(Date.valueOf(a.getAppn_sch_date().toLocalDateTime().toLocalDate()));
		r.setDoctor(a.getDoctor());
		r.setPatient(a.getPm());

		return r;

	}

	public int bookAppointmentWithNewPatient(AppointmentForm app) {
		// Book an appointment with a new patient
		PatientModel p = new PatientModel();
		p.setPatn_name(app.getNewPatientName());
		p.setPatn_age(app.getNewPatientAge());
		p.setPatn_bgroup(app.getNewPatientBgroup());
		p.setPatn_rdate(LocalDate.now());
		p.setPatn_gender(app.getNewPatientGender());

		patdao.addNewPatient(p);
		DateTimeFormatter sqlformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println("in docdao appointment");
		int app_id = apdao.bookAppointment(p, docdao.getdoc(app.getDoctor()),
				app.getAppointmentDate().toString() + " "
						+ LocalTime.parse(app.getSlots(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlformat),
				app.getAppnrefer(), app.getAppnfee());
		return app_id;
	}

	public void cancelAppointment(int app_id) {
		// Cancel an appointment
		apdao.cancelAppointment(app_id);

	}

	public MailAppOutputModel getAppointmentByID(int app_id) {
		// Get appointment details by ID
		AppointmentEntity a = apdao.getAppointmentById(app_id);
		MailAppOutputModel ao = new MailAppOutputModel();
		ao.setAppn_id(a.getAppn_id());
		ao.setDoc_name(a.getDoctor().getDoctName());
		ao.setAppn_booked_Date(a.getAppn_booked_Date());
		ao.setAppn_sch_date(a.getAppn_sch_date());
		ao.setAppn_payamount(a.getAppn_payamount());
		ao.setAppn_paymode(a.getAppn_paymode());
		ao.setDoc_Photo(a.getDoctor().getDoctPhoto());
		ao.setAppn_payreference(a.getAppn_payreference());
		ao.setPat_name(a.getPm().getPatn_name());
		String mail = "";
		if (a.getPm().getAccessPatientId() != null) {
			mail = a.getPm().getAccessPatient().getUserPass().getMail();
		}
		if (a.getPm().getUserPass() != null) {
			mail = a.getPm().getUserPass().getMail();
		}
		ao.setMail(mail);
		return ao;

	}

	@Transactional
	public void reschduleAppointment(RescheduleAppointmentModel rm) {
		// Reschedule an appointment
		apdao.reschduleAppointment(rm);

	}

	public int bookAppointment(AppointmentForm appointment, int patientId) {
		// Book an appointment with a specified patient ID
		DateTimeFormatter sqlFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println(
				LocalTime.parse(appointment.getSlots(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlFormat));

		int appointmentId = apdao.bookAppointment(patdao.getPatientById(patientId),
				docdao.getdoc(appointment.getDoctor()),
				appointment.getAppointmentDate().toString() + " " + LocalTime
						.parse(appointment.getSlots(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlFormat),
				appointment.getAppnrefer(), appointment.getAppnfee());

		return appointmentId;
	}

	public List<AppointmentEntity> getPatientAppointmentByID(int patn_id) {
		// Get all appointments of a patient by their ID
		List<AppointmentEntity> alist = apdao.getAppointmentsByPatientId(patn_id);
		return alist;
	}

	public List<AppoutformFamily> getFormFamily(int id) {
		// Get family members for form filling
		List<AppoutformFamily> r = new ArrayList<>();
		for (PatientModel p : patdao.getAllFamily(id)) {
			AppoutformFamily f = new AppoutformFamily();
			f.setFam_patn_name(p.getPatn_name());
			f.setFam_patni_id(p.getPatn_id());
			r.add(f);

		}
		return r;
	}
}