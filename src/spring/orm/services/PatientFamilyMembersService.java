package spring.orm.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.FamilyMembersDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.input.FamilyMembersInput;

@Service
public class PatientFamilyMembersService {
	FamilyMembersDAO familyMemberDao;

	private PatientDAO patientDao;

	@Autowired
	public PatientFamilyMembersService(FamilyMembersDAO familyMemberDao, PatientDAO patientDao) {
		super();
		this.familyMemberDao = familyMemberDao;
		this.patientDao = patientDao;
	}

	private static final Logger logger = LoggerFactory.getLogger(PatientFamilyMembersService.class);

	// Method that saves the family member into the database
	@Transactional
	public int addFamilyMember(FamilyMembersInput familyMember, PatientSession patientSession) {

		logger.info("Entered into addFamilyMember");

		// Create a new PatientModel object and set the required information
		PatientModel patientModel = new PatientModel();
		patientModel.setPatn_name(familyMember.getPfmbName());
		patientModel.setPatn_age(familyMember.getPfmbAge());
		patientModel.setPatn_gender(familyMember.getPfmbGender());
		patientModel.setPatn_bgroup(familyMember.getPfmbbgroup());
		patientModel.setAccessPatientId(patientSession.getId());
		logger.info("Created a new PatientModel object and set the required information");

		// Save the PatientModel and get the patient ID
		int pid = familyMemberDao.savePatientDetails(patientModel);
		logger.info("Saved the PatientModel and get the patient ID");

		// Add the family member using the FamilyMembersDao
		familyMemberDao.addFamilyByPatientInfo(patientModel, pid, patientSession.getId(), familyMember.getPfmbRelation());
		logger.info("Saved the family member information into the database");

		return patientModel.getPatn_id();
	}

	// method to fetch all family members for the given ID using the FamilyMembersDao
	public List<FamilyMembersInput> getAllFamilyMembers(Integer patientId) {
		logger.info("Entered into getAllFamilyMembers");

		logger.info("Retrieving all family members data for the given ID using the FamilyMembersDao");
		// Retrieve all family members for the given ID using the FamilyMembersDao
		return familyMemberDao.getFamilyDetailsByPatientId(patientId);
	}

	// method that fetches family member information
	public FamilyMembersInput getFamilyMemberInfo(int patientId, int familyMemberid) {
		logger.info("Entered into getFamilyMemberInfo");

		return familyMemberDao.getFamilyMemberByPatientId(patientId, familyMemberid);
	}

	// method that saves the family member details
	public void saveFamilyMemberInfo(FamilyMembersInput familyMember) {
		logger.info("Entered into saveFamilyMemberInfo");
		familyMemberDao.saveChanges(familyMember);
	}

	// method that deletes the family member
	public void deleteFamilyMember(int patientId) {
		logger.info("Entered into deleteFamilyMember");
		familyMemberDao.deleteFamilyMember(patientId);
	}

}