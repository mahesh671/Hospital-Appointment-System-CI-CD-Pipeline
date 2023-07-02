package spring.orm.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.DAO.DCDAO;
import spring.orm.model.PatientDiagnonisticReports;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

@Component
public class DCImpDAO implements DCDAO {

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(DCImpDAO.class);

	// method that fetches the test name and total profit from the database
	@Override
	public List<OutputTestNameProfit> fetchTestNameWiseProfit() {
		logger.info("Entered into fetchTestNameWiseProfit");

		// Retrieve test name and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit( t.test_name, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_name";
		logger.info("setting the hibernate query");
		List<OutputTestNameProfit> testNameProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class).getResultList();
		logger.info("fecthed the testnameprofitdata");

		return testNameProfitData;
	}

	// method that fetches the test method and total profit from the database
	@Override
	public List<OutputTestMethodProfit> fetchTestMethodWiseProfit() {
		logger.info("Entered into  fetchTestMethodWiseProfit");

		// Retrieve test method and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit( t.test_method, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_method";
		logger.info("setting the hibernate query");

		List<OutputTestMethodProfit> testMethodProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class).getResultList();
		logger.info("fecthed the testmethodprofitdata");

		return testMethodProfitData;
	}

	// method that fetches the test category and total profit from the database
	public List<OutputTestCategoryProfit> fetchTestCategoryWiseProfit() {
		logger.info("Entered into  fetchTestCategoryWiseProfit");
		// Retrieve test category and total profit using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit( t.test_category, SUM(t.test_price)) FROM PatientDiagnosticTests pdt JOIN pdt.pk.tm t GROUP BY t.test_category";
		logger.info("setting the hibernate query");

		List<OutputTestCategoryProfit> testCategoryProfitData = entityManager.createQuery(hql).getResultList();
		logger.info("fecthed the testcategoryprofitdata");

		return testCategoryProfitData;
	}

	// method that fetches the test name and total profit from the database date wise
	public List<OutputTestNameProfit> fetchTestNameDateWiseProfit(String from, String to) {
		logger.info("Entered into  fetchTestNameDateWiseProfit");

		// Retrieve test name and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestNameProfit(t.test_name, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_name";
		logger.info("setting the hibernate query");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);

		List<OutputTestNameProfit> testNameProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestNameProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		logger.info("fecthed the testnameprofitdata date wise");

		return testNameProfitData;
	}

	// method that fetches the test method and total profit from the database date wise
	@Override
	public List<OutputTestMethodProfit> fetchTestMethodDateWiseProfit(String from, String to) {
		logger.info("Entered into fetchTestMethodDateWiseProfit");

		// Retrieve test method and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestMethodProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";
		logger.info("setting the hibernate query");

		// the date in the form of yyyy-mm-dd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);
		List<OutputTestMethodProfit> testMethodProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestMethodProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		logger.info("fecthed the testmethodprofitdata date wise");

		return testMethodProfitData;
	}

	// method that fetches the test category and total profit from the database date wise
	@Override
	public List<OutputTestCategoryProfit> fetchTestCategoryDateWiseProfit(String from, String to) {
		logger.info("Entered into fetchTestCategoryDateWiseProfit");

		// Retrieve test category and total profit within a specific date range using HQL
		String hql = "SELECT new spring.orm.model.output.OutputTestCategoryProfit(t.test_method, SUM(t.test_price)) "
				+ "FROM PatientDiagnosticTests pdt " + "JOIN pdt.pk.pd pd " + "JOIN pdt.pk.tm t "
				+ "WHERE pd.dgbl_date BETWEEN :startDate AND :endDate " + "GROUP BY t.test_method";
		logger.info("setting the hibernate query");

		// format the date in the form of yyyy-mm-dd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate a = LocalDate.parse(from);
		LocalDate b = LocalDate.parse(to);

		List<OutputTestCategoryProfit> testCategoryProfitData = entityManager
				.createQuery(hql, spring.orm.model.output.OutputTestCategoryProfit.class).setParameter("startDate", a)
				.setParameter("endDate", b).getResultList();
		logger.info("fecthed the testcategoryprofitdata date wise");

		return testCategoryProfitData;
	}

	// method that fetches the patient reports
	@Override
	public List<OutputReportData> fetchPatientReportsInfo(int pid) {
		logger.info("Entered into fetchPatientReportsInfo pid" + pid);

		String hql = "SELECT new spring.orm.model.output.OutputReportData(p.patn_id, pd.dgbl_id, p.patn_name, p.patn_gender, pd.dgbl_amount) "
				+ "FROM PatientDiagnosis pd " + "JOIN pd.pm p " + "WHERE pd.dgbl_id NOT IN "
				+ "(SELECT pdr.compositeKey.dgbl_id FROM PatientDiagnonisticReports pdr) " + "AND p.patn_id = :val";
		logger.info("setting the hibernate query");

		// Retrieve specfic patient information for generating reports
		List<OutputReportData> data = entityManager.createQuery(hql, spring.orm.model.output.OutputReportData.class)
				.setParameter("val", pid).getResultList();
		logger.info("fecthed the PatientReports data date wise");

		return data;
	}

	// method that fetches the max index for the document based on patient id
	private int getMaxIndex(int id) {
		logger.info("Entered into getMaxIndex pid" + id);
		// Retrieve the maximum index value for a patient's diagnostic report and increment by 1 to get next uploaded
		// report index
		String hql = "SELECT MAX(d.compositeKey.dgdr_index) from PatientDiagnonisticReports d where d.compositeKey.dgbl_id=:val";
		logger.info("setting the hibernate query");

		Integer maxIndex = (Integer) entityManager.createQuery(hql).setParameter("val", id).getSingleResult();
		logger.info("fecthed the max index value");

		return maxIndex != null ? maxIndex + 1 : 1;

	}

	// method that fetches the patient name
	public String fetchPatientName(int pid) {
		logger.info("Entered into fetchPatientName pid" + pid);

		// Retrieve the name of a patient using their ID
		String hql = "select p.patn_name from PatientModel p where p.patn_id=:val";
		logger.info("setting the hibernate query");

		String name = (String) entityManager.createQuery(hql).setParameter("val", pid).getSingleResult();
		logger.info("fecthed the Patient name");

		return name;
	}

	// method that fetches all the patient id's
	public List<Integer> fetchPatientIds() {
		logger.info("Entered into fetchPatientIds");
		// Retrieve the IDs of patients who have pending diagnostic reports
		String hql = "SELECT  distinct dbm.dgbl_patn_id FROM DiagnosticBillModel dbm where dbm.dgbl_id not in (select pd.compositeKey.dgbl_id from PatientDiagnonisticReports pd)";
		logger.info("setting the hibernate query");
		List<Integer> patientIds = entityManager.createQuery(hql, Integer.class).getResultList();

		logger.info("fecthed the Patient Id data list");

		return patientIds;
	}

	// method that saves the patient report into the database
	@Transactional
	public String saveReportInfo(int id, byte[] content) {
		logger.info("Entered into saveReportInfo dId" + id);
		// Save patient diagnostic report information
		int idx = getMaxIndex(id);
		System.out.println(idx + "idx");

		// creates entity model to save the data using hibernate
		PatientDiagnonisticReports data = new PatientDiagnonisticReports(id, idx, content);
		logger.info("created the patientDiagnosticReports data");

		entityManager.persist(data);
		logger.info("saved the patientDiagnosticReports data");
		logger.info("sending the response");
		return "success";
	}

}