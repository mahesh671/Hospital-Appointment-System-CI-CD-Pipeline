package spring.orm.test.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import spring.orm.contract.DAO.AppointmentDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.DAO.PatientProfileUpdateDAO;
import spring.orm.controller.PostConsultUpdateController;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.ProfileUpdateForm;
import spring.orm.model.output.PrescriptionOutputmodel;
import spring.orm.services.UpdateProfileService;

public class PostConsultUpdateControllerTest {

	@Mock
	private PatientDAO patientDAO;

	@Mock
	private PatientProfileUpdateDAO profileUpdateDAO;

	@Mock
	private HttpSession httpSession;

	@Mock
	private UpdateProfileService updateProfileService;

	@Mock
	private AppointmentDAO appointmentDAO;

	@InjectMocks
	private PostConsultUpdateController controller;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		// Inject the mock DoctorDAO into the DoctorController
		controller = new PostConsultUpdateController(patientDAO, profileUpdateDAO, null, updateProfileService,
				appointmentDAO);
	}

	@Test
	public void testGetAllPrescription() {
		// Arrange
		Model model = Mockito.mock(Model.class);
		PatientSession patientSession = new PatientSession();
		patientSession.setId(1);

		List<PrescriptionOutputmodel> prescriptionList = new ArrayList<>();
		Mockito.when(profileUpdateDAO.getallPrescription(eq(1))).thenReturn(prescriptionList);

		// Act
		String viewName = controller.getAllPrescription(model, patientSession);

		// Assert
		assertEquals(viewName, "patient/patpresdisplay");
		Mockito.verify(model).addAttribute(eq("pres"), eq(prescriptionList));
	}

	@Test
	public void testGetPatientId() {
		// Arrange
		Model model = Mockito.mock(Model.class);
		List<Integer> patientIDList = new ArrayList<>();
		Mockito.when(patientDAO.getPatientIds()).thenReturn(patientIDList);

		// Act
		ResponseEntity<String> response = controller.getPatientId(model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(patientIDList));
	}

	@Test
	public void testGetPatientById() {
		// Arrange
		Model model = Mockito.mock(Model.class);
		int patientId = 1;
		List<Integer> patientIdList = new ArrayList<>();
		Mockito.when(profileUpdateDAO.getAllAppointmentIds(eq(patientId))).thenReturn(patientIdList);

		// Act
		ResponseEntity<String> response = controller.getPatientById(patientId, model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(patientIdList));
	}

	@Test
	public void testGetPatientProfile() {
		// Arrange
		ProfileUpdateForm profileUpdateForm = new ProfileUpdateForm();
		CommonsMultipartFile reportsInput = Mockito.mock(CommonsMultipartFile.class);
		int appnid = 1;
		AppointmentEntity appointmentEntity = Mockito.mock(AppointmentEntity.class);
		PatientSession patientSession = new PatientSession();
		patientSession.setId(1);

		// Mockito.when(profileUpdateForm.getAppnid()).thenReturn(appnid);
		Mockito.when(appointmentDAO.getAppointmentById(eq(appnid))).thenReturn(appointmentEntity);
		Mockito.when(appointmentEntity.getAppn_sch_date())
				.thenReturn(java.sql.Timestamp.valueOf("2022-01-01 00:00:00"));

		// Act
		String viewName = controller.getPatientProfile(profileUpdateForm, reportsInput);

		// Assert
		assertEquals(viewName, "admin/postredirect");
		Mockito.verify(updateProfileService).updateProfile(eq(profileUpdateForm), eq(reportsInput));
		Mockito.verify(appointmentDAO).updateAppStatusComp(eq(appnid));
		Mockito.verify(patientDAO).updateLastvisitAndLastAppointmentInfo(eq(profileUpdateForm.getPatientId()),
				eq(java.sql.Timestamp.valueOf("2022-01-01 00:00:00").toLocalDateTime().toLocalDate()), eq(appnid));
	}

	// Add test methods for other controller methods here

}
