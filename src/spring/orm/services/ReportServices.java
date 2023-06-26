package spring.orm.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.DCDao;

@Component
public class ReportServices {
	@Autowired
	DCDao dc;

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

		dc.saveReportInfo(id, filepath);

	}

	public boolean isImageFile(CommonsMultipartFile file) {
		// TODO Auto-generated method stub
		// Define the allowed image file extensions
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

		// Get the file extension from the original filename
		String originalFilename = file.getOriginalFilename();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

		// Check if the file extension is in the allowed list
		return allowedExtensions.contains(fileExtension);

	}

}