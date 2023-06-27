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

import org.springframework.stereotype.Repository;

import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.model.output.patientPrescriptionOutputmodel;

@Repository
public class PatientDAOImpl implements PatientDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public int savePatient(PatientModel patient) {
		// Save a new patient to the database
		em.persist(patient);
		return patient.getPatn_id();
	}

	@Override
	@Transactional
	public List<Object> getapptestcards(int p) {
		LocalDate currentDate = LocalDate.now();
		String d = currentDate.toString();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		List<Object> ls = new ArrayList<>();
		try {
			date = sdf.parse(d);

			// Retrieve the list of appointments for the given patient on the current date
			List<AppointmentEntity> lm = em.createQuery(
					"SELECT a FROM AppointmentEntity a JOIN a.pm p WHERE CAST(a.appn_sch_date AS date) = :d AND p.patn_id = :p",
					AppointmentEntity.class).setParameter("d", date).setParameter("p", p).getResultList();

			int app = lm.size();
			System.out.println(app);

			// Retrieve the list of diagnostic tests for the given patient
			List<Integer> tm = em.createQuery(
					"SELECT dt.id.dgbltestId FROM Diagnostictestbill dt, DiagnosticBillModel d, PatientModel p WHERE p.patn_id = :p AND d.dgbl_patn_id = p.patn_id AND d.dgbl_id = dt.id.dgblId")
					.setParameter("p", p).getResultList();
			int tests = tm.size();
			System.out.println(tests);

			ls.add(app);
			ls.add(tests);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	@Transactional
	public List<PatientModel> getAllPatientModels() {
		// Retrieve all patient models from the database
		return em.createQuery("SELECT p FROM PatientModel p", PatientModel.class).getResultList();
	}

	@Override
	public PatientModel getPatientById(int existingPatientid) {
		// Retrieve a patient model by its ID from the database
		return em.find(PatientModel.class, existingPatientid);
	}

	@Override
	@Transactional
	public int addNewPatient(PatientModel p) {
		// Add a new patient to the database
		em.persist(p);
		return p.getPatn_id();
	}

	@Override
	public List<Object> getapps(int pid) {
		// Retrieve the list of appointments for the given patient ID
		List<Object> lm2 = em.createQuery(
				"SELECT a.appn_id, d.doctName, a.appn_sch_date, a.appn_status FROM AppointmentEntity a JOIN a.doctor d WHERE a.pm.patn_id = :p")
				.setParameter("p", pid).getResultList();
		return lm2;
	}

	@Override
	@Transactional
	public List<PatientMedicalProfile> getParaGroup(int pid) {
		// Retrieve the patient's medical profile for the given patient ID
		List<PatientMedicalProfile> lp = em.createQuery("SELECT pp "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p",
				PatientMedicalProfile.class).setParameter("p", pid).getResultList();
		return lp;
	}

	@Override
	public List<patientPrescriptionOutputmodel> getPrescription(int patn_id) {
		// Retrieve the patient's prescription for the given patient ID
		List<patientPrescriptionOutputmodel> lp = em
				.createQuery("SELECT pm.patn_prescription FROM PatientMedicalProfile pm WHERE pm.id.patn_id = :patn_id",
						patientPrescriptionOutputmodel.class)
				.setParameter("patn_id", patn_id).getResultList();
		return lp;
	}

	@Override
	@Transactional
	public List<ParaGroupOutput> getParaGroupParaout() {
		// Retrieve the patient's medical profile with parameter groups
		String hql = "SELECT new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter, pp.patn_pargroup, pp.patn_value, a.appn_sch_date) "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p ";

		List<ParaGroupOutput> lp = em.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", 4).getResultList();

		return lp;
	}

	public List<PatientModel> getAllFamily(int pat_id) {
		// Retrieve all family members for the given patient ID
		String hql = "SELECT p1 FROM PatientModel p1 JOIN PatientModel p2 ON p1.accessPatientId = p2.patn_id WHERE p1.accessPatientId = :pat_id";

		TypedQuery<PatientModel> query = em.createQuery(hql, PatientModel.class);
		query.setParameter("pat_id", pat_id);
		List<PatientModel> patients = query.getResultList();
		return patients;
	}

	@Override
	public List<OutputPatientTestReports> getPatientReports(int pid) {
		// Retrieve the patient's test reports for the given patient ID
		String hql = "SELECT new spring.orm.model.output.OutputPatientTestReports(pdr.compositeKey.dgbl_id, dbm.dgbl_date, pdr.dgdr_path)"
				+ " FROM PatientDiagnonisticReports pdr"
				+ " JOIN DiagnosticBillModel dbm ON pdr.compositeKey.dgbl_id = dbm.dgbl_id "
				+ "WHERE 1 = 1 AND (dbm.dgbl_patn_id IN (SELECT f.pfmbPatnId FROM FamilyMembers f WHERE f.patnAccessPatnId = :p_id) OR dbm.dgbl_patn_id = :p_id)";
		List<OutputPatientTestReports> data = em
				.createQuery(hql, spring.orm.model.output.OutputPatientTestReports.class).setParameter("p_id", pid)
				.getResultList();

		System.out.println("called?");
		for (OutputPatientTestReports x : data) {
			System.out.println(x.getDgbl_id());
		}

		return data;
	}

	@Override
	public List<PatientNameOutputModel> getAllPatientidsNames() {
		// Retrieve all patient IDs and names from the database
		String hql = "SELECT new spring.orm.model.output.PatientNameOutputModel(p.patn_id, p.patn_fname, p.patn_lname) "
				+ "FROM PatientModel p";
		List<PatientNameOutputModel> data = em.createQuery(hql, spring.orm.model.output.PatientNameOutputModel.class)
				.getResultList();
		return data;
	}

	@Override
	@Transactional
	public List<PatientlastvisitOutput> getlastvisit(int p) {
		// Retrieve the patient's last visit details for the given patient ID
		String hql = "select new spring.orm.model.output.PatientlastvisitOutput(p.patn_lastvisit,d.doctName,a.appn_payamount,a.appn_sch_date) from AppointmentEntity a,PatientModel p,DoctorTemp d where p.patn_id=:p and p.patn_id=a.pm.patn_id and d.id=a.doctor.id and a.appn_status='YETO'";
		List<PatientlastvisitOutput> lp = em.createQuery(hql, spring.orm.model.output.PatientlastvisitOutput.class)
				.setParameter("p", p).getResultList();
		return lp;
	}

	///
	@Override
	public List<Object> getapptests(int p) {
		// Retrieve the diagnostic test IDs and names for the given patient ID
		List<Object> lm3 = em.createQuery(
				"SELECT d.id.dgbltestId, t.test_name FROM Diagnostictestbill d, TestModel t, DiagnosticBillModel d1 "
						+ "WHERE d.id.dgblId = d1.dgbl_id AND d.id.dgbltestId = t.test_id AND d1.dgbl_patn_id = :p")
				.setParameter("p", p).getResultList();

		return lm3;
	}

	public List<ParaGroupOutput> getParaGroupParaout(int p) {
		// Retrieve the patient's medical profile with parameter groups for the given patient ID
		String hql = "SELECT new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter, pp.patn_pargroup, pp.patn_value, a.appn_sch_date) "
				+ "FROM PatientMedicalProfile pp, AppointmentEntity a WHERE pp.id.patn_id = a.pm.patn_id AND pp.id.patn_id = :p";

		List<ParaGroupOutput> lp = em.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", p).getResultList();

		return lp;
	}

	@Transactional
	public List<Integer> getAllPatientids() {
		// Retrieve all patient IDs with pending appointments
		return em.createQuery(
				"SELECT p.patn_id FROM PatientModel p, AppointmentEntity a WHERE a.appn_status = 'YETO' AND a.pm.patn_id = p.patn_id")
				.getResultList();
	}

	@Override
	@Transactional
	public void updateLastvisitAndLastAppointment(int pat_id, LocalDate last_visited, int app_id) {
		// Update the patient's last visit and last appointment details
		PatientModel p = em.find(PatientModel.class, pat_id);
		p.setPatn_lastapp_id(app_id);
		p.setPatn_lastvisit(last_visited);
		em.merge(p);
	}
}