package spring.orm.model.output;

import java.util.Date;

public class OutputBookedAppointmnets {

	private int appn_id;

	private String pat_name;

	private String doc_name;

	private Date appn_sch_date;

	private String spec_title;

	private String appn_status;

	public OutputBookedAppointmnets(int appn_id, String pat_name, String doc_name, Date appn_sch_date,
			String spec_title, String appn_status) {
		super();
		this.appn_id = appn_id;
		this.pat_name = pat_name;
		this.doc_name = doc_name;
		this.appn_sch_date = appn_sch_date;
		this.spec_title = spec_title;
		this.appn_status = appn_status;
	}

	public OutputBookedAppointmnets() {

	}

	public int getAppn_id() {
		return appn_id;
	}

	public void setAppn_id(int appn_id) {
		this.appn_id = appn_id;
	}

	public String getPat_name() {
		return pat_name;
	}

	public void setPat_name(String pat_name) {
		this.pat_name = pat_name;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getSpec_title() {
		return spec_title;
	}

	public Date getAppn_sch_date() {
		return appn_sch_date;
	}

	public void setAppn_sch_date(Date appn_sch_date) {
		this.appn_sch_date = appn_sch_date;
	}

	public void setSpec_title(String spec_title) {
		this.spec_title = spec_title;
	}

	public String getAppn_status() {
		return appn_status;
	}

	public void setAppn_status(String appn_status) {
		this.appn_status = appn_status;
	}

	@Override
	public String toString() {
		return "OutputBookedAppointmnets [appn_id=" + appn_id + ", pat_name=" + pat_name + ", doc_name=" + doc_name
				+ ", appn_sch_date=" + appn_sch_date + ", spec_title=" + spec_title + ", appn_status=" + appn_status
				+ "]";
	}

}