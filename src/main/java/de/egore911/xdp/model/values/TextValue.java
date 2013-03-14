package de.egore911.xdp.model.values;

public class TextValue extends Value {

	private int maxLength;
	private String text;

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
