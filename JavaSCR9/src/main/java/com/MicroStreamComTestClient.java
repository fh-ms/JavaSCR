package com;

import java.nio.channels.SocketChannel;
import java.time.Duration;

import one.microstream.communication.binary.types.ComBinary;
import one.microstream.communication.types.ComClient;
import one.microstream.communication.types.ComClientChannel;

public class MicroStreamComTestClient
{
	public static void main(final String[] args)
	{
		final ComClient<SocketChannel> client = ComBinary.Foundation()
			// whitelisting
			.registerEntityTypes(
				MyRequest.class,
				MyResponse.class
			)
			.setPort(1337)
			.createClient();
		
		final double x = Util.randomValue(100);
		final double y = Util.randomValue( 10);

		final ComClientChannel<SocketChannel> channel = client.connect();
		final MyRequest  request  = new MyRequest(x, y);
		final MyResponse response = (MyResponse)channel.request(request);
		channel.close();
		
		System.out.println("Took " + Duration.between(request.getTimestamp(), response.getTimestamp()).toMillis() + " ms");
		System.out.println(x + " * " + y + " = " + response.getProduct());
		System.out.println(x + " / " + y + " = " + response.getQuotient());
	}
}
