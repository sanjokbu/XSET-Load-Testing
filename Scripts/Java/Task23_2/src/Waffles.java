public class Waffles extends Sweets {

	private String shape = "Cердце";

	public Waffles() {
		super("Вафли", 300, 350);
	}

	public String getParam() {
		return shape;
	}

	public String getParamName() {
		return "Форма";
	}
}
