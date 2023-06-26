package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import spring.orm.model.entity.DoctorTemp;

@Entity
@Table(name = "doctorschedule")
public class DoctorSchedule {
	@Id
	@Column(name = "doct_id")
	private int doctorId;
	@Column(name = "weekday")
	private String weekday;
	@Column(name = "dcsc_timefrom")
	private String timeFrom;
	@Column(name = "dcsc_timeto")
	private String timeTo;
	@Column(name = "dcsc_avgat")
	private int averageAppointmentTime;

	@OneToOne
	@JoinColumn(name = "doct_id")
	private DoctorTemp doctor;

	public DoctorSchedule(int id, String weekday, String timeFrom, String timeTo, int averageAppointmentTime) {
		super();
		this.doctorId = id;
		this.weekday = weekday;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.averageAppointmentTime = averageAppointmentTime;

	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public int getAverageAppointmentTime() {
		return averageAppointmentTime;
	}

	public void setAverageAppointmentTime(int averageAppointmentTime) {
		this.averageAppointmentTime = averageAppointmentTime;
	}

	public DoctorSchedule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DoctorSchedule [doctorId=" + doctorId + ", weekday=" + weekday + ", timeFrom=" + timeFrom + ", timeTo="
				+ timeTo + ", averageAppointmentTime=" + averageAppointmentTime + "]";
	}

}
