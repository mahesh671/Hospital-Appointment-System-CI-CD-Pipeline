package spring.orm.test.services;import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.DAO.DCDAO;
import spring.orm.services.ReportServices;

public class ReportServiceTest {

    @Mock
    private DCDAO dcDao;

    @InjectMocks
    private ReportServices reportServices;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reportServices = new ReportServices(dcDao);
    }
    @Test
    public void testFileUpload_Success() throws Exception {
        // Mock the file and its content
        CommonsMultipartFile file = mock(CommonsMultipartFile.class);
        byte[] content = "file content".getBytes();
        when(file.getBytes()).thenReturn(content);

        // Perform the file upload
        String result = reportServices.fileUpload(file, 1);

        // Verify the interactions and assertions
        verify(file).getBytes();
        verify(dcDao).saveReportInfo(eq(1), eq(content));
        Assert.assertEquals(result, "success");
    }


    @Test
    public void testIsImageFile() {
        // Mock the file with a valid image extension
        CommonsMultipartFile file = mock(CommonsMultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image.jpg");

        // Perform the isImageFile check
        boolean isImage = reportServices.isImageFile(file);

        // Verify the interactions and assertions
        verify(file).getOriginalFilename();
        Assert.assertTrue(isImage);
    }

    @Test
    public void testIsNotImageFile() {
        // Mock the file with an invalid image extension
        CommonsMultipartFile file = mock(CommonsMultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("document.pdf");

        // Perform the isImageFile check
        boolean isImage = reportServices.isImageFile(file);

        // Verify the interactions and assertions
        verify(file).getOriginalFilename();
        Assert.assertFalse(isImage);
    }
}
