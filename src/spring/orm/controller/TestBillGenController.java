package spring.orm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import spring.orm.contract.DiagnosticBillDao;
import spring.orm.contract.PatientDao;
import spring.orm.contract.TestDao;
import spring.orm.model.testModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.PatientNameOutputModel;
import spring.orm.services.TestServices;
import spring.orm.util.MailSend;

@Controller
public class TestBillGenController {

	TestServices ts;
	@Autowired
	DiagnosticBillDao dbs;
	@Autowired
	TestDao td;
	@Autowired
	HttpSession httpSession;
	@Autowired
	private PatientDao pdao;

	@RequestMapping("/dcadmin/booktest")
	public String GetCat(Model model) {

		List<testModel> lc = td.gettests();
		List<PatientNameOutputModel> lp = pdao.getAllPatientidsNames();

		System.out.println("*********************" + lc);
		System.out.println("*********************" + lp);
		Gson gson = new Gson();
		String json = gson.toJson(lc);
		Gson gson1 = new Gson();
		String json1 = gson1.toJson(lp);
		model.addAttribute("cats", json);
		model.addAttribute("pats", json1);

		return "dcadmin/booktest";

	}

	@RequestMapping(value = "/dcadmin/gettestbycat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> GettestbyCat(@RequestParam String cat, Model model) {

		List<testModel> test = td.gettestbycat(cat);
		// System.out.println(test.get(1));

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(test));

	}

	@RequestMapping(value = "/dcadmin/bookdctest", method = RequestMethod.POST)
	public void booktestt(Model model, @ModelAttribute BillInputModel bi) {
		System.out.println("in book");
		dbs.booktestt(bi);

		System.out.println("Inside testdetails");
		// return "dcadmin/booktest";

	}

	@RequestMapping(value = "/dcadmin/gettestprice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> Gettestprice(@RequestParam int test, Model model) {
		System.out.println("inside  price testcat");
		Object price = td.gettestprice(test);
		// System.out.println(test.get(1));

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(price));

	}

	@RequestMapping(value = "/dcadmin/storedb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storedb(Model model, @RequestParam int patient) {
		System.out.println("inside  price testcat");
		int billid = dbs.storedb(patient);

		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(billid));
	}

	@RequestMapping(value = "/dcadmin/totalbills", method = RequestMethod.GET)
	public ResponseEntity<String> totalbills(@RequestParam int patient, Model model) {
		System.out.println("in book");
		List<Object> lb = dbs.gettotalbills(patient);

		System.out.println("Inside total testdetails");
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lb));

	}

	@RequestMapping(value = "/dcadmin/mailsend2", method = RequestMethod.POST)
	public @ResponseBody void mailsend(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String email, @RequestParam String content) {
		System.out.println("in mail1");

		try {
			MailSend.sendEmail1(request, response, email, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}