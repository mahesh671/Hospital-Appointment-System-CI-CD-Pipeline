package spring.orm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.orm.contract.DocScheduleDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;

@Repository
public class DoctorDaoTempImpl implements DoctorsDaoTemp {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DocScheduleDao docschedule;

	@Override
	@Transactional
	public Long findCount() {
		// TODO Auto-generated method stub
		System.out.println("in dao");
		Long d = em.createQuery("select count(d) from DoctorTemp d where d.isDeleted = :status", Long.class)

				.setParameter("status", false).getSingleResult();
		return d;
	}

	@Transactional
	@Override
	public List<DoctorTemp> getAllDoc() {
		// TODO Auto-generated method stub

		return em.createQuery("select d from DoctorTemp d", DoctorTemp.class).getResultList();
	}

	@Transactional
	public List<DoctorTemp> getAllDocSpec(String Spec) {
		// TODO Auto-generated method stub

		return em.createQuery("select d from Doctor d where d.specialization.id=: spec", DoctorTemp.class)
				.setParameter("spec", Spec).getResultList();

	}

	@Transactional
	public DoctorTemp getdoc(int Id) {
		// TODO Auto-generated method stub

		return em.createQuery("select d from DoctorTemp d where d.doctId=:doct ",DoctorTemp.class).setParameter("doct", Id).getSingleResult();
	}

	@Transactional
	@Override
	public void saveDoc(DoctorTemp s) {
		// TODO Auto-generated method stub
		System.out.println("hello");

		em.persist(s);
	}

	@Transactional
	@Override
	public void updatedoc(DoctorTemp s) {
		// TODO Auto-generated method stub

		System.out.println("In Update Spec");
		em.merge(s);

	}

	@Override
	public List<DoctorList> getallDocScheduleBySpec(String spec, String like) {
		// TODO Auto-generated method stub
		List<DoctorList> dout = em.createQuery(
				"select new spring.orm.model.output.DoctorList( d.doctId,d.doctName) from DoctorTemp d where (d.schedule.weekday ='ALL' or d.schedule.weekday like CONCAT('%', :like, '%')) and d.specialization.id=:spec and d.isDeleted = false ",
				DoctorList.class).setParameter("like", like).setParameter("spec", spec).getResultList();
			
		return dout;
	}

	@Override
	public DoctorOutPutModel getDocById(int id) {
		// TODO Auto-generated method stub
		DoctorOutPutModel d = new DoctorOutPutModel();
		d.setD(em.find(DoctorTemp.class, id));
		d.setDocsche(docschedule.getSchedulebyId(d.getDoctId()));

		return d;

	}

	public List<DoctorOutPutModel> getallDocSchedule() {
		// TODO Auto-generated method stub
		List<DoctorOutPutModel> dout = new ArrayList<>();
		for (DoctorTemp d : em.createQuery("select d from DoctorTemp d where isDeleted = false", DoctorTemp.class)
				.getResultList()) {
			dout.add(new DoctorOutPutModel(d, docschedule.getSchedulebyId(d.getDoctId())));
		}
		return dout;

	}

	@Override
	public void deletedoc(int id) {
		DoctorTemp d = em.find(DoctorTemp.class, id);
		d.setDeleted(true);

		em.merge(d);

	}

}
