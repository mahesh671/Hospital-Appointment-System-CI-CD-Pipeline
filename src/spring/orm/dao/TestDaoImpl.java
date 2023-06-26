package spring.orm.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.TestDao;
import spring.orm.model.TestModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;

@Component
public class TestDaoImpl implements TestDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void savetest(TestInputModel t) {
		//saves the tests to DB through entity model
		TestModel s = new TestModel();
		s.setTest_category(t.getTest_category());
		s.setTest_fromrange(t.getTest_fromrange());
		s.setTest_method(t.getTest_method());
		s.setTest_name(t.getTest_name());
		s.setTest_price(t.getTest_price());
		s.setTest_torange(t.getTest_torange());
		em.persist(s);
	}

	@Override
	@Transactional
	public List<TestModel> gettests() {
		 // Retrieve all tests from the database		
		List<TestModel> t = em.createQuery("SELECT t FROM TestModel t where isDeleted = false", TestModel.class)
				.getResultList();
		return t;
	}

	@Override
	@Transactional
	public TestModel gettestbyid(int id) {
		 // Retrieve a specific test by its ID
		return em.find(TestModel.class, id);
	}

	@Override
	@Transactional
	public void updatetest(TestModel t) {
		// Update an existing test
		em.merge(t);
	}

	@Transactional
	@Override
	public void deltest(int test_id) {
		 // Soft delete a test by marking it as deleted
		TestModel s = em.find(TestModel.class, test_id);
		s.setDeleted(true);
		em.merge(s);
	}

	@Transactional
	@Override
	public List<TestModel> gettestbycat(String cat) {
		// Retrieve tests by category
		return em.createQuery("select t from TestModel t where t.test_category=:cat", TestModel.class)
				.setParameter("cat", cat).getResultList();
	}

	@Transactional
	@Override
	public Object gettestprice(int test) {
	    // Retrieve the price of a specific test
		return em.createQuery("select t.test_price from TestModel t where t.test_id=:test").setParameter("test", test)
				.getSingleResult();
	}




	@Override
	public List<testsPatientsModel> getalltestpatients() {
		// Retrieve all test-patient details
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,TestModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id ";

		List<testsPatientsModel> data = em.createQuery(hql, testsPatientsModel.class).getResultList();
		return data;
	}

	@Override
	public List<testsPatientsModel> gettestwisepatients(int test) {
		// Retrieve test-patient details for a specific test
		List<testsPatientsModel> data = null;
		
		// Construct the HQL query
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id, p.patn_name, t.test_name, t.test_method, t.test_category, t.test_price, d.dgbl_date) "
				+ "from PatientModel p, TestModel t, DiagnosticBillModel d, Diagnostictestbill dt "
				+ "where dt.id.dgbltestId = t.test_id and dt.id.dgblId = d.dgbl_id and d.dgbl_patn_id = p.patn_id ";
		
		if (test >= 0) {
			System.out.println("-4");
			hql += " and t.test_id = :test"; // Filter by test ID if a valid ID is provided
		}
		
		if (test == -1) {
			System.out.println("-1");
			hql += ""; // No additional filter if the test ID is -1
		}
		
		// Create the TypedQuery with the constructed HQL query
		TypedQuery<testsPatientsModel> q = em.createQuery(hql, spring.orm.model.output.testsPatientsModel.class);
		
		if (test >= 0) {
			q.setParameter("test", test); // Set the test parameter if a valid ID is provided
		}
		
		// Execute the query and retrieve the results
		data = q.getResultList();
		
		return data;
	}

	



	
}