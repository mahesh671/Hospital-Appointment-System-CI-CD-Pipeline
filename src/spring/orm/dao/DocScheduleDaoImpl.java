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
		// TODO Auto-generated method stub
		// Session s = sF.getCurrentSession();
		System.out.println("hi.....");
		// List<DoctorTemp> d = em.createQuery("select d from DoctorTemp d where d.isDeleted = :status",
		// DoctorTemp.class)
		//
		// .setParameter("status",false).getResultList();
		// return d;
		return em.find(DoctorSchedule.class, id);
	}

	@Override
	@Transactional
	public void addDoctorSchedule(DoctorInput d, int id) {
		// TODO Auto-generated method stub
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
		DoctorSchedule sch = new DoctorSchedule(id, UtilManager.converWeekdays(d.getWeekday()),
				d.getDocfrom().format(format), d.getDocto().format(format), d.getDocavgtime());
		em.persist(sch);

	}

	@Override

	public void updateSchedule(DoctorUpdateModel d) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
		DoctorSchedule sch = em.find(DoctorSchedule.class, d.getDoc_id());
		if (d.getDocavgtime() != null) {
			sch.setAverageAppointmentTime(d.getDocavgtime());
		}
		if (d.getDocfrom() != null && d.getDocto() != null) {
			sch.setTimeFrom(d.getDocfrom().format(format));
			sch.setTimeTo(d.getDocto().format(format));
		}
		if (d.getWeekday() != null) {
			sch.setWeekday(UtilManager.converWeekdays(d.getWeekday()));
		}

		// UtilManager.converWeekdays(d.getWeekday());
		// d.getDocfrom().format(format), d.getDocto().format(format), d.getDocavgtime();
		em.merge(sch);

	}

	@Override
	public void deleteSchedule(DoctorUpdateModel d) {
		// TODO Auto-generated method stub

	}

}