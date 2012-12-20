package test.util;


import static org.junit.Assert.*;

import org.ejml.data.Complex64F;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.PolynomialRootFinder;

public class PolynomialRootFinderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindRoots() {
		double[] coefficients = new double[4];
		coefficients[0] = 1;
		coefficients[1] = -2;
		coefficients[2] = -1;
		coefficients[3] = 2;
		double[] result = PolynomialRootFinder.findRoots(coefficients);
		assertTrue(contains(result,-1) && contains(result,2) && contains(result,1));
	}
	
	private boolean contains(double[] array, double el) {
		for (int i = 0; i < array.length; i++) {
			if(array[i] - el <= 1e-15)
				return true;
		}
		return false;
	}
	
}
