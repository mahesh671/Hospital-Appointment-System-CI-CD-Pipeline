package spring.orm.contract.DAO;

import java.util.List;

import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;

public interface DoctorsDAO {

	// Retrieves a DoctorTemp object by its ID
	public DoctorTemp getDoctor(int Id);

	// Updates a DoctorTemp object
	public void updatedoc(DoctorTemp d);

	// Saves a DoctorTemp object
	void saveDoc(DoctorTemp s);

	// Retrieves a list of DoctorList objects based on the specified specialty and
	// search criteria
	public List<DoctorList> getallDocScheduleBySpec(String spec, String like);

	// Retrieves a DoctorOutPutModel object by its ID
	public DoctorOutPutModel getDocById(int id);

	// Retrieves a list of DoctorOutPutModel objects representing all doctors
	public List<DoctorOutPutModel> getallDocSchedule();

	// Retrieves a list of all DoctorTemp objects
	public List<DoctorTemp> getAllDoc();

	// Deletes a DoctorTemp object by its ID
	public void deletedoc(int id);

	// Returns the count of doctors
	public Long findCount();
}