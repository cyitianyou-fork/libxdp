package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Border;
import de.egore911.xdp.model.values.LineValue;
import de.egore911.xdp.model.values.Value;
import de.egore911.xdp.util.Constants;

public class LineParser extends Parser {

	private static final Logger log = Logger.getLogger(LineParser.class
			.getName());

	private LineValue value;

	public LineParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "line"), parent);
		value = new LineValue();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("hand".equals(attribute.getName().getLocalPart())) {
				// TODO disabled because of log spamming
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of '" + startEvent.getName().getLocalPart()
						+ "' not handled yet");
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
				if ("edge".equals(event.asStartElement().getName()
						.getLocalPart())) {

					Border border = value.getBorder();
					if (border == null) {
						border = new Border();
						value.setBorder(border);
					}

					log.info("+EdgeParser");
					EdgeParser edgeParser = new EdgeParser(eventReader,
							event.asStartElement(), this);
					border.setEdge(edgeParser.getEdge());
					log.info("-EdgeParser");
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("edge"
						.equals(event.asEndElement().getName().getLocalPart())) {
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}

			if (event.isCharacters()) {
				if (!event.asCharacters().getData().trim().equals("")) {
					log.severe(event.asCharacters().getData().trim());
				}
			}
		}

	}

	public Value getValue() {
		return value;
	}

}
