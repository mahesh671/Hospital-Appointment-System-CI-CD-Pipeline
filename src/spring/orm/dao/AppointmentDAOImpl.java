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

import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.PatientDAO;
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
public class AppointmentDAOImpl implements AppointmentDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PatientDAO patdao;

	@Autowired
	private DoctorsDAO doctdao;

	@Override
	@Transactional
	public List<Object[]> fetchUpcomingAppointmentData() {
		// This method fetches upcoming appointment data and returns a list of Object
		// arrays.

		String hql = "SELECT a.appn_id, d.name, p.patn_name, a.appn_sch_date " + "FROM Appointmentmodel a "
				+ "JOIN a.doctor d " + "JOIN a.pm p " + "WHERE a.appn_sch_date > :date";
		// This HQL query retrieves the appointment ID, doctor name, patient name, and
		// scheduled date for upcoming appointments.

		Query q = em.createQuery(hql);
		// Create a Query object using the EntityManager and the HQL query.

		q.setParameter("date", LocalDate.now());
		// Set the value of the "date" parameter in the HQL query to today's date.

		List<Object[]> data = q.getResultList();
		// Execute the query and retrieve the result as a List of Object arrays.

		for (Object[] x : data) {
			for (Object y : x) {
				System.out.println(y + " ");
			}
			System.out.println();
		}
		// Iterate over each Object array in the result list and print each element.

		return data;
		// Return the fetched appointment data as a List of Object arrays.
	}

	public List<Object[]> fetchBADDateWise(String from, String to) {
		// This method retrieves appointment data based on a date range.

		String hql = "select a.appn_id, d.name, p.patn_name, a.appn_sch_date, a.appn_status "
				+ "from Appointmentmodel a " + "join a.doctor d" + "join a.pm p"
				+ "where date_trunc('day', a.appn_sch_date) between :startDate and :endDate";
		/*
		 * The HQL query to fetch the required appointment data is defined here. It
		 * selects specific fields from the Appointmentmodel entity and joins with the
		 * doctor and pm entities. The date range is specified using the "between"
		 * clause with placeholders for start and end dates.
		 */

		Query q = em.createQuery(hql);
		// Create a query object using the EntityManager and the defined HQL query.

		q.setParameter("startDate", LocalDate.parse(from));
		/*
		 * Set the value of the "startDate" parameter in the query with the parsed
		 * "from" date string.
		 */
		q.setParameter("endDate", LocalDate.parse(to));
		/*
		 * Set the value of the "endDate" parameter in the query with the parsed "to"
		 * date string.
		 */

		List<Object[]> data = q.getResultList();
		/*
		 * Execute the query and retrieve the result as a List of Object arrays. Each
		 * array represents a row of data with the selected fields.
		 */
		for (Object[] obj : data) {
			// Iterate over each row of data in the result.

			for (Object x : obj) {
				// Iterate over each element in the row.

				System.out.println(x + " ");
				// Print the value of each element.
			}
			System.out.println(" ");
			// Print an empty line after printing the elements of each row.
		}

		return null;
		/*
		 * Return null as a placeholder since the actual implementation to process and
		 * return the fetched data is missing
		 */
	}



	@Override
	@Transactional
	public List<AppointmentEntity> getAllAppointments() {
		// This method retrieves all appointment entities from the database.

		return em.createQuery("select a from AppointmentEntity a", AppointmentEntity.class).getResultList();
		/*
		 * Execute a JPQL query to select all appointment entities from the database and
		 * return the result list.
		 */
	}

	@Override
	@Transactional
	public boolean isSlotBooked(int doc_id, String date, String time) {
		/*
		 * This method checks if a time slot is booked for a given doctor on a specific
		 * date and time.
		 */
		String sch_date = date + " " + time; // Combine the provided date and time to create a schedule date string.
		System.out.println(doc_id + " " + sch_date); // Print the doctor ID and schedule date for debugging purposes.

		String q = "select a from AppointmentEntity a where a.doctor.id= : doc_id and a.appn_sch_date=:sch_date ";
		/*
		 * Define a JPQL query to retrieve appointment entities for the given doctor ID
		 * and schedule date.
		 */
		Query qu = em.createQuery(q, AppointmentEntity.class); // Create a query object using the defined query and
																// entity class.
		qu.setParameter("doc_id", doc_id); // Set the "doc_id" parameter in the query to the provided doctor ID.
		qu.setParameter("sch_date", Timestamp.valueOf(sch_date)); // Set the "sch_date" parameter in the query to the
																	// converted schedule date.

		List<AppointmentEntity> a = qu.getResultList(); // Execute the query and retrieve the list of appointment
														// entities.

		if (a.size() > 0) {
			return true; // If the list of appointment entities is not empty, it means the slot is
							// booked.
		}

		return false; // If the list of appointment entities is empty, it means the slot is available.
	}

	@Override
	@Transactional
	public int bookAppointment(PatientModel existingPatientid, DoctorTemp doctor, String bookedDate, String payref,
			double payamount) {
		/*
		 * This method is used to book an appointment and takes the necessary
		 * parameters.
		 */

		AppointmentEntity a = new AppointmentEntity(); // Create a new instance of AppointmentEntity.

		a.setDoctor(doctor); // Set the doctor for the appointment.

		a.setAppn_booked_Date(new Timestamp(System.currentTimeMillis())); // Set the booked date for the appointment to
																			// the current timestamp.

		a.setPm(existingPatientid); // Set the existing patient for the appointment.

		a.setAppn_sch_date(Timestamp.valueOf(bookedDate)); // Set the scheduled date for the appointment using the
															// provided string value.

		a.setAppn_paymode("CARD"); // Set the payment mode for the appointment to "CARD".

		a.setAppn_payamount(payamount); // Set the payment amount for the appointment.

		a.setAppn_payreference(payref); // Set the payment reference for the appointment.

		a.setAppn_status("YETO"); // Set the status of the appointment to "YETO" (indicating it is not yet
									// confirmed).

		em.persist(a); // Persist the appointment entity to the database.

		return a.getAppn_id(); // Return the appointment ID.
	}

	@Override
	public AppointmentEntity getAppointmentById(int app_id) {
		return em.find(AppointmentEntity.class, app_id);
	}

	@Override
	@Transactional
	public void cancelAppointment(int app_id) {
		/*
		 * This method is used to cancel an appointment based on the provided
		 * appointment ID.
		 */
		AppointmentEntity a = em.find(AppointmentEntity.class, app_id);
		/*
		 * Retrieve the AppointmentEntity object from the EntityManager based on the
		 * provided appointment ID and assign it to the variable 'a'.
		 */
		a.setAppn_status("CNL");
		/*
		 * Set the appointment status to "CNL" (indicating cancellation) for the
		 * retrieved AppointmentEntity object.
		 */
		em.merge(a);
		/*
		 * Merge the changes made to the AppointmentEntity object back into the
		 * persistence context to update it in the database.
		 */
	}

	@Override
	public void reschduleAppointment(RescheduleAppointmentModel rm) {
		/*
		 * This method is used to reschedule an appointment based on the provided
		 * RescheduleAppointmentModel object.
		 */
		AppointmentEntity a = em.find(AppointmentEntity.class, rm.getAppointmentId());
		/*
		 * Retrieve the appointment entity from the EntityManager based on the
		 * appointment ID provided in the RescheduleAppointmentModel object.
		 */
		DateTimeFormatter sqlformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		/*
		 * Create a DateTimeFormatter object with the pattern "HH:mm:ss" to format the
		 * time in SQL format.
		 */
		System.out.println("in docdao appointment");
		/*
		 * Print a message to indicate that the code is in the "docdao appointment"
		 * section for debugging or logging purposes.
		 */
		a.setAppn_sch_date(Timestamp.valueOf(rm.getRescheduleDate() + " "
				+ LocalTime.parse(rm.getSlot(), DateTimeFormatter.ofPattern("hh:mm a")).format(sqlformat)));
		/*
		 * Set the rescheduled appointment date by combining the reschedule date from
		 * the RescheduleAppointmentModel object with the parsed time from the slot
		 * using the provided time format.
		 */
		em.merge(a);
		// Merge the updated appointment entity back into the EntityManager.

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
		/*
		 * This method retrieves a list of appointments based on the provided patient
		 * ID.
		 */
		String query = "SELECT a FROM AppointmentEntity a WHERE a.pm.patn_id = :patientId";
		/*
		 * This query selects all AppointmentEntity objects from the database where the
		 * associated patient's ID matches the provided patientId parameter.
		 */
		return em.createQuery(query, AppointmentEntity.class).setParameter("patientId", patn_id).getResultList();
		/*
		 * This line executes the query and returns a list of AppointmentEntity objects
		 * that match the specified patient ID.
		 */
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
		/*
		 * This method retrieves the top appointments and returns a list of
		 * AdminAppOutModel objects.
		 */
		List<AdminAppOutModel> toplist = em.createQuery(
				"select new spring.orm.model.output.AdminAppOutModel(a.doctor.doctName,a.pm.patn_name,a.appn_sch_date) from AppointmentEntity a ORDER BY a.appn_sch_date DESC ",
				AdminAppOutModel.class).setMaxResults(10).getResultList();
		/*
		 * This statement uses a JPQL query to retrieve appointment details from the
		 * AppointmentEntity table. The query selects specific fields (doctor name,
		 * patient name, appointment scheduled date) and constructs AdminAppOutModel
		 * objects using the selected fields. The appointments are ordered by the
		 * scheduled date in descending order. The 'setMaxResults(10)' limits the result
		 * to the top 10 appointments. The query result is stored in the 'toplist'
		 * variable, which is a List<AdminAppOutModel>.
		 */

		return toplist; // Return the list of top appointments.
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
		// This method retrieves the dashboard report for the administrator.

		Long doccount = (Long) em.createQuery("select count(d) from DoctorTemp d where d.isDeleted=false")
				.getSingleResult();
		/*
		 * Retrieve the count of active doctors from the "DoctorTemp" entity where
		 * "isDeleted" is false and store it in the "doccount" variable.
		 */
		Long appointments = (Long) em.createQuery("select count(a) from AppointmentEntity a ").getSingleResult();
		/*
		 * Retrieve the count of all appointments from the "AppointmentEntity" and store
		 * it in the "appointments" variable.
		 */
		Long specs = (Long) em.createQuery("select count(s) from Specialization s").getSingleResult();
		/*
		 * Retrieve the count of all specializations from the "Specialization" entity
		 * and store it in the "specs" variable.
		 */
		Double payments = (Double) em.createQuery("select sum(a.appn_payamount) from AppointmentEntity a")
				.getSingleResult();
		/*
		 * Retrieve the sum of payment amounts from the "AppointmentEntity" and store it
		 * in the "payments" variable.
		 */
		AdminDashOut a = new AdminDashOut(doccount, appointments, specs, payments);
		// Create a new instance of the "AdminDashOut" class using the retrieved data.

		return a;
		// Return the "AdminDashOut" object representing the dashboard report.
	}

	@Override
	@Transactional
	public void updateAppStatusComp(int app_id) {
		/*
		 * This method updates the status of an appointment to "CMPL" (completed) based
		 * on the provided appointment ID.
		 */
		AppointmentEntity a = em.find(AppointmentEntity.class, app_id);
		/*
		 * Retrieve the AppointmentEntity object from the EntityManager based on the
		 * provided appointment ID and store it in 'a' variable.
		 */
		a.setAppn_status("CMPL");
		/*
		 * Set the appointment status of the retrieved AppointmentEntity object to
		 * "CMPL" (completed).
		 */
		em.merge(a);
		/*
		 * Update the AppointmentEntity object in the EntityManager with the changes
		 * made.
		 */
	}

}