package spring.orm.model.input;

public class RescheduleAppointmentModel {
	private Integer appointmentId;
	private String rescheduleDate;
	private String slot;

	// Constructors, getters, and setters
	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getRescheduleDate() {
		return rescheduleDate;
	}

	public void setRescheduleDate(String rescheduleDate) {
		this.rescheduleDate = rescheduleDate;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public RescheduleAppointmentModel(int appointmentId, String rescheduleDate, String slot) {
		super();
		this.appointmentId = appointmentId;
		this.rescheduleDate = rescheduleDate;
		this.slot = slot;
	}

	public RescheduleAppointmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RescheduleAppointmentModel [appointmentId=" + appointmentId + ", rescheduleDate=" + rescheduleDate
				+ ", slot=" + slot + "]";
	}

}