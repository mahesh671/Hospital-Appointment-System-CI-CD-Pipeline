package spring.orm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import spring.orm.contract.DAO.DCDAO;
import spring.orm.contract.services.ReportService;
import spring.orm.model.input.DCFilter;
import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

@Controller
@RequestMapping("/dcadmin")
public class ReportController {

	ReportService reportService;

	private DCDAO dCDAo;

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	public ReportController(ReportService rs, DCDAO dcd) {
		super();
		this.reportService = rs;
		this.dCDAo = dcd;
	}

	// Handles the / URL and returns the view name for dcscreen
	@RequestMapping(value = "/")
	public String viewDCScreen() {
		logger.info("Entered into viewDCScreen");
		logger.info("Fetching the the dc dashboard screen");
		return "dcadmin/dcscreen";
	}

	// Handles the /DCReports GET request and populates the model with patient reports
	@RequestMapping(value = "/DCReports", method = RequestMethod.GET)
	public String viewReportPage(Model model) {
		logger.info("Entered into viewReportsPage");

		// fetches the patient id
		List<Integer> data = dCDAo.fetchPatientIds();
		logger.info("fetched all the patient ids");

		// insert the fetched patient id list into model
		model.addAttribute("pids", data);
		logger.info("Inserted the data into models");

		logger.info("Fetching the the report screen");
		return "dcadmin/DCReport";
	}

	// Handles the /uploaddata GET request and retrieves patient information based on pid
	@RequestMapping(value = "/uploaddata", method = RequestMethod.GET)
	public String getPendingReports(@RequestParam("pid") int pid, Model model) {
		logger.info("Entered into viewReportsPage");

		// fetches the patient reports that still needs to be uploaded
		List<OutputReportData> ord = dCDAo.fetchPatientReportsInfo(pid);

		logger.info("fetched the patient reports that still needs  to be uploaded");

		// inserts the patient information list into the model
		model.addAttribute("ord", ord);
		logger.info("Inserted the data into models");

		logger.info("Fetching the the reportData screen");

		return "dcadmin/ReportData";
	}

	// Handles the /uploadfile POST request for file upload functionality particularly for image file
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("dgblId") int id) {
		logger.info("Entered into uploadFile");

		// Check if the uploaded file is an image file
		if (!reportService.isImageFile(file)) {
			logger.info("uploaded file is not an image file");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid file type. Only image files are allowed.");
		}
		logger.info("uploaded file is an image file");

		// gets the response based on the result of file upload
		String res = reportService.fileUpload(file, id);

		logger.info("sending the response of result string res: " + res);

		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	// Handles the /testprofitdata GET request and retrieves test profit data on name,method and category wise
	@RequestMapping(value = "/testprofitdata", method = RequestMethod.GET)
	public String TestProfitData(Model model) {
		logger.info("Entered into TestProfitData");

		// fetches the test name wise profit data
		List<OutputTestNameProfit> tndata = dCDAo.fetchTestNameWiseProfit();
		logger.info("fetched the test name wise profit data");

		// fetches the test method wise profit data
		List<OutputTestMethodProfit> tmdata = dCDAo.fetchTestMethodWiseProfit();
		logger.info("fetched the test method wise profit data");

		// fetches the test category wise profit data
		List<OutputTestCategoryProfit> tcdata = dCDAo.fetchTestCategoryWiseProfit();
		logger.info("fetched the test category wise profit data");

		// inserts the test name wise data list into the model
		model.addAttribute("tndata", tndata);
		logger.info("Inserted the data into models");
		// inserts the test method wise data list into the model
		model.addAttribute("tmdata", tmdata);
		logger.info("Inserted the data into models");
		// inserts the test category wise data list into the model
		model.addAttribute("tcdata", tcdata);
		logger.info("Inserted the data into models");

		logger.info("sending the response TestProfitData screen");
		return "dcadmin/TestProfitData";
	}

	// Handles the /testDateWiseProfitdata GET request and retrieves test profit data based on date range through
	// DCFilter input model
	@RequestMapping(value = "/testDateWiseProfitdata", method = RequestMethod.GET)
	public String TestDateWiseProfitData(@ModelAttribute DCFilter fill, Model model) {
		logger.info("Entered into TestProfitData with date wise filter");

		// fetches the test name wise profit data with date wise filter
		List<OutputTestNameProfit> tndata = dCDAo.fetchTestNameDateWiseProfit(fill.getFrom(), fill.getTo());
		logger.info("fetched the test name wise profit data with date wise filter");

		// fetches the test method wise profit data with date wise filter
		List<OutputTestMethodProfit> tmdata = dCDAo.fetchTestMethodDateWiseProfit(fill.getFrom(), fill.getTo());
		logger.info("fetched the test method wise profit data with date wise filter");

		// fetches the test category wise profit data with date wise filter
		List<OutputTestCategoryProfit> tcdata = dCDAo.fetchTestCategoryDateWiseProfit(fill.getFrom(), fill.getTo());
		logger.info("fetched the test category wise profit data with date wise filter");

		// inserts the test name wise data list into the model
		model.addAttribute("tndata", tndata);
		logger.info("Inserted the data into models");

		// inserts the test method wise data list into the model
		model.addAttribute("tmdata", tmdata);
		logger.info("Inserted the data into models");

		// inserts the test category wise data list into the model
		model.addAttribute("tcdata", tcdata);
		logger.info("Inserted the data into models");

		logger.info("Fetching the the TestProfitDateWiseData screen");

		return "dcadmin/TestProfitDateWiseData";
	}

	// Handles the /patientname GET request and retrieves the patient name based on pid
	@RequestMapping(value = "/patientname", method = RequestMethod.GET)
	public ResponseEntity<String> patientName(@RequestParam("pid") int pid) {
		logger.info("Entered into patientName");

		// fetches the patient name
		String res = dCDAo.fetchPatientName(pid);
		logger.info("fetched into patientName");
		logger.info("Sending the response res: " + res);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}