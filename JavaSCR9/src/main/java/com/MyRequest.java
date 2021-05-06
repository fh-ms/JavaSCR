
package com;

public class MyRequest extends ComObject
{
	private final double x;
	private final double y;
	
	public MyRequest(final double x, final double y)
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
}
