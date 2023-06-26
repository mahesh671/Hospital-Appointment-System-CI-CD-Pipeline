package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import spring.orm.contract.UserDao;
import spring.orm.model.DiagnosticBillModel;
import spring.orm.model.PatientModel;
import spring.orm.model.Specialization;
import spring.orm.model.UserPass;

@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager em;

	public UserPass getUserDetails(String uname) {
		try {
			String sql = "SELECT u FROM UserPass u WHERE u.username = :uname";
			TypedQuery<UserPass> query = em.createQuery(sql, UserPass.class).setParameter("uname", uname);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean saveUser(String ottps, int time) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("is_otp_expired");
		// Register the input parameters
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);

		// Set the input parameters
		query.setParameter(1, ottps);
		query.setParameter(2, 3);
		query.execute();

		boolean isExpired = (boolean) query.getSingleResult();
		return isExpired;
	}

	@Transactional
	public void updateUser(UserPass u) {

		em.merge(u);
	}

	@Transactional
	public void registeruser(String name, String pass, String mail, String role) {

		UserPass user = new UserPass(name, pass, mail, role);
		em.persist(user);
		System.out.println("User registered successfully");
	}

	public List<Specialization> getpecdetails() {
		return em.createQuery("SELECT s from Specialization s", Specialization.class).getResultList();

	}

	public List<DiagnosticBillModel> getdbilldetails() {
		return em.createQuery("SELECT d FROM diagnosticBillModel d", DiagnosticBillModel.class).getResultList();
	}

	@Override
	@Transactional
	public void saveUser(UserPass user) {
		em.persist(user);

	}

	@Override
	public PatientModel getPatientDetails(Integer patn_id) {
		System.out.println(patn_id);
		return em.find(PatientModel.class, patn_id);
	}

}
