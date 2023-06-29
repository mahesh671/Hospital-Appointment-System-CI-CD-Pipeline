package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.FamilyMembersDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.FamilyMembers;
import spring.orm.model.input.FamilyMembersInput;

@Component
public class FamilyMembersDAOImpl implements FamilyMembersDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(FamilyMembersDAOImpl.class);

	// method that saves the patient information into database
	@Override
	public int savePatientDetails(PatientModel pm) {
		logger.info("Entered into savePatientDetails");

		// Save the patient model to the database and return the generated patient ID
		entityManager.persist(pm);

		logger.info("saved the patient information into the database");
		return pm.getPatn_id();
	}

	// method that adds the family member information to a particular database
	@Override
	@Transactional
	public void addFamilyByPatientInfo(PatientModel pm, int pid, Integer id, String relation) {
		logger.info("Entered into addFamilyByPatientInfo pid: " + pid);

		// Add a family member to the database for the given patient ID, access ID, and relation
		FamilyMembers fm = new FamilyMembers();
		fm.setPatnAccessPatnId(id);
		fm.setPfmbPatnId(pid);
		fm.setPfmbName(pm.getPatn_name());
		fm.setPfmbAge(pm.getPatn_age());
		fm.setPfmbRelation(relation);
		logger.info("saved the patient information to the family member object");

		entityManager.persist(fm);
		logger.info("saved the patient information into the database");
	}

	// method that fetches the family details of a specific patient by his patient id
	@Override
	public List<FamilyMembersInput> getFamilyDetailsByPatientId(Integer pid) {
		logger.info("Entered into getFamilyDetailsByPatientId pid: " + pid);

		// Query that retrieve the family members for the given access ID
		String hql = "SELECT new spring.orm.model.input.FamilyMembersInput(f.pfmbName, f.pfmbRelation, f.pfmbAge, p.patn_gender, p.patn_bgroup) "
				+ "FROM PatientModel p JOIN FamilyMembers f ON p.patn_id = f.pfmbPatnId WHERE p.accessPatientId = :accessId";

		// returns a query object
		TypedQuery<FamilyMembersInput> query = entityManager.createQuery(hql, FamilyMembersInput.class);
		query.setParameter("accessId", pid);

		logger.info("fetched the patient information from the database");

		// returns a list of family member information in the form of list
		List<FamilyMembersInput> familyMembersList = query.getResultList();

		logger.info("returns the family member data list to the calling method");

		return familyMembersList;
	}

}