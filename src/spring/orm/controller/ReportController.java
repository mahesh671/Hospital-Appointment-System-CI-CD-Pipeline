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

import spring.orm.contract.DCDAO;
import spring.orm.model.input.DCFilter;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;
import spring.orm.services.ReportServices;

@Controller
@RequestMapping("/dcadmin")
public class ReportController {

	ReportServices reportService;

	private DCDAO dCDAo;

	@Autowired
	public ReportController(ReportServices rs, DCDAO dcd) {
		super();
		this.reportService = rs;
		this.dCDAo = dcd;
	}

	// Handles the / URL and returns the view name for dcscreen
	@RequestMapping(value = "/")
	public String viewDCScreen() {
		return "dcadmin/dcscreen";
	}

	// Handles the /DCReports GET request and populates the model with patient reports
	@RequestMapping(value = "/DCReports", method = RequestMethod.GET)
	public String getReports(Model model) {
		List<Integer> data = dCDAo.fetchPatientIds();
		model.addAttribute("pids", data);
		System.out.println(data);
		return "dcadmin/DCReport";
	}

	// Handles the /uploaddata GET request and retrieves patient information based on pid
	@RequestMapping(value = "/uploaddata", method = RequestMethod.GET)
	public String getPendingReports(@RequestParam("pid") int pid, Model model) {
		List<OutputReportData> ord = dCDAo.fetchPatientReportsInfo(pid);
		model.addAttribute("ord", ord);
		return "dcadmin/ReportData";
	}

	// Handles the /uploadfile POST request for file upload functionality particularly for image file
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("dgblId") int id) {
		// Check if the uploaded file is an image file
		if (!reportService.isImageFile(file)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid file type. Only image files are allowed.");
		}

		System.out.println(id);
		String res = reportService.fileUpload(file, id);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	// Handles the /testprofitdata GET request and retrieves test profit data on name,method and category wise
	@RequestMapping(value = "/testprofitdata", method = RequestMethod.GET)
	public String TestProfitData(Model model) {
		List<OutputTestNameProfit> tndata = dCDAo.fetchTestNameWiseProfit();
		List<OutputTestMethodProfit> tmdata = dCDAo.fetchTestMethodWiseProfit();
		List<OutputTestCategoryProfit> tcdata = dCDAo.fetchTestCategoryWiseProfit();

		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);
		model.addAttribute("tcdata", tcdata);

		return "dcadmin/TestProfitData";
	}

	// Handles the /testDateWiseProfitdata GET request and retrieves test profit data based on date range through
	// DCFilter input model
	@RequestMapping(value = "/testDateWiseProfitdata", method = RequestMethod.GET)
	public String TestDateWiseProfitData(@ModelAttribute DCFilter fill, Model model) {

		System.out.println(fill.getFrom() + " " + fill.getTo());

		List<OutputTestNameProfit> tndata = dCDAo.fetchTestNameDateWiseProfit(fill.getFrom(), fill.getTo());
		List<OutputTestMethodProfit> tmdata = dCDAo.fetchTestMethodDateWiseProfit(fill.getFrom(), fill.getTo());
		List<OutputTestCategoryProfit> tcdata = dCDAo.fetchTestCategoryDateWiseProfit(fill.getFrom(), fill.getTo());
		model.addAttribute("tndata", tndata);
		model.addAttribute("tmdata", tmdata);
		model.addAttribute("tcdata", tcdata);

		return "dcadmin/TestProfitDateWiseData";
	}

	// Handles the /patientname GET request and retrieves the patient name based on pid
	@RequestMapping(value = "/patientname", method = RequestMethod.GET)
	public ResponseEntity<String> patientName(@RequestParam("pid") int pid) {
		// System.out.println("called?");
		String res = dCDAo.fetchPatientName(pid);
		// System.out.println(res);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}