package spring.orm.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.DCDao;
import spring.orm.model.PatientDiagnonisticReports;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

@Component
public class DCImpDAO implements DCDao {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<OutputTestNameProfit> fetchTestNameProfit() {

		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit( t.test_name, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_name";

		List<OutputTestNameProfit> data = em.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class)
				.getResultList();

		return data;
	}

	@Override
	public List<OutputTestMethodProfit> fetchTestMethodProfit() {

		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit( t.test_method, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_method";

		List<OutputTestMethodProfit> data = em.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class)
				.getResultList();

		return data;
	}

	public List<OutputTestCategoryProfit> fetchTestCategoryProfit() {

		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit( t.test_category, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_category";

		List<OutputTestCategoryProfit> data = em.createQuery(hql).getResultList();

		return data;
	}

	public List<OutputTestNameProfit> fetchTestNameProfitDate(String from, String to) {

		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit(t.test_name, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_name";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);
		List<OutputTestNameProfit> data = em.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class)
				.setParameter("startDate", a).setParameter("endDate", b).getResultList();
		return data;
	}

	@Override
	public List<OutputTestMethodProfit> fetchTestMethodProfitDate(String from, String to) {

		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);
		List<OutputTestMethodProfit> data = em.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class)
				.setParameter("startDate", a).setParameter("endDate", b).getResultList();
		return data;
	}

	@Override
	public List<OutputTestCategoryProfit> fetchTestCategoryProfitDate(String from, String to) {

		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);
		List<OutputTestCategoryProfit> data = em
				.createQuery(hql, spring.orm.model.output.OutputTestCategoryProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		return data;
	}

	@Override
	public List<OutputReportData> fetchPatientInfo(int pid) {
		System.out.println("hello?");
		String hql = "SELECT new spring.orm.model.output.OutputReportData(p.patn_id, pd.dgbl_id, p.patn_name, p.patn_gender, pd.dgbl_amount) "
				+ "FROM PatientDiagnosis pd " + "JOIN pd.pm p " + "WHERE pd.dgbl_id NOT IN "
				+ "(SELECT pdr.compositeKey.dgbl_id FROM PatientDiagnonisticReports pdr) " + "AND p.patn_id = :val";
		// highest 10,5,2 pat_id
		List<OutputReportData> data = em.createQuery(hql, spring.orm.model.output.OutputReportData.class)
				.setParameter("val", pid).getResultList();

		return data;
	}

	private int maxIndex(int id) {
		String hql = "SELECT MAX(d.compositeKey.dgdr_index) from PatientDiagnonisticReports d where d.compositeKey.dgbl_id=:val";

		Integer maxIndex = (Integer) em.createQuery(hql).setParameter("val", id).getSingleResult();

		return maxIndex != null ? maxIndex + 1 : 1;

	}

	public String fetchPatientName(int pid) {

		String hql = "select p.patn_name from PatientModel p where p.patn_id=:val";

		String name = (String) em.createQuery(hql).setParameter("val", pid).getSingleResult();

		return name;

	}

	public List<Integer> fetchPatientReports() {

		String hql = "SELECT  distinct dbm.dgbl_patn_id FROM DiagnosticBillModel dbm where dbm.dgbl_id not in (select pd.compositeKey.dgbl_id from PatientDiagnonisticReports pd)";

		List<Integer> pids = em.createQuery(hql, Integer.class).getResultList();

		/*
		 * for (Integer x : pids) { System.out.println(x); }
		 */

		return pids;
	}

	@Transactional
	public String saveReportInfo(int id, byte[] content) {

		int idx = maxIndex(id);
		System.out.println(idx + "idx");
		PatientDiagnonisticReports data = new PatientDiagnonisticReports(id, idx, content);

		em.persist(data);

		return "success";
	}

}