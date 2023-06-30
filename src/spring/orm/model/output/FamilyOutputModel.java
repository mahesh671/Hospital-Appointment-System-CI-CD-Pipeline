package spring.orm.model.output;

public class FamilyOutputModel {
	private int id;
	private String name;
	private int age;
	private String gender;
	private String bloodGroup;
	private String relation;

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "FamilyOutputModel [name=" + name + ", age=" + age + ", gender=" + gender + ", bloodGroup=" + bloodGroup
				+ ", relation=" + relation + "]";
	}

	public FamilyOutputModel(int id, String name, int age, String gender, String bloodGroup, String relation) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.relation = relation;
	}

	public FamilyOutputModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}