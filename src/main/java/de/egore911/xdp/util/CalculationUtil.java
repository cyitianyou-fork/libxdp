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
package de.egore911.xdp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculationUtil {

	public static Pattern VALUE_PATTERN = Pattern
			.compile("([\\d\\.-]+)((\\w*))");

	public static double parse(String value) {
		Matcher matcher = VALUE_PATTERN.matcher(value);
		if (!matcher.matches()) {
			throw new RuntimeException("Size specification " + value
					+ " not understood");
		}

		if (matcher.groupCount() != 3) {
			throw new RuntimeException("Size specification " + value
					+ " not parseable");
		}

		return convert(Double.parseDouble(matcher.group(1)), matcher.group(2));
	}

	private static double convert(double value, String unit) {
		if ("mm".equals(unit)) {
			return value;
		}
		if ("pt".equals(unit)) {
			return pt2mm(value);
		}
		if ("in".equals(unit)) {
			return in2mm(value);
		}
		if ("px".equals(unit)) {
			return px2mm(value);
		}
		throw new RuntimeException("Unit specification " + unit
				+ " not understood");
	}

	public static double mm2px(double mm) {
		return mm * 3.78038;
	}

	public static double px2mm(double px) {
		return px / 3.78038;
	}

	public static double pt2mm(double pt) {
		return pt * 0.3527;
	}

	public static double pt2px(double pt) {
		return pt / 0.75;
	}

	public static double px2pt(double px) {
		return px * 0.75;
	}

	public static double pt2em(double pt) {
		return pt / 12;
	}

	public static double em2pt(double em) {
		return em * 12;
	}

	public static double in2mm(double in) {
		return in * 25.4;
	}
}
