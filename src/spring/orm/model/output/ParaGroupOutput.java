package spring.orm.model.output;

import java.util.Date;

public class ParaGroupOutput {
	private String patn_parameter;

	private String patn_pargroup;

	private String patn_value;

	private Date appn_sch_date;

	@Override
	public String toString() {
		return "ParaGroupOutput [patn_parameter=" + patn_parameter + ", patn_pargroup=" + patn_pargroup
				+ ", patn_value=" + patn_value + ", appn_sch_date=" + appn_sch_date + "]";
	}

	public ParaGroupOutput(String patn_parameter, String patn_pargroup, String patn_value, Date appn_sch_date) {
		super();
		this.patn_parameter = patn_parameter;
		this.patn_pargroup = patn_pargroup;
		this.patn_value = patn_value;
		this.appn_sch_date = appn_sch_date;
	}

	public ParaGroupOutput() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPatn_parameter() {
		return patn_parameter;
	}

	public void setPatn_parameter(String patn_parameter) {
		this.patn_parameter = patn_parameter;
	}

	public String getPatn_pargroup() {
		return patn_pargroup;
	}

	public void setPatn_pargroup(String patn_pargroup) {
		this.patn_pargroup = patn_pargroup;
	}

	public String getPatn_value() {
		return patn_value;
	}

	public void setPatn_value(String patn_value) {
		this.patn_value = patn_value;
	}

	public Date getAppn_sch_date() {
		return appn_sch_date;
	}

	public void setAppn_sch_date(Date appn_sch_date) {
		this.appn_sch_date = appn_sch_date;
	}

}