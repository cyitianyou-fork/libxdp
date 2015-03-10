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
package de.egore911.xdp.model;

import de.egore911.xdp.util.CalculationUtil;

public class Font {

	private String family;
	private double size;
	private String weight;
	private boolean underline;
	private Fill fill;

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		if ("Arial".equals(family)) {
			this.family = "Arial";
		} else if ("Microsoft Sans Serif".equals(family)
				|| "Myriad Pro".equals(family)
				|| "MS Sans Serif".equals(family)) {
			this.family = "Sans";
		} else if ("Verdana".equals(family)) {
			this.family = "Verdana";
		} else if ("Wingdings".equals(family)) {
			this.family = "Wingdings";
		} else if ("Times New Roman".equals(family)) {
			this.family = "Wingdings";
		} else {
			throw new RuntimeException("Unhandled font family " + family);
		}
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setSize(String value) {
		size = CalculationUtil.parse(value);
	}

	public double getSizePx() {
		return CalculationUtil.mm2px(size);
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		if ("bold".equals(weight)) {
			this.weight = weight;
		} else {
			throw new RuntimeException("Font weight " + weight + " unknown");
		}
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public Fill getFill() {
		return fill;
	}

	public void setFill(Fill fill) {
		this.fill = fill;
	}

}
