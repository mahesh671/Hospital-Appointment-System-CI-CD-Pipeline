package spring.orm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.TestDao;
import spring.orm.model.TestModel;

@Service
public class TestServices {
	@Autowired
	private TestDao tdao;

	public List<TestModel> gettests() {
		//Used to get and display the test
		return tdao.gettests();
	}



	public TestModel gettestbyid(int id) {
		//get the test bt testid
		return tdao.gettestbyid(id);
	}

	public void updatetest(TestModel t) {
		//Used to update the test
		tdao.updatetest(t);

	}

	public void deltest(int test_id) {
		//Used to delete the test
		tdao.deltest(test_id);

	}

	

	public List<String> getTestCat() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TestModel> gettestbycat(String cat) { 
		//Gives the test by categorywise in testbillgen
		return tdao.gettestbycat(cat);
	}

	public Object gettestprice(int test) {
		//Gives the test price used in testbillgen
		return tdao.gettestprice(test);
	}

}