package spring.orm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.model.entity.PatientConsultationUpdateId;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.input.ProfileUpdateForm;

@Component
public class UpdateProfileService {

	@Autowired
	private PatientProfileUpdateDAO ppd;

	public void UpdateProfile(ProfileUpdateForm ppu, CommonsMultipartFile reportsInput) {
		// TODO Auto-generated method stub
		PatientMedicalProfile pmp = new PatientMedicalProfile();
		PatientConsultationUpdateId pcu = new PatientConsultationUpdateId(ppu.getPatientId(), ppu.getAppnid());
		int pid = pcu.getPatn_id();
		int appnid = pcu.getAppn_id();

		pcu.setAppn_id(appnid);
		pcu.setPatn_id(pid);
		pmp.setId(pcu);
		pmp.setPatn_parameter(ppu.getParameter());
		pmp.setPatnParGroup(ppu.getPatgroup());
		pmp.setPatn_prescription(reportsInput.getBytes());
		pmp.setPatn_value(ppu.getValue());
		ppd.save(pmp);

	}
}
