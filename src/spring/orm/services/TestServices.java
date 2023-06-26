package spring.orm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.TestDao;
import spring.orm.model.testModel;

@Service
public class TestServices {
	@Autowired
	private TestDao tdao;

	public List<testModel> gettests() {
		System.out.println("in gettest dao");
		return tdao.gettests();
	}



	public testModel gettestbyid(int id) {
		return tdao.gettestbyid(id);
	}

	public void updatetest(testModel t) {
		tdao.updatetest(t);

	}

	public void deltest(int test_id) {
		// TODO Auto-generated method stub
		tdao.deltest(test_id);

	}

	public List<testModel> getcat() {
		// TODO Auto-generated method stub
		return tdao.getcat();
	}

	public List<String> getTestCat() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<testModel> gettestbycat(String cat) { // TODO Auto-generated method stub return
		return tdao.gettestbycat(cat);
	}

	public Object gettestprice(int test) {
		// TODO Auto-generated method stub
		return tdao.gettestprice(test);
	}

}