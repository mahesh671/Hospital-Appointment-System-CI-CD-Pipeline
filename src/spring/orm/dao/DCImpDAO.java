package spring.orm.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.DCDAO;
import spring.orm.model.PatientDiagnonisticReports;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

@Component
public class DCImpDAO implements DCDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<OutputTestNameProfit> fetchTestNameWiseProfit() {
		// Retrieve test name and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit( t.test_name, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_name";

		List<OutputTestNameProfit> testNameProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class).getResultList();

		return testNameProfitData;
	}

	@Override
	public List<OutputTestMethodProfit> fetchTestMethodWiseProfit() {
		// Retrieve test method and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit( t.test_method, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_method";

		List<OutputTestMethodProfit> testMethodProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class).getResultList();

		return testMethodProfitData;
	}

	public List<OutputTestCategoryProfit> fetchTestCategoryWiseProfit() {
		// Retrieve test category and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit( t.test_category, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_category";

		List<OutputTestCategoryProfit> testCategoryProfitData = entityManager.createQuery(hql).getResultList();

		return testCategoryProfitData;
	}

	public List<OutputTestNameProfit> fetchTestNameDateWiseProfit(String from, String to) {
		// Retrieve test name and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit(t.test_name, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_name";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);

		List<OutputTestNameProfit> testNameProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();

		return testNameProfitData;
	}

	@Override
	public List<OutputTestMethodProfit> fetchTestMethodDateWiseProfit(String from, String to) {
		// Retrieve test method and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";
		// the date in the form of yyyy-mm-dd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);
		List<OutputTestMethodProfit> testMethodProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		return testMethodProfitData;
	}

	@Override
	public List<OutputTestCategoryProfit> fetchTestCategoryDateWiseProfit(String from, String to) {
		// Retrieve test category and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";

		// format the date in the form of yyyy-mm-dd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);

		List<OutputTestCategoryProfit> testCategoryProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestCategoryProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		return testCategoryProfitData;
	}

	@Override
	public List<OutputReportData> fetchPatientReportsInfo(int pid) {
		System.out.println("hello?");
		String hql = "SELECT new spring.orm.model.output.OutputReportData(p.patn_id, pd.dgbl_id, p.patn_name, p.patn_gender, pd.dgbl_amount) "
				+ "FROM PatientDiagnosis pd " + "JOIN pd.pm p " + "WHERE pd.dgbl_id NOT IN "
				+ "(SELECT pdr.compositeKey.dgbl_id FROM PatientDiagnonisticReports pdr) " + "AND p.patn_id = :val";
		// Retrieve specfic patient information for generating reports
		List<OutputReportData> data = entityManager.createQuery(hql, spring.orm.model.output.OutputReportData.class)
				.setParameter("val", pid).getResultList();

		return data;
	}

	private int getMaxIndex(int id) {
		// Retrieve the maximum index value for a patient's diagnostic report and increment by 1 to get next uploaded
		// report index
		String hql = "SELECT MAX(d.compositeKey.dgdr_index) from PatientDiagnonisticReports d where d.compositeKey.dgbl_id=:val";

		Integer maxIndex = (Integer) entityManager.createQuery(hql).setParameter("val", id).getSingleResult();

		return maxIndex != null ? maxIndex + 1 : 1;

	}

	public String fetchPatientName(int pid) {
		// Retrieve the name of a patient using their ID
		String hql = "select p.patn_name from PatientModel p where p.patn_id=:val";

		String name = (String) entityManager.createQuery(hql).setParameter("val", pid).getSingleResult();

		return name;
	}

	public List<Integer> fetchPatientIds() {
		// Retrieve the IDs of patients who have pending diagnostic reports
		String hql = "SELECT  distinct dbm.dgbl_patn_id FROM DiagnosticBillModel dbm where dbm.dgbl_id not in (select pd.compositeKey.dgbl_id from PatientDiagnonisticReports pd)";

		List<Integer> patientIds = entityManager.createQuery(hql, Integer.class).getResultList();

		return patientIds;
	}

	@Transactional
	public String saveReportInfo(int id, byte[] content) {
		// Save patient diagnostic report information
		int idx = getMaxIndex(id);
		System.out.println(idx + "idx");

		// creates entity model to save the data using hibernate
		PatientDiagnonisticReports data = new PatientDiagnonisticReports(id, idx, content);

		entityManager.persist(data);

		return "success";
	}

}