package spring.orm.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.PatientDAO;
import spring.orm.contract.UserDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.UserPass;
import spring.orm.model.input.RegistrationForm;

@Service
public class RegistrationService {

	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private UserDAO udao;

	public void registerPatient(RegistrationForm rf) {
		/*
		 * This method is responsible for registering a new patient based on the provided RegistrationForm object.
		 */
		PatientModel patient = new PatientModel();
		patient.setPatn_name(rf.getName());
		patient.setPatn_age(rf.getAge());
		patient.setPatn_gender(rf.getGender());

		// Perform additional logic if needed

		int id = patientDAO.savePatientData(patient);
		UserPass user = new UserPass();
		user.setUsername(rf.getUname());
		user.setPassword(rf.getPass());
		user.setPatient(patient);
		user.setMail(rf.getGmail());
		user.setrole(rf.getRole());
		udao.saveUser(user);

	}

	public UserPass getUser(String uname) {
		// This method retrieves the UserPass object based on the provided username.

		return udao.getUserDetails(uname);
	}

	public void updateUser(UserPass user) {
		// This method updates the user's information in the database.

		udao.updateUser(user);

	}

	public boolean isUsernameAvailable(String username) {
		// This method checks the availability of a username.

		UserPass userDetails = udao.getUserDetails(username);
		if (userDetails != null && userDetails.getUsername().equals(username)) {
			return false;
		}
		return true;
	}

	public PatientModel getPatientDetails(int patn_id) {
		// This method retrieves the PatientModel object based on the provided patient ID.

		return udao.getPatientDetails(patn_id);
	}

	public PatientSession createPatientSession(UserPass user, PatientModel patientModel) {
		PatientSession patientSession = new PatientSession();
		patientSession.setId(patientModel.getPatn_id());
		patientSession.setUsername(user.getUsername());
		patientSession.setName(patientModel.getPatn_name());
		patientSession.setAge(patientModel.getPatn_age());
		patientSession.setGender(patientModel.getPatn_gender().charAt(0));
		patientSession.setAccessPatientId(patientModel.getAccessPatientId());
		patientSession.setBloodGroup(patientModel.getPatn_bgroup());
		patientSession.setRegistrationDate(patientModel.getPatn_rdate());
		patientSession.setLastVisitDate(patientModel.getPatn_lastvisit());
		patientSession.setEmail(user.getMail());
		return patientSession;
	}

	public void storePatientSession(HttpSession session, PatientSession patientSession) {
		session.setAttribute("patientSession", patientSession);
	}

	 public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }
}