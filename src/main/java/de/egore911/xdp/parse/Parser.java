package de.egore911.xdp.parse;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;

public abstract class Parser {

	private Parser parent;
	private QName handles;

	public Parser(QName handles, Parser parent) {
		this.handles = handles;
		this.parent = parent;
	}

	public boolean isTerminatingEvent(EndElement endElement) {
		return endElement.getName().equals(handles);
	}

	public Parser getParent() {
		return this.parent;
	}

	public boolean isTopParser() {
		return this.parent == null;
	}

}
