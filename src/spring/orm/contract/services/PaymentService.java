package spring.orm.contract.services;

import org.json.JSONException;
import org.springframework.ui.Model;

import com.razorpay.RazorpayException;

import spring.orm.model.input.AppointmentForm;

public interface PaymentService {

	/**
	 * Makes a payment for a test.
	 *
	 * @param billid   The ID of the bill.
	 * @param amount   The amount to be paid.
	 * @param currency The currency of the payment.
	 * @return The payment order details as a string.
	 * @throws RazorpayException If an error occurs during the payment process.
	 * @throws JSONException     If an error occurs while creating the JSON object.
	 */
	String makeTestPayment(String billid, String amount, String currency) throws RazorpayException, JSONException;

	/**
	 * Makes a payment for an appointment.
	 *
	 * @param amount   The amount to be paid.
	 * @param currency The currency of the payment.
	 * @param model    The model object.
	 * @return The payment order details as a string.
	 * @throws RazorpayException If an error occurs during the payment process.
	 * @throws JSONException     If an error occurs while creating the JSON object.
	 */
	String makeAppointmentPayment(String amount, String currency, Model model) throws RazorpayException, JSONException;

	/**
	 * Initiates a refund for an appointment.
	 *
	 * @param appointment The appointment details.
	 * @return The refund details as a string.
	 * @throws RazorpayException If an error occurs during the refund process.
	 * @throws JSONException     If an error occurs while creating the JSON object.
	 */
	String makeRefund(AppointmentForm appointment) throws RazorpayException, JSONException;

}