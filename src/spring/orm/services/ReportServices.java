package spring.orm.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.DAO.DCDAO;
import spring.orm.contract.services.ReportService;

@Component
public class ReportServices implements ReportService {

	DCDAO dc;

	@Autowired
	public ReportServices(DCDAO dc) {
		super();
		this.dc = dc;
	}

	private static final Logger logger = LoggerFactory.getLogger(ReportServices.class);

	// Uploads the image file into db based on report id
	public String fileUpload(CommonsMultipartFile file, int id) {
		logger.info("Entered into fileUpload");

		byte[] filecontent = null;

		

			try {
				// converting the image into byte form
				filecontent = file.getBytes();
			} catch (Exception e) {
				logger.info("Exception caused " + e);
				e.printStackTrace();
				return "error";
			}
		

		// saves the file to its corresponding report id
		saveFileDetails(id, filecontent);
		logger.info("saved the file to its corresponding report id");

		logger.info("returning the response as a success");
		return "success";

	}

	// saves the file in byte form to database
	public void saveFileDetails(int id, byte[] content) {
		logger.info("Entered into saveFileDetails");

		// Save the file details for the given ID and file path using the DCDao contract
		dc.saveReportInfo(id, content);
		logger.info("Saved the file details for the given ID and file path using the DCDao contract");

	}

	// checks whether file is an Image file or not
	public boolean isImageFile(CommonsMultipartFile file) {
		logger.info("Entered into isImageFile");

		// Define the allowed image file extensions
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
		logger.info("Defined the allowed image file extensions");

		// Get the file extension from the original filename
		String originalFilename = file.getOriginalFilename();

		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
		logger.info("Got the file extension from the original filename");

		boolean res = allowedExtensions.contains(fileExtension);
		logger.info("sending the response based on the check res: " + res);
		// Check if the file extension is in the allowed list
		return res;

	}

}