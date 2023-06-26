package spring.orm.services;

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
		return false;
	}

}