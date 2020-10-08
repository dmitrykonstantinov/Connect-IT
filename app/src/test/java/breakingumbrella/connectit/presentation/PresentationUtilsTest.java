package breakingumbrella.connectit.presentation;

import org.junit.Assert;
import org.junit.Test;

public class PresentationUtilsTest {

	@Test
	public void linearize() {
		Assert.assertEquals(75, PresentationUtils.linearize(7, 5, 10));
		Assert.assertEquals(5, PresentationUtils.linearize(0, 5, 10));
		Assert.assertEquals(6, PresentationUtils.linearize(1, 2, 4));
	}

	@Test
	public void deLinearize() {
		Assert.assertArrayEquals(new int[]{7, 5}, PresentationUtils.deLinearize(75, 10));
		Assert.assertArrayEquals(new int[]{0, 5}, PresentationUtils.deLinearize(5, 10));
		Assert.assertArrayEquals(new int[]{1, 2}, PresentationUtils.deLinearize(6, 4));
	}

}