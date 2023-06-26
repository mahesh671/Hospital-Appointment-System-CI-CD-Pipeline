package spring.orm.dao;

import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import spring.orm.contract.DocScheduleDao;
import spring.orm.model.DoctorSchedule;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.util.UtilManager;

@Repository
public class DocScheduleDaoImpl implements DocScheduleDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public DoctorSchedule getSchedulebyId(int id) {
		/*
		 * This method retrieves a DoctorSchedule entity from the database based on the
		 * provided ID.
		 */
		return em.find(DoctorSchedule.class, id);
		/*
		 * Using the EntityManager 'em', the method retrieves the DoctorSchedule entity
		 * by finding it in the database using the provided ID. The found DoctorSchedule
		 * entity is returned.
		 */
	}

	@Override
	@Transactional
	public void addDoctorSchedule(DoctorInput d, int id) {
		/*
		 * This method adds a new doctor schedule to the database based on the provided
		 * DoctorInput object and doctor ID.
		 */
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");

		// Create a new DoctorSchedule object using the provided data and formatted time
		// values.
		DoctorSchedule sch = new DoctorSchedule(id, UtilManager.converWeekdays(d.getWeekday()),
				d.getDocfrom().format(format), d.getDocto().format(format), d.getDocavgtime());
		em.persist(sch);

	}

	@Override
	public void updateSchedule(DoctorUpdateModel d) {
		/*
		 * This method updates the schedule of a doctor based on the provided
		 * DoctorUpdateModel object.
		 */
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
		DoctorSchedule sch = em.find(DoctorSchedule.class, d.getDoc_id());
		if (d.getDocavgtime() != null) {
			sch.setAverageAppointmentTime(d.getDocavgtime());
		}
		/*
		 * Update the average appointment time of the doctor's schedule if the value is
		 * provided in the DoctorUpdateModel object.
		 */
		if (d.getDocfrom() != null && d.getDocto() != null) {
			sch.setTimeFrom(d.getDocfrom().format(format));
			sch.setTimeTo(d.getDocto().format(format));
		}

		/*
		 * Update the time range (from and to) of the doctor's schedule if the values
		 * are provided in the DoctorUpdateModel object.
		 */
		if (d.getWeekday() != null) {
			sch.setWeekday(UtilManager.converWeekdays(d.getWeekday()));
		}

		/*
		 * Update the weekdays of the doctor's schedule by converting the provided
		 * weekday values using a utility method.
		 */
		em.merge(sch);

	}
}