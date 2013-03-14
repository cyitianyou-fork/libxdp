package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.values.HtmlValue;
import de.egore911.xdp.util.Constants;

public class HtmlParser extends Parser {

	private static final Logger log = Logger.getLogger(HtmlParser.class
			.getName());

	private HtmlValue value;

	public HtmlParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.HTML_NAMESPACE, "body"), parent);

		value = new HtmlValue();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("APIVersion".equals(attribute.getName().getLocalPart())) {
				// TODO disabled because of log spamming
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'body' not handled yet");
			}
		}

		if (startEvent.isEndElement()) {
			if (isTerminatingEvent(startEvent.asEndElement())) {
				return;
			}
		}

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				value.appendHtmlContent("<");
				value.appendHtmlContent(event.asStartElement().getName()
						.getLocalPart());
				@SuppressWarnings("unchecked")
				Iterator<Attribute> attriter = event.asStartElement()
						.getAttributes();
				while (attriter.hasNext()) {
					Attribute attribute = attriter.next();
					value.appendHtmlContent(" ");
					value.appendHtmlContent(attribute.getName().getLocalPart());
					value.appendHtmlContent("=\"");
					value.appendHtmlContent(attribute.getValue());
					value.appendHtmlContent("\"");
				}
				if (event.asStartElement().getName().getLocalPart()
						.equals("br")) {
					value.appendHtmlContent(" /");
				}
				value.appendHtmlContent(">");
			}

			if (event.isEndElement()) {
				if (isTerminatingEvent(event.asEndElement())) {
					return;
				}
				if (!event.asEndElement().getName().getLocalPart().equals("br")) {
					value.appendHtmlContent("</");
					value.appendHtmlContent(event.asEndElement().getName()
							.getLocalPart());
					value.appendHtmlContent(">");
				}
			}

			if (event.isCharacters()) {
				value.appendHtmlContent(event.asCharacters().getData());
			}
		}
	}

	public HtmlValue getValue() {
		return value;
	}

}
