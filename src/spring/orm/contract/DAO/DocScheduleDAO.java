package spring.orm.contract.DAO;

import spring.orm.model.DoctorSchedule;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;

public interface DocScheduleDAO {

	// Retrieves a doctor's schedule based on the provided ID.
	public DoctorSchedule getSchedulebyId(int id);

	// Updates the doctor's schedule using the provided `DoctorUpdateModel` object.
	public void updateSchedule(DoctorUpdateModel d);

	/*
	 * Adds a new doctor's schedule using the provided `DoctorInput` object and
	 * doctor ID.
	 */
	public void addDoctorSchedule(DoctorInput d, int docid);

	/*
	 * public void deleteSchedule(DoctorUpdateModel d); 
	 * Deletes the doctor's
	 * schedule using the provided `DoctorUpdateModel` object.
	 */
}