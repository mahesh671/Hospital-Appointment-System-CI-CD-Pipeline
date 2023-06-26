package spring.orm.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "familymembers")
public class FamilyMembers {

	@Column(name = "patn_access_patn_id")
	private Integer patnAccessPatnId;

	@Id
	@Column(name = "pfmb_patn_id")
	private int pfmbPatnId;

	@Column(name = "pfmb_name")
	private String pfmbName;

	@Column(name = "pfmb_relation")
	private String pfmbRelation;

	@Column(name = "pfmb_age")
	private int pfmbAge;

	public Integer getPatnAccessPatnId() {
		return patnAccessPatnId;
	}

	public void setPatnAccessPatnId(Integer patnAccessPatnId) {
		this.patnAccessPatnId = patnAccessPatnId;
	}

	public int getPfmbPatnId() {
		return pfmbPatnId;
	}

	public void setPfmbPatnId(int pfmbPatnId) {
		this.pfmbPatnId = pfmbPatnId;
	}

	public String getPfmbName() {
		return pfmbName;
	}

	public void setPfmbName(String pfmbName) {
		this.pfmbName = pfmbName;
	}

	public String getPfmbRelation() {
		return pfmbRelation;
	}

	public void setPfmbRelation(String pfmbRelation) {
		this.pfmbRelation = pfmbRelation;
	}

	public int getPfmbAge() {
		return pfmbAge;
	}

	public void setPfmbAge(int pfmbAge) {
		this.pfmbAge = pfmbAge;
	}

	@Override
	public String toString() {
		return "FamilyMembers [patnAccessPatnId=" + patnAccessPatnId + ", pfmbPatnId=" + pfmbPatnId + ", pfmbName="
				+ pfmbName + ", pfmbRelation=" + pfmbRelation + ", pfmbAge=" + pfmbAge + "]";
	}

	public FamilyMembers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyMembers(Integer patnAccessPatnId, int pfmbPatnId, String pfmbName, String pfmbRelation, int pfmbAge) {
		super();
		this.patnAccessPatnId = patnAccessPatnId;
		this.pfmbPatnId = pfmbPatnId;
		this.pfmbName = pfmbName;
		this.pfmbRelation = pfmbRelation;
		this.pfmbAge = pfmbAge;
	}

}