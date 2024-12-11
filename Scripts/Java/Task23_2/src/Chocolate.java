public class Chocolate extends Sweets{

	private String type = "Молочный";

	public Chocolate() {
		super ("Шоколад", 150, 300);
	}

	public String getParam() {
		return type;
	}

	public String getParamName() {
		return "Вид";
	}
}