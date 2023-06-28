package spring.orm.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.TestDAO;
import spring.orm.model.TestModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.patientsoutputmodel;
import spring.orm.model.output.testsCategoriesModel;
import spring.orm.model.output.testsPatientsModel;

@Component
public class TestDAOImpl implements TestDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(TestDAOImpl.class);

	@Transactional
	@Override
	public void saveTest(TestInputModel testInputModel) {
		// saves the tests to DB through entity model
		logger.info(testInputModel.toString());
		TestModel testModel = new TestModel();
		testModel.setTest_category(testInputModel.getTest_category());
		testModel.setTest_fromrange(testInputModel.getTest_fromrange());
		testModel.setTest_method(testInputModel.getTest_method());
		testModel.setTest_name(testInputModel.getTest_name());
		testModel.setTest_price(testInputModel.getTest_price());
		testModel.setTest_torange(testInputModel.getTest_torange());
		logger.info("The Saved test details:{}", testModel.toString());
		entityManager.persist(testModel);
	}

	@Override
	public List<patientsoutputmodel> getPatients() {
		// Retrieve all tests from the database
		String hql = "SELECT   new spring.orm.model.output.patientsoutputmodel(p.patn_id,p.patn_name) FROM PatientModel p ";
		logger.info("{}", hql);
		List<patientsoutputmodel> data = entityManager.createQuery(hql, patientsoutputmodel.class).getResultList();
		logger.info(data.toString());
		return data;
	}

	@Override
	@Transactional
	public TestModel getTestById(int id) {
		// Retrieve a specific test by its ID
		logger.info(" The test by id :{}", id);

		return entityManager.find(TestModel.class, id);
	}

	@Override
	@Transactional
	public void updateTest(TestModel testModel) {
		// Update an existing test
		logger.info(testModel.toString());
		entityManager.merge(testModel);
	}

	@Transactional
	@Override
	public void deleteTest(int test_id) {
		// Soft delete a test by marking it as deleted
		logger.info("delete test_id:{}", test_id);
		TestModel s = entityManager.find(TestModel.class, test_id);
		s.setDeleted(true);
		entityManager.merge(s);
	}

	@Transactional
	@Override
	public List<TestModel> getTestByCategory(String cat) {
		// Retrieve tests by category
		logger.info("Category:{}", cat);
		return entityManager.createQuery("select t from TestModel t where t.test_category=:cat", TestModel.class)
				.setParameter("cat", cat).getResultList();
	}

	@Transactional
	@Override
	public Object getSelectedTestPrice(int test) {
		// Retrieve the price of a specific test
		logger.info("{}", test);
		return entityManager.createQuery("select t.test_price from TestModel t where t.test_id=:test")
				.setParameter("test", test).getSingleResult();
	}

	@Override
	public List<testsPatientsModel> getAllTestPatients() {
		// Retrieve all test-patient details

		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,TestModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id ";
		logger.info("Get all test Patients:{}", hql);
		List<testsPatientsModel> data = entityManager.createQuery(hql, testsPatientsModel.class).getResultList();
		return data;
	}

	@Override
	public List<testsPatientsModel> getTestWisePatients(int test) {
		// Retrieve test-patient details for a specific test
		List<testsPatientsModel> data = null;

		// Construct the HQL query
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id, p.patn_name, t.test_name, t.test_method, t.test_category, t.test_price, d.dgbl_date) "
				+ "from PatientModel p, TestModel t, DiagnosticBillModel d, Diagnostictestbill dt "
				+ "where dt.id.dgbltestId = t.test_id and dt.id.dgblId = d.dgbl_id and d.dgbl_patn_id = p.patn_id ";
		logger.info("Get testwise Patients:{}", test);
		if (test >= 0) {
			logger.info("If test exist:{}", test);
			hql += " and t.test_id = :test"; // Filter by test ID if a valid ID is provided
		}

		if (test == -1) {
			logger.info("If there is no test selected:{}", test);
			hql += ""; // No additional filter if the test ID is -1
		}

		// Create the TypedQuery with the constructed HQL query
		TypedQuery<testsPatientsModel> q = entityManager.createQuery(hql,
				spring.orm.model.output.testsPatientsModel.class);

		if (test >= 0) {
			q.setParameter("test", test); // Set the test parameter if a valid ID is provided
		}

		// Execute the query and retrieve the results
		data = q.getResultList();

		return data;
	}

	@Override
	public List<testsPatientsModel> getAllTestPatientDetails(int test, String date1, String date2) {
		List<testsPatientsModel> data = null;
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,TestModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id ";
		logger.info("Get all test Patient Details:{}", hql);
		if (test >= 0) {
			// Filter by test ID if a valid ID is provided
			logger.info("If test exist:{}", test);
			hql += " and t.test_id = :test";
		}
		if (test == -1) {
			// No additional filter if the test ID is -1
			logger.info("If there is no test selected:{}", test);
			hql += "";
		}
		if (!date1.equals("") & !date2.equals("")) {
			// Get the tests by applying the between dates filter
			logger.info("Between dates:{} {}", date1, date2);

			hql += " and d.dgbl_date>=:date1 and d.dgbl_date<=:date2";
		}
		TypedQuery<testsPatientsModel> q = entityManager.createQuery(hql,
				spring.orm.model.output.testsPatientsModel.class);
		if (test >= 0) {
			q.setParameter("test", test);
		}
		if (!date1.equals("") & !date2.equals("")) {
			q.setParameter("date1", LocalDate.parse(date1));
			q.setParameter("date2", LocalDate.parse(date2));
		}

		data = q.getResultList();

		return data;

	}

	// Retrieve all tests from the database
	public List<TestModel> getTests() {
		// Create a query to fetch active tests from the database
		List<TestModel> testModel = entityManager
				.createQuery("SELECT t FROM TestModel t where isDeleted = false", TestModel.class).getResultList();
		logger.info(testModel.toString());
		return testModel;
	}

	@Override
	@Transactional
	public List<testsCategoriesModel> getCategories() {
		// Retrieve all Categories from the database
		String hql = "SELECT  DISTINCT  new spring.orm.model.output.testsCategoriesModel(t.test_category) FROM TestModel t where isDeleted = false";
		List<testsCategoriesModel> data = entityManager.createQuery(hql, testsCategoriesModel.class).getResultList();
		logger.info("Get Categories:{}", data);
		return data;

	}

}