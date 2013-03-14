package de.egore911.xdp.util;

import org.junit.Assert;
import org.junit.Test;

public class CalculationUtilTest {

	@Test
	public void testPt2PxVia() {
		Assert.assertEquals(CalculationUtil.pt2px(100),
				CalculationUtil.mm2px(CalculationUtil.pt2mm(100)), 0.001);
		Assert.assertEquals(CalculationUtil.pt2px(11),
				CalculationUtil.mm2px(CalculationUtil.pt2mm(11)), 0.001);
	}

	@Test
	public void testMm2px() {
		Assert.assertEquals(33.0027174,
				CalculationUtil.mm2px(Double.parseDouble("8.73")), 0.001);
	}

	@Test
	public void testParse() {
		Assert.assertEquals(8.73, CalculationUtil.parse("8.73mm"), 0.001);
		Assert.assertEquals(-10.513333, CalculationUtil.parse("-10.513333mm"),
				0.001);
	}

	@Test
	public void testPt2Px() {
		Assert.assertEquals(10, CalculationUtil.pt2px(7.5), 0.001);
		Assert.assertEquals(8, CalculationUtil.pt2px(6), 0.001);
	}

	@Test
	public void testPx2Pt() {
		Assert.assertEquals(7.5, CalculationUtil.px2pt(10), 0.001);
		Assert.assertEquals(6, CalculationUtil.px2pt(8), 0.001);
	}

	@Test
	public void testPt2em() {
		Assert.assertEquals(0.5, CalculationUtil.pt2em(6), 0.001);
		Assert.assertEquals(0.625, CalculationUtil.pt2em(7.5), 0.001);
	}

	@Test
	public void testEm2pt() {
		Assert.assertEquals(6, CalculationUtil.em2pt(0.5), 0.001);
		Assert.assertEquals(7.5, CalculationUtil.em2pt(0.625), 0.001);
	}
}
