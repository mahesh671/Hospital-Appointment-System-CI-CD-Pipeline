package spring.orm.contract;

import java.util.List;

import spring.orm.model.PatientModel;
import spring.orm.model.input.FamilyMembersInput;

public interface FamilyMembersDao {

	int savefm(PatientModel pm);

	void addfamily(PatientModel pm, int pid, Integer id,String relation);

	List<FamilyMembersInput> getfamily(Integer id);

}