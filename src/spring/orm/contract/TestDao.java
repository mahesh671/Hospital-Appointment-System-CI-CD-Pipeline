package spring.orm.contract;

import java.util.List;

import spring.orm.model.TestModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;

public interface TestDao {

	public void savetest(TestInputModel s);

	public List<TestModel> gettests();

	public TestModel gettestbyid(int id);

	public void deltest(int test_id);

	

	public List<TestModel> gettestbycat(String cat);

	

	public Object gettestprice(int tests);

	
	public void updatetest(TestModel t);

	public List<testsPatientsModel> getalltestpatients();

	public List<testsPatientsModel> gettestwisepatients(int test);

	
}
