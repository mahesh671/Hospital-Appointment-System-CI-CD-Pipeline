package spring.orm.test.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.DAO.UserDAO;
import spring.orm.contract.services.RegistrationServices;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.UserPass;
import spring.orm.model.input.RegistrationForm;
import spring.orm.services.RegistrationService;

public class RegistrationServiceTest {

	@Mock
	private PatientDAO patientDAO;

	@Mock
	private UserDAO userDAO;

	@Mock
	private UserPass userPass;

	@InjectMocks
	private RegistrationService registrationService;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testRegisterPatient() {
		// Mock data
		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setName("John Doe");
		registrationForm.setAge(30);
		registrationForm.setGender("Male");

		when(patientDAO.savePatientData(any())).thenReturn(1);

		// Invoke the method under test
		registrationService.registerPatient(registrationForm);

		// Verify the interactions and assertions
		verify(patientDAO).savePatientData(any());
		// Add more assertions based on your expected results
	}

	@Test
	public void testGetUser() {
		// Mock data
		String username = "testuser";
		UserPass mockUserPass = new UserPass();
		// Set mock user pass data

		when(userDAO.getUserDetails(username)).thenReturn(mockUserPass);

		// Invoke the method under test
		UserPass result = registrationService.getUser(username);

		// Verify the interactions and assertions
		verify(userDAO).getUserDetails(username);
		// Add more assertions based on your expected results
	}

	@Test
	public void testUpdateUser() {
		// Mock data
		UserPass mockUserPass = new UserPass();
		// Set mock user pass data

		// Invoke the method under test
		registrationService.updateUser(mockUserPass);

		// Verify the interactions and assertions
		verify(userDAO).updateUser(mockUserPass);
		// Add more assertions based on your expected results
	}

	@Test
	public void testIsUsernameAvailable() {
		// Mock data
		String username = "testuser";

		UserPass mockUserPass = new UserPass();
		mockUserPass.setUsername("existinguser");

		when(userDAO.getUserDetails(any())).thenReturn(mockUserPass);

		// Invoke the method under test
		boolean result = registrationService.isUsernameAvailable(username);

		// Verify the interactions and assertions
		verify(userDAO).getUserDetails(any());
		// Add more assertions based on your expected results
	}

	@Test
	public void testGetPatientDetails() {
		// Mock data
		int patientId = 1;
		PatientModel mockPatientModel = new PatientModel();
		// Set mock patient model data

		when(userDAO.getPatientDetails(patientId)).thenReturn(mockPatientModel);

		// Invoke the method under test
		PatientModel result = registrationService.getPatientDetails(patientId);

		// Verify the interactions and assertions
		verify(userDAO).getPatientDetails(patientId);
		// Add more assertions based on your expected results
	}

	@Test
	public void testCreatePatientSession() {
		// Mock data
		int patientId = 1;
		UserPass mockUser = mock(UserPass.class);

		PatientModel mockPatient = new PatientModel();
		// Set mock patient data
		mockPatient.setPatn_gender("Male");

		PatientDAO patientDAO = mock(PatientDAO.class);
		RegistrationServices registrationService = new RegistrationService();
		registrationService.setPatientDAO(patientDAO);

		when(patientDAO.getPatientById(patientId)).thenReturn(mockPatient);

		// Invoke the method under test
		PatientSession result = registrationService.createPatientSession(mockUser, mockPatient);

		// Verify the interactions and assertions
		// verify(patientDAO).getPatientById(mockPatient.getPatn_id());
		// Add more assertions based on your expected results
	}

	@Test
	public void testStorePatientSession() {
		// Mock data
		HttpSession mockSession = mock(HttpSession.class);
		PatientSession mockPatientSession = mock(PatientSession.class);
		// Set mock patient session data

		// Invoke the method under test
		registrationService.storePatientSession(mockSession, mockPatientSession);

		// Verify the interactions and assertions
		verify(mockSession).setAttribute("patientSession", mockPatientSession);
		// Add more assertions based on your expected results
	}
}
