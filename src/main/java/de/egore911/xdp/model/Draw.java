package de.egore911.xdp.model;

import de.egore911.xdp.model.ui.UiElement;
import de.egore911.xdp.model.util.Dimension;
import de.egore911.xdp.model.util.Position;
import de.egore911.xdp.model.values.Value;

public class Draw {

	private String name;
	private Page page;
	private Value value;
	private UiElement ui;
	private final Dimension dimension = new Dimension();
	private final Position position = new Position();
	private Font font;
	private Margin margin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Position getPosition() {
		return position;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public UiElement getUi() {
		return ui;
	}

	public void setUi(UiElement ui) {
		this.ui = ui;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Margin getMargin() {
		return margin;
	}

	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getCanonicalName());
		buffer.append('[');
		buffer.append(getName());
		buffer.append(',');
		buffer.append(getValue());
		buffer.append(']');
		return buffer.toString();
	}
}
