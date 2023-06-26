package spring.orm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.PatientDao;
import spring.orm.contract.UserDao;
import spring.orm.model.PatientModel;
import spring.orm.model.UserPass;
import spring.orm.model.input.RegistrationForm;

@Service
public class RegistrationService {

	@Autowired
	private PatientDao patientDao;
	@Autowired
	private UserDao udao;

	public void registerPatient(RegistrationForm rf) {
		PatientModel patient = new PatientModel();
		patient.setPatn_name(rf.getName());
		patient.setPatn_age(rf.getAge());
		patient.setPatn_gender(rf.getGender());

		// Perform additional logic if needed

		int id = patientDao.savePatient(patient);
		UserPass user = new UserPass();
		user.setUsername(rf.getUname());
		user.setPassword(rf.getPass());
		user.setPatient(patient);
		user.setMail(rf.getGmail());
		user.setrole(rf.getRole());
		udao.saveUser(user);

	}

	public UserPass getUser(String uname) {
		// TODO Auto-generated method stub
		return udao.getUserDetails(uname);
	}

	public void updateUser(UserPass user) {
		// TODO Auto-generated method stub
		udao.updateUser(user);

	}

	public boolean isUsernameAvailable(String username) {
		// TODO Auto-generated method stub
		UserPass userDetails = udao.getUserDetails(username);
		if (userDetails != null && userDetails.getUsername().equals(username)) {
			return false;
		}
		return true;
	}

	public PatientModel getPatientDetails(int patn_id) {
		// TODO Auto-generated method stub
		return udao.getPatientDetails(patn_id);
	}
}