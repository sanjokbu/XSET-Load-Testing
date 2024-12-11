public abstract class Employee {

	private static int count = 0;

	private int id;
	private String name;
	private String surname;
	private int age;
	private String jodTitle;
	private String skills;


	public Employee(String name, String surname, int age, String jodTitle, String skills) {

		count += 1;

		this.id = count;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.jodTitle = jodTitle;
		this.skills = skills;

		System.out.println("Работник " + name + " " + surname + " принят на работу на должность " + jodTitle + " с табельным номером: " + id);

	}


	public String getName() {
		return name;
	}


	public String getSurname() {
		return surname;
	}


	public int getId() {
		return id;
	}

	public int getAge() {
		return age;
	}


	public String getJodTitle() {
		return jodTitle;
	}


	public String getSkills() {
		return skills;
	}


	public void startJob() {
		System.out.println(jodTitle + " " + name + " пришёл на работу");
	}


	public void work() {
		System.out.print(jodTitle + " " + name + " ");
	}


	public void eat() {
		System.out.print(jodTitle + " " + name + " ");
	}


	public void finishJob() {
		System.out.println(jodTitle + " " + name + " ушёл с работы");
	}
}
