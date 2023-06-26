package spring.orm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.PrescriptionOutputmodel;

@Repository
public class PatientProfileUpdateDAOImpl implements PatientProfileUpdateDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void save(PatientMedicalProfile pmp) {
		// TODO Auto-generated method stub
		em.persist(pmp);
	}

	@Override
	@Transactional
	public List<PrescriptionOutputmodel> getallPrescription(int id) {

		String hql = "select new spring.orm.model.output.PrescriptionOutputmodel(a.appn_id,a.appn_sch_date,p.patn_parameter,p.patn_pargroup,p.patn_value,p.patn_prescription) "
				+ "from PatientMedicalProfile p,AppointmentEntity a where (p.id.patn_id in(select f.pfmbPatnId from FamilyMembers f where f.patnAccessPatnId=:p) or p.id.patn_id=:p ) and a.pm.patn_id=p.id.patn_id";
		System.out.println(hql);
		List<PrescriptionOutputmodel> lp = em.createQuery(hql, spring.orm.model.output.PrescriptionOutputmodel.class)
				.setParameter("p", id).getResultList();
		return lp;
	}

	@Override
	public List<Integer> getAllappnids(int patn_id) {
		// TODO Auto-generated method stub
		List<Integer> lp3 = new ArrayList<>();
		List<Integer> lp2 = new ArrayList<>(Collections.nCopies(10000, null));
		lp2 = em.createQuery("select a.appn_id "
				+ "from AppointmentEntity a where a.appn_id not in(select pmp.id.patn_appn_id from PatientMedicalProfile pmp where pmp.id.patn_id =: patn_id ) and a.pm.patn_id=:patn_id")
				.setParameter("patn_id", patn_id)

				.getResultList();
		System.out.println(lp2);

		return lp2;
	}

}
