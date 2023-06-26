package spring.orm.model.output;

public class AdminProfitAppOut {
	
	private String payref;
	private String paymode;
	private Double payamount;
	private String docname;
	public String getPayref() {
		return payref;
	}
	public void setPayref(String payref) {
		this.payref = payref;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public Double getPayamount() {
		return payamount;
	}
	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public AdminProfitAppOut() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminProfitAppOut(String payref, String paymode, Double payamount, String docname) {
		super();
		this.payref = payref;
		this.paymode = paymode;
		this.payamount = payamount;
		this.docname = docname;
	}
	
	

}
