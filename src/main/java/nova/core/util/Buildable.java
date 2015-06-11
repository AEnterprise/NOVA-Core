package nova.core.util;


public interface Buildable<T extends Buildable<T>> extends Identifiable{
    Factory<T> factory();
    default void afterConstruction() {}
    default void arguments(Object ... args) {}
	default void afterFinalizers() {}
}
