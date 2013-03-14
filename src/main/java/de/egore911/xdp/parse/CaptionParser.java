package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Caption;
import de.egore911.xdp.util.Constants;

public class CaptionParser extends Parser {

	private static final Logger log = Logger.getLogger(CaptionParser.class
			.getName());

	private Caption caption;

	public CaptionParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "caption"), parent);

		caption = new Caption();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("reserve".equals(attribute.getName().getLocalPart())) {
				caption.setReserve(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'caption' not handled yet");
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
				if ("value".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+ValueParser");
					new ValueParser(eventReader, event.asStartElement(), this);
					log.info("-ValueParser");
				} else if ("font".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+FontParser");
					FontParser parser = new FontParser(eventReader,
							event.asStartElement(), this);
					getCaption().setFont(parser.getFont());
					log.info("-FontParser");
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("text"
						.equals(event.asEndElement().getName().getLocalPart())) {
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}

		}
	}

	public Caption getCaption() {
		return caption;
	}
}
