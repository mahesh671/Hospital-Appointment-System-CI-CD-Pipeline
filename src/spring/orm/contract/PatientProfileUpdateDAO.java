package spring.orm.contract;

import java.util.List;

import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.PrescriptionOutputmodel;

public interface PatientProfileUpdateDAO {

	boolean save(PatientMedicalProfile pmp);

	List<PrescriptionOutputmodel> getallPrescription(int id);

	public List<Integer> getAllAppointmentIds(int patn_id);

}
