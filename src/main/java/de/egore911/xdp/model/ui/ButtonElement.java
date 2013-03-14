package de.egore911.xdp.model.ui;

public class ButtonElement extends UiElement {

	public ButtonElement(UiElement value) {
		setField(value.getField());
		setDraw(value.getDraw());
		// TODO Copy all values
	}

}
