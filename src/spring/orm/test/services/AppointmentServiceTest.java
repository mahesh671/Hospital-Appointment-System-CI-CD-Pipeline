package spring.orm.test.services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.AppointmentDAO;
import spring.orm.contract.DAO.DoctorsDAO;
import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.services.AppointmentServices;
import spring.orm.contract.services.PaymentService;
import spring.orm.customexceptions.SlotAlreadyBookedException;
import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AppOutFormFamily;
import spring.orm.model.output.MailAppOutputModel;
import spring.orm.model.output.RescheduleAppointmentOutput;
import spring.orm.util.MailSendHelper;

public class AppointmentServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;

    @Mock
    private DoctorsDAO doctorDAO;

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private PaymentService paymentServices;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AppointmentServices appointmentService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAppointments() {
        // Mock appointment list
        List<AppointmentEntity> mockAppointments = new ArrayList<>();
        AppointmentEntity appointment1 = new AppointmentEntity();
        appointment1.setAppn_id(1);
        appointment1.setAppn_sch_date(Timestamp.valueOf("2023-07-01 10:00:00"));
        mockAppointments.add(appointment1);

        // Set up appointmentDAO mock
        when(appointmentDAO.getAllAppointments()).thenReturn(mockAppointments);

        // Call the method to test
        List<AppointmentEntity> appointments = appointmentService.getAllAppointments();

        // Verify the results
        Assert.assertEquals(appointments.size(), 1);
        AppointmentEntity resultAppointment = appointments.get(0);
        Assert.assertEquals(resultAppointment.getAppn_id(), 1);
        Assert.assertEquals(resultAppointment.getDateFormetted(), "01-07-2023");
        Assert.assertEquals(resultAppointment.getTimeFormetted(), "10:00 am");
    }

    @Test
    public void testBookAppointment() throws Exception {
        // Mock appointment form
        AppointmentForm appointmentForm = new AppointmentForm();
        appointmentForm.setExistingPatientid("1");
        appointmentForm.setDoctor(1);
        appointmentForm.setAppointmentDate(LocalDate.now().toString());
        appointmentForm.setSlots("10:00 am");
        appointmentForm.setAppnrefer("Referral");
        appointmentForm.setAppnfee(100.0);

        // Mock patient
        PatientModel patient = new PatientModel();
        patient.setPatn_id(1);

        // Set up patientDAO mock
        when(patientDAO.getPatientById(1)).thenReturn(patient);

        // Set up doctorDAO mock
        when(doctorDAO.getDoctor(1)).thenReturn(mock(DoctorTemp.class)); // You can customize the doctor mock as needed

        // Set up appointmentDAO mock
        when(appointmentDAO.bookAppointment(eq(patient), mock(DoctorTemp.class), mock(String.class), eq("Referral"), eq(100.0)))
                .thenReturn(1);

        // Call the method to test
        int appointmentId = appointmentService.bookAppointment(appointmentForm);

        // Verify the results
        Assert.assertEquals(appointmentId, 1);
    }

    @Test(expectedExceptions = SlotAlreadyBookedException.class)
    public void testBookAppointmentWithNewPatient_slotAlreadyBooked() throws SlotAlreadyBookedException {
        // Mock appointment form
        AppointmentForm appointmentForm = new AppointmentForm();
        appointmentForm.setNewPatientName("John Smith");
        appointmentForm.setNewPatientAge("30");
        appointmentForm.setNewPatientBgroup("O+");
        appointmentForm.setNewPatientGender("Male");
        appointmentForm.setDoctor(1);
        appointmentForm.setAppointmentDate(LocalDate.now().toString());
        appointmentForm.setSlots("10:00 am");
        appointmentForm.setAppnrefer("Referral");
        appointmentForm.setAppnfee(100.0);

        // Mock patient
        PatientModel patient = new PatientModel();
        patient.setPatn_id(1);

        // Set up patientDAO mock
        when(patientDAO.addNewPatient(anyObject())).thenReturn(1);

        // Set up doctorDAO mock
        when(doctorDAO.getDoctor(1)).thenReturn(mock(DoctorTemp.class)); // You can customize the doctor mock as needed

        // Set up appointmentDAO mock to throw SlotAlreadyBookedException
        doThrow(new SlotAlreadyBookedException("SLott booked", "payrefe", mock(AppointmentEntity.class)))
                .when(appointmentDAO).bookAppointment(anyObject(), anyObject(), anyString(), anyString(), anyDouble());

        // Call the method to test
        appointmentService.bookAppointmentWithNewPatient(appointmentForm);
    }


    @Test
    public void testGetAppointmentByIdOutput() {
        // Mock appointment
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setAppn_id(1);
        appointment.setAppn_sch_date(Timestamp.valueOf("2023-07-01 10:00:00"));
        appointment.setDoctor(mock(DoctorTemp.class)); // You can customize the doctor mock as needed
        appointment.setPm(mock(PatientModel.class)); // You can customize the patient mock as needed

        // Set up appointmentDAO mock
        when(appointmentDAO.getAppointmentById(1)).thenReturn(appointment);

        // Call the method to test
        RescheduleAppointmentOutput result = appointmentService.getAppointmentByIdOutput(1);

        // Verify the results
        Assert.assertEquals(result.getApp_id(), 1);
        Assert.assertEquals(result.getSlot(), "10:00");
        Assert.assertEquals(result.getApp_sch_date(), Date.valueOf("2023-07-01"));
        Assert.assertNotNull(result.getDoctor());
        Assert.assertNotNull(result.getPatient());
    }

    @Test
    public void testCancelAppointment() {
        // Call the method to test
        appointmentService.cancelAppointment(1);

        // Verify that the cancelAppointment method of appointmentDAO was called
        verify(appointmentDAO).cancelAppointment(1);
    }

    @Test
    public void testGetAppointmentByID() {
        // Mock appointment
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setAppn_id(1);
        appointment.setDoctor(mock(DoctorTemp.class)); // You can customize the doctor mock as needed
        appointment.setAppn_booked_Date(mock(Timestamp.class)); // You can customize the booked date mock as needed
        appointment.setAppn_sch_date(mock(Timestamp.class)); // You can customize the schedule date mock as needed
        appointment.setAppn_payamount(100.0);
        appointment.setAppn_paymode("Cash");
        appointment.setAppn_payreference("Reference");
        appointment.setPm(mock(PatientModel.class)); // You can customize the patient mock as needed

        // Set up appointmentDAO mock
        when(appointmentDAO.getAppointmentById(1)).thenReturn(appointment);

        // Call the method to test
        MailAppOutputModel result = appointmentService.getAppointmentByID(1);

        // Verify the results
        Assert.assertEquals(result.getAppn_id(), 1);
        Assert.assertNotNull(result.getDoc_name());
        Assert.assertNotNull(result.getAppn_booked_Date());
        Assert.assertNotNull(result.getAppn_sch_date());
        Assert.assertEquals(result.getAppn_payamount(), 100.0);
        Assert.assertEquals(result.getAppn_paymode(), "Cash");
        Assert.assertNotNull(result.getDoc_Photo());
        Assert.assertEquals(result.getAppn_payreference(), "Reference");
        Assert.assertNotNull(result.getPat_name());
        Assert.assertNotNull(result.getMail());
    }

    @Test
    public void testReschduleAppointment() {
        // Mock reschedule appointment model
        RescheduleAppointmentModel rm = new RescheduleAppointmentModel();

        // Call the method to test
        appointmentService.reschduleAppointment(rm);

        // Verify that the reschduleAppointment method of appointmentDAO was called
        verify(appointmentDAO).reschduleAppointment(rm);
    }

    @Test
    public void testBookAppointmentWithPatientId() throws SlotAlreadyBookedException {
        // Mock appointment form
        AppointmentForm appointmentForm = new AppointmentForm();
        appointmentForm.setDoctor(1);
        appointmentForm.setAppointmentDate(LocalDate.now().toString());
        appointmentForm.setSlots("10:00 AM");
        appointmentForm.setAppnrefer("Referral");
        appointmentForm.setAppnfee(100.0);

        // Set up patientDAO mock
        when(patientDAO.getPatientById(1)).thenReturn(anyObject()); // You can customize the patient mock as needed

        // Set up doctorDAO mock
        when(doctorDAO.getDoctor(1)).thenReturn(mock(DoctorTemp.class)); // You can customize the doctor mock as needed

        // Set up appointmentDAO mock
        when(appointmentDAO.bookAppointment(mock(PatientModel.class), mock(DoctorTemp.class), anyString(), mock(String.class), mock(Double.class))).thenReturn(1);

        // Call the method to test
        int appointmentId = appointmentService.bookAppointment(appointmentForm, 1);

        // Verify the results
        Assert.assertEquals(appointmentId, 1);
    }

    @Test
    public void testGetPatientAppointmentByID() {
        // Mock appointment list
        List<AppointmentEntity> appointmentList = new ArrayList<>();
        AppointmentEntity appointment1 = new AppointmentEntity();
        appointment1.setAppn_id(1);
        appointmentList.add(appointment1);
        // Add more appointments to the list as needed

        // Set up appointmentDAO mock
        when(appointmentDAO.getAppointmentsByPatientId(1)).thenReturn(appointmentList);

        // Call the method to test
        List<AppointmentEntity> result = appointmentService.getPatientAppointmentByID(1);

        // Verify the results
        Assert.assertEquals(result.size(), 1);
        // Verify other aspects of the result as needed
    }

    @Test
    public void testGetFormFamily() {
        // Mock patient list
        List<PatientModel> patientList = new ArrayList<>();
        PatientModel patient1 = new PatientModel();
        patient1.setPatn_name("John Smith");
        patient1.setPatn_id(1);
        patientList.add(patient1);
        // Add more patients to the list as needed

        // Set up patientDAO mock
        when(patientDAO.getFamilyDetailsById(1)).thenReturn(patientList);

        // Call the method to test
        List<AppOutFormFamily> result = appointmentService.getFormFamily(1);

        // Verify the results
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getFam_patn_name(), "John Smith");
        Assert.assertEquals(result.get(0).getFam_patni_id(), 1);
        // Verify other aspects of the result as needed
    }

   
}
