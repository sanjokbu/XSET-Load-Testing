public class Cleaner extends Employee {

	public Cleaner(String name, String surname, int age, String skills) {
		super(name, surname, age, "Уборщик", skills);
	}

	@Override
	public void work() {
		super.work();
		System.out.println("убирает помещения");
	}

	@Override
	public void eat() {
		super.eat();
		System.out.println("обедает 30 минут");
	}
}
