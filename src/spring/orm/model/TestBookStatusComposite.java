package spring.orm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TestBookStatusComposite implements Serializable {
	@Column(name = "tb_patn_id")
	private int tb_patn_id;
	@Column(name = "test_id")
	private int test_id;

	public TestBookStatusComposite() {
	}

	public TestBookStatusComposite(int tbPatnId, int testId) {
		this.tb_patn_id = tbPatnId;
		this.test_id = testId;
	}

	public int getTbPatnId() {
		return tb_patn_id;
	}

	public void setTbPatnId(int tb_patn_id) {
		this.tb_patn_id = tb_patn_id;
	}

	public int getTestId() {
		return test_id;
	}

	public void setTestId(int testId) {
		this.test_id = testId;
	}

	// Implement equals() and hashCode() methods

}
