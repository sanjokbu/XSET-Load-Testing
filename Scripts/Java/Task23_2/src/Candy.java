public class Candy extends Sweets {

	private String filling = "Крем";

	public Candy() {
		super("Конфета", 20, 50);
	}

	public String getParam() {
		return filling;
	}

	public String getParamName() {
		return "Начинка";
	}
}
