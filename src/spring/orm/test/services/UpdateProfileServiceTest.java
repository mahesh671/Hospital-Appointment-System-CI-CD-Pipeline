package spring.orm.test.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.input.ProfileUpdateForm;
import spring.orm.services.UpdateProfileService;

public class UpdateProfileServiceTest {

	@Mock
	private PatientProfileUpdateDAO patientProfileUpdateDAO;

	@InjectMocks
	private UpdateProfileService updateProfileService;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateProfile() {
		// Arrange
		ProfileUpdateForm profileUpdateForm = new ProfileUpdateForm();
		profileUpdateForm.setPatientId(1); // Set patient ID
		profileUpdateForm.setAppnid(2); // Set appointment ID
		profileUpdateForm.setParameter("Parameter");
		profileUpdateForm.setPatgroup("Group");
		profileUpdateForm.setValue("Value");
		CommonsMultipartFile reportsInput = mock(CommonsMultipartFile.class);

		// Act
		updateProfileService.updateProfile(profileUpdateForm, reportsInput);

		// Assert
		verify(patientProfileUpdateDAO, times(1)).save(any(PatientMedicalProfile.class));
		// Add more assertions as needed
	}
}