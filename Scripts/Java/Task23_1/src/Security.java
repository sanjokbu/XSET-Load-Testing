public class Security extends Employee {

	public Security (String name, String surname, int age, String skills) {
		super(name, surname, age, "Охранник", skills);
	}

	@Override
	public void work() {
		super.work();
		System.out.println("охраняет главный зал");
	}

	@Override
	public void eat() {
		super.eat();
		System.out.println("обедает 20 минут");
	}
}