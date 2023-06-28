package spring.orm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.TestDAO;
import spring.orm.model.TestModel;

@Service
public class TestServices {
	@Autowired
	private TestDAO tDAO;

	public List<TestModel> getTests() {
		// Used to get and display the test
		return tDAO.getTests();
	}

	public TestModel getTestById(int id) {
		// get the test bt testid
		return tDAO.getTestById(id);
	}

	public void updateTest(TestModel t) {
		// Used to update the test
		tDAO.updateTest(t);

	}

	public void deleteTest(int test_id) {
		// Used to delete the test
		tDAO.deleteTest(test_id);

	}

	public List<String> getTestCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TestModel> getTestByCategory(String category) {
		// Gives the test by categorywise in testbillgen
		return tDAO.getTestByCategory(category);
	}

	public Object getTestByPrice(int test) {
		// Gives the test price used in testbillgen
		return tDAO.getSelectedTestPrice(test);
	}

}