package spring.orm.test.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import spring.orm.controller.PostConsultUpdateController;
import spring.orm.dao.PatientProfileUpdateDAOImpl;
import spring.orm.model.entity.PatientConsultationUpdateId;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.PrescriptionOutputmodel;

public class PatientProfileUpdateDAOImplTest {

	@Mock
	private EntityManager mockedEntityManager;

	@InjectMocks
	private PatientProfileUpdateDAOImpl patientProfileUpdateDAO;

	PostConsultUpdateController pcuc;

	public PatientProfileUpdateDAOImplTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() {
		// Arrange
		PatientMedicalProfile patientMedicalProfile = new PatientMedicalProfile();
		// Set the required properties of the patientMedicalProfile
		PatientConsultationUpdateId id = new PatientConsultationUpdateId();
		id.setAppn_id(1);
		id.setPatn_id(3);

		patientMedicalProfile.setId(id);
		patientMedicalProfile.setPatn_parameter("Diabetes");

		patientMedicalProfile.setPatn_value("127");
		patientMedicalProfile.setPatnParGroup("General");
		when(mockedEntityManager.find(eq(PatientMedicalProfile.class), any())).thenReturn(patientMedicalProfile);

		// Act
		boolean b = patientProfileUpdateDAO.save(patientMedicalProfile);

		assertEquals(b, true);
		// Add more assertions for other properties if needed
	}

	@Test
	public void testGetAllPrescription() {
		// Arrange
		int id = 3; // Sample patient ID
		// Create a list of expected PrescriptionOutputmodel objects
		List<PrescriptionOutputmodel> expectedPrescriptions = new ArrayList<>();
		String dateTimeString = "2023-06-26 11:30:00";
		Timestamp timestamp = Timestamp.valueOf(dateTimeString);
		PrescriptionOutputmodel prescription1 = new PrescriptionOutputmodel();
		prescription1.setAppn_id(5);
		prescription1.setAppn_sch_date(timestamp);
		prescription1.setPatn_parameter("Blood Pressure");
		prescription1.setPatn_pargroup("BP");
		prescription1.setPatn_value("120 mmgh");

		expectedPrescriptions.add(prescription1);

		Query query = Mockito.mock(Query.class);

		when(query.getResultList()).thenReturn(expectedPrescriptions);
		when(mockedEntityManager.createQuery(Mockito.anyString())).thenReturn(query);

		// Act
		List<PrescriptionOutputmodel> lp = patientProfileUpdateDAO.getallPrescription(id);

		// Assert
		verify(mockedEntityManager, times(1)).createQuery(Mockito.anyString());
		verify(query, times(1)).getResultList();

		assertEquals(lp.size(), 1);
		assertEquals(lp.get(0).getAppn_sch_date(), prescription1.getAppn_sch_date());
		assertEquals(lp.get(0).getAppn_id(), prescription1.getAppn_id());
		assertEquals(lp.get(0).getPatn_parameter(), prescription1.getPatn_parameter());
		assertEquals(lp.get(0).getPatn_pargroup(), prescription1.getPatn_pargroup());
		// Add additional assertions based on the expectedPrescriptions and actualPrescriptions data
	}

	// @Test
	// public void testGetAllPrescription() {
	// // Arrange
	// int id = 3; // Sample patient ID
	// // Create a list of expected PrescriptionOutputmodel objects
	// List<PrescriptionOutputmodel> expectedPrescriptions = new ArrayList<>();
	// String dateTimeString = "2023-06-26 11:30:00";
	// Timestamp timestamp = Timestamp.valueOf(dateTimeString);
	// PrescriptionOutputmodel prescription1 = new PrescriptionOutputmodel();
	// prescription1.setAppn_id(5);
	// prescription1.setAppn_sch_date(timestamp);
	// prescription1.setPatn_parameter("Blood Pressure");
	// prescription1.setPatn_pargroup("BP");
	// prescription1.setPatn_value("120 mmgh");
	//
	// expectedPrescriptions.add(prescription1);
	//
	// Query query = Mockito.mock(Query.class);
	// System.out.println("in testservice0");
	// when(mockedEntityManager.createQuery(Mockito.anyString())).thenReturn(query);
	// when(query.getResultList()).thenReturn(expectedPrescriptions);
	// // Mock the query execution
	// System.out.println("in testservice");
	// List<PrescriptionOutputmodel> lp = patientProfileUpdateDAO.getallPrescription(id);
	// verify(mockedEntityManager, times(1)).createQuery(Mockito.anyString());
	// verify(query, times(1)).getResultList();
	// System.out.println("in testservice");
	// // Assert
	// assertEquals(lp.size(), 1);
	// assertEquals(lp.get(0).getAppn_sch_date(), prescription1.getAppn_sch_date());
	// assertEquals(lp.get(0).getAppn_id(), prescription1.getAppn_id());
	// assertEquals(lp.get(0).getPatn_parameter(), prescription1.getPatn_parameter());
	// assertEquals(lp.get(0).getPatn_pargroup(), prescription1.getPatn_pargroup());
	// // Add additional assertions based on the expectedPrescriptions and actualPrescriptions data
	//
	// }

	// @Test
	// public void testGetAllAppointmentIds() {
	// // Arrange
	// int patnId = 7; // Replace with your test data
	// List<Integer> expectedIds = Arrays.asList(7);// Replace with your expected appointment IDs
	//
	// // Mock the query execution and return the expectedIds list
	// when(mockedEntityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class)))
	// .thenReturn(Mockito.mock(TypedQuery.class));
	// when(mockedEntityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))
	// .setParameter(Mockito.eq("patn_id"), Mockito.eq(patnId))).thenReturn(Mockito.mock(TypedQuery.class));
	// when(mockedEntityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))
	// .setParameter(Mockito.eq("patn_id"), Mockito.eq(patnId)).getResultList()).thenReturn(expectedIds);
	// // Act
	// List<Integer> result = patientProfileUpdateDAO.getAllAppointmentIds(patnId);
	// int x = result.size();
	// System.out.println(result + "result ");
	// // Assert
	// assertEquals(expectedIds.size(), x);
	//
	// }

}
