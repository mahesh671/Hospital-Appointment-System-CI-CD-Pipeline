package spring.orm.contract.services;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface ReportService {

	// Uploads the image file into db based on report id
	String fileUpload(CommonsMultipartFile file, int id);

	// saves the file in byte form to database
	void saveFileDetails(int id, byte[] content);

	// checks whether file is an Image file or not
	boolean isImageFile(CommonsMultipartFile file);

}