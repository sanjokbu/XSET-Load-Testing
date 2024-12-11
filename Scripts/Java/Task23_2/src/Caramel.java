public class Caramel extends Sweets{

	private String flavor = "Клубника";

	public Caramel() {
		super("Карамель", 50, 100);
	}

	public String getParam() {
		return flavor;
	}

	public String getParamName() {
		return "Вкус";
	}
}
