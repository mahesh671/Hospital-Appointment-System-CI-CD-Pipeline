package spring.orm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
public class PDTPrimaryKey implements Serializable{

	@ManyToOne
	@JoinColumn(name = "dgbl_id")
	private PatientDiagnosis pd; 
	
	@ManyToOne
	@JoinColumn(name="dgbl_test_id")
	private testModel tm;
	
	public PDTPrimaryKey() {
		
	}

	public PDTPrimaryKey(PatientDiagnosis pd, testModel tm) {
		this.pd = pd;
		this.tm = tm;
	}

	public PatientDiagnosis getPd() {
		return pd;
	}

	public void setPd(PatientDiagnosis pd) {
		this.pd = pd;
	}

	public testModel getTm() {
		return tm;
	}

	public void setTm(testModel tm) {
		this.tm = tm;
	}
	
}
