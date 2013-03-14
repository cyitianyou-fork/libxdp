package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Field;
import de.egore911.xdp.model.Page;
import de.egore911.xdp.model.ui.ButtonElement;
import de.egore911.xdp.model.ui.TextElement;
import de.egore911.xdp.model.values.TextValue;
import de.egore911.xdp.model.values.Value;
import de.egore911.xdp.util.Constants;

public class FieldParser extends Parser {

	private static final Logger log = Logger.getLogger(FieldParser.class
			.getName());

	private Field field;

	public FieldParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "field"), parent);
		field = new Field();

		Page page = ((PageParser) parent).getPage();
		page.getFields().add(field);
		field.setPage(page);

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("name".equals(attribute.getName().getLocalPart())) {
				field.setName(attribute.getValue());
			} else if ("w".equals(attribute.getName().getLocalPart())) {
				field.getDimension().setWidth(attribute.getValue());
			} else if ("h".equals(attribute.getName().getLocalPart())) {
				field.getDimension().setHeight(attribute.getValue());
			} else if ("x".equals(attribute.getName().getLocalPart())) {
				field.getPosition().setX(attribute.getValue());
			} else if ("y".equals(attribute.getName().getLocalPart())) {
				field.getPosition().setY(attribute.getValue());
			} else if ("presence".equals(attribute.getName().getLocalPart())) {
				field.setVisible(attribute.getValue());
			} else if ("access".equals(attribute.getName().getLocalPart())) {
				if (attribute.getValue().equals("readOnly")) {
					field.setReadOnly(true);
				} else if (attribute.getValue().equals("protected")) {
					field.setEditable(false);
				}
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'subform' not handled yet");
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
				} else if ("caption".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+CaptionParser");
					CaptionParser parser = new CaptionParser(eventReader,
							event.asStartElement(), this);
					getField().setCaption(parser.getCaption());
					log.info("-CaptionParser");
				} else if ("font".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+FontParser");
					FontParser parser = new FontParser(eventReader,
							event.asStartElement(), this);
					getField().setFont(parser.getFont());
					log.info("-FontParser");
				} else if ("border".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+BorderParser");
					BorderParser parser = new BorderParser(eventReader,
							event.asStartElement(), this);
					getField().setBorder(parser.getBorder());

					log.info("-BorderParser");
				} else if ("value".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+ValueParser");
					new ValueParser(eventReader, event.asStartElement(), this);
					log.info("-ValueParser");
				} else if ("ui".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+UiParser");
					new UiParser(eventReader, event.asStartElement(), this);
					log.info("-UiParser");
				} else if ("margin".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+MarginParser");
					new MarginParser(eventReader, event.asStartElement(), this);
					log.info("-MarginParser");
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if (isTerminatingEvent(event.asEndElement())) {
					if (field.getValue() == null) {
						if (field.getUi() instanceof TextElement) {
							field.setValue(new TextValue());
						} else if (field.getUi() instanceof ButtonElement) {
							field.setValue(new Value());
						} else {
							throw new NullPointerException("Field "
									+ field.getName() + " has no value");
						}
					}
					break;
				}
				log.severe("</" + event.asEndElement().getName().getLocalPart());
			}
		}

	}

	public Field getField() {
		return this.field;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("FieldParser[");
		buffer.append(field);
		buffer.append("]");
		return buffer.toString();
	}
}
