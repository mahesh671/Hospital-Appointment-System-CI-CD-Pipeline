package spring.orm.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "das_users")
public class UserPass {

	@Id
	@Column(name = "uname")
	private String username;
	@Column(name = "pass")
	private String Password;
	@Column(name = "mail")
	private String mail;
	@Column(name = "otp")
	private String otp;
	@Column(name = "status")
	private String status;
	@Column(name = "otptime")
	private LocalDateTime otptime;
	@Column(name = "role")
	private String role;
	@OneToOne
	@JoinColumn(name = "patn_id")
	private PatientModel patient;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOtptime() {
		return otptime;
	}

	public void setOtptime(LocalDateTime otptime) {
		this.otptime = otptime;
	}

	public PatientModel getPatient() {
		return patient;
	}

	public void setPatient(PatientModel patient) {
		this.patient = patient;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserPass(String username, String password, String mail, String role) {

		this.username = username;
		Password = password;
		this.mail = mail;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	public LocalDateTime getotptime() {
		return otptime;
	}

	public void setotptime(LocalDateTime otpTimestamp) {
		this.otptime = otpTimestamp;
	}

	public String getRole() {
		return role;
	}

	public void setrole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserPass [username=" + username + ", Password=" + Password + ", mail=" + mail + ", otp=" + otp
				+ ", status=" + status + ", otptime=" + otptime + ", role=" + role + "]";
	}

	public UserPass() {
		super();
	}

}