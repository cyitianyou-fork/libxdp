package de.egore911.xdp.model;

import de.egore911.xdp.model.values.Value;
import de.egore911.xdp.util.CalculationUtil;

public class Caption {

	private Font font;
	private double reserve;
	private Value value;

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public double getReserve() {
		return reserve;
	}

	public void setReserve(double reserve) {
		this.reserve = reserve;
	}

	public void setReserve(String value) {
		this.reserve = CalculationUtil.parse(value);
	}

	public double getReservePx() {
		return CalculationUtil.mm2px(reserve);
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
