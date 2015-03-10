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

import de.egore911.xdp.model.Draw;
import de.egore911.xdp.model.Field;
import de.egore911.xdp.model.ui.ButtonElement;
import de.egore911.xdp.model.ui.CheckboxElement;
import de.egore911.xdp.model.ui.TextElement;
import de.egore911.xdp.model.ui.UiElement;
import de.egore911.xdp.util.Constants;

public class UiParser extends Parser {

	private static final Logger log = Logger
			.getLogger(UiParser.class.getName());

	private UiElement ui;

	public UiParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "ui"), parent);

		ui = new UiElement();

		if (parent instanceof FieldParser) {
			Field field = ((FieldParser) parent).getField();
			ui.setField(field);
			field.setUi(ui);
		} else {
			Draw draw = ((DrawParser) parent).getDraw();
			ui.setDraw(draw);
			draw.setUi(ui);
		}

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

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				if ("textEdit".equals(event.asStartElement().getName()
						.getLocalPart())) {
					ui = new TextElement(ui);

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();

						if ("multiLine".equals(attribute.getName()
								.getLocalPart())) {
							((TextElement) ui).setMultiline(true);
						} else {
							log.warning("Attribute '"
									+ attribute.getName().getLocalPart()
									+ "' of 'value/text' not handled yet");
						}
					}
				} else if ("button".equals(event.asStartElement().getName()
						.getLocalPart())) {
					ui = new ButtonElement(ui);

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						log.warning("Attribute '"
								+ attribute.getName().getLocalPart()
								+ "' of 'value/button' not handled yet");
					}
				} else if ("checkButton".equals(event.asStartElement()
						.getName().getLocalPart())) {
					ui = new CheckboxElement(ui);

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						log.warning("Attribute '"
								+ attribute.getName().getLocalPart()
								+ "' of 'value/checkButton' not handled yet");
					}
				} else if ("border".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+BorderParser");
					BorderParser parser = new BorderParser(eventReader,
							event.asStartElement(), this);
					((FieldParser) getParent()).getField().setBorder(
							parser.getBorder());
					log.info("-BorderParser");
				} else if ("defaultUi".equals(event.asStartElement().getName()
						.getLocalPart())) {
					// TODO disabled because of log spamming
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("textEdit".equals(event.asEndElement().getName()
						.getLocalPart())) {
					// Nothing
				} else if ("defaultUi".equals(event.asEndElement().getName()
						.getLocalPart())) {
					// TODO disabled because of log spamming
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}
		}

		if (parent instanceof FieldParser) {
			Field field = ((FieldParser) parent).getField();
			field.setUi(ui);
		} else {
			Draw draw = ((DrawParser) parent).getDraw();
			draw.setUi(ui);
		}
	}
}
