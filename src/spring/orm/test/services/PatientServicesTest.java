package spring.orm.test.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.PatientDAO;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientlastvisitOutput;
import spring.orm.services.PatientServices;

public class PatientServicesTest {

	@Mock
	private PatientDAO patientDAOMock;

	@InjectMocks
	private PatientServices patientServices;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetTestsDetails() {
		// Arrange
		int patientId = 123;
		List<Object> expectedTests = new ArrayList<>();
		expectedTests.add("Stress Test");
		expectedTests.add("MRI Scan");

		when(patientDAOMock.getPatientTestsById(patientId)).thenReturn(expectedTests);

		// Act
		List<Object> tests = patientServices.getTestsDetails(patientId);

		// Assert
		assertEquals(tests, expectedTests);
		verify(patientDAOMock, times(1)).getPatientTestsById(patientId);
	}

	@Test
	public void testGetAppointmentsDetails() {
		// Arrange
		int patientId = 3;
		List<Object> expectedAppointments = new ArrayList<>();
		expectedAppointments.add(3);
		expectedAppointments.add("Dr. Robert Davis");
		expectedAppointments.add("2023-06-08 14:00:00");
		expectedAppointments.add("CNL");

		when(patientDAOMock.getAppointmentsById(patientId)).thenReturn(expectedAppointments);

		// Act
		List<Object> appointments = patientServices.getAppointmentsDetails(patientId);

		// Assert
		assertEquals(appointments, expectedAppointments);
		verify(patientDAOMock, times(1)).getAppointmentsById(patientId);
	}

	@Test
	public void testGetParamterValues() {

		int patientId = 3;
		// Arrange
		List<ParaGroupOutput> expectedParameterValues = new ArrayList<>();
		ParaGroupOutput paraGroupOutput1 = new ParaGroupOutput();
		paraGroupOutput1.setPatn_parameter("cholestrol");
		paraGroupOutput1.setPatn_parameter("General");
		paraGroupOutput1.setPatn_parameter("180 mg/dL");
		paraGroupOutput1.setPatn_parameter("2023-06-08 14:00:00");
		expectedParameterValues.add(paraGroupOutput1);

		when(patientDAOMock.getParameterValues(patientId)).thenReturn(expectedParameterValues);

		// Act
		List<ParaGroupOutput> parameterValues = patientServices.getParamterValues(3);

		// Assert
		assertEquals(parameterValues, expectedParameterValues);
		verify(patientDAOMock, times(1)).getParameterValues(patientId);
	}

	@Test
	public void testGetPatientLastVisit() {
		// Arrange
		LocalDate specificDate = LocalDate.of(2023, 06, 8);
		Date Date = new Date(2023, 06, 8);
		int patientId = 3;
		List<PatientlastvisitOutput> expectedLastVisitInfo = new ArrayList<>();
		PatientlastvisitOutput lastVisit1 = new PatientlastvisitOutput(specificDate, "Dr. Robert Davis", 200.0, Date);
		expectedLastVisitInfo.add(lastVisit1);

		when(patientDAOMock.getLastAppointmentInfoById(patientId)).thenReturn(expectedLastVisitInfo);

		// Act
		List<PatientlastvisitOutput> lastVisitInfo = patientServices.getPatientLastVisit(patientId);

		// Assert
		assertEquals(lastVisitInfo, expectedLastVisitInfo);
		verify(patientDAOMock, times(1)).getLastAppointmentInfoById(patientId);
	}

	@Test
	public void testGetAppointmentTestCounts() {
		// Create a sample patient ID
		int patientId = 3;

		// Create a list of appointment test counts
		List<Object> expectedCounts = Arrays.asList(1, 3);

		// Mock the behavior of the patientDAO.getAppointmentTestsCount() method
		when(patientDAOMock.getAppointmentTestsCount(patientId)).thenReturn(expectedCounts);

		// Call the method under test
		List<Object> actualCounts = patientServices.getAppointmentTestCounts(patientId);

		// Verify the result
		assertEquals(expectedCounts, actualCounts);

		// Verify that the patientDAO.getAppointmentTestsCount() method was called with the correct argument
		verify(patientDAOMock, times(1)).getAppointmentTestsCount(patientId);
	}
}