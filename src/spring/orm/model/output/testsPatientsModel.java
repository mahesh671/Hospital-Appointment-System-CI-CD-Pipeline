package spring.orm.model.output;

import java.time.LocalDate;

public class testsPatientsModel {
	int patn_id;
	String patn_name;

	String test_name;
	String test_method;
	String test_category;
	int test_price;
	private LocalDate dgbl_date;
	private String dgbl_dates;

	public String getDgbl_dates() {
		return dgbl_dates;
	}

	public void setDgbl_dates(String dgbl_dates) {
		this.dgbl_dates = dgbl_dates;
	}

	public LocalDate getDateField() {
		return dgbl_date;
	}

	public void setDateField(LocalDate dgbl_date) {
		this.dgbl_date = dgbl_date;
	}

	public testsPatientsModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getTest_category() {
		return test_category;
	}

	public void setTest_category(String test_category) {
		this.test_category = test_category;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public String getTest_method() {
		return test_method;
	}

	public void setTest_method(String test_method) {
		this.test_method = test_method;
	}

	public int getTest_price() {
		return test_price;
	}

	public void setTest_price(int test_price) {
		this.test_price = test_price;
	}

	public testsPatientsModel(int patn_id, String patn_name, String test_name, String test_method, String test_category,
			int test_price, LocalDate dgbl_date) {
		super();
		this.patn_id = patn_id;
		this.patn_name = patn_name;
		this.test_category = test_category;
		this.test_name = test_name;
		this.test_method = test_method;
		this.test_price = test_price;
		this.dgbl_date = dgbl_date;
		this.dgbl_dates=dgbl_date.toString();
	}

	public LocalDate getDgbl_date() {
		return dgbl_date;
	}

	public void setDgbl_date(LocalDate dgbl_date) {
		this.dgbl_date = dgbl_date;
		this.dgbl_dates=dgbl_date.toString();
	}

	@Override
	public String toString() {
		return "testsPatientsModel [patn_id=" + patn_id + ", patn_name=" + patn_name + ", test_category="
				+ test_category + ", test_name=" + test_name + ", test_method=" + test_method + ", test_price="
				+ test_price + ", dgbl_date=" + dgbl_date + "]";
	}
}
