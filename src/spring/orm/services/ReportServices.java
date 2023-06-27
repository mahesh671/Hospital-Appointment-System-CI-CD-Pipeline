package spring.orm.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.DCDAO;

@Component
public class ReportServices {
	@Autowired
	DCDAO dc;

	// Uploads the image file into db based on report id
	public String fileUploadMethod(CommonsMultipartFile file, int id) {
		byte[] filepath = null;
		if (!file.isEmpty()) {
			try {
				filepath = file.getBytes();
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		} else

		{
			return "empty";
		}

		saveFileDetails(id, filepath);

		return "success";

	}

	public void saveFileDetails(int id, byte[] filepath) {

		// Save the file details for the given ID and file path using the DCDao contract
		dc.saveReportInfo(id, filepath);

	}

	public boolean isImageFile(CommonsMultipartFile file) {

		// Define the allowed image file extensions
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

		// Get the file extension from the original filename
		String originalFilename = file.getOriginalFilename();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

		// Check if the file extension is in the allowed list
		return allowedExtensions.contains(fileExtension);

	}

}