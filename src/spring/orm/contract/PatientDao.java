package spring.orm.contract;

import java.time.LocalDate;
import java.util.List;

import spring.orm.model.PatientModel;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.model.output.patientPrescriptionOutputmodel;

public interface PatientDao {
	public List<Object> getapptestcards(int id);

	public List<PatientModel> getAllPatientModels();

	public PatientModel getPatientById(int existingPatientid);

	public int addNewPatient(PatientModel p);

	public int savePatient(PatientModel patient);

	List<Integer> getAllPatientids();

	public List<OutputPatientTestReports> getPatientReports(int id);

	public List<patientPrescriptionOutputmodel> getPrescription(int patn_id);

	public List<ParaGroupOutput> getParaGroupParaout();

	public List<PatientModel> getAllFamily(int pat_id);

	public List<PatientNameOutputModel> getAllPatientidsNames();

	public List<PatientlastvisitOutput> getlastvisit(int p);

	public List<PatientMedicalProfile> getParaGroup(int p);

	public List<Object> getapptests(int p);

	public List<Object> getapps(int p);

	public List<ParaGroupOutput> getParaGroupParaout(int p);

	public void updateLastvisitAndLastAppointment(int pat_id, LocalDate last_visited, int app_id);

}
