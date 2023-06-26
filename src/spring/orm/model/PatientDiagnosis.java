package spring.orm.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spring.orm.model.entity.AppointmentEntity;

@Entity
@Table(name = "dbiils")
public class PatientDiagnosis {

	// dgbl_id,dgbl_patn_id,dgbl_date,dgbl_amount,dgbl_appn_id,dgbl_patn_type
	@Id
	private int dgbl_id;

	@ManyToOne
	@JoinColumn(name = "dgbl_patn_id")
	private PatientModel pm;

	@Column(name = "dgbl_date")
	private LocalDate dgbl_date;

	@Column(name = "dgbl_amount")
	private int dgbl_amount;

	@ManyToOne
	@JoinColumn(name = "dgbl_appn_id")
	private AppointmentEntity am;

	@Column(name = "dgbl_patn_type")
	private String dgbl_patn_type;

	public int getDgbl_id() {
		return dgbl_id;
	}

	public void setDgbl_id(int dgbl_id) {
		this.dgbl_id = dgbl_id;
	}

	public PatientModel getPm() {
		return pm;
	}

	public void setPm(PatientModel pm) {
		this.pm = pm;
	}

	public LocalDate getDgbl_date() {
		return dgbl_date;
	}

	public void setDgbl_date(LocalDate dgbl_date) {
		this.dgbl_date = dgbl_date;
	}

	public int getDgbl_amount() {
		return dgbl_amount;
	}

	public void setDgbl_amount(int dgbl_amount) {
		this.dgbl_amount = dgbl_amount;
	}

	public AppointmentEntity getAm() {
		return am;
	}

	public void setAm(AppointmentEntity am) {
		this.am = am;
	}

	public String getDgbl_patn_type() {
		return dgbl_patn_type;
	}

	public void setDgbl_patn_type(String dgbl_patn_type) {
		this.dgbl_patn_type = dgbl_patn_type;
	}

}
