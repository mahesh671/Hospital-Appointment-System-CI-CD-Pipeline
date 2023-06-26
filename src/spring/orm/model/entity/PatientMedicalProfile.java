package spring.orm.model.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PatientMedicalProfile")
public class PatientMedicalProfile {
	@EmbeddedId
	private PatientConsultationUpdateId id;

	@Column(name = "patn_parameter")
	private String patn_parameter;
	@Column(name = "patn_pargroup")
	private String patn_pargroup;
	@Column(name = "patn_value")
	private String patn_value;
	@Column(name = "patn_prescription")
	private byte[] patn_prescription;

	public byte[] getPatn_prescription() {
		return patn_prescription;
	}

	public void setPatn_prescription(byte[] patn_prescription) {
		this.patn_prescription = patn_prescription;
	}

	public PatientMedicalProfile() {
		super();
	}

	public PatientConsultationUpdateId getId() {
		return id;
	}

	public void setId(PatientConsultationUpdateId id) {
		this.id = id;
	}

	public String getPatn_parameter() {
		return patn_parameter;
	}

	public void setPatn_parameter(String patn_parameter) {
		this.patn_parameter = patn_parameter;
	}

	public String getPatnParGroup() {
		return patn_pargroup;
	}

	public void setPatnParGroup(String patnParGroup) {
		this.patn_pargroup = patnParGroup;
	}

	public String getPatn_value() {
		return patn_value;
	}

	public void setPatn_value(String patn_value) {
		this.patn_value = patn_value;
	}

	public PatientMedicalProfile(PatientConsultationUpdateId id, String patn_parameter, String patnParGroup,
			String patn_value, byte[] patn_prescription) {
		super();
		this.id = id;
		this.patn_parameter = patn_parameter;
		this.patn_pargroup = patnParGroup;
		this.patn_value = patn_value;
		this.patn_prescription = patn_prescription;
	}

}
