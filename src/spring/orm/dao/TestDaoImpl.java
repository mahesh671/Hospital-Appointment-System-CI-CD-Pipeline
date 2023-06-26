package spring.orm.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.TestDao;
import spring.orm.model.testModel;
import spring.orm.model.input.TestInputModel;
import spring.orm.model.output.testsPatientsModel;

@Component
public class TestDaoImpl implements TestDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void savetest(TestInputModel t) {
		testModel s = new testModel();
		s.setTest_category(t.getTest_category());
		s.setTest_fromrange(t.getTest_fromrange());
		s.setTest_method(t.getTest_method());
		s.setTest_name(t.getTest_name());
		s.setTest_price(t.getTest_price());
		s.setTest_torange(t.getTest_torange());

		System.out.println("Inside savetest");
		em.persist(s);
	}

	@Override
	@Transactional
	public List<testModel> gettests() {

		System.out.println("1");
		List<testModel> t = em.createQuery("SELECT t FROM testModel t where isDeleted = false", testModel.class)
				.getResultList();
		System.out.println("Hello" + t);
		return t;
	}

	@Override
	@Transactional
	public testModel gettestbyid(int id) {

		return em.find(testModel.class, id);
	}

	@Override
	@Transactional
	public void updatetest(testModel t) {

		// testModel s = session.get(testModel.class, test_id);
		// Update the properties of the testModel object

		em.merge(t);
	}

	@Transactional
	@Override
	public void deltest(int test_id) {

		testModel s = em.find(testModel.class, test_id);
		s.setDeleted(true);

		em.merge(s);
	}

	@Transactional
	@Override
	public List<testModel> gettestbycat(String cat) {
		// TODO Auto-generated method stub return
		System.out.println(cat);
		return em.createQuery("select t from testModel t where t.test_category=:cat", testModel.class)
				.setParameter("cat", cat).getResultList();
	}

	@Transactional
	@Override
	public Object gettestprice(int test) {
		// TODO Auto-generated method stub return
		// System.out.println(cat);
		System.out.println(test);
		return em.createQuery("select t.test_price from testModel t where t.test_id=:test").setParameter("test", test)
				.getSingleResult();
	}

	@Override
	public List<testModel> getcat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTestCat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getviewtests(int testid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<testsPatientsModel> getalltestpatientdetails(int test, String date1, String date2) {
		List<testsPatientsModel> data = null;
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,testModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id ";
		System.out.println(test);
		if (test >= 0) {
			System.out.println("-4");
			hql += " and t.test_id = :test";
		}
		if (test == -1) {
			System.out.println("-1");
			hql += "";
		}
		if (!date1.equals("") & !date2.equals("")) {

			hql += " and d.dgbl_date>=:date1 and d.dgbl_date<=:date2";
		}
		TypedQuery<testsPatientsModel> q = em.createQuery(hql, spring.orm.model.output.testsPatientsModel.class);
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

	@Override
	public List<testsPatientsModel> getalltestpatients() {
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,testModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id ";

		List<testsPatientsModel> data = em.createQuery(hql, testsPatientsModel.class).getResultList();
		return data;
	}

	@Override
	public List<testsPatientsModel> gettestwisepatients(int test) {
		List<testsPatientsModel> data = null;
		String hql = "SELECT new spring.orm.model.output.testsPatientsModel(p.patn_id,p.patn_name,t.test_name,t.test_method,t.test_category,t.test_price,d.dgbl_date) "
				+ "from PatientModel p,testModel t,DiagnosticBillModel d,Diagnostictestbill dt "
				+ "where dt.id.dgbltestId=t.test_id and dt.id.dgblId=d.dgbl_id and d.dgbl_patn_id=p.patn_id  ";
		if (test >= 0) {
			System.out.println("-4");
			hql += " and t.test_id = :test";
		}
		if (test == -1) {
			System.out.println("-1");
			hql += "";
		}
		TypedQuery<testsPatientsModel> q = em.createQuery(hql, spring.orm.model.output.testsPatientsModel.class);
		if (test >= 0) {
			q.setParameter("test", test);
		}
		data = q.getResultList();
		return data;
	}
}