package spring.orm.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.DAO.TestDAO;
import spring.orm.contract.services.TestService;
import spring.orm.model.TestModel;
import spring.orm.model.output.testsCategoriesModel;

@Service
public class TestServices implements TestService {
	@Autowired
	private TestDAO testDAO;

	private static final Logger logger = LoggerFactory.getLogger(TestServices.class);

	public List<TestModel> getTests() {
		// Used to get and display the test
		logger.info("Retrieving tests...");
		return testDAO.getTests();
	}

	public TestModel getTestById(int id) {
		// get the test bt testid
		logger.info("Retrieving test by ID: {}", id);
		return testDAO.getTestById(id);
	}

	public void updateTest(TestModel t) {
		// Used to update the test
		logger.info("Updating test: {}", t);
		testDAO.updateTest(t);

	}

	public void deleteTest(int test_id) {
		// Used to delete the test
		logger.info("Deleting test with ID: {}", test_id);
		testDAO.deleteTest(test_id);

	}

	public List<testsCategoriesModel> getTestCategory() {
		// TODO Auto-generated method stub
		logger.info("Retrieving test categories...");
		return testDAO.getCategories();

	}

	public List<TestModel> getTestByCategory(String category) {
		// Gives the test by categorywise in testbillgen
		logger.info("Retrieving tests by category: {}", category);
		return testDAO.getTestByCategory(category);
	}

	public Object getTestByPrice(int test) {
		// Gives the test price used in testbillgen
		logger.info("Retrieving test price for test ID: {}", test);
		return testDAO.getSelectedTestPrice(test);
	}

}