package spring.orm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DiagnosticBillModelId implements Serializable {
	@Column(name = "dgbl_id")
	private int dgblId;

	@Column(name = "dgbl_test_id")
	private int dgbltestId;

	public DiagnosticBillModelId() {
	}

	public DiagnosticBillModelId(int dgblId, int dgbltestId) {
		this.dgblId = dgblId;
		this.dgbltestId = dgbltestId;
	}

	public int getDgblId() {
		return dgblId;
	}

	public void setDgblId(int dgblId) {
		this.dgblId = dgblId;
	}

	public int getDgblPatnId() {
		return dgbltestId;
	}

	public void setDgblPatnId(int dgblPatnId) {
		this.dgbltestId = dgblPatnId;
	}

	// Overrides for hashCode() and equals() methods
}
