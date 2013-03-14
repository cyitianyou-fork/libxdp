package de.egore911.xdp.model;

import de.egore911.xdp.util.CalculationUtil;

public class Margin {

	private double top;
	private double left;
	private double bottom;
	private double right;

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public void setTop(String value) {
		this.top = CalculationUtil.parse(value);
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public void setLeft(String value) {
		this.left = CalculationUtil.parse(value);
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public void setBottom(String value) {
		this.bottom = CalculationUtil.parse(value);
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public void setRight(String value) {
		this.right = CalculationUtil.parse(value);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getSimpleName());
		buffer.append("[top=");
		buffer.append(top);
		buffer.append("mm, left=");
		buffer.append(left);
		buffer.append("mm, bottom=");
		buffer.append(bottom);
		buffer.append("mm, right=");
		buffer.append(right);
		buffer.append("mm]");
		return buffer.toString();
	}

}
