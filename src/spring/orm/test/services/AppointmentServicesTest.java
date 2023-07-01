package spring.orm.test.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.AppointmentDAO;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.services.AppointmentService;

public class AppointmentServicesTest {
	@Mock
	private AppointmentDAO appointmentDAO;

	@InjectMocks
	private AppointmentService appointmentServices;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllAppointments() {
		// Create a list of appointments
		List<AppointmentEntity> appointments = new ArrayList<>();

		// Create a sample appointment
		AppointmentEntity appointment = new AppointmentEntity();
		appointment.setAppn_id(5);
		String dateTimeString = "2023-06-09 09:30:00";
		Timestamp timestamp = Timestamp.valueOf(dateTimeString);
		appointment.setAppn_sch_date(timestamp);

		// Add the sample appointment to the list
		appointments.add(appointment);

		// Mock the behavior of the appointmentDAO.getAllAppointments() method
		when(appointmentDAO.getAllAppointments()).thenReturn(appointments);

		// Call the method under test
		List<AppointmentEntity> result = appointmentServices.getAllAppointments();

		// Verify the result
		assertEquals(appointments, result);

		// Verify that the appointmentDAO.getAllAppointments() method was called
		verify(appointmentDAO, times(1)).getAllAppointments();
	}
}
