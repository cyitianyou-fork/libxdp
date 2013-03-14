package de.egore911.xdp.model.values;

import de.egore911.xdp.model.Border;

public class HtmlValue extends Value {

	private String htmlContent;
	private Border border;

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public void appendHtmlContent(String htmlContent) {
		if (this.htmlContent == null) {
			this.htmlContent = "";
		}
		this.htmlContent += htmlContent;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getCanonicalName());
		buffer.append("[");
		buffer.append(htmlContent);
		buffer.append("]");
		return buffer.toString();
	}
}
