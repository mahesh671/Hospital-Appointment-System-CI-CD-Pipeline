package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.SpecializationDao;
import spring.orm.model.Specialization;

@Component
public class SpecializationDaoImpl implements SpecializationDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public Specialization getSpecialization(String Id) {
		// TODO Auto-generated method stub
		return em.find(Specialization.class, Id);
	}

	@Override
	public List<Specialization> getAllSpec() {
		// TODO Auto-generated method stub
		return em.createQuery("select s from Specialization s where isDeleted = false", Specialization.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void delSpec(String id) {
		// TODO Auto-generated method stub
		// System.out.println("daoLayer");
		// Query q = em.createQuery("delete from Specialization s where s.id=:id");
		// q.setParameter("id", id);
		// System.out.println(q.executeUpdate());
		System.out.println(id);
		Specialization s = em.find(Specialization.class, id);
		s.setDeleted(true);
		em.merge(s);
	}

	@Transactional
	@Override
	public void addSpec(Specialization s) {
		// TODO Auto-generated method stub

		if (em.find(Specialization.class, s.getId()) == null) {
			em.persist(s);
		} else {
			Specialization alreadyexisted = em.find(Specialization.class, s.getId());
			alreadyexisted.setDeleted(false);
			em.merge(alreadyexisted);
		}
	}

	@Transactional
	@Override
	public void updateSpec(Specialization s) {
		// TODO Auto-generated method stub

		em.merge(s);

	}

	// public List<Specialization> getspec() {
	//
	// return em.createQuery("SELECT t FROM Specialization t", Specialization.class).getResultList();
	// }

	// @Override

	// public void savespec(String idInput,String titleInput,String descriptionInput) {
	// StoredProcedureQuery query = em.createStoredProcedureQuery("insert_test");
	// System.out.println("called");
	// // query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	// query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	// query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	// query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
	//
	// query.setParameter(1, idInput);
	// query.setParameter(2, titleInput);
	// query.setParameter(3, descriptionInput);
	//
	//
	// query.execute();
	// System.out.println("inserted");
	// }

	// @Override
	// public Specialization gettestbyid(String id) {
	// // TODO Auto-generated method stub
	// //
	// // System.out.println(
	// // em.createQuery("select t from testModel t where t.test_id=id", testModel.class).getSingleResult());
	// // return em.createQuery("select t from testModel t where t.test_id=id", testModel.class).getResultList();
	// // // TODO Auto-generated method stub
	// return em.createQuery("select t from testModel t where t.test_id=:id",Specialization.class).setParameter("id",
	// id)
	// .getSingleResult();
	//
	// }

	// TODO Auto-generated method stub

}