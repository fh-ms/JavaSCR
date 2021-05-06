package com;

import java.nio.channels.SocketChannel;

import one.microstream.communication.binary.types.ComBinary;
import one.microstream.communication.binary.types.ComPersistenceAdaptorBinary;
import one.microstream.communication.types.ComFoundation;
import one.microstream.communication.types.ComHost;
import one.microstream.persistence.types.PersistenceSizedArrayLengthController;

public class MicoStreamComTestServer
{
	public static void main(final String[] args)
	{
		final ComPersistenceAdaptorBinary.Creator.Default adaptorCreator = ComBinary.DefaultPersistenceAdaptorCreator();
		
		// array bomb prevention with fitting array length controller
		// arrays and collections are created with actual data length, not specified length
		// this is the default setting, just here for demonstration purposes
		adaptorCreator.foundation().setSizedArrayLengthController(PersistenceSizedArrayLengthController.Fitting());
		
		// array bomb prevention with limited array length controller
		//creator.foundation().setSizedArrayLengthController(PersistenceSizedArrayLengthController.Limited(1000));
				
		final ComHost<SocketChannel> host = ComFoundation.New()
			.setPersistenceAdaptorCreator(adaptorCreator)
			// whitelisting
			.registerEntityTypes(
				MyRequest.class,
				MyResponse.class
			)
			.setPort(1337)
			.setHostChannelAcceptor(channel ->
			{
				final MyRequest request = (MyRequest)channel.receive();
				channel.send(createResponse(request));
				channel.close();
			})
			.createHost()
		;
		host.run();
	}

	static MyResponse createResponse(final MyRequest request)
	{
		final double x = request.getX();
		final double y = request.getY();
		final MyResponse response = new MyResponse(
			Util.round(x * y),
			Util.round(x / y)
		);
		return response;
	}
	
}
