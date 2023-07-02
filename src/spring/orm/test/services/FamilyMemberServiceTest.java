package spring.orm.test.services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import spring.orm.contract.FamilyMembersDAO;
import spring.orm.contract.PatientDAO;
import spring.orm.model.PatientModel;
import spring.orm.model.PatientSession;
import spring.orm.model.input.FamilyMembersInput;
import spring.orm.services.PatientFamilyMembersService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FamilyMemberServiceTest {

    @Mock
    private FamilyMembersDAO familyMemberDao;

    @Mock
    private PatientDAO patientDao;

    @InjectMocks
    private PatientFamilyMembersService familyMembersService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddFamilyMember() {
        // Mock data
        FamilyMembersInput familyMember = new FamilyMembersInput();
        familyMember.setPfmbName("John Doe");
        familyMember.setPfmbAge(30);
        familyMember.setPfmbGender("Male");
        familyMember.setPfmbbgroup("AB+");
        familyMember.setPfmbRelation("Sibling");

        int patientId = 1;

        PatientModel patientModel = new PatientModel();
        patientModel.setPatn_name(familyMember.getPfmbName());
        patientModel.setPatn_age(familyMember.getPfmbAge());
        patientModel.setPatn_gender(familyMember.getPfmbGender());
        patientModel.setPatn_bgroup(familyMember.getPfmbbgroup());
        patientModel.setAccessPatientId(patientId);

        int pid = 1;

        // Stubbing the methods with argument matchers
        when(familyMemberDao.savePatientDetails(any(PatientModel.class))).thenReturn(pid);
        doNothing().when(familyMemberDao).addFamilyByPatientInfo(any(PatientModel.class), anyInt(), anyInt(), anyString());

        // Perform the method under test
        int result = familyMembersService.addFamilyMember(familyMember, patientId);

        // Verify the interactions and assertions
        verify(familyMemberDao, times(1)).savePatientDetails(any(PatientModel.class));
        verify(familyMemberDao, times(1)).addFamilyByPatientInfo(any(PatientModel.class), eq(pid),
                eq(patientId), eq(familyMember.getPfmbRelation()));

        Assert.assertEquals(patientModel.getPatn_id(), result);
    }
    @Test
    public void testGetAllFamilyMembers() {
        // Mock data
        Integer patientId = 1;

        // Stubbing the method
        List<FamilyMembersInput> expectedFamilyMembers = new ArrayList<>();
        when(familyMemberDao.getFamilyDetailsByPatientId(patientId)).thenReturn(expectedFamilyMembers);

        // Perform the method under test
        List<FamilyMembersInput> result = familyMembersService.getAllFamilyMembers(patientId);

        // Verify the interactions and assertions
        verify(familyMemberDao, times(1)).getFamilyDetailsByPatientId(patientId);
        assert result == expectedFamilyMembers;
    }

    @Test
    public void testGetFamilyMemberInfo() {
        // Mock data
        int patientId = 1;
        int familyMemberId = 2;

        // Stubbing the method
        FamilyMembersInput expectedFamilyMember = new FamilyMembersInput();
        when(familyMemberDao.getFamilyMemberByPatientId(patientId, familyMemberId)).thenReturn(expectedFamilyMember);

        // Perform the method under test
        FamilyMembersInput result = familyMembersService.getFamilyMemberInfo(patientId, familyMemberId);

        // Verify the interactions and assertions
        verify(familyMemberDao, times(1)).getFamilyMemberByPatientId(patientId, familyMemberId);
        assert result == expectedFamilyMember;
    }

    @Test
    public void testSaveFamilyMemberInfo() {
        // Mock data
        FamilyMembersInput familyMember = new FamilyMembersInput();

        // Perform the method under test
        familyMembersService.saveFamilyMemberInfo(familyMember);

        // Verify the interactions
        verify(familyMemberDao, times(1)).saveChanges(familyMember);
    }

    @Test
    public void testDeleteFamilyMember() {
        // Mock data
        int patientId = 1;

        // Perform the method under test
        familyMembersService.deleteFamilyMember(patientId);

        // Verify the interactions
        verify(familyMemberDao, times(1)).deleteFamilyMember(patientId);
    }
}
