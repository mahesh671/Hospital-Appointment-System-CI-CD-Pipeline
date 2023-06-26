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

import spring.orm.contract.PatientDao;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.OutputPatientTestReports;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.model.output.patientPrescriptionOutputmodel;

@Repository
public class PatientDaoImpl implements PatientDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public int savePatient(PatientModel patient) {
		em.persist(patient);
		return patient.getPatn_id();
	}

	@Override
	@Transactional
	public List<Object> getapptestcards(int p) {

		LocalDate currentDate = LocalDate.now();
		String d = currentDate.toString();

		// String d = d1.toString();
		// System.out.println(d);
		// System.out.println(Timestamp.valueOf(d));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		List<Object> ls = new ArrayList<>();
		try {
			date = sdf.parse(d);
			// Parse the date string to a java.util.Date object

			List<AppointmentEntity> lm = em.createQuery(
					"SELECT a FROM AppointmentEntity a JOIN a.pm p WHERE CAST(a.appn_sch_date AS date) = :d AND p.patn_id = :p",
					AppointmentEntity.class).setParameter("d", date).setParameter("p", p).getResultList();

			// return s.get(Specialization.class, Id);
			int app = lm.size();
			System.out.println(app);
			List<Integer> tm = em.createQuery(
					"select dt.id.dgbltestId from Diagnostictestbill dt,DiagnosticBillModel d,PatientModel p where p.patn_id=:p and d.dgbl_patn_id=p.patn_id and d.dgbl_id=dt.id.dgblId ")
					.setParameter("p", p).getResultList();
			int tests = tm.size();
			System.out.println(tests);

			ls.add(app);
			ls.add(tests);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	@Transactional
	public List<PatientModel> getAllPatientModels() {
		// TODO Auto-generated method stub
		return em.createQuery("select p from PatientModel p", PatientModel.class).getResultList();
	}

	@Override
	public PatientModel getPatientById(int existingPatientid) {
		// TODO Auto-generated method stub
		return em.find(PatientModel.class, existingPatientid);
	}

	@Override
	@Transactional
	public int addNewPatient(PatientModel p) {
		// TODO Auto-generated method stub
		em.persist(p);

		return p.getPatn_id();
	}

	@Override
	public List<Object> getapps(int pid) {
		List<Object> lm2 = em.createQuery(
				"SELECT a.appn_id, d.doctName, a.appn_sch_date, a.appn_status FROM AppointmentEntity a JOIN a.doctor d WHERE a.pm.patn_id = :p")
				.setParameter("p", pid).getResultList();
		return lm2;
	}

	@Override
	@Transactional
	public List<PatientMedicalProfile> getParaGroup(int pid) {

		List<PatientMedicalProfile> lp = em.createQuery("select pp "
				+ "from PatientMedicalProfile pp,AppointmentEntity a where pp.id.patn_id=a.pm.patn_id and pp.id.patn_id=:p ",
				PatientMedicalProfile.class).setParameter("p", pid)

				.getResultList();
		return lp;
	}

	@Override
	public List<patientPrescriptionOutputmodel> getPrescription(int patn_id) {
		// TODO Auto-generated method stub
		List<patientPrescriptionOutputmodel> lp = (List<patientPrescriptionOutputmodel>) em
				.createQuery("select pm.patn_prescription from PatientMedicalProfile pm where pm.id.patn_id=:patn_id",
						patientPrescriptionOutputmodel.class)
				.setParameter("patn_id", patn_id);

		return lp;
	}

	@Override
	@Transactional
	public List<ParaGroupOutput> getParaGroupParaout() {
		String hql = "select new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter,pp.patn_pargroup,pp.patn_value , a.appn_sch_date) "
				+ "from PatientMedicalProfile pp,AppointmentEntity a where pp.id.patn_id=a.pm.patn_id and pp.id.patn_id=:p ";

		List<ParaGroupOutput> lp = em.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", 4)

				.getResultList();

		return lp;
	}

	public List<PatientModel> getAllFamily(int pat_id) {
		String hql = "select p1 from PatientModel p1 join PatientModel p2 on p1.accessPatientId=p2.patn_id where p1.accessPatientId=:pat_id";

		TypedQuery<PatientModel> query = em.createQuery(hql, PatientModel.class);
		query.setParameter("pat_id", pat_id);
		List<PatientModel> patients = query.getResultList();
		return patients;
	}

	@Override
	public List<OutputPatientTestReports> getPatientReports(int pid) {
		// TODO Auto-generated method stub
		/*
		 * select pdr.dgbl_id,dbm.dgbl_date,pdr.dgdr_path from pdreports pdr join dbiils dbm on pdr.dgbl_id=dbm.dgbl_id
		 * join familymembers fm on dbm.dgbl_patn_id=fm.patn_access_patn_id where dbm.dgbl_patn_id=3
		 */

		String hql = "SELECT new spring.orm.model.output.OutputPatientTestReports(pdr.compositeKey.dgbl_id, dbm.dgbl_date, pdr.dgdr_path)"
				+ " FROM PatientDiagnonisticReports pdr"
				+ " JOIN DiagnosticBillModel dbm on pdr.compositeKey.dgbl_id=dbm.dgbl_id "
				+ "WHERE 1=1 AND  (dbm.dgbl_patn_id in (select f.pfmbPatnId from FamilyMembers f where f.patnAccessPatnId=:p_id) or dbm.dgbl_patn_id=:p_id)";
		List<OutputPatientTestReports> data = em
				.createQuery(hql, spring.orm.model.output.OutputPatientTestReports.class).setParameter("p_id", pid)
				.getResultList();
		//
		System.out.println("called?");
		for (OutputPatientTestReports x : data) {
			System.out.println(x.getDgbl_id());

		}

		return data;
		// System.out.println("hi");

	}

	@Override
	public List<PatientNameOutputModel> getAllPatientidsNames() {
		// TODO Auto-generated method stub
		String hql = "select new spring.orm.model.output.PatientNameOutputModel(p.patn_id,p.patn_name) from PatientModel p";
		List<PatientNameOutputModel> lp = em.createQuery(hql, spring.orm.model.output.PatientNameOutputModel.class)

				.getResultList();
		return lp;
		// return null;
	}

	@Override
	public List<PatientlastvisitOutput> getlastvisit(int p) {
		String hql = "select new spring.orm.model.output.PatientlastvisitOutput(p.patn_lastvisit,d.doctName,a.appn_payamount,a.appn_sch_date) from AppointmentEntity a,PatientModel p,DoctorTemp d where p.patn_id=:p and p.patn_id=a.pm.patn_id and d.doctId=a.doctor.doctId and a.appn_status='YETO'";
		List<PatientlastvisitOutput> lp = em.createQuery(hql, spring.orm.model.output.PatientlastvisitOutput.class)
				.setParameter("p", p)

				.getResultList();
		return lp;
	}

	@Override
	public List<Object> getapptests(int p) {
		// TODO Auto-generated method stub
		List<Object> lm3 = em.createQuery(
				"select d.id.dgbltestId,t.test_name from Diagnostictestbill d , testModel t,DiagnosticBillModel d1 where d.id.dgblId=d1.dgbl_id and d.id.dgbltestId=t.test_id and d1.dgbl_patn_id=:p ")
				.setParameter("p", p).getResultList();

		return lm3;
	}

	public List<ParaGroupOutput> getParaGroupParaout(int p) {
		String hql = "select new spring.orm.model.output.ParaGroupOutput(pp.patn_parameter,pp.patn_pargroup,pp.patn_value , a.appn_sch_date) "
				+ "from PatientMedicalProfile pp,AppointmentEntity a where pp.id.patn_id=a.pm.patn_id and pp.id.patn_id=:p ";

		List<ParaGroupOutput> lp = em.createQuery(hql, spring.orm.model.output.ParaGroupOutput.class)
				.setParameter("p", p)

				.getResultList();

		return lp;
	}

	@Transactional
	public List<Integer> getAllPatientids() {
		// TODO Auto-generated method stub
		return em.createQuery(
				"select p.patn_id from PatientModel p,AppointmentEntity a where a.appn_status='YETO' and a.pm.patn_id=p.patn_id")
				.getResultList();
	}

	@Override
	@Transactional
	public void updateLastvisitAndLastAppointment(int pat_id, LocalDate last_visited, int app_id) {
		// TODO Auto-generated method stub

		PatientModel p = em.find(PatientModel.class, pat_id);
		p.setPatn_lastapp_id(app_id);
		p.setPatn_lastvisit(last_visited);
		em.merge(p);

	}

}
