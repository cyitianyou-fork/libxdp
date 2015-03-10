/*
 * Copyright (c) 2013 Christoph Brill
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
import de.egore911.xdp.model.Draw;
import de.egore911.xdp.model.Field;
import de.egore911.xdp.model.ui.TextElement;
import de.egore911.xdp.model.values.ImageValue;
import de.egore911.xdp.model.values.LineValue;
import de.egore911.xdp.model.values.TextValue;
import de.egore911.xdp.model.values.Value;
import de.egore911.xdp.util.Constants;

public class ValueParser extends Parser {

	private static final Logger log = Logger.getLogger(ValueParser.class
			.getName());

	private Value value;

	public ValueParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "value"), parent);

		value = new Value();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			log.warning("Attribute '" + attribute.getName().getLocalPart()
					+ "' of 'value' not handled yet");
		}

		if (startEvent.isEndElement()) {
			if (isTerminatingEvent(startEvent.asEndElement())) {
				return;
			}
		}

		StringBuilder imageAware = null;

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				if ("text".equals(event.asStartElement().getName()
						.getLocalPart())) {
					value = new TextValue();

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						if ("maxChars".equals(attribute.getName()
								.getLocalPart())) {
							((TextValue) value).setMaxLength(Integer
									.valueOf(attribute.getValue()));
						} else {
							log.warning("Attribute '"
									+ attribute.getName().getLocalPart()
									+ "' of 'value/text' not handled yet");
						}
					}
				} else if ("rectangle".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+RectangleParser");
					RectangleParser rectangleParser = new RectangleParser(
							eventReader, event.asStartElement(), this);
					value = rectangleParser.getValue();
					log.info("-RectangleParser");
				} else if ("line".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+LineParser");
					LineParser lineParser = new LineParser(eventReader,
							event.asStartElement(), this);
					value = lineParser.getValue();
					log.info("-LineParser");
				} else if ("image".equals(event.asStartElement().getName()
						.getLocalPart())) {
					value = new ImageValue();

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						if ("contentType".equals(attribute.getName()
								.getLocalPart())) {
							((ImageValue) value).setMimeType(attribute
									.getValue());
						} else if ("href".equals(attribute.getName()
								.getLocalPart())) {
							((ImageValue) value).setSrc(attribute.getValue());
						} else {
							log.warning("Attribute '"
									+ attribute.getName().getLocalPart()
									+ "' of 'value/image' not handled yet");
						}
					}
					imageAware = new StringBuilder();

				} else if ("body".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+HtmlParser");
					value = new HtmlParser(eventReader, event.asStartElement(),
							this).getValue();
					log.info("-HtmlParser");
				} else if ("line".equals(event.asStartElement().getName()
						.getLocalPart())) {
					value = new LineValue();

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						log.warning("Attribute '"
								+ attribute.getName().getLocalPart()
								+ "' of 'value/line' not handled yet");
					}
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("text"
						.equals(event.asEndElement().getName().getLocalPart())) {
					// Nothing
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else if ("image".equals(event.asEndElement().getName()
						.getLocalPart())) {
					((ImageValue) value).setBase64Data(imageAware.toString());
					imageAware = null;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}

			if (event.isCharacters()) {
				if (!event.asCharacters().getData().trim().equals("")) {
					if (value instanceof TextValue) {
						((TextValue) value).setText(event.asCharacters()
								.getData());
					} else if (value instanceof ImageValue) {
						imageAware.append(event.asCharacters().getData());
					}
				}
			}
		}

		if (value.getClass().equals(Value.class)) {

			if (parent instanceof FieldParser) {
				Field field = ((FieldParser) parent).getField();
				if (field.getUi() instanceof TextElement) {
					field.setValue(new TextValue());
				}
			} else {
				throw new RuntimeException("No usable value found for "
						+ parent);
			}

		}

		if (parent instanceof FieldParser) {
			Field field = ((FieldParser) parent).getField();
			field.setValue(value);
		} else if (parent instanceof CaptionParser) {
			Caption caption = ((CaptionParser) parent).getCaption();
			caption.setValue(value);
		} else {
			Draw draw = ((DrawParser) parent).getDraw();
			draw.setValue(value);
		}

	}
}
