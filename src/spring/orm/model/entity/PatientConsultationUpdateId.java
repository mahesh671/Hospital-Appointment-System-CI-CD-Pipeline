package spring.orm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PatientConsultationUpdateId implements Serializable {
	@Column(name = "patn_id")
	private int patn_id;

	@Column(name = "patn_appn_id")
	private int patn_appn_id;

	public PatientConsultationUpdateId(int patn_id, int patn_appn_id) {
		super();
		this.patn_id = patn_id;
		this.patn_appn_id = patn_appn_id;
	}

	public int getPatn_id() {
		return patn_id;
	}

	public void setPatn_id(int patn_id) {
		this.patn_id = patn_id;
	}

	public int getAppn_id() {
		return patn_appn_id;
	}

	public void setAppn_id(int appn_id) {
		this.patn_appn_id = appn_id;
	}

	public PatientConsultationUpdateId() {
		super();
		// TODO Auto-generated constructor stub
	}

}
