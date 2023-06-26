package spring.orm.model.input;

import java.sql.Date;

public class AppointmentForm {
	private String specialization;
	private Date appointmentDate;
	private int doctor;
	private String slots;
	private String bookingType;
	private double appnfee;
	private String appnrefer;
	private String appnmode;
	private int existingPatientid;

	private String newPatientName;

	private String newPatientGender;

	private int newPatientAge;
	private String newPatientBgroup;

	public AppointmentForm(String specialization, Date appointmentDate, int doctor, String slots, String bookingType,
			double appnfee, String appnrefer, String appnmode, int existingPatientid, String newPatientName,
			String newPatientGender, int newPatientAge, String newPatientBgroup) {
		super();
		this.specialization = specialization;
		this.appointmentDate = appointmentDate;
		this.doctor = doctor;
		this.slots = slots;
		this.bookingType = bookingType;
		this.appnfee = appnfee;
		this.appnrefer = appnrefer;
		this.appnmode = appnmode;
		this.existingPatientid = existingPatientid;
		this.newPatientName = newPatientName;
		this.newPatientGender = newPatientGender;
		this.newPatientAge = newPatientAge;
		this.newPatientBgroup = newPatientBgroup;
	}

	public String getAppnrefer() {
		return appnrefer;
	}

	public void setAppnrefer(String appnrefer) {
		this.appnrefer = appnrefer;
	}

	public String getAppnmode() {
		return appnmode;
	}

	public void setAppnmode(String appnmode) {
		this.appnmode = appnmode;
	}

	// Getters and Setters

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		System.out.println("In spec");
		this.specialization = specialization;
		System.out.println(this.specialization);

	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		System.out.println("In Apdate");
		this.appointmentDate = Date.valueOf(appointmentDate);
	}

	public int getDoctor() {
		return doctor;
	}

	public void setDoctor(int doctor) {
		System.out.println("In doctor");
		this.doctor = doctor;
		System.out.println(this.doctor);
	}

	public String getSlots() {
		return slots;
	}

	public void setSlots(String slots) {
		System.out.println("In slot");
		this.slots = slots;
		System.out.println(this.slots);
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		System.out.println("In bookingtype");
		this.bookingType = bookingType;
		System.out.println(this.bookingType);
	}

	public int getExistingPatientid() {
		return existingPatientid;
	}

	public void setExistingPatientid(String existingPatientid) {
		if (!existingPatientid.isEmpty()) {
			System.out.println("In patientid");
			this.existingPatientid = Integer.parseInt(existingPatientid);
			System.out.println(this.existingPatientid);
		}
		System.out.println(this.existingPatientid);
	}

	public String getNewPatientName() {
		return newPatientName;
	}

	public void setNewPatientName(String newPatientName) {
		System.out.println("patientname");
		this.newPatientName = newPatientName;
		System.out.println(this.newPatientName);
	}

	public String getNewPatientGender() {
		return newPatientGender;
	}

	public void setNewPatientGender(String newPatientGender) {
		System.out.println("In patgen");
		this.newPatientGender = newPatientGender;
		System.out.println(this.newPatientGender);
	}

	public int getNewPatientAge() {
		return newPatientAge;
	}

	public void setNewPatientAge(String newPatientAge) {
		if (!newPatientAge.isEmpty()) {
			System.out.println("In page");
			this.newPatientAge = Integer.parseInt(newPatientAge);
			System.out.println(this.newPatientAge);
		}

	}

	public void setAppointmentDate(Date appointmentDate) {
		System.out.println("In ap date");
		this.appointmentDate = appointmentDate;
		System.out.println(this.appointmentDate);
	}

	public String getNewPatientBgroup() {
		return newPatientBgroup;
	}

	public void setNewPatientBgroup(String newPatientBgroup) {
		this.newPatientBgroup = newPatientBgroup;
	}

	@Override
	public String toString() {
		return "AppointmentForm [specialization=" + specialization + ", appointmentDate=" + appointmentDate
				+ ", doctor=" + doctor + ", slots=" + slots + ", bookingType=" + bookingType + ", appnfee=" + appnfee
				+ ", appnrefer=" + appnrefer + ", appnmode=" + appnmode + ", existingPatientid=" + existingPatientid
				+ ", newPatientName=" + newPatientName + ", newPatientGender=" + newPatientGender + ", newPatientAge="
				+ newPatientAge + ", newPatientBgroup=" + newPatientBgroup + "]";
	}

	// public AppointmentForm(String specialization, Date appointmentDate, int doctor, String slots, String bookingType,
	// int existingPatientid, String newPatientName, String newPatientGender, int newPatientAge) {
	// super();
	// this.specialization = specialization;
	// this.appointmentDate = appointmentDate;
	// this.doctor = doctor;
	// this.slots = slots;
	// this.bookingType = bookingType;
	// this.existingPatientid = existingPatientid;
	// this.newPatientName = newPatientName;
	// this.newPatientGender = newPatientGender;
	// // this.newPatientAge = newPatientAge;
	// }

	public AppointmentForm() {
		// TODO Auto-generated constructor stub
		System.out.println("In Af constructor");
	}

	public double getAppnfee() {
		return appnfee;
	}

	public void setAppnfee(double appnfee) {
		System.out.println("In fee");
		this.appnfee = appnfee;
		System.out.println(this.appnfee);
	}

}
