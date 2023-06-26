package spring.orm.contract;

import java.util.List;

import spring.orm.model.testModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;

public interface TestDao {

	public void savetest(TestInputModel s);

	public List<testModel> gettests();

	public testModel gettestbyid(int id);

	public void deltest(int test_id);

	public List<testModel> getcat();

	public List<testModel> gettestbycat(String cat);

	public List<String> getTestCat();

	public Object gettestprice(int tests);

	public List<Object> getviewtests(int testid);

	public void updatetest(testModel t);

	public List<testsPatientsModel> getalltestpatients();

	public List<testsPatientsModel> gettestwisepatients(int test);

	List<testsPatientsModel> getalltestpatientdetails(int test, String date1, String date2);

}
