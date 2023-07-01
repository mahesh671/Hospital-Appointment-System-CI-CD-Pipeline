package spring.orm.contract;

import java.time.LocalDate;
import java.util.List;

import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.input.ProfileInputModel;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.model.output.patientPrescriptionOutputmodel;

public interface PatientDAO {
	public List<Object> getAppointmentTestsCount(int id);

	public List<PatientModel> getAllPatientInfo();

	public PatientModel getPatientById(int existingPatientid);

	public int addNewPatient(PatientModel p);

	public int savePatientData(PatientModel patient);

	List<Integer> getPatientIds();

	public List<OutputPatientTestReports> getPatientReportsById(int id);

	public List<patientPrescriptionOutputmodel> getPatientPrescriptionById(int patn_id);

	public List<ParaGroupOutput> getParameterValues(int patn_id);

	public List<PatientModel> getFamilyDetailsById(int pat_id);

	public List<PatientNameOutputModel> getAllPatientNames();

	public List<PatientlastvisitOutput> getLastAppointmentInfoById(int p);

	public List<PatientMedicalProfile> getPatientMedicalProfileById(int p);

	public List<Object> getPatientTestsById(int p);

	public List<Object> getAppointmentsById(int p);

	public List<ParaGroupOutput> getParameterValuesChart(int p);

	public void updateLastvisitAndLastAppointmentInfo(int pat_id, LocalDate last_visited, int app_id);

	public String getContactNumber(int pid);

	public void updatePatientData(ProfileInputModel pim, PatientSession ps);

}
