package spring.orm.contract;

import java.util.List;

import spring.orm.model.Specialization;

public interface SpecializationDAO {

	public Specialization getSpecialization(String Id);

	public List<Specialization> getAllSpec();

	public void delSpec(String id);

	void addSpec(Specialization s);

	void updateSpec(Specialization s);

	// public void addSpec(Specialization s);

	// public List<Specialization> getspec();
	//
	// public void savespec(String id,String title,String description);
	//
	// public void savetest(String idInput, String titleInput, String descriptionInput);
	//
	// Specialization gettestbyid(String id);
}
