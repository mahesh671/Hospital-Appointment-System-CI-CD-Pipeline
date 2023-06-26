package spring.orm.contract;

import spring.orm.model.DoctorSchedule;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;

public interface DocScheduleDao {

	public DoctorSchedule getSchedulebyId(int id);

	public void updateSchedule(DoctorUpdateModel d);

	public void addDoctorSchedule(DoctorInput d, int docid);

	public void deleteSchedule(DoctorUpdateModel d);

}
