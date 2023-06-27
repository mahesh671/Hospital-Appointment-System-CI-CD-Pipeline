package spring.orm.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.orm.contract.DiagnosticBillDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.TestDAO;
import spring.orm.model.DiagnosticBillModel;
import spring.orm.model.DiagnosticBillModelId;
import spring.orm.model.Diagnostictestbill;
import spring.orm.model.TestBookStatusComposite;
import spring.orm.model.entity.TestBookStatus;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.BookedTestIDs;
import spring.orm.model.output.OutputPatientTestReports;

@Repository
public class DiagnosticBillDAOImpl implements DiagnosticBillDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PatientDAO pd;

	@Autowired
	private TestDAO td;

	TestBookStatus st = new TestBookStatus();
	private Object d;
	private Object p11;

	List<Integer> testi = new ArrayList<>();
	String con;
	BookedTestIDs btid = new BookedTestIDs(testi, con);

	public List<DiagnosticBillModel> getdbilldetails() {
		return em.createQuery("SELECT d FROM diagnosticBillModel d", DiagnosticBillModel.class).getResultList();
	}

	@Transactional
	public void booktestt(BillInputModel bi) {

		System.out.println("inside ne");

		int test = bi.getTest();
		String type = bi.getType();

		int patn_id = bi.getPatient();

		TestBookStatusComposite id = new TestBookStatusComposite();
		id.setTbPatnId(patn_id);
		id.setTestId(test);

		st.setType(type);
		st.setId(id);
		st.setBookedDate(LocalDate.now());
		st.setStatus("pending");

		em.persist(st);

	}

	@Transactional
	public List<Object> gettotalbills(int patient) {

		List<Object> tests1 = new ArrayList<>();
		List<Object> tests2 = new ArrayList<>();
		int amt = 0;

		int patn_id = patient;
		if (patn_id >= 0) {

			List<Integer> testh = (List<Integer>) em.createQuery(
					"select t.id.test_id  from TestBookStatus t where t.id.tb_patn_id=:patn_id and t.status= 'pending'")
					.setParameter("patn_id", patn_id).getResultList();
			System.out.println("length here" + testh.size());
			for (int i = 0; i < testh.size(); i++) {
				int j = (int) testh.get(i);

				tests1 = em
						.createQuery("SELECT t.test_name, t.test_method, t.test_price "
								+ "FROM TestModel t, TestBookStatus tb " + "WHERE t.test_id = :testId "
								+ "AND tb.status = 'pending' " + "AND tb.id.test_id = t.test_id")
						.setParameter("testId", j).getResultList();

				int testsprice = (int) em.createQuery("select t.test_price from TestModel t where t.test_id=:j ")
						.setParameter("j", j).getSingleResult();

				amt = amt + testsprice;
				System.out.println(amt);
				tests2.add(tests1);

			}
			tests2.add(amt);
			btid.setAmt(amt);

		}

		return tests2;

	}

	@Transactional
	public int storedb(int patient) {
		int patn_id = patient;
		int billid = 0;
		String type = (String) em.createQuery("select t.type from TestBookStatus t  where t.id.tb_patn_id=:patn_id")
				.setMaxResults(1).setParameter("patn_id", patn_id).getSingleResult();
		List<Integer> t1 = em.createQuery(
				"select t.id.test_id from TestBookStatus t  where t.id.tb_patn_id=:patn_id  and t.status= 'pending'")
				.setParameter("patn_id", patn_id).getResultList();

		if (patn_id >= 0) {

			int price = btid.getAmt();

			System.out.println((Integer) patn_id);
			System.out.println(LocalDate.now());
			System.out.println(price);

			DiagnosticBillModel d1 = new DiagnosticBillModel((Integer) patn_id, LocalDate.now(), price, type);
			System.out.println("in 2");
			em.persist(d1);

			for (int i = 0; i < t1.size(); i++) {

				int price2 = (int) em.createQuery("select t.test_price from TestModel t where t.test_id=:j ")
						.setParameter("j", t1.get(i)).getSingleResult();
				DiagnosticBillModelId id = new DiagnosticBillModelId((Integer) d1.getDgbl_id(), t1.get(i));
				billid = ((Integer) d1.getDgbl_id());
				System.out.println(t1.get(i));

				Diagnostictestbill d2 = new Diagnostictestbill(id, price2);

				em.persist(d2);

				TestBookStatusComposite comid = new TestBookStatusComposite();
				comid.setTbPatnId(patn_id);
				comid.setTestId(t1.get(i));

				TestBookStatus st = new TestBookStatus();

				st.setId(comid);
				st.setBookedDate(LocalDate.now());
				st.setStatus("paid");
				st.setType(type);
				st = em.merge(st); // Merge the detached entity and capture the managed instance

				em.remove(st);

			}

		}
		return billid;

	}

	@Override
	public List<OutputPatientTestReports> gettestdate(String date1, String date2, int pid) {
		// TODO Auto-generated method stub
		String hql = "SELECT new spring.orm.model.output.OutputPatientTestReports(pdr.compositeKey.dgbl_id, dbm.dgbl_date, pdr.dgdr_path)"
				+ " FROM PatientDiagnonisticReports pdr"
				+ " JOIN DiagnosticBillModel dbm on pdr.compositeKey.dgbl_id=dbm.dgbl_id "
				+ "WHERE 1=1 AND  (dbm.dgbl_patn_id in (select f.pfmbPatnId from FamilyMembers f where f.patnAccessPatnId=:p_id) or dbm.dgbl_patn_id=:p_id) and dbm.dgbl_date>=:d1 and dbm.dgbl_date<=:d2";

		List<OutputPatientTestReports> data = em
				.createQuery(hql, spring.orm.model.output.OutputPatientTestReports.class).setParameter("p_id", pid) // change
																													// it
																													// to
																													// pid
																													// later
				.setParameter("d1", LocalDate.parse(date1)).setParameter("d2", LocalDate.parse(date2)).getResultList();
		System.out.println(data.size());

		for (OutputPatientTestReports x : data) {
			System.out.println(x.getDgbl_date());
		}
		return data;
	}

}