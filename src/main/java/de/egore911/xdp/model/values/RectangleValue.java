package de.egore911.xdp.model.values;

import de.egore911.xdp.model.Border;
import de.egore911.xdp.model.Fill;

public class RectangleValue extends Value {

	private Border border;
	private Fill fill;

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Fill getFill() {
		return fill;
	}

	public void setFill(Fill fill) {
		this.fill = fill;
	}

}
