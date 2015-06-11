package nova.core.util;

import com.google.common.collect.ImmutableList;
import nova.internal.core.Game;
import se.jbee.inject.Dependency;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public final class Factory<T extends Buildable> implements Identifiable{

	private final Class<T> clazz;
	private final ImmutableList<Consumer<T>> finalizers;
    private final Optional<String> name;

	private Factory(Class<T> clazz) {
		this.clazz = clazz;
		this.finalizers = ImmutableList.<Consumer<T>>builder().build();
        this.name = Optional.empty();
    }

	private Factory(Class<T> clazz, ImmutableList<Consumer<T>> finalizers, Optional<String> name) {
		this.clazz = clazz;
		this.finalizers = finalizers;
        this.name = name;
	}

	private Factory(Factory<T> factory) {
		this(factory.clazz, factory.finalizers, factory.name);
	}

	public static <T extends Buildable> Factory of(Class<T> clazz) {
		return new Factory<>(clazz);
	}

	public static <T extends Buildable> Factory of(T obj) {
		return obj.factory();
	}

	public Factory<T> arguments(Object... args) {
		Object argsCopy = Arrays.copyOf(args, args.length);
		return this.withFinalizer((buildable) -> buildable.arguments(argsCopy));
	}

	public Factory<T> withFinalizer(Consumer<T> finalizer) {
		return new Factory<>(this.clazz, ImmutableList.<Consumer<T>>builder().addAll(this.finalizers).add(finalizer).build(), name);
	}

    public Factory<T> name(String name) {
        return new Factory<>(this.clazz, this.finalizers, Optional.of(name));
    }

    public Optional<String> name() {
        return name;
    }

	public T resolve() {
		T obj = Game.resolve(Dependency.dependency(clazz));
		obj.afterConstruction();
		finalizers.stream().forEach((cons) -> cons.accept(obj));
		obj.afterFinalizers();
		return obj;
	}

	@Override
    public Factory<T> clone() {
		return new Factory<>(this);
	}

    @Override
    public String getID() {
        return name.map((ID) -> clazz.getCanonicalName() + ":" + ID).orElseGet(clazz::getCanonicalName);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof Factory && sameType((Factory) obj);
    }

    @Override
    public int hashCode() {
        return getID().hashCode();
    }
}
