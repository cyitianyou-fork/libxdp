package de.egore911.xdp.model;

import java.awt.Color;

public class Fill {

	private Color color;
	private boolean visible = true;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
