package spring.orm.contract;

import spring.orm.model.PatientModel;
import spring.orm.model.UserPass;

public interface UserDao {
	public UserPass getUserDetails(String s);

	public boolean saveUser(String ottps, int time);

	public void updateUser(UserPass u);

	public void registeruser(String name, String pass, String mail, String role);

	public void saveUser(UserPass user);

	public PatientModel getPatientDetails(Integer patn_id);

}