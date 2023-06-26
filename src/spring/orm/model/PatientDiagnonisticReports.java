package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pdreports")
public class PatientDiagnonisticReports {

	@EmbeddedId
	private ReportsComposite compositeKey;

	@Column(name = "dgdr_path")
	private byte[] dgdr_path;

	// Constructors, getters, and setters

	// Constructor without ID field (if applicable)
	public PatientDiagnonisticReports(int dgbl_id, int dgdr_index, byte[] dgdr_path) {
		this.compositeKey = new ReportsComposite(dgbl_id, dgdr_index);
		this.dgdr_path = dgdr_path;
	}

	// Getters and setters for all fields

	public ReportsComposite getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(ReportsComposite compositeKey) {
		this.compositeKey = compositeKey;
	}

	public int getDgbl_id() {
		return compositeKey.getDgbl_id();
	}

	public void setDgblId(int dgbl_id) {
		compositeKey.setDgblId(dgbl_id);
	}

	public int getDgdr_index() {
		return compositeKey.getDgdr_index();
	}

	public void setDgdrIndex(int dgdr_index) {
		compositeKey.setDgdrIndex(dgdr_index);
	}

	public byte[] getDgdr_path() {
		return dgdr_path;
	}

	public void setDgdr_path(byte[] dgdr_path) {
		this.dgdr_path = dgdr_path;
	}

}