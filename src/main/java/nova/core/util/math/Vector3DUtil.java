package nova.core.util.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;

import java.util.Random;

/**
 * An extension of Apache Common's Vector3D class
 * @author Calclavia
 */
public class Vector3DUtil {

	public static final Vector3D ONE = new Vector3D(1, 1, 1);
	public static final Vector3D CENTER = new Vector3D(0.5, 0.5, 0.5);
	public static final Vector3D FORWARD = Vector3D.MINUS_K;

	/**
	 * @return Creates a random unit vector
	 */
	public static Vector3D random() {
		Random random = new Random();
		return new Vector3D(random.nextDouble(), random.nextDouble(), random.nextDouble()).scalarMultiply(2).subtract(ONE);
	}

	public static Vector3D max(Vector3D a, Vector3D b) {
		return new Vector3D(FastMath.max(a.getX(), b.getX()), FastMath.max(a.getY(), b.getY()), FastMath.max(a.getZ(), b.getZ()));
	}

	public static Vector3D min(Vector3D a, Vector3D b) {
		return new Vector3D(FastMath.min(a.getX(), b.getX()), FastMath.min(a.getY(), b.getY()), FastMath.min(a.getZ(), b.getZ()));
	}

	public static Vector3D cartesianProduct(Vector3D a, Vector3D b) {
		return new Vector3D(a.getX() * b.getX(), a.getY() * b.getY(), a.getZ() * b.getZ());
	}

	public static Vector3D xCross(Vector3D vec) {
		return new Vector3D(0, vec.getZ(), -vec.getY());
	}

	public static Vector3D zCross(Vector3D vec) {
		return new Vector3D(-vec.getY(), vec.getX(), 0);
	}

	public static Vector3D midpoint(Vector3D a, Vector3D b) {
		return a.add(b).scalarMultiply(0.5);
	}

	public static Vector3D reciprocal(Vector3D vec) {
		return new Vector3D(1 / vec.getX(), 1 / vec.getY(), 1 / vec.getZ());
	}

	public static Vector3D perpendicular(Vector3D vec) {
		if (vec.getZ() == 0) {
			return zCross(vec);
		}

		return xCross(vec);
	}

	public static Vector3D round(Vector3D vec) {
		return new Vector3D(FastMath.round(vec.getX()), FastMath.round(vec.getY()), FastMath.round(vec.getZ()));
	}

	public static Vector3D ceil(Vector3D vec) {
		return new Vector3D(FastMath.ceil(vec.getX()), FastMath.ceil(vec.getY()), FastMath.ceil(vec.getZ()));
	}

	public static Vector3D floor(Vector3D vec) {
		return new Vector3D(FastMath.floor(vec.getX()), FastMath.floor(vec.getY()), FastMath.floor(vec.getZ()));
	}

	public static Vector3D abs(Vector3D vec) {
		return new Vector3D(FastMath.abs(vec.getX()), FastMath.abs(vec.getY()), FastMath.abs(vec.getZ()));
	}
}
