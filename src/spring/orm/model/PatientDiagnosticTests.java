package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dtestbill")
public class PatientDiagnosticTests {

	@EmbeddedId
	private PDTPrimaryKey pk;

	@Column(name = "dgbl_test_price")
	private int profit;

	public PatientDiagnosticTests() {

	}

	public PatientDiagnosticTests(PDTPrimaryKey pk, int profit) {
		super();
		this.pk = pk;
		this.profit = profit;
	}

	public PDTPrimaryKey getPk() {
		return pk;
	}

	public void setPk(PDTPrimaryKey pk) {
		this.pk = pk;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}
}
