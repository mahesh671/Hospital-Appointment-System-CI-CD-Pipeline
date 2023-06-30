package spring.orm.model;

// package spring.orm.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientModel {

	@Id
	@Column(name = "patn_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int patn_id;
	@Column(name = "patn_name")
	private String patn_name;
	@Column(name = "patn_age")
	private int patn_age;
	@Column(name = "patn_gender")
	private String patn_gender;
	@Column(name = "patn_bgroup")
	private String patn_bgroup;
	@Column(name = "patn_access_patn_id")
	private Integer accessPatientId;
	@ManyToOne
	@JoinColumn(name = "patn_access_patn_id", insertable = false, updatable = false)
	private PatientModel accessPatient;

	@Column(name = "patn_rdate")
	private LocalDate patn_rdate;
	@Column(name = "patn_lastvisit")
	private LocalDate patn_lastvisit;
	@Column(name = "patn_lastapp_id")
	private Integer patn_lastapp_id = null;
	@Column(name = "patn_contact")
	private String patn_contact;

	@OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, optional = true)
	private UserPass userPass;

	public UserPass getUserPass() {
		return userPass;
	}

	public void setUserPass(UserPass userPass) {
		this.userPass = userPass;
	}

	public Integer getAccessPatientId() {
		return accessPatientId;
	}

	public void setAccessPatientId(Integer accessPatientId) {
		this.accessPatientId = accessPatientId;
	}

	public void setPatn_lastapp_id(Integer patn_lastapp_id) {
		this.patn_lastapp_id = patn_lastapp_id;
	}

	public String getPatn_contact() {
		return patn_contact;
	}

	public void setPatn_contact(String patn_contact) {
		this.patn_contact = patn_contact;
	}

	public int getPatn_id() {
		return patn_id;
	}

	public void setPatn_id(int patn_id) {
		this.patn_id = patn_id;
	}

	public String getPatn_name() {
		return patn_name;
	}

	public void setPatn_name(String patn_name) {
		this.patn_name = patn_name;
	}

	public int getPatn_age() {
		return patn_age;
	}

	public void setPatn_age(int patn_age) {
		this.patn_age = patn_age;
	}

	public String getPatn_gender() {
		return patn_gender;
	}

	public void setPatn_gender(String patn_gender) {
		this.patn_gender = patn_gender;
	}

	public String getPatn_bgroup() {
		return patn_bgroup;
	}

	public void setPatn_bgroup(String patn_bgroup) {
		this.patn_bgroup = patn_bgroup;
	}

	public LocalDate getPatn_rdate() {
		return patn_rdate;
	}

	public void setPatn_rdate(LocalDate patn_rdate) {
		this.patn_rdate = patn_rdate;
	}

	public LocalDate getPatn_lastvisit() {
		return patn_lastvisit;
	}

	public void setPatn_lastvisit(LocalDate patn_lastvisit) {
		this.patn_lastvisit = patn_lastvisit;
	}

	public Integer getPatn_lastapp_id() {

		return patn_lastapp_id;
	}

	public void setPatn_lastapp_id(int patn_lastapp_id) {
		this.patn_lastapp_id = patn_lastapp_id;
	}

	public PatientModel getAccessPatient() {
		return accessPatient;
	}

	public void setAccessPatient(PatientModel accessPatient) {
		this.accessPatient = accessPatient;
	}

	@Override
	public String toString() {
		return "PatientModel [patn_id=" + patn_id + ", patn_name=" + patn_name + ", patn_age=" + patn_age
				+ ", patn_gender=" + patn_gender + ", patn_bgroup=" + patn_bgroup + ", accessPatientId="
				+ accessPatientId + ", accessPatient=" + accessPatient + ", patn_rdate=" + patn_rdate
				+ ", patn_lastvisit=" + patn_lastvisit + ", patn_lastapp_id=" + patn_lastapp_id + ", patn_contact="
				+ patn_contact + ", userPass=" + userPass + "]";
	}

}
