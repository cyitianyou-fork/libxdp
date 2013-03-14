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
