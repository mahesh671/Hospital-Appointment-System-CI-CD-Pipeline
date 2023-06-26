package spring.orm.contract;

import java.util.List;

import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;

public interface DoctorsDaoTemp {

	public DoctorTemp getdoc(int Id);

	public void updatedoc(DoctorTemp d);

	void saveDoc(DoctorTemp s);

	public List<DoctorList> getallDocScheduleBySpec(String spec, String like);

	public DoctorOutPutModel getDocById(int id);

	public List<DoctorOutPutModel> getallDocSchedule();

	// public void updatedoc(DoctorInput d, CommonsMultipartFile docphoto);

	public List<DoctorTemp> getAllDoc();

	public void deletedoc(int id);

	public Long findCount();

}
