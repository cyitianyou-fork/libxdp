package de.egore911.xdp.model;

import java.awt.Color;

import de.egore911.xdp.util.CalculationUtil;

public class Edge {

	public enum Type {
		lowered, raised
	}

	private double width = -1;
	private Type type;
	private Color color;

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidthPx() {
		return CalculationUtil.mm2px(width);
	}

	public void setWidth(String value) {
		width = CalculationUtil.parse(value);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(String type) {
		this.type = Type.valueOf(type);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
