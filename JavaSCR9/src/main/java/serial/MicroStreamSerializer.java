package serial;

import static one.microstream.X.notNull;

import java.io.Closeable;

import one.microstream.X;
import one.microstream.collections.types.XGettingCollection;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.binary.types.BinaryPersistence;
import one.microstream.persistence.binary.types.BinaryPersistenceFoundation;
import one.microstream.persistence.exceptions.PersistenceExceptionTransfer;
import one.microstream.persistence.types.PersistenceContextDispatcher;
import one.microstream.persistence.types.PersistenceIdSet;
import one.microstream.persistence.types.PersistenceManager;
import one.microstream.persistence.types.PersistenceSource;
import one.microstream.persistence.types.PersistenceTarget;
import one.microstream.persistence.types.PersistenceTypeDictionaryManager;


public interface MicroStreamSerializer extends Closeable
{
	public Binary serialize(Object object);
	
	public Object deserialize(Binary data);
	
	@Override
	public void close();
	
	
	public static MicroStreamSerializer New()
	{
		return new MicroStreamSerializer.Default(BinaryPersistence.Foundation());
	}
	
	public static MicroStreamSerializer New(final BinaryPersistenceFoundation<?> foundation)
	{
		return new MicroStreamSerializer.Default(notNull(foundation));
	}
	
	
	public static class Default implements MicroStreamSerializer
	{
		private final BinaryPersistenceFoundation<?> foundation        ;
		private PersistenceManager<Binary>           persistenceManager;
		private Binary                               input             ;
		private Binary                               output            ;
		
		Default(final BinaryPersistenceFoundation<?> foundation)
		{
			super();
			this.foundation = foundation;
		}
		
		@Override
		public synchronized Binary serialize(final Object object)
		{
			this.lazyInit();
			this.persistenceManager.store(object);
			return this.output;
		}
		
		@Override
		public synchronized Object deserialize(final Binary data)
		{
			this.lazyInit();
			this.input = data;
			return this.persistenceManager.get();
		}
		
		@Override
		public synchronized void close()
		{
			if(this.persistenceManager != null)
			{
				this.persistenceManager.objectRegistry().truncateAll();
				this.persistenceManager.close();
				this.persistenceManager = null;
				this.input              = null;
				this.output             = null;
			}
		}
		
		private void lazyInit()
		{
			if(this.persistenceManager == null)
			{
				final PersistenceSourceBinary source = ()   -> X.Constant(this.input);
				final PersistenceTargetBinary target = data -> this.output = data;
				
				final BinaryPersistenceFoundation<?> foundation = this.foundation
					.setPersistenceSource(source)
					.setPersistenceTarget(target)
					.setContextDispatcher(PersistenceContextDispatcher.LocalObjectRegistration())
				;
				
				foundation.setTypeDictionaryManager(
					PersistenceTypeDictionaryManager.Transient(
						foundation.getTypeDictionaryCreator()
					)
				);
				
				foundation.getTypeHandlerManager().initialize();
				
				this.persistenceManager = foundation.createPersistenceManager();
			}
			else
			{
				this.persistenceManager.objectRegistry().clearAll();
			}
		}
		
		
		static interface PersistenceSourceBinary extends PersistenceSource<Binary>
		{
			@Override
			default XGettingCollection<? extends Binary> readByObjectIds(final PersistenceIdSet[] oids)
				throws PersistenceExceptionTransfer
			{
				return null;
			}
		}
		
		
		static interface PersistenceTargetBinary extends PersistenceTarget<Binary>
		{
			@Override
			default boolean isWritable()
			{
				return true;
			}
		}
		
	}
	
}

