public abstract class Sweets {

	private final String name;
	private final int weight;
	private int price;

	public Sweets (String name, int weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public int getPrice() {
		return price;
	}

	public abstract String getParam();
	public abstract String getParamName();

}
