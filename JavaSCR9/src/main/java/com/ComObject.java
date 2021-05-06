package com;

import java.time.LocalDateTime;

public abstract class ComObject
{
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	public LocalDateTime getTimestamp()
	{
		return this.timestamp;
	}
}
