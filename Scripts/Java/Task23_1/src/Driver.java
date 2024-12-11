public class Driver extends Employee {

	public Driver (String name, String surname, int age, String skills) {
		super(name, surname, age, "Водитель", skills);
	}

	@Override
	public void work() {
		super.work();
		System.out.println("возит людей");
	}

	@Override
	public void eat() {
		super.eat();
		System.out.println("обедает 40 минут");
	}
}