package de.egore911.xdp.model.ui;

public class CheckboxElement extends UiElement {

	public CheckboxElement(UiElement value) {
		setField(value.getField());
		setDraw(value.getDraw());
		// TODO Copy all values
	}

}
