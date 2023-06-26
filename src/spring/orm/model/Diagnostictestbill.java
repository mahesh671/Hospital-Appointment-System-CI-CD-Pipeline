package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@Table(name = "dtestbill")
public class Diagnostictestbill {
	@EmbeddedId
	private DiagnosticBillModelId id;

	@Column(name = "dgbl_test_price")
	private int dgbl_test_price;

	public Diagnostictestbill() {
		super();
	}

	public Diagnostictestbill(DiagnosticBillModelId id, int dgbl_test_price) {
		this.id = id;

		this.dgbl_test_price = dgbl_test_price;
	}

	public DiagnosticBillModelId getId() {
		return id;
	}

	public void setId(DiagnosticBillModelId id) {
		this.id = id;
	}

	public int getDgbl_test_price() {
		return dgbl_test_price;
	}

	public void setDgbl_test_price(int dgbl_test_price) {
		this.dgbl_test_price = dgbl_test_price;
	}

}