package nova.core.util.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * An interface applied to objects that can act as vector transformers.
 * @author Calclavia
 */
@FunctionalInterface
public interface Transformer {
	/**
	 * Called to transform a vector.
	 * @param vec - The vector being transformed
	 * @return The transformed vector.
	 */
	Vector3D apply(Vector3D vec);
}
