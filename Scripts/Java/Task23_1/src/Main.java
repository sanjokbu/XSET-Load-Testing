import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("НАЧАЛСЯ НАЙМ ЛЮДЕЙ НА РАБОТУ: ");
		Employee[] employees = {
			new Cleaner("Андрей", "Семёнов", 21, "Хорошо убирает"),
			new Manager("Мария", "Иванова", 43, "Умело руководит проектами"),
			new Cleaner("Анатолий", "Казанов", 23, "Моет стекла без раводов"),
			new Security("Евгений", "Шишкин", 43, "Хорошо стреляет из лука"),
			new Driver("Евлампий", "Смактунович", 26, "Водитель от бога"),
			new Manager("Ашот", "Гургеновян", 36, "Раньше руководил поездами"),
			new Manager("Валентина", "Мулинкова", 34, "Не умеет плакать"),
			new Security("Артур", "Араранян", 29, "Владеет карате"),
			new Manager("Митя", "Филюшкин", 51, "Умеет думать"),
			new Driver("Павел", "Мутаев", 55, "Умеет входить в крутой поворот на скорости 100 км/ч")
		};

		System.out.println();
		System.out.println("СПИСОК СОРУДНИКОВ КОМПАНИИ: ");

		for (Employee employee : employees) {
			System.out.println("Сотрудник с табельным номером: " + employee.getId() + ": " +
					employee.getName() + " " + employee.getSurname() +
					", возраст: " + employee.getAge() +
					", должность: " + employee.getJodTitle() +
					", особые навыки: " + employee.getSkills());
		}

		System.out.println();
		System.out.println("Для отображения действий сотрудника в течении дня, введите его табельный номер:");
		int numEmployee = scanner.nextInt();

		if (numEmployee < employees.length + 1) {
			employees [numEmployee - 1].work();
			employees [numEmployee - 1].startJob();
			employees [numEmployee - 1].eat();
			employees [numEmployee - 1].finishJob();
		} else {
			System.out.println("Сотрудника с таким номером не найдено!");
		}
	}
}
