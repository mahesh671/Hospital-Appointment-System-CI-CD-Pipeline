package spring.orm.model.output;

import java.util.List;

public class BookedTestIDs {
	private List<Integer> Testids;
	private String contact;
	private int amt;
	private String type;
	private String status;
	private int billid;

	public int getBillid() {
		return billid;
	}

	public void setBillid(int billid) {
		this.billid = billid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Integer> getTestids() {
		return Testids;
	}

	public void setTestids(List<Integer> testids) {
		Testids = testids;
	}

	public void setnullTestids() {
		Testids = null;
	}

	public BookedTestIDs(List<Integer> testids, String contact) {
		super();
		Testids = testids;
		this.contact = contact;
	}

}