package spring.orm.contract;

import java.util.List;

import spring.orm.model.PatientModel;
import spring.orm.model.entity.AppointmentEntity;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.AppointmentForm;
import spring.orm.model.input.BookedAppForm;
import spring.orm.model.input.RescheduleAppointmentModel;
import spring.orm.model.output.AdminAppOutModel;
import spring.orm.model.output.AdminDashOut;
import spring.orm.model.output.AdminProfitAppOut;
import spring.orm.model.output.OutputBookedAppointmnets;

public interface AppointmentDao {
	public List<AppointmentEntity> getAllAppointments();

	List<Object[]> fetchUpcomingAppointmentData();

	public boolean isSlotBooked(int doc_id, String date, String time);

	public int bookAppointment(PatientModel existingPatientid, DoctorTemp doctor, String string, String payref,
			double appfee);

	public AppointmentEntity getAppointmentById(int app_id);

	public void cancelAppointment(int app_id);

	public void reschduleAppointment(RescheduleAppointmentModel rm);

	public List<OutputBookedAppointmnets> fetchBookedAppData(BookedAppForm baf, Integer p_id);

	public int bookAppointment(AppointmentForm appointment, int patientId);

	public List<AppointmentEntity> getAppointmentsByPatientId(int patn_id);
	
	public List<AdminAppOutModel> getTopapp();

	public List<AdminProfitAppOut> getTopprof();
	
	public AdminDashOut getDashreport();
	
	public void updateAppStatusComp(int app_id);

}
