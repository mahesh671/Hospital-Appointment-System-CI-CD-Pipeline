package spring.orm.test.controllers;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.AppointmentDAO;
import spring.orm.contract.DAO.DoctorsDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.DAO.SpecializationDAO;
import spring.orm.contract.services.DoctorOutputServices;
import spring.orm.contract.services.PaymentService;
import spring.orm.controller.AppointmentController;
import spring.orm.model.PatientSession;
import spring.orm.model.Specialization;
import spring.orm.model.output.AppOutFormFamily;
import spring.orm.services.AppointmentService;

public class AppointmentControllerTest {
	@Mock
	private SpecializationDAO specializationDAO;

	@Mock
	private DoctorOutputServices doctorservice;

	@Mock
	private PatientDAO patientDAO;

	@Mock
	private DoctorsDAO doctorDAO;

	@Mock
	private AppointmentDAO appointmentDAO;

	@Mock
	private AppointmentService appointmentService;

	@Mock
	private PaymentService ps;

	@InjectMocks
	private AppointmentController appointmentController;

	@Mock
	private Model model;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetNewAppointment() {
		List<Specialization> specializationList = new ArrayList<>();
		Mockito.when(specializationDAO.getAllSpecializations()).thenReturn(specializationList);

		String result = appointmentController.getNewAppointment(model);

		Mockito.verify(specializationDAO).getAllSpecializations();
		Mockito.verify(model).addAttribute("speclist", specializationList);
		Mockito.verify(model).addAttribute("patlist", patientDAO.getAllPatientInfo());

		assert result.equals("admin/appointment");
	}

	@Test
	public void testGetNewPatientAppointment() {
		PatientSession patientSession = new PatientSession();
		List<Specialization> specializationList = new ArrayList<>();
		List<AppOutFormFamily> familyMembers = new ArrayList<>();

		Mockito.when(specializationDAO.getAllSpecializations()).thenReturn(specializationList);
		int validId = 1;
		Mockito.when(patientSession.getId()).thenReturn(validId);
		Mockito.when(appointmentService.getFormFamily(patientSession.getId())).thenReturn(familyMembers);

		String result = appointmentController.getNewPatientAppointment(patientSession, model);

		Mockito.verify(specializationDAO).getAllSpecializations();

		Mockito.verify(appointmentService).getFormFamily(patientSession.getId());
		Mockito.verify(model).addAttribute("speclist", specializationList);
		Mockito.verify(model).addAttribute("fam", familyMembers);

		assert result.equals("patient/appointment");
	}

	// Add more test methods for other controller methods

	// ...
}
