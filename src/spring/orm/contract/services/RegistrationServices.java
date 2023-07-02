package spring.orm.contract.services;

import javax.servlet.http.HttpSession;

import spring.orm.contract.DAO.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.UserPass;
import spring.orm.model.input.RegistrationForm;

public interface RegistrationServices {

	void registerPatient(RegistrationForm rf);

	UserPass getUser(String uname);

	void updateUser(UserPass user);

	boolean isUsernameAvailable(String username);

	PatientModel getPatientDetails(int patn_id);

	PatientSession createPatientSession(UserPass user, PatientModel patientModel);

	void storePatientSession(HttpSession session, PatientSession patientSession);

	void setPatientDAO(PatientDAO patientDAO);

}