package spring.orm.contract;

import spring.orm.model.PatientModel;
import spring.orm.model.UserPass;

public interface UserDAO {
	// Retrieves and returns the user details for the given username or email
	public UserPass getUserDetails(String s);

	// Saves the user with the provided one-time password and expiration time
	public boolean saveUser(String ottps, int time);

	// Updates the user's details with the provided UserPass object
	public void updateUser(UserPass u);

	// Registers a new user with the given name, password, email, and role
	public void registeruser(String name, String pass, String mail, String role);

	// Saves the user with the provided UserPass object
	public void saveUser(UserPass user);

	// Retrieves and returns the patient details for the given patient ID
	public PatientModel getPatientDetails(Integer patn_id);

}