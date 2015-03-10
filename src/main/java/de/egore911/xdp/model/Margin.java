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

public class Margin {

	private double top;
	private double left;
	private double bottom;
	private double right;

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public void setTop(String value) {
		this.top = CalculationUtil.parse(value);
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public void setLeft(String value) {
		this.left = CalculationUtil.parse(value);
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public void setBottom(String value) {
		this.bottom = CalculationUtil.parse(value);
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public void setRight(String value) {
		this.right = CalculationUtil.parse(value);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getClass().getSimpleName());
		buffer.append("[top=");
		buffer.append(top);
		buffer.append("mm, left=");
		buffer.append(left);
		buffer.append("mm, bottom=");
		buffer.append(bottom);
		buffer.append("mm, right=");
		buffer.append(right);
		buffer.append("mm]");
		return buffer.toString();
	}

}
