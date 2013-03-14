package de.egore911.xdp.model.ui;

import de.egore911.xdp.model.Draw;
import de.egore911.xdp.model.Field;

public class UiElement {

	private Field field;
	private Draw draw;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

}
