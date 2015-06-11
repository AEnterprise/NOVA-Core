package nova.core.util;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Calclavia
 */
public abstract class Manager<T extends Buildable, F extends Factory<T>> {
	public final Registry<F> registry;

	public Manager(Registry<F> registry) {
		this.registry = registry;
	}

	public F register(Class<? extends T> registerType) {
		return register((args) -> ReflectionUtil.newInstance(registerType, args));
	}

	public abstract F register(Function<Object[], T> constructor);

	public F register(F factory) {
		registry.register(factory);
		return factory;
	}

	public Optional<T> get(String name) {
		Optional<F> factory = getFactory(name);
		return factory.map(Factory::resolve);
	}

	public Optional<F> getFactory(String name) {
		return registry.get(name);
	}
}
