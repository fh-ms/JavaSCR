package com;

import java.util.Random;

public class Util
{
	public static double randomValue(final int bounds)
	{
		final Random random = new Random();
		return round(random.nextDouble() * random.nextInt(bounds) + random.nextDouble());
	}
	
	public static double round(final double d)
	{
		return Math.round(d * 100.0) / 100.0;
	}
}
