package de.egore911.xdp.model.util;

import de.egore911.xdp.util.CalculationUtil;

public class Position {

	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(String value) {
		x = CalculationUtil.parse(value);
	}

	public void setY(String value) {
		y = CalculationUtil.parse(value);
	}

	public double getYPx() {
		return CalculationUtil.mm2px(y);
	}

	public double getXPx() {
		return CalculationUtil.mm2px(x);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getCanonicalName());
		buffer.append('[');
		buffer.append(x);
		buffer.append(',');
		buffer.append(y);
		buffer.append(']');
		return buffer.toString();
	}
}
