package spring.orm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import spring.orm.contract.FamilyMembersDao;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.FamilyMembers;
import spring.orm.model.input.FamilyMembersInput;

@Component
public class FamilyMembersDaoImpl implements FamilyMembersDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public int savefm(PatientModel pm) {
		// TODO Auto-generated method stub
		System.out.println(pm);
		em.persist(pm);

		return pm.getPatn_id();
	}

	@Override
	@Transactional

	public void addfamily(PatientModel pm, int pid, Integer id,String relation) {
		// TODO Auto-generated method stub
		FamilyMembers fm = new FamilyMembers();
		fm.setPatnAccessPatnId(id);
		fm.setPfmbPatnId(pid);
		fm.setPfmbName(pm.getPatn_name());
		fm.setPfmbAge(pm.getPatn_age());
		fm.setPfmbRelation(relation);
		System.out.println(id);
		em.persist(fm);
	}
	SessionFactory sessionFactory;
	@Override
	public List<FamilyMembersInput> getfamily(Integer id) {
		 
		    String hql = " select new spring.orm.model.input.FamilyMembersInput( f.pfmbName, f.pfmbRelation, f.pfmbAge, p.patn_gender,"
		    		+ " p.patn_bgroup) FROM PatientModel p join FamilyMembers f on p.patn_id= f.pfmbPatnId where p.accessPatientId=:accessId ";
		    TypedQuery<FamilyMembersInput> query = em.createQuery(hql, FamilyMembersInput.class);
		    query.setParameter("accessId", id);
		    List<FamilyMembersInput> familyMembersList = query.getResultList();

		    // Convert the FamilyMembers entity objects to FamilyMembersInput models
		 
		    return familyMembersList;
	}}