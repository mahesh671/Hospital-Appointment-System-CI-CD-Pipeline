package spring.orm.contract;

import java.util.List;

import spring.orm.model.PatientModel;
import spring.orm.model.entity.FamilyMembers;
import spring.orm.model.input.FamilyMembersInput;

public interface FamilyMembersDAO {

	int savePatientDetails(PatientModel pm);

	void addFamilyByPatientInfo(PatientModel pm, int pid, Integer id, String relation);

	List<FamilyMembersInput> getFamilyDetailsByPatientId(Integer id);

	public FamilyMembersInput getFamilyMemberByPatientId(int pid, int fpid);

	public FamilyMembers getFamilyMember(int pfmid);

	public void saveChanges(FamilyMembersInput fam);

	public void deleteFamilyMember(int pid);
}