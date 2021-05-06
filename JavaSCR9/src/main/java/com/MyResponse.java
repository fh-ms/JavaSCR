
package com;

public class MyResponse extends ComObject
{
	private final double product;
	private final double quotient;
	
	public MyResponse(final double product, final double quotient)
	{
		super();
		this.product  = product;
		this.quotient = quotient;
	}
	
	public double getProduct()
	{
		return this.product;
	}
	
	public double getQuotient()
	{
		return this.quotient;
	}
}
