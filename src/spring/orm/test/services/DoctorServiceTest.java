package spring.orm.test.services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import spring.orm.contract.AppointmentDAO;
import spring.orm.contract.DocScheduleDAO;
import spring.orm.contract.DoctorsDAO;
import spring.orm.contract.SpecializationDAO;
import spring.orm.model.DoctorSchedule;
import spring.orm.model.Specialization;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;
import spring.orm.services.DoctorOutputService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class DoctorServiceTest {

    @Mock
    private DocScheduleDAO docschedao;

    @Mock
    private SpecializationDAO specdao;

    @Mock
    private DoctorsDAO doctdao;

    @Mock
    private AppointmentDAO appdao;

    @InjectMocks
    private DoctorOutputService doctorOutputService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllDocBySpecDate() {
        // Mock data
        String specialization = "Cardiology";
        Date appointmentDate = new Date();
        List<DoctorList> expectedList = new ArrayList<>();
        expectedList.add(new DoctorList());
        when(doctdao.getallDocScheduleBySpec(eq(specialization), anyString())).thenReturn(expectedList);

        // Invoke the method under test
        List<DoctorList> result = doctorOutputService.getAllDocBySpecDate(specialization, appointmentDate);

        // Verify the interactions and assertions
        verify(doctdao).getallDocScheduleBySpec(eq(specialization), anyString());
        Assert.assertEquals(result, expectedList);
    }

    @Test
    public void testGetDocbyID() {
        // Mock data
        int doctorId = 1;
        DoctorOutPutModel expectedOutput = new DoctorOutPutModel();
        when(doctdao.getDocById(doctorId)).thenReturn(expectedOutput);

        // Invoke the method under test
        DoctorOutPutModel result = doctorOutputService.getDocbyID(doctorId);

        // Verify the interactions and assertions
        verify(doctdao).getDocById(doctorId);
        Assert.assertEquals(result, expectedOutput);
    }

    @Test
    public void testGetDocTimeSlots() {
        // Mock data
        int doctorId = 1;
        String date = "2023-07-01";
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setTimeFrom("09:00");
        schedule.setTimeTo("17:00");
        schedule.setAverageAppointmentTime(30);
        when(docschedao.getSchedulebyId(doctorId)).thenReturn(schedule);
        List<String> expectedSlots = new ArrayList<>();
        expectedSlots.add("09:00 AM");
        expectedSlots.add("09:30 AM");
        expectedSlots.add("10:00 AM");
        // ...

        // Invoke the method under test
        List<String> result = doctorOutputService.getDocTimeSlots(doctorId, date);

        // Verify the interactions and assertions
        verify(docschedao).getSchedulebyId(doctorId);
        Assert.assertEquals(result, expectedSlots);
    }

    @Test
    public void testGenerateTimeSlots() {
        // Mock data
        int id = 1;
        String date = "2023-07-01";
        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setTimeFrom("09:00");
        doctorSchedule.setTimeTo("17:00");
        doctorSchedule.setAverageAppointmentTime(30);
        when(docschedao.getSchedulebyId(id)).thenReturn(doctorSchedule);

        // Invoke the method under test
        List<String> result = doctorOutputService.generateTimeSlots(
                doctorSchedule.getTimeFrom(),
                doctorSchedule.getTimeTo(),
                doctorSchedule.getAverageAppointmentTime(),
                date,
                id
        );

        // Verify the interactions and assertions
        verify(docschedao).getSchedulebyId(id);
        assertNotNull(result);
        assertEquals(result.size(), 17);
        assertEquals(result.get(0), "09:00 AM");
        assertEquals(result.get(16), "04:30 PM");
    }

    @Test
    public void testAddDoc() throws Exception {
        // Mock data
        DoctorInput doctorInput = new DoctorInput();
        CommonsMultipartFile pic = mock(CommonsMultipartFile.class);
        DoctorTemp doctorTemp = new DoctorTemp();
        doctorTemp.setDoctId(1);
        
        String specialization = "Cardiology"; // Set a valid specialization value
        doctorInput.setDocspec(specialization);
        
        when(specdao.getSpecialization(anyString())).thenReturn(new Specialization());
        doNothing().when(doctdao).saveDoc(any(DoctorTemp.class));

        // Invoke the method under test
        int result = doctorOutputService.addDoc(doctorInput, pic);

        // Verify the interactions and assertions
        verify(specdao).getSpecialization(anyString());
        verify(doctdao).saveDoc(any(DoctorTemp.class));
        assertEquals(result, 1);
    }




    @Test
    public void testUpdateDoctor() throws Exception {
        // Mock data
        DoctorUpdateModel doctorUpdateModel = new DoctorUpdateModel();
        CommonsMultipartFile docPhoto = mock(CommonsMultipartFile.class);
        doctorUpdateModel.setDoc_id(1);
        // Set other properties of doctorUpdateModel

        DoctorTemp doctorTemp = new DoctorTemp();
        doctorTemp.setDoctId(1);
        when(doctdao.getDoctor(anyInt())).thenReturn(doctorTemp);
        doNothing().when(doctdao).updatedoc(any(DoctorTemp.class));
        doNothing().when(docschedao).updateSchedule(any(DoctorUpdateModel.class));

        // Invoke the method under test
        int result = doctorOutputService.updateDoctor(doctorUpdateModel, docPhoto);

        // Verify the interactions and assertions
        verify(doctdao).getDoctor(anyInt());
        verify(doctdao).updatedoc(any(DoctorTemp.class));
        verify(docschedao).updateSchedule(any(DoctorUpdateModel.class));
        assertEquals(result, 1);
    }

}

