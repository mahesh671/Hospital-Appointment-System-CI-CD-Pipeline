package spring.orm.model.input;

public class RegistrationForm {
	private String uname;
	private String name;
	private Integer age;
	private String gmail;
	private String gender;
	private String pass;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "RegistrationForm [uname=" + uname + ", name=" + name + ", age=" + age + ", gmail=" + gmail + ", gender="
				+ gender + ", pass=" + pass + "]";
	}

	public RegistrationForm(String uname, String name, Integer age, String gmail, String gender, String pass) {
		super();
		this.uname = uname;
		this.name = name;
		this.age = age;
		this.gmail = gmail;
		this.gender = gender;
		this.pass = pass;
	}

	public RegistrationForm() {
		super();
	}

	// Getters and Setters
}