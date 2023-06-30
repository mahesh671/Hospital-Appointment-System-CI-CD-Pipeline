package spring.orm.test.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import spring.orm.contract.DiagnosticBillDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.contract.TestDAO;
import spring.orm.controller.TestBookBillGenController;
import spring.orm.model.TestModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.patientsoutputmodel;
import spring.orm.model.output.testsCategoriesModel;
import spring.orm.services.TestServices;

public class TestBookBillGenControllerTest {

	@Mock
	private TestServices testService;

	@Mock
	private DiagnosticBillDAO diagnosticBillDAO;

	@Mock
	private TestDAO testDAO;

	@Mock
	private PatientDAO patientDAO;

	@Mock
	private Model model;

	@InjectMocks
	private TestBookBillGenController controller;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		// Inject the mock DoctorDAO into the DoctorController
		controller = new TestBookBillGenController(testService, diagnosticBillDAO, testDAO, null, patientDAO);
	}

	@Test
	public void testGetCategories() {
		// Arrange
		List<testsCategoriesModel> categories = new ArrayList<>();
		when(testDAO.getCategories()).thenReturn(categories);

		// Act
		ResponseEntity<String> response = controller.GetCategories(model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(categories));
	}

	@Test
	public void testGetPatients() {
		// Arrange
		List<patientsoutputmodel> patients = new ArrayList<>();
		when(testDAO.getPatients()).thenReturn(patients);

		// Act
		ResponseEntity<String> response = controller.getPatients(model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(patients));
	}

	@Test
	public void testGetTestByCategory() {
		// Arrange
		String category = "TestCategory";
		List<TestModel> tests = new ArrayList<>();
		when(testDAO.getTestByCategory(eq(category))).thenReturn(tests);

		// Act
		ResponseEntity<String> response = controller.getTestByCategory(category, model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(tests));
	}

	@Test
	public void testBookTest() {
		// Arrange
		BillInputModel billInputModel = new BillInputModel();

		// Act
		controller.BookTest(model, billInputModel);

		// Assert
		verify(diagnosticBillDAO).bookDcTest(eq(billInputModel));
	}

	@Test
	public void testGetTestPrice() {
		// Arrange
		int testId = 123;
		Object price = new Object();
		when(testDAO.getSelectedTestPrice(eq(testId))).thenReturn(price);

		// Act
		ResponseEntity<String> response = controller.getTestPrice(testId, model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(price));
	}

	@Test
	public void testStoreToDatabase() {
		// Arrange
		int patientId = 456;
		int billId = 789;
		when(diagnosticBillDAO.storeToDatabase(eq(patientId))).thenReturn(billId);

		// Act
		ResponseEntity<String> response = controller.storeToDatabase(model, patientId);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(billId));
	}

	@Test
	public void testTotalBills() {
		// Arrange
		int patientId = 456;
		List<Object> bills = new ArrayList<>();
		when(diagnosticBillDAO.getTotalBills(eq(patientId))).thenReturn(bills);

		// Act
		ResponseEntity<String> response = controller.totalBills(patientId, model);

		// Assert
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), new Gson().toJson(bills));
	}

	@Test
	public void testMailSend() {
		// Arrange
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		String email = "test@example.com";
		String content = "Test Content";

		// Act
		controller.mailSend(request, response, email, content);

		// Assert
		// verify(MailSendHelper).sendEmailTestBooking(eq(request), eq(response), eq(email), eq(content));
	}
}
