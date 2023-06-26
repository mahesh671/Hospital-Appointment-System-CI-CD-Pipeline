package spring.orm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
public class PDTPrimaryKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "dgbl_id")
	private PatientDiagnosis pd; 
	
	@ManyToOne
	@JoinColumn(name="dgbl_test_id")
	private TestModel tm;
	
	public PDTPrimaryKey() {
		
	}

	public PDTPrimaryKey(PatientDiagnosis pd, TestModel tm) {
		this.pd = pd;
		this.tm = tm;
	}

	public PatientDiagnosis getPd() {
		return pd;
	}

	public void setPd(PatientDiagnosis pd) {
		this.pd = pd;
	}

	public TestModel getTm() {
		return tm;
	}

	public void setTm(TestModel tm) {
		this.tm = tm;
	}
	
}
