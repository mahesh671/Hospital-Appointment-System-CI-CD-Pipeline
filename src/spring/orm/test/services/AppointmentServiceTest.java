package spring.orm.test.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.output.MailAppOutputModel;
import spring.orm.services.AppointmentService;
import spring.orm.services.PaymentServices;

class AppointmentServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;

    @Mock
    private DoctorsDAO doctorDAO;

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private PaymentServices paymentServices;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeMethod

    void setUp() {
        MockitoAnnotations.initMocks(this);
        appointmentService = new AppointmentService(appointmentDAO, doctorDAO, patientDAO, paymentServices);
    }

    @Test
    public void testBookAppointment() throws Exception {
        // Mock data
        AppointmentForm appointmentForm = new AppointmentForm();
        appointmentForm.setSpecialization("Cardiology");
        appointmentForm.setAppointmentDate(Date.valueOf(LocalDate.now()));
        appointmentForm.setDoctor(1);
        appointmentForm.setSlots("09:00 AM");
        appointmentForm.setBookingType("New");
        appointmentForm.setAppnfee(100.0);
        appointmentForm.setAppnrefer("Referral Details");
        appointmentForm.setAppnmode("Mode");
        appointmentForm.setExistingPatientid("0");
        appointmentForm.setNewPatientName("John Doe");
        appointmentForm.setNewPatientGender("Male");
        appointmentForm.setNewPatientAge("30");
        appointmentForm.setNewPatientBgroup("AB+");

        when(appointmentDAO.bookAppointment(any(), any(), any(), any(), anyDouble())).thenReturn(1);

        // Invoke the method under test
        int result = appointmentService.bookAppointment(appointmentForm);

        // Verify the interactions and assertions
        verify(appointmentDAO).bookAppointment(any(), any(), any(), any(), anyDouble());
        // Add more assertions based on your expected results
    }

    @Test
    public void testGetAppointmentByIdOutput() {
        // Mock data
        int appointmentId = 1;
        AppointmentEntity mockAppointment = new AppointmentEntity();
        // Set mock appointment data

        when(appointmentDAO.getAppointmentById(anyInt())).thenReturn(mockAppointment);

        // Invoke the method under test
        appointmentService.getAppointmentByIdOutput(appointmentId);

        // Verify the interactions and assertions
        verify(appointmentDAO).getAppointmentById(anyInt());
        // Add more assertions based on your expected results
    }

    @Test
    public void testCancelAppointment() {
        // Mock data
        int appointmentId = 1;

        // Invoke the method under test
        appointmentService.cancelAppointment(appointmentId);

        // Verify the interactions and assertions
        verify(appointmentDAO).cancelAppointment(appointmentId);
        // Add more assertions based on your expected results
    }

    @Test
    public void testGetAppointmentByID() {
        // Mock data
        int appointmentId = 1;
        AppointmentEntity mockAppointment = new AppointmentEntity();
        // Set mock appointment data

        when(appointmentDAO.getAppointmentById(anyInt())).thenReturn(mockAppointment);

        // Invoke the method under test
        MailAppOutputModel result = appointmentService.getAppointmentByID(appointmentId);

        // Verify the interactions and assertions
        verify(appointmentDAO).getAppointmentById(anyInt());
        // Add more assertions based on your expected results
    }

    @Test
    public void testGetDoctorAvailableSlots() {
        // Mock data
        int doctorId = 1;
        Date appointmentDate = Date.valueOf(LocalDate.now());
        List<String> mockSlots = new ArrayList<>();
        // Set mock available slots

        when(appointmentDAO.getDoctorAvailableSlots(anyInt(), any())).thenReturn(mockSlots);

        // Invoke the method under test
        List<String> result = appointmentService.getDoctorAvailableSlots(doctorId, appointmentDate);

        // Verify the interactions and assertions
        verify(appointmentDAO).getDoctorAvailableSlots(anyInt(), any());
        // Add more assertions based on your expected results
    }

    @Test
    public void testSendBookingMail() throws Exception {
        // Mock data
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        int appointmentId = 1;
        String userMail = "test@example.com";
        MailAppOutputModel mockOutputModel = new MailAppOutputModel();
        mockOutputModel.setMail(userMail);

        when(appointmentDAO.getAppointmentById(anyInt())).thenReturn(new AppointmentEntity()); // Mock the appointment DAO method call
        when(appointmentService.getAppointmentByID(anyInt())).thenReturn(mockOutputModel);

        // Invoke the method under test
        appointmentService.sendBookingMail(request, response, appointmentId);

        // Verify the interactions and assertions
        verify(appointmentDAO).getAppointmentById(anyInt());
        verify(appointmentService).getAppointmentByID(anyInt());
        // Add more assertions based on your expected results
    }


}
