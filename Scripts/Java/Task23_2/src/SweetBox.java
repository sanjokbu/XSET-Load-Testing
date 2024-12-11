public class SweetBox {

	private Sweets[] sweets = new Sweets[0];
	private int weight = 0;
	private int price = 0;


	public int getWeight() {
		return weight;
	}


	public int getPrice() {
		return price;
	}


	public void getInfoSweets() {

		int count = 1;

		System.out.println("Список сладостей в коробке:");

		for (Sweets sweet : sweets) {
			System.out.println(count + ". " + sweet.getName() + "; Вес: " + sweet.getWeight()+" гр; Цена: " + sweet.getPrice() + " руб; " + sweet.getParamName() + ": " + sweet.getParam());
			count++;
		}
	}


	public void addSweets (Sweets sweet) {

		Sweets[] temp = new Sweets[sweets.length + 1];

		System.arraycopy(sweets, 0, temp, 0, sweets.length);

		temp[sweets.length] = sweet;

		weight += sweet.getWeight();
		price += sweet.getPrice();
		sweets = temp;
	}

	public void deleteSweets (int index) {

		Sweets[] temp = new Sweets[sweets.length - 1];

		for (int i = 0, j = 0; i < sweets.length; i++) {

			if (i != index) {
				temp[j] = sweets[i];
				j++;
			}
		}

		weight -= sweets[index].getWeight();
		price -= sweets [index].getPrice();
		sweets = temp;

	}

	public void optimizeByPrice (int weight) {

		while (this.weight > weight && sweets.length > 1) {

			int minPrice = Integer.MAX_VALUE;
			int index = 0;

			for (int i = 0; i < sweets.length; i++) {

				if (sweets[i].getPrice() < minPrice) {
					minPrice = sweets[i].getPrice();
					index = i;
				}
			}

			deleteSweets(index);
		}
	}

	public void optimizeByWeight (int weight) {

		while (this.weight > weight && sweets.length > 1) {

			int minWeight = Integer.MAX_VALUE;
			int index = 0;

			for (int i = 0; i < sweets.length; i++) {

				if (sweets[i].getWeight() < minWeight) {
					minWeight = sweets[i].getWeight();
					index = i;
				}
			}

			deleteSweets(index);
		}
	}
}
