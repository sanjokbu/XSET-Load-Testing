import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		SweetBox sweetBox = new SweetBox();

		System.out.println("Список доступных сладостей: ");
		System.out.println("0 - Выход");
		System.out.println("1 - Конфета");
		System.out.println("2 - Шоколадка");
		System.out.println("3 - Карамель");
		System.out.println("4 - Вафли");
		System.out.println("5 - Печенье");

		System.out.println();
		System.out.println("Хотите добавить сладости вручную или случайным образом?");
		System.out.println("ДОБАВИТЬ ВРУЧНУЮ (введите - 1) или СЛУЧАЙНЫМ ОБРАЗОМ (введите - 2):");
		int response = scanner.nextInt();

		if (response == 1) {

			int num = 1;

			System.out.println("Введите номер сладости, которую необходимо добавить в коробку или введите 0 для выхода:");

			while (num != 0) {

				num = scanner.nextInt();

				switch (num) {
					case 0:
						break;
					case 1:
						sweetBox.addSweets(new Candy());
						break;
					case 2:
						sweetBox.addSweets(new Chocolate());
						break;
					case 3:
						sweetBox.addSweets(new Caramel());
						break;
					case 4:
						sweetBox.addSweets(new Waffles());
						break;
					case 5:
						sweetBox.addSweets(new Cookie());
						break;
					default:
						System.out.println("Сладость с таким номером не найдена! Попробуйте ещё раз или введите 0 для выхода:");
						continue;
				}

				System.out.println("Сладость добавлена в коробку!\n");
				System.out.println("Введите номер сладости, которую необходимо добавить в коробку или введите 0 для выхода:");
			}

		} else if (response == 2) {

			System.out.println("Введите количество сладостей которое необходимо добавить в коробку:");
			int num = scanner.nextInt();

			for (int i = 0; i < num; i++) {

				switch ((int)(Math.random() * 5) + 1) {
					case 1:
						sweetBox.addSweets(new Candy());
						break;
					case 2:
						sweetBox.addSweets(new Chocolate());
						break;
					case 3:
						sweetBox.addSweets(new Caramel());
						break;
					case 4:
						sweetBox.addSweets(new Waffles());
						break;
					case 5:
						sweetBox.addSweets(new Cookie());
						break;
				}
			}

		} else {
			System.out.println("Неверный ввод!");
			System.exit(0);
		}

		System.out.println();
		sweetBox.getInfoSweets();

		System.out.println();
		System.out.println("Общий вес сладостей в коробке: " + sweetBox.getWeight() + " гр");
		System.out.println("Общая цена сладостей в коробке: " + sweetBox.getPrice() + " руб");

		System.out.println();
		System.out.println("Желаете оптимизировать подарок?");
		System.out.println("ДА (введите - 1) или НЕТ (введите - 2):");

		response = scanner.nextInt();

		if (response == 1) {

			System.out.println();

			System.out.println("Введите желаемый вес в граммах:");
			int weight = scanner.nextInt();

			System.out.println();
			System.out.println("Выберете тип оптимизации подарка!");
			System.out.println("ПО ЦЕНЕ (введите - 1) или ПО ВЕСУ (введите 2):");
			response = scanner.nextInt();

			if (response == 1){
				sweetBox.optimizeByPrice(weight);
			} else if (response == 2) {
				sweetBox.optimizeByWeight(weight);
			} else {
				System.out.println("Неверное число");
			}

		}

		System.out.println();
		System.out.println("==========================================");
		System.out.println("Ваша коробка со сладостями готова!!!");
		System.out.println("==========================================");

		sweetBox.getInfoSweets();

		System.out.println();
		System.out.println("Общий вес сладостей в коробке: " + sweetBox.getWeight() + " гр");

		System.out.println("==========================================");
		System.out.println("К оплате: " + sweetBox.getPrice() + " py6");
		System.out.println("==========================================");

		scanner.close();

	}
}