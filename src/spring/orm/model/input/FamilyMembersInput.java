package spring.orm.model.input;

public class FamilyMembersInput {
	private String pfmbName;
	private String pfmbRelation;
	private Integer pfmbAge;
	private String pfmbGender;
	private String pfmbbgroup;

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

	public Integer getPfmbAge() {
		return pfmbAge;
	}

	public void setPfmbAge(Integer pfmbAge) {
		this.pfmbAge = pfmbAge;
	}

	public String getPfmbGender() {
		return pfmbGender;
	}

	public void setPfmbGender(String pfmbGender) {
		this.pfmbGender = pfmbGender;
	}

	public String getPfmbbgroup() {
		return pfmbbgroup;
	}

	@Override
	public String toString() {
		return "FamilyMembersInput [pfmbName=" + pfmbName + ", pfmbRelation=" + pfmbRelation + ", pfmbAge=" + pfmbAge
				+ ", pfmbGender=" + pfmbGender + ", pfmbbgroup=" + pfmbbgroup + "]";
	}

	public void setPfmbbgroup(String pfmbbgroup) {
		this.pfmbbgroup = pfmbbgroup;
	}

	public FamilyMembersInput() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyMembersInput(String pfmbName, String pfmbRelation, Integer pfmbAge, String pfmbGender,
			String pfmbbgroup) {
		super();
		this.pfmbName = pfmbName;
		this.pfmbRelation = pfmbRelation;
		this.pfmbAge = pfmbAge;
		this.pfmbGender = pfmbGender;
		this.pfmbbgroup = pfmbbgroup;
	}

}