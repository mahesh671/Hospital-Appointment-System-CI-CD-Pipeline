package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.DAO.FamilyMembersDAO;
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
		String hql = "SELECT new spring.orm.model.input.FamilyMembersInput(f.pfmbPatnId,f.pfmbName, f.pfmbRelation, f.pfmbAge, p.patn_gender, p.patn_bgroup) "
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

	// method that fetches the family member details across the database
	public FamilyMembersInput getFamilyMemberByPatientId(int pid, int fpid) {
		logger.info("Entered into getFamilyMemberByPatientId pid: " + pid);

		// query to retrieve family member information
		String hql = "SELECT new spring.orm.model.input.FamilyMembersInput(f.pfmbPatnId,f.pfmbName,f.pfmbRelation,f.pfmbAge, p.patn_gender, p.patn_bgroup) "
				+ "FROM PatientModel p JOIN FamilyMembers f ON p.patn_id = f.pfmbPatnId WHERE p.accessPatientId = :accessId and f.pfmbPatnId=:val";

		logger.info("fetching the family member information from the database");
		return entityManager.createQuery(hql, FamilyMembersInput.class).setParameter("accessId", pid)
				.setParameter("val", fpid).getSingleResult();

	}

	// method that fetches the family member data from database
	public FamilyMembers getFamilyMember(int pfmid) {
		logger.info("Enter into getFamilyMember");

		logger.info("fetching the family member data");
		return entityManager.find(FamilyMembers.class, pfmid);

	}

	// method that saves the changes made in family member information
	@Transactional
	public void saveChanges(FamilyMembersInput fam) {
		logger.info("Entered into saveChanges");
		int fpid = fam.getPfmbPatnId();

		// fetching member info from patient table
		PatientModel patientFamilyData = entityManager.find(PatientModel.class, fam.getPfmbPatnId());

		// fetching the member info from family table
		FamilyMembers familyMemberData = entityManager.find(FamilyMembers.class, fam.getPfmbPatnId());

		// changing the values in patient table
		patientFamilyData.setPatn_name(fam.getPfmbName());
		patientFamilyData.setPatn_age(fam.getPfmbAge());
		patientFamilyData.setPatn_gender(fam.getPfmbGender());
		patientFamilyData.setPatn_bgroup(fam.getPfmbbgroup());

		// changing the values in family member table
		familyMemberData.setPfmbName(fam.getPfmbName());
		familyMemberData.setPfmbAge(fam.getPfmbAge());
		familyMemberData.setPfmbRelation(fam.getPfmbRelation());

		// saves the patient model
		entityManager.merge(patientFamilyData);

		// saves the family member model
		entityManager.merge(familyMemberData);

		logger.info("saved the changes");

	}

	// method that deletes the family member from database (soft delete)
	@Transactional
	public void deleteFamilyMember(int fpid) {
		logger.info("Entered into deleteFamilyMember");

		// fetching member info from patient table
		PatientModel patientFamilyData = entityManager.find(PatientModel.class, fpid);

		// fetching the member info from family table
		FamilyMembers familyMemberData = entityManager.find(FamilyMembers.class, fpid);

		// making the access id null
		patientFamilyData.setAccessPatientId(null);
		// making the access id null
		familyMemberData.setPatnAccessPatnId(null);

		// saves the patient model
		entityManager.merge(patientFamilyData);

		// saves the family member model
		entityManager.merge(familyMemberData);

		logger.info("saved the changes");
	}

}