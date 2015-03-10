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
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import de.egore911.xdp.model.Form;
import de.egore911.xdp.model.Page;
import de.egore911.xdp.util.Constants;

public class PageParser extends Parser {

	private static final Logger log = Logger.getLogger(PageParser.class
			.getName());

	private final Page page;

	public PageParser(XMLEventReader eventReader, StartElement event,
			Parser parent) {
		super(new QName(Constants.XDP_NAMESPACE, "subform"), parent);
		page = new Page();

		Form form = ((FormParser) parent).getForm();
		form.getPages().add(page);
		page.setForm(form);

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = event.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("name".equals(attribute.getName().getLocalPart())) {
				page.setName(attribute.getValue());
			} else if ("w".equals(attribute.getName().getLocalPart())) {
				page.getDimension().setWidth(attribute.getValue());
			} else if ("h".equals(attribute.getName().getLocalPart())) {
				page.getDimension().setHeight(attribute.getValue());
			} else if ("x".equals(attribute.getName().getLocalPart())) {
				page.getPosition().setX(attribute.getValue());
			} else if ("y".equals(attribute.getName().getLocalPart())) {
				page.getPosition().setY(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'subform' not handled yet");
			}
		}
	}

	public Page getPage() {
		return page;
	}
}
