package spring.orm.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.PatientDao;
import spring.orm.contract.FamilyMembersDao;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.Specialization;
import spring.orm.model.entity.FamilyMembers;
import spring.orm.model.input.FamilyMembersInput;
import spring.orm.model.output.FamilyOutputModel;

@Service
public class PatientFamilyMembersService {
	@Autowired
	FamilyMembersDao fmdao;
	@Autowired
	private PatientDao pdao;
	

	@Transactional
	public int addfm(FamilyMembersInput fm, PatientSession patientSession) {
		// TODO Auto-generated method stub
		PatientModel pm = new PatientModel();
		pm.setPatn_name(fm.getPfmbName());
		pm.setPatn_age(fm.getPfmbAge());
		pm.setPatn_gender(fm.getPfmbGender());
		pm.setPatn_bgroup(fm.getPfmbbgroup());
		pm.setAccessPatientId(patientSession.getId());
		System.out.println(pm);
		int pid = fmdao.savefm(pm);
		System.out.println(pid);
		fmdao.addfamily(pm, pid, patientSession.getId(),fm.getPfmbRelation());
		return pm.getPatn_id();
	}


	public List<FamilyMembersInput> getAllFamilyMembers(Integer id) {
		// TODO Auto-generated method stub
		System.out.println("in getfamily dao");
		return fmdao.getfamily(id);
	}

}