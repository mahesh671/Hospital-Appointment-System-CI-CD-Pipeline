package spring.orm.contract.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import spring.orm.customexceptions.SlotAlreadyBookedException;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AppOutFormFamily;
import spring.orm.model.output.MailAppOutputModel;
import spring.orm.model.output.RescheduleAppointmentOutput;

public interface AppointmentServices {

	List<AppointmentEntity> getAllAppointments();

	int bookAppointment(AppointmentForm app) throws Exception;

	RescheduleAppointmentOutput getAppointmentByIdOutput(int app_id);

	int bookAppointmentWithNewPatient(AppointmentForm appointmentForm) throws SlotAlreadyBookedException;

	void cancelAppointment(int app_id);

	MailAppOutputModel getAppointmentByID(int app_id);

	void reschduleAppointment(RescheduleAppointmentModel rm);

	int bookAppointment(AppointmentForm appointment, int patientId) throws SlotAlreadyBookedException;

	List<AppointmentEntity> getPatientAppointmentByID(int patn_id);

	List<AppOutFormFamily> getFormFamily(int id);

	void sendBookingMail(HttpServletRequest request, HttpServletResponse response, int app_id);

}