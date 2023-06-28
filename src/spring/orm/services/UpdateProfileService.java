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
	private PatientProfileUpdateDAO patientProfileUpdate;

	public void updateProfile(ProfileUpdateForm profileUpdateForm, CommonsMultipartFile reportsInput) {
		// TODO Auto-generated method stub
		PatientMedicalProfile patinetMedicalProfile = new PatientMedicalProfile();
		PatientConsultationUpdateId patientConsultationUpdate = new PatientConsultationUpdateId(profileUpdateForm.getPatientId(), profileUpdateForm.getAppnid());
		int pid = patientConsultationUpdate.getPatn_id();
		int appnid = patientConsultationUpdate.getAppn_id();

		patientConsultationUpdate.setAppn_id(appnid);
		patientConsultationUpdate.setPatn_id(pid);
		patinetMedicalProfile.setId(patientConsultationUpdate);
		patinetMedicalProfile.setPatn_parameter(profileUpdateForm.getParameter());
		patinetMedicalProfile.setPatnParGroup(profileUpdateForm.getPatgroup());
		patinetMedicalProfile.setPatn_prescription(reportsInput.getBytes());
		patinetMedicalProfile.setPatn_value(profileUpdateForm.getValue());
		patientProfileUpdate.save(patinetMedicalProfile);

	}
}
