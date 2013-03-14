package de.egore911.xdp.model;

public class Field extends Draw {

	private boolean visible = true;
	private Border border;
	private boolean editable = true;
	private boolean readOnly;
	private Caption caption;

	public void setVisible(String value) {
		this.visible = !value.equals("invisible") && !value.equals("hidden");
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Border getBorder() {
		return border;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getCanonicalName());
		buffer.append('[');
		buffer.append(getName());
		buffer.append(']');
		return buffer.toString();
	}

}
