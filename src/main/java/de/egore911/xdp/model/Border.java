package de.egore911.xdp.model;

public class Border {

	private Edge edge;
	private Fill fill;
	private boolean visible = true;

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public Fill getFill() {
		return fill;
	}

	public void setFill(Fill fill) {
		this.fill = fill;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
