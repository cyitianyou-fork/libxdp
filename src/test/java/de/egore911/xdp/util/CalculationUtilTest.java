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
