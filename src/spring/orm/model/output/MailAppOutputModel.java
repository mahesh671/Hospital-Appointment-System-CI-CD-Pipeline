package spring.orm.model.output;

import java.sql.Timestamp;
import java.util.Base64;

public class MailAppOutputModel {

	private int appn_id;

	private String doc_name;
	private String Specialization;

	private String doc_Photo;
	private String pat_name;

	private Timestamp appn_booked_Date;
	private Timestamp appn_sch_date;

	private String mail;
	private String appn_paymode;

	private String appn_payreference;

	private double appn_payamount;

	public int getAppn_id() {
		return appn_id;
	}

	public void setAppn_id(int appn_id) {
		this.appn_id = appn_id;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getSpecialization() {
		return Specialization;
	}

	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}

	public String getDoc_Photo() {
		return doc_Photo;
	}

	public void setDoc_Photo(byte[] doc_Photo) {
		this.doc_Photo = Base64.getEncoder().encodeToString(doc_Photo);
		;
	}

	public String getPat_name() {
		return pat_name;
	}

	public void setPat_name(String pat_name) {
		this.pat_name = pat_name;
	}

	public Timestamp getAppn_booked_Date() {
		return appn_booked_Date;
	}

	public void setAppn_booked_Date(Timestamp appn_booked_Date) {
		this.appn_booked_Date = appn_booked_Date;
	}

	public Timestamp getAppn_sch_date() {
		return appn_sch_date;
	}

	public void setAppn_sch_date(Timestamp appn_sch_date) {
		this.appn_sch_date = appn_sch_date;
	}

	public String getAppn_paymode() {
		return appn_paymode;
	}

	public void setAppn_paymode(String appn_paymode) {
		this.appn_paymode = appn_paymode;
	}

	public String getAppn_payreference() {
		return appn_payreference;
	}

	public void setAppn_payreference(String appn_payreference) {
		this.appn_payreference = appn_payreference;
	}

	public double getAppn_payamount() {
		return appn_payamount;
	}

	public void setAppn_payamount(double appn_payamount) {
		this.appn_payamount = appn_payamount;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "MailAppOutputModel [appn_id=" + appn_id + ", doc_name=" + doc_name + ", Specialization="
				+ Specialization + ", doc_Photo=" + doc_Photo + ", pat_name=" + pat_name + ", appn_booked_Date="
				+ appn_booked_Date + ", appn_sch_date=" + appn_sch_date + ", appn_paymode=" + appn_paymode
				+ ", appn_payreference=" + appn_payreference + ",mail= " + mail + ", appn_payamount=" + appn_payamount
				+ "]";
	}

}
