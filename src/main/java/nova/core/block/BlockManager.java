package nova.core.block;

import nova.core.event.CancelableEvent;
import nova.core.event.CancelableEventBus;
import nova.core.event.EventBus;
import nova.core.util.Factory;
import nova.core.item.ItemManager;
import nova.core.util.Manager;
import nova.core.util.RegistrationException;
import nova.core.util.Registry;
import nova.internal.core.Game;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockManager extends Manager<Block, Factory<Block>> {

	public final EventBus<BlockRegisteredEvent> blockRegisteredListeners = new CancelableEventBus<>();
	private final Supplier<ItemManager> itemManager;

	private BlockManager(Registry<Factory<Block>> registry, Supplier<ItemManager> itemManager) {
		super(registry);
		this.itemManager = itemManager;
	}

	/**
	 * Gets the block registered that represents air.
	 * @return
	 */
	public Block getAirBlock() {
		return Game.blocks().get("air").get();
	}

	public Factory<Block> getAirBlockFactory() {
		return Game.blocks().getFactory("air").get();
	}

	/**
	 * Register a new block with custom constructor arguments.
	 * @param constructor Block instance {@link Supplier}
	 * @return Dummy block
	 */
	@Override
	public Factory<Block> register(Function<Object[], Block> constructor) {
		return register(new BlockFactory(constructor));
	}

	/**
	 * Register a new block with custom constructor arguments.
	 * @param factory {@link Factory} of registered block
	 * @return Dummy block
	 */
	@Override
	public Factory<Block> register(Factory<Block> factory) throws RegistrationException{
		if(!factory.name().isPresent())
			throw new RegistrationException(String.format("Factory passed for registration is not named. [%s]", factory));


		BlockRegisteredEvent event = new BlockRegisteredEvent(factory);
		blockRegisteredListeners.publish(event);
		registry.register(event.blockFactory);
		event.blockFactory.getDummy().onRegister();
		return event.blockFactory;
	}

	@CancelableEvent.Cancelable
	public static class BlockRegisteredEvent extends CancelableEvent {
		public BlockFactory blockFactory;

		public BlockRegisteredEvent(BlockFactory blockFactory) {
			this.blockFactory = blockFactory;
		}
	}

	public <T> Factory<T> register(Class<T> blockClass) {

	}
}
