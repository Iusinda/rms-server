package fyp.rms.server;

public class TestObject {
	private int id;
	private String text;

	public TestObject() {
		this(3, "Success");
	}

	public TestObject(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}
}
