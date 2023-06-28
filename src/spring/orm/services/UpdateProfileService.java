package spring.orm.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.controller.AppointmentController;
import spring.orm.model.entity.PatientConsultationUpdateId;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.input.ProfileUpdateForm;

@Component
public class UpdateProfileService {

	@Autowired
	private PatientProfileUpdateDAO patientProfileUpdate;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	public void updateProfile(ProfileUpdateForm profileUpdateForm, CommonsMultipartFile reportsInput) {
		// TODO Auto-generated method stub
		logger.info("Inside Profile Update service");
		PatientMedicalProfile patinetMedicalProfile = new PatientMedicalProfile();
		PatientConsultationUpdateId patientConsultationUpdate = new PatientConsultationUpdateId(
				profileUpdateForm.getPatientId(), profileUpdateForm.getAppnid());
		int pid = patientConsultationUpdate.getPatn_id();
		int appnid = patientConsultationUpdate.getAppn_id();
		logger.info("patiend id", pid);
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