package spring.orm.model.output;

import java.time.LocalDate;
import java.util.Date;

public class PatientlastvisitOutput {
	private LocalDate patn_lastvisit;
	private String doct_name;
	private double appn_payamount;
	private Date appn_sch_date;

	public PatientlastvisitOutput() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientlastvisitOutput(LocalDate patn_lastvisit, String doct_name, double appn_payamount,
			Date appn_sch_date) {
		super();
		this.patn_lastvisit = patn_lastvisit;
		this.doct_name = doct_name;
		this.appn_payamount = appn_payamount;
		this.appn_sch_date = appn_sch_date;
	}

	@Override
	public String toString() {
		return "PatientlastvisitOutput [patn_lastvisit=" + patn_lastvisit + ", doct_name=" + doct_name
				+ ", appn_payamount=" + appn_payamount + ", appn_sch_date=" + appn_sch_date + "]";
	}

	public LocalDate getPatn_lastvisit() {
		return patn_lastvisit;
	}

	public void setPatn_lastvisit(LocalDate patn_lastvisit) {
		this.patn_lastvisit = patn_lastvisit;
	}

	public String getDoct_name() {
		return doct_name;
	}

	public void setDoct_name(String doct_name) {
		this.doct_name = doct_name;
	}

	public double getAppn_payamount() {
		return appn_payamount;
	}

	public void setAppn_payamount(double appn_payamount) {
		this.appn_payamount = appn_payamount;
	}

	public Date getAppn_sch_date() {
		return appn_sch_date;
	}

	public void setAppn_sch_date(Date appn_sch_date) {
		this.appn_sch_date = appn_sch_date;
	}
}