package spring.orm.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class ReportsComposite implements Serializable {

	@Column(name = "dgbl_id")
	private int dgbl_id;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dgdr_index")
	private int dgdr_index;

	// Default constructor, getters, and setters

	public ReportsComposite() {
		// Default constructor
	}

	public ReportsComposite(int dgblId, int dgdrIndex) {
		this.dgbl_id = dgblId;
		this.dgdr_index = dgdrIndex;
	}

	// Getters and setters for all fields

	public int getDgbl_id() {
		return dgbl_id;
	}

	public void setDgblId(int dgbl_id) {
		this.dgbl_id = dgbl_id;
	}

	public int getDgdr_index() {
		return dgdr_index;
	}

	public void setDgdrIndex(int dgdr_index) {
		this.dgdr_index = dgdr_index;
	}

	// Override equals() and hashCode() methods

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ReportsComposite that = (ReportsComposite) o;
		return dgbl_id == that.dgbl_id && dgdr_index == that.dgdr_index;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dgbl_id, dgdr_index);
	}
}