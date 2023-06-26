package spring.orm.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.orm.contract.AppointmentDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.PatientDao;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.BookedAppForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AdminAppOutModel;
import spring.orm.model.output.AdminDashOut;
import spring.orm.model.output.AdminProfitAppOut;
import spring.orm.model.output.OutputBookedAppointmnets;

@Component
public class AppointmentDaoImpl implements AppointmentDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PatientDao patdao;

	@Autowired
	private DoctorsDaoTemp doctdao;

	@Override
	@Transactional
	public List<Object[]> fetchUpcomingAppointmentData() {

		String hql = "SELECT a.appn_id, d.name, p.patn_name, a.appn_sch_date " + "FROM Appointmentmodel a "
				+ "JOIN a.doctor d " + "JOIN a.pm p " + "WHERE a.appn_sch_date > :date";

		Query q = em.createQuery(hql);

		q.setParameter("date", "2023-06-05");

		List<Object[]> data = q.getResultList();

		for (Object[] x : data) {
			for (Object y : x) {
				System.out.println(y + " ");
			}
			System.out.println();
		}

		return data;
	}

	public List<Object[]> fetchBADDateWise(String from, String to) {
		String hql = "select a.appn_id, d.name, p.patn_name, a.appn_sch_date, a.appn_status "
				+ "from Appointmentmodel a " + "join a.doctor d" + "join a.pm p"
				+ "where date_trunc('day', a.appn_sch_date) between :startDate and :endDate";

		Query q = em.createQuery(hql);

		q.setParameter("startDate", LocalDate.parse(from));

		q.setParameter("endDate", LocalDate.parse(to));

		List<Object[]> data = q.getResultList();

		for (Object[] obj : data) {

			for (Object x : obj) {
				System.out.println(x + " ");
			}
			System.out.println(" ");
		}

		return null;
	}

	public List<Object[]> fetchBADDateSpecWise(String from, String to, String spec) {
		return null;
	}

	public List<Object[]> fetchBADDateSpecActWise(String from, String to, String spec, String act) {
		return null;

	}

	public List<Object[]> fetchBADDateSpecActDocWise(String from, String to, String spec, String act, String doc) {
		return null;

	}

	@Override
	@Transactional
	public List<AppointmentEntity> getAllAppointments() {
		// TODO Auto-generated method stub
		return em.createQuery("select a from AppointmentEntity a", AppointmentEntity.class).getResultList();
	}

	@Override
	@Transactional
	public boolean isSlotBooked(int doc_id, String date, String time) {
		// TODO Auto-generated method stub

		String sch_date = date + " " + time;
		System.out.println(doc_id + " " + sch_date);
		String q = "select a from AppointmentEntity a where a.doctor.id= : doc_id and a.appn_sch_date=:sch_date ";
		Query qu = em.createQuery(q, AppointmentEntity.class);
		qu.setParameter("doc_id", doc_id);
		qu.setParameter("sch_date", Timestamp.valueOf(sch_date));
		List<AppointmentEntity> a = qu.getResultList();
		// AppointmentEntity a = (AppointmentEntity) qu.getSingleResult();
		if (a.size() > 0)
			return true;
		return false;

	}

	@Override
	@Transactional
	public int bookAppointment(PatientModel existingPatientid, DoctorTemp doctor, String bookedDate, String payref,
			double payamount) {
		// TODO Auto-generated method stub
		AppointmentEntity a = new AppointmentEntity();
		a.setDoctor(doctor);
		a.setAppn_booked_Date(new Timestamp(System.currentTimeMillis()));
		a.setPm(existingPatientid);
		a.setAppn_sch_date(Timestamp.valueOf(bookedDate));
		a.setAppn_paymode("CARD");
		a.setAppn_payamount(payamount);
		a.setAppn_payreference(payref);
		a.setAppn_status("YETO");
		em.persist(a);
		return a.getAppn_id();

	}

	@Override
	public AppointmentEntity getAppointmentById(int app_id) {
		// TODO Auto-generated method stub
		return em.find(AppointmentEntity.class, app_id);
	}

	@Override
	@Transactional
	public void cancelAppointment(int app_id) {
		// TODO Auto-generated method stub
		AppointmentEntity a = em.find(AppointmentEntity.class, app_id);
		a.setAppn_status("CNL");
		em.merge(a);

	}

	@Override
	public void reschduleAppointment(RescheduleAppointmentModel rm) {
		// TODO Auto-generated method stub
		AppointmentEntity a = em.find(AppointmentEntity.class, rm.getAppointmentId());
		DateTimeFormatter sqlformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println("in docdao appointment");

		a.setAppn_sch_date(Timestamp.valueOf(rm.getRescheduleDate() + " "
				+ LocalTime.parse(rm.getSlot(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlformat)));
		em.merge(a);

	}

	@Override
	@Transactional
	public List<OutputBookedAppointmnets> fetchBookedAppData(BookedAppForm baf, Integer p_id) {

		String hql = "SELECT new spring.orm.model.output.OutputBookedAppointmnets(a.appn_id, p.patn_name, d.doctName, a.appn_sch_date, s.title, a.appn_status)"
				+ "FROM AppointmentEntity a" + " JOIN a.pm p " + " JOIN a.doctor d"
				+ " JOIN d.specialization s where 1=1";
		List<OutputBookedAppointmnets> data = null;

		if (!baf.getSpec().equals("select")) {
			hql += " and s.title = :spec";
		}
		if (!baf.getDoctor().equals("select")) {
			hql += " and d.doctName = :doc";
		}
		if (!baf.getStatus().equals("select")) {
			hql += " and a.appn_status = :status";
		}
		if (!baf.getFrom().equals("") && !baf.getTo().equals("")) {
			hql += " and a.appn_sch_date >:from and a.appn_sch_date< :to";
		}
		if (p_id != null) {
			hql += " and (a.pm.patn_id in (select f.pfmbPatnId from FamilyMembers f where f.patnAccessPatnId=:p_id) or a.pm.patn_id=:p_id)";
		}

		hql += " order by a.appn_id";

		TypedQuery<OutputBookedAppointmnets> q = em.createQuery(hql,
				spring.orm.model.output.OutputBookedAppointmnets.class);
		if (!baf.getSpec().equals("select")) {
			q.setParameter("spec", baf.getSpec());
		}
		if (!baf.getDoctor().equals("select")) {
			q.setParameter("doc", baf.getDoctor());
		}
		if (!baf.getStatus().equals("select")) {
			q.setParameter("status", baf.getStatus());
		}
		if (!baf.getFrom().equals("") && !baf.getTo().equals("")) {
			LocalDateTime fromDateTime = LocalDateTime.parse(baf.getFrom() + "T00:00:00");
			LocalDateTime toDateTime = LocalDateTime.parse(baf.getTo() + "T23:59:59");

			Date fromDate = Date.from(fromDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Date toDate = Date.from(toDateTime.atZone(ZoneId.systemDefault()).toInstant());

			q.setParameter("from", fromDate, TemporalType.TIMESTAMP);
			q.setParameter("to", toDate, TemporalType.TIMESTAMP);
		}
		if (p_id != null) {
			q.setParameter("p_id", p_id);
		}

		data = q.getResultList();

		return data;

	}

	@Override
	public List<AppointmentEntity> getAppointmentsByPatientId(int patn_id) {

		String query = "SELECT a FROM AppointmentEntity a WHERE a.pm.patn_id = :patientId";
		return em.createQuery(query, AppointmentEntity.class).setParameter("patientId", patn_id).getResultList();

	}

	@Override
	@Transactional
	public int bookAppointment(AppointmentForm appointment, int patientId) {
		// Retrieve the existing patient using the patientId
		PatientModel existingPatient = patdao.getPatientById(patientId);

		// Retrieve the doctor using the doctor ID from the appointment form
		DoctorTemp doctor = doctdao.getdoc(appointment.getDoctor());

		// Create a new AppointmentEntity object and set its properties
		AppointmentEntity appointmentEntity = new AppointmentEntity();
		appointmentEntity.setDoctor(doctor);
		appointmentEntity.setAppn_booked_Date(new Timestamp(System.currentTimeMillis()));
		appointmentEntity.setPm(existingPatient);
		appointmentEntity.setAppn_sch_date(
				Timestamp.valueOf(appointment.getAppointmentDate().toString() + " " + appointment.getSlots()));
		appointmentEntity.setAppn_paymode(appointment.getAppnmode());
		appointmentEntity.setAppn_payamount(appointment.getAppnfee());
		appointmentEntity.setAppn_payreference(appointment.getAppnrefer());
		appointmentEntity.setAppn_status("YETO");

		// Persist the appointment entity in the database
		em.persist(appointmentEntity);

		return appointmentEntity.getAppn_id();
	}

	@Override
	public List<AdminAppOutModel> getTopapp() {
		// TODO Auto-generated method stub

		List<AdminAppOutModel> toplist = em.createQuery(
				"select new spring.orm.model.output.AdminAppOutModel(a.doctor.doctName,a.pm.patn_name,a.appn_sch_date) from AppointmentEntity a ORDER BY a.appn_sch_date DESC ",
				AdminAppOutModel.class).setMaxResults(10).getResultList();
		return toplist;
	}

	@Override
	public List<AdminProfitAppOut> getTopprof() {
		List<AdminProfitAppOut> topprof = em.createQuery(
				"select new spring.orm.model.output.AdminProfitAppOut(a.appn_payreference,a.appn_paymode,(a.appn_payamount),a.doctor.doctName) from AppointmentEntity a ORDER BY a.appn_booked_Date DESC",
				AdminProfitAppOut.class).setMaxResults(10).getResultList();
		System.out.println(topprof.size());
		return topprof;
	}

	@Override
	public AdminDashOut getDashreport() {
		// TODO Auto-generated method stub

		Long doccount = (Long) em.createQuery("select count(d) from DoctorTemp d where d.isDeleted=false")
				.getSingleResult();
		Long appointments = (Long) em.createQuery("select count(a) from AppointmentEntity a ").getSingleResult();
		Long specs = (Long) em.createQuery("select count(s) from Specialization s").getSingleResult();
		Double payments = (Double) em.createQuery("select sum(a.appn_payamount) from AppointmentEntity a")
				.getSingleResult();
		AdminDashOut a = new AdminDashOut(doccount, appointments, specs, payments);

		return a;

	}

	@Override
	@Transactional
	public void updateAppStatusComp(int app_id) {
		// TODO Auto-generated method stub
		AppointmentEntity a = em.find(AppointmentEntity.class, app_id);
		a.setAppn_status("CMPL");
		em.merge(a);

	}

}