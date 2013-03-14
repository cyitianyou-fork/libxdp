package de.egore911.xdp.model.util;

import de.egore911.xdp.util.CalculationUtil;

public class Dimension {

	private double width;
	private double height;

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setWidth(String w) {
		width = CalculationUtil.parse(w);
	}

	public void setHeight(String h) {
		height = CalculationUtil.parse(h);
	}

	public double getWidthPx() {
		return CalculationUtil.mm2px(width);
	}

	public double getHeightPx() {
		return CalculationUtil.mm2px(height);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getSimpleName());
		buffer.append("[");
		buffer.append(width);
		buffer.append("mm x ");
		buffer.append(height);
		buffer.append("mm]");
		return buffer.toString();
	}
}
