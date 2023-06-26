package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.DCDao;
import spring.orm.model.input.DCFilter;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;
import spring.orm.services.ReportServices;

@Controller
@RequestMapping("/dcadmin")
public class ReportController {

	@Autowired
	ReportServices rs;
	@Autowired
	private DCDao dcd;

	@RequestMapping(value = "/upload")
	public String dcreports(Model model) {

		return "dcadmin/dcreportspage";
	}

	@RequestMapping(value = "/")
	public String dcscreen() {
		return "dcadmin/dcscreen";
	}

	@RequestMapping(value = "/DCReports", method = RequestMethod.GET)
	public String reports(Model model) {
		List<Integer> data = dcd.fetchPatientReports();
		model.addAttribute("pids", data);
		System.out.println(data);
		return "dcadmin/DCReport";

	}

	@RequestMapping(value = "/uploaddata", method = RequestMethod.GET)
	public String upreports(@RequestParam("pid") int pid, Model model) {
		List<OutputReportData> ord = dcd.fetchPatientInfo(pid);
		model.addAttribute("ord", ord);
		return "dcadmin/ReportData";

	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("dgblId") int id) {
		if (!rs.isImageFile(file)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid file type. Only image files are allowed.");
		}

		System.out.println(id);
		String res = rs.fileUploadMethod(file, id);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@RequestMapping(value = "/testprofitdata", method = RequestMethod.GET)
	public String TestProfitData(Model model) {
		List<OutputTestNameProfit> tndata = dcd.fetchTestNameProfit();
		List<OutputTestMethodProfit> tmdata = dcd.fetchTestMethodProfit();
		List<OutputTestCategoryProfit> tcdata = dcd.fetchTestCategoryProfit();

		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);
		model.addAttribute("tcdata", tcdata);

		return "dcadmin/TestProfitData";
	}

	@RequestMapping(value = "/testDateWiseProfitdata", method = RequestMethod.GET)
	public String TestDateWiseProfitData(@ModelAttribute DCFilter fill, Model model) {

		System.out.println(fill.getFrom() + " " + fill.getTo());

		List<OutputTestNameProfit> tndata = dcd.fetchTestNameProfitDate(fill.getFrom(), fill.getTo());
		List<OutputTestMethodProfit> tmdata = dcd.fetchTestMethodProfitDate(fill.getFrom(), fill.getTo());
		List<OutputTestCategoryProfit> tcdata = dcd.fetchTestCategoryProfitDate(fill.getFrom(), fill.getTo());
		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);
		model.addAttribute("tcdata", tcdata);

		return "dcadmin/TestProfitDateWiseData";
	}

	@RequestMapping(value = "/patientname", method = RequestMethod.GET)
	public ResponseEntity<String> patientName(@RequestParam("pid") int pid) {
		// System.out.println("called?");
		String res = dcd.fetchPatientName(pid);
		// System.out.println(res);
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

}