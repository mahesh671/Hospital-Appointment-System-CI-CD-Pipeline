package spring.orm.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.input.ProfileInputModel;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.model.output.patientPrescriptionOutputmodel;

@Repository
public class PatientDAOImpl implements PatientDAO {
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);

	// method that saves the patient data to the database
	@Override
	@Transactional
	public int savePatientData(PatientModel patient) {
		logger.info("Entered into fetchTestNameWiseProfit");
		// Save a new patient to the database
		entityManager.persist(patient);
		logger.info("saved the patient information");
		return patient.getPatn_id();
	}

	// method that fetches the count for appointments and tests
	@Override
	@Transactional
	public List<Object> getAppointmentTestsCount(int patientId) {
		logger.info("Entered into getAppointmentTestsCount p: " + patientId);
		LocalDate currentDate = LocalDate.now();
		String d = currentDate.toString();

		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		List<Object> countData = new ArrayList<>();
		try {
			date = simpleFormat.parse(d);

			// Retrieve the list of appointments for the given patient on the current date
			List<AppointmentEntity> appointmentList = entityManager.createQuery(
					"SELECT a FROM AppointmentEntity a JOIN a.pm p WHERE CAST(a.appn_sch_date AS date) = :d AND p.patn_id = :p",
					AppointmentEntity.class).setParameter("d", date).setParameter("p", patientId).getResultList();

			logger.info("fetched the appointment lists for a specific patient pid: " + patientId);

			int app = appointmentList.size();

			// Retrieve the list of diagnostic tests for the given patient
			List<Integer> testList = entityManager.createQuery(
					"SELECT dt.id.dgbltestId FROM Diagnostictestbill dt, DiagnosticBillModel d, PatientModel p WHERE p.patn_id = :p AND d.dgbl_patn_id = p.patn_id AND d.dgbl_id = dt.id.dgblId")
					.setParameter("p", patientId).getResultList();

			logger.info("fetched the test conducted lists for a specific patient pid: " + patientId);

			int tests = testList.size();

			countData.add(app);
			countData.add(tests);

			logger.info("inserted the count data into the list");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("sending the  count data to the caller ");
		return countData;
	}

	// method that fetches all the patient info
	@Override
	@Transactional
	public List<PatientModel> getAllPatientInfo() {
		logger.info("Entered into getAppointmentTestsCount");
		// Retrieve all patient models from the database
		return entityManager.createQuery("SELECT p FROM PatientModel p", PatientModel.class).getResultList();
	}

	@Override
	public PatientModel getPatientById(int existingPatientid) {
		logger.info("Entered into getPatientById");
		// Retrieve a patient model by its ID from the database
		return entityManager.find(PatientModel.class, existingPatientid);
	}

	// method that adds new patient information into the database
	@Override
	@Transactional
	public int addNewPatient(PatientModel patient) {
		logger.info("Entered into addNewPatient");
		// Add a new patient to the database
		entityManager.persist(patient);
		logger.info("saved the patient information into the database");
		return patient.getPatn_id();
	}

	// method that fetches the appointment information into the database
	@Override
	public List<Object> getAppointmentsById(int patientId) {
		logger.info("Entered into getAppointmentsById pid: " + patientId);
		// Retrieve the list of appointments for the given patient ID
		String hql = "SELECT a.appn_id, d.doctName, a.appn_sch_date, a.appn_status FROM AppointmentEntity a JOIN a.doctor d WHERE a.pm.patn_id = :p";
		List<Object> lm2 = entityManager.createQuery(hql).setParameter("p", patientId).getResultList();

		logger.info("fetched all the appointments of the patient");
		return lm2;
	}

	// method that fetches all the PatientMedicalProfile based Id
	@Override
	@Transactional
	public List<PatientMedicalProfile> getPatientMedicalProfileById(int patientId) {
		logger.info("Entered into getPatientMedicalProfileById pid: " + patientId);

		// Retrieve the patient's medical profile for the given patient ID
		List<PatientMedicalProfile> patientMedicalProfileList = entityManager.createQuery("SELECT pp "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p",
				PatientMedicalProfile.class).setParameter("p", patientId).getResultList();

		logger.info("fetched all the PatientMedicalProfile of the patient");

		return patientMedicalProfileList;
	}

	// method that fetches all the PatientPrescription based Id
	@Override
	public List<patientPrescriptionOutputmodel> getPatientPrescriptionById(int patn_id) {
		logger.info("Entered into getPatientPrescriptionById pid: " + patn_id);

		// Retrieve the patient's prescription for the given patient ID
		List<patientPrescriptionOutputmodel> patientPrescriptionList = entityManager
				.createQuery("SELECT pm.patn_prescription FROM PatientMedicalProfile pm WHERE pm.id.patn_id = :patn_id",
						patientPrescriptionOutputmodel.class)
				.setParameter("patn_id", patn_id).getResultList();

		logger.info("fetched all the PatientPrescription of the patient");

		return patientPrescriptionList;
	}

	// method that fetches all the ParameterValues
	@Override
	@Transactional
	public List<ParaGroupOutput> getParameterValues(int pid) {
		logger.info("Entered into getParameterValues");

		// Retrieve the patient's medical profile with parameter groups
		String hql = "SELECT new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter, pp.patn_pargroup, pp.patn_value, a.appn_sch_date) "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p ";

		List<ParaGroupOutput> paraGroupList = entityManager.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", pid).getResultList();

		logger.info("fetched all the ParameterValues of the patient");

		return paraGroupList;
	}

	// method that fetches all the FamilyDetails by id
	public List<PatientModel> getFamilyDetailsById(int pat_id) {
		logger.info("Entered into getFamilyDetailsById pid: " + pat_id);

		// Retrieve all family members for the given patient ID
		String hql = "SELECT p1 FROM PatientModel p1 JOIN PatientModel p2 ON p1.accessPatientId = p2.patn_id WHERE p1.accessPatientId = :pat_id";

		TypedQuery<PatientModel> query = entityManager.createQuery(hql, PatientModel.class);
		query.setParameter("pat_id", pat_id);

		List<PatientModel> patients = query.getResultList();
		logger.info("fetched FamilyDetails of the patient");
		return patients;
	}

	// method that fetches all the PatientReports by id
	@Override
	public List<OutputPatientTestReports> getPatientReportsById(int pid) {
		logger.info("Entered into getPatientReportsById");

		// Retrieve the patient's test reports for the given patient ID
		String hql = "SELECT new spring.orm.model.output.OutputPatientTestReports(pdr.compositeKey.dgbl_id, dbm.dgbl_date, pdr.dgdr_path)"
				+ " FROM PatientDiagnonisticReports pdr"
				+ " JOIN DiagnosticBillModel dbm ON pdr.compositeKey.dgbl_id = dbm.dgbl_id "
				+ "WHERE 1 = 1 AND (dbm.dgbl_patn_id IN (SELECT f.pfmbPatnId FROM FamilyMembers f WHERE f.patnAccessPatnId = :p_id) OR dbm.dgbl_patn_id = :p_id)";

		List<OutputPatientTestReports> testReportData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputPatientTestReports.class).setParameter("p_id", pid)
				.getResultList();

		logger.info("fetched the patient test reports");

		return testReportData;
	}

	// method that fetches all the PatientNames
	@Override
	public List<PatientNameOutputModel> getAllPatientNames() {
		logger.info("Entered into getAllPatientNames");

		// Retrieve all patient IDs and names from the database
		String hql = "SELECT new spring.orm.model.output.PatientNameOutputModel(p.patn_id, p.patn_fname, p.patn_lname) "
				+ "FROM PatientModel p";

		List<PatientNameOutputModel> patientNameData = entityManager
				.createQuery(hql, spring.orm.model.output.PatientNameOutputModel.class).getResultList();

		logger.info("fetched the patient information");

		return patientNameData;
	}

	// method that fetches Last Appointment Info By patient Id
	@Override
	@Transactional
	public List<PatientlastvisitOutput> getLastAppointmentInfoById(int p) {
		logger.info("Entered into getLastAppointmentInfoById p: " + p);

		// Retrieve the patient's last visit details for the given patient ID
		String hql = "select new spring.orm.model.output.PatientlastvisitOutput(p.patn_lastvisit,d.doctName,a.appn_payamount,a.appn_sch_date) from AppointmentEntity a,PatientModel p,DoctorTemp d where p.patn_id=:p and p.patn_id=a.pm.patn_id and d.id=a.doctor.id and a.appn_status='YETO'";

		List<PatientlastvisitOutput> lastAppointmentData = entityManager
				.createQuery(hql, spring.orm.model.output.PatientlastvisitOutput.class).setParameter("p", p)
				.getResultList();

		logger.info("fetched the patient last appointment information");

		return lastAppointmentData;
	}

	// method that fetches PatientTests by patient id
	@Override
	public List<Object> getPatientTestsById(int p) {
		logger.info("Entered into getPatientTestsById p: " + p);

		String hql = "SELECT d.id.dgbltestId, t.test_name FROM Diagnostictestbill d, TestModel t, DiagnosticBillModel d1 WHERE d.id.dgblId = d1.dgbl_id AND d.id.dgbltestId = t.test_id AND d1.dgbl_patn_id = :p";
		// Retrieve the diagnostic test IDs and names for the given patient ID
		List<Object> patientTestsData = entityManager.createQuery(hql).setParameter("p", p).getResultList();

		logger.info("fetched the patient test details");

		return patientTestsData;
	}

	// method that fetches Parameter Values by patient id
	public List<ParaGroupOutput> getParameterValuesChart(int p) {
		logger.info("Entered into getParameterValuesChart p: " + p);

		// Retrieve the patient's medical profile with parameter groups for the given patient ID
		String hql = "SELECT new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter, pp.patn_pargroup, pp.patn_value, a.appn_sch_date) "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p";

		List<ParaGroupOutput> parameterValuesList = entityManager.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", p).getResultList();

		logger.info("fetched the patient parameter values");

		return parameterValuesList;
	}

	// method that fetches Patient Ids
	@Transactional
	public List<Integer> getPatientIds() {
		logger.info("Entered into getPatientIds");

		String hql = "SELECT p.patn_id FROM PatientModel p, AppointmentEntity a WHERE a.appn_status = 'YETO' AND a.pm.patn_id = p.patn_id";

		logger.info("fetched the patient ids list");
		// Retrieve all patient IDs with pending appointments
		return entityManager.createQuery(hql).getResultList();
	}

	// method that updates last visit date and appointment id for the particular patient
	@Override
	@Transactional
	public void updateLastvisitAndLastAppointmentInfo(int pat_id, LocalDate last_visited, int app_id) {
		logger.info("Entered into updateLastvisitAndLastAppointmentInfo pid: " + pat_id);

		// Update the patient's last visit and last appointment details
		PatientModel patient = entityManager.find(PatientModel.class, pat_id);
		patient.setPatn_lastapp_id(app_id);
		patient.setPatn_lastvisit(last_visited);
		logger.info("set the  Lastvisit And LastAppointment Information");

		entityManager.merge(patient);
		logger.info("saved the information into database");
	}

	// method which fetches the patient contact info
	public String getContactNumber(int pid) {
		logger.info("Entered into getContactNumber pid: " + pid);
		// select patn_contact from patients where patn_id=25

		String hql = "Select pm.patn_contact from PatientModel pm where pm.patn_id=:val";

		logger.info("fetching the contact number");

		return (String) entityManager.createQuery(hql).setParameter("val", pid).getSingleResult();
	}

	// method which updates the patient information
	@Transactional
	public void updatePatientData(ProfileInputModel pim, PatientSession ps) {
		logger.info("updatePatientData");

		PatientModel patientModel = entityManager.find(PatientModel.class, ps.getId());

		patientModel.setPatn_id(ps.getId());
		patientModel.setPatn_name(pim.getPatnName());
		patientModel.setPatn_age(pim.getPatnAge());
		patientModel.setPatn_gender(pim.getPatnGender());
		patientModel.setPatn_bgroup(pim.getPatnBgroup().trim());
		patientModel.setAccessPatientId(ps.getAccessPatientId());
		patientModel.setPatn_rdate(ps.getRegistrationDate());
		patientModel.setPatn_lastvisit(ps.getLastVisitDate());
		patientModel.setPatn_lastapp_id(ps.getLastAppointmentId());
		patientModel.setPatn_contact(getContactNumber(ps.getId()));

		entityManager.merge(patientModel);

		logger.info("updated the patient data");

	}

}