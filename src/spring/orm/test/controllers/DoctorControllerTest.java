package spring.orm.test.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.DocScheduleDAO;
import spring.orm.contract.DAO.DoctorsDAO;
import spring.orm.contract.DAO.SpecializationDAO;
import spring.orm.contract.services.DoctorOutputServices;
import spring.orm.controller.DoctorController;
import spring.orm.customexceptions.InvalidWeekdayException;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;

public class DoctorControllerTest {

	@Mock
	private DoctorsDAO doctorDAO;

	@Mock
	private SpecializationDAO specializationDAO;

	@Mock
	private DoctorOutputServices doctorOutputService;

	@Mock
	private DocScheduleDAO doctorScheduleDAO;

	@InjectMocks
	private DoctorController doctorController;

	@Mock
	private Logger loggerMock;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		// Create a mock instance of the DoctorDAO
		doctorDAO = Mockito.mock(DoctorsDAO.class);

		// Inject the mock DoctorDAO into the DoctorController
		doctorController = new DoctorController(doctorDAO, specializationDAO, doctorOutputService, doctorScheduleDAO);
	}

	@Test
	public void testSaveDoctor() throws InvalidWeekdayException, FileNotFoundException {
		// Mock input data
		DoctorInput doctorInputModel = new DoctorInput();
		CommonsMultipartFile docPhoto = mock(CommonsMultipartFile.class);
		Model model = mock(Model.class);

		// Mock dependencies
		int docId = 17;
		when(doctorOutputService.addDoc(eq(doctorInputModel), eq(docPhoto))).thenReturn(docId);

		// Call the controller method
		String result = doctorController.saveDoctor(doctorInputModel, docPhoto, model);

		// Verify the interactions and assertions
		verify(doctorScheduleDAO).addDoctorSchedule(eq(doctorInputModel), eq(docId));
		verify(model, never()).addAttribute(eq("error"), anyString());
		assertEquals(result, "admin/redirect");
	}

	@Test
	public void testGetDoctorpage() {
		// Arrange
		int page = 1;
		Model modelMock = Mockito.mock(Model.class);

		// Act
		String result = doctorController.getDoctorpage(page, modelMock);

		// Assert
		Mockito.verify(modelMock).addAttribute(Mockito.eq("docsche"), Mockito.anyList());
		Mockito.verify(modelMock).addAttribute(Mockito.eq("totalPages"), Mockito.anyInt());
		Mockito.verify(modelMock).addAttribute(Mockito.eq("currentPage"), Mockito.anyInt());
		Mockito.verify(modelMock).addAttribute(Mockito.eq("speclist"), Mockito.anyList());

		Assert.assertEquals(result, "admin/doctor");
	}

	@Test
	public void testUpdateDoctor() throws IOException, InvalidWeekdayException, FileNotFoundException {
		// Mock input data
		DoctorUpdateModel doctorUpdate = new DoctorUpdateModel();
		CommonsMultipartFile docPhoto = mock(CommonsMultipartFile.class);
		Model model = mock(Model.class);

		// Mock dependencies
		int doctorId = 1;
		when(doctorOutputService.updateDoctor(eq(doctorUpdate), eq(docPhoto))).thenReturn(doctorId);

		// Call the controller method
		String result = doctorController.updateDoctor(doctorUpdate, docPhoto, model);

		// Verify the interactions and assertions
		verify(model, never()).addAttribute(eq("error"), anyString());
		assertEquals(result, "redirect:/admin/doctors");
	}

	@Test
	public void testGetDoctor() throws NullPointerException {
		// Mock input data
		int doctorId = 1;

		// Mock dependencies
		DoctorTemp doctor = new DoctorTemp();
		when(doctorDAO.getDoctor(eq(doctorId))).thenReturn(doctor);

		// Call the controller method
		DoctorTemp result = doctorController.getDoctor(doctorId);

		// Verify the interactions and assertions
		assertEquals(result, doctor);
	}

	@Test
	public void testHandleInvalidWeekdayException() {
		// Mock exception
		InvalidWeekdayException exception = mock(InvalidWeekdayException.class);

		// Call the exception handler method
		ResponseEntity<?> response = doctorController.handleInvalidWeekdayException(exception);

		// Verify the interactions and assertions
		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		assertEquals(response.getBody(), exception.getMessage());
	}
}