package spring.orm.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.FamilyMembersDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.input.FamilyMembersInput;

@Service
public class PatientFamilyMembersService {
	@Autowired
	FamilyMembersDAO fmdao;
	@Autowired
	private PatientDAO pdao;

	@Transactional
	public int addFamilyMember(FamilyMembersInput fm, PatientSession patientSession) {
		// Create a new PatientModel object and set the required information
		PatientModel pm = new PatientModel();
		pm.setPatn_name(fm.getPfmbName());
		pm.setPatn_age(fm.getPfmbAge());
		pm.setPatn_gender(fm.getPfmbGender());
		pm.setPatn_bgroup(fm.getPfmbbgroup());
		pm.setAccessPatientId(patientSession.getId());

		// Save the PatientModel and get the patient ID
		int pid = fmdao.savePatientDetails(pm);

		// Add the family member using the FamilyMembersDao
		fmdao.addFamilyByPatientInfo(pm, pid, patientSession.getId(), fm.getPfmbRelation());

		return pm.getPatn_id();
	}

	public List<FamilyMembersInput> getAllFamilyMembers(Integer id) {
		// Retrieve all family members for the given ID using the FamilyMembersDao
		return fmdao.getFamilyDetailsByPatientId(id);
	}

}