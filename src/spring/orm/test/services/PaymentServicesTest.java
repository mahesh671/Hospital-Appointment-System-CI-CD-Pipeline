package spring.orm.test.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.razorpay.RazorpayException;

import spring.orm.contract.services.PaymentService;

public class PaymentServicesTest {

	@Mock
	private HttpSession httpSession;

	@InjectMocks
	private PaymentService paymentServices;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMakeTestPayment() throws RazorpayException, JSONException {
		// Arrange
		String billId = "123";
		String amount = "100";
		String currency = "INR";
		// Act
		String paymentOrder = paymentServices.makeTestPayment(billId, amount, currency);

		// Assert
		verify(httpSession, times(1)).setAttribute(eq("ticketNumber"), anyString());

		assert (paymentOrder != null);

	}

	@Test
	public void testMakeAppointmentPayment() throws RazorpayException, JSONException {
		// Arrange
		String billId = "123";
		String amount = "100";
		String currency = "INR";
		// Act
		String paymentOrder = paymentServices.makeTestPayment(billId, amount, currency);

		// Assert
		verify(httpSession, times(1)).setAttribute(eq("ticketNumber"), anyString());

		assert (paymentOrder != null);

	}

}