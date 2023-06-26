package spring.orm.model.output;

public class AdminDashOut {
	
	private Long doccount;
	private Long total_appointments;
	private Long total_specializations;
	private double total_payments;
	
	

	
	public double getTotal_payments() {
		return total_payments;
	}
	public void setTotal_payments(double total_payments) {
		this.total_payments = total_payments;
	}
	
	public Long getDoccount() {
		return doccount;
	}
	public void setDoccount(Long doccount) {
		this.doccount = doccount;
	}
	public Long getTotal_appointments_today() {
		return total_appointments;
	}
	public void setTotal_appointments_today(Long total_appointments_today) {
		this.total_appointments = total_appointments_today;
	}
	public Long getTotal_specializations() {
		return total_specializations;
	}
	public void setTotal_specializations(Long total_specializations) {
		this.total_specializations = total_specializations;
	}
	public AdminDashOut(Long doccount, Long total_appointments_today, Long total_specializations,
			double total_payments) {
		super();
		this.doccount = doccount;
		this.total_appointments = total_appointments_today;
		this.total_specializations = total_specializations;
		this.total_payments = total_payments;
	}
	public Long getTotal_appointments() {
		return total_appointments;
	}
	public void setTotal_appointments(Long total_appointments) {
		this.total_appointments = total_appointments;
	}
	public AdminDashOut() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
