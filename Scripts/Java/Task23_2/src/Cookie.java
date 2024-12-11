public class Cookie extends Sweets{

	private String dough = "Песочное";

	public Cookie() {
		super ("Печенье", 100, 150);
	}

	public String getParam() {
		return dough;
	}

	public String getParamName() {
		return "Тесто";
	}
}
