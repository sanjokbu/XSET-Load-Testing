public class Manager extends Employee{

	public Manager (String name, String surname, int age, String skills) {
		super(name, surname, age, "Менеджер", skills);
	}

	@Override
	public void work() {
		super.work();
		System.out.println("планирует и организовывает работы");
	}

	@Override
	public void eat() {
		super.eat();
		System.out.println("обедает 60 минут");
	}
}