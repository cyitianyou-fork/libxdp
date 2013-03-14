package de.egore911.xdp.model.ui;

public class TextElement extends UiElement {

	private boolean multiline;

	public TextElement(UiElement value) {
		setField(value.getField());
		setDraw(value.getDraw());
		// TODO Copy all values
	}

	public boolean isMultiline() {
		return multiline;
	}

	public void setMultiline(boolean multiline) {
		this.multiline = multiline;
	}

}
