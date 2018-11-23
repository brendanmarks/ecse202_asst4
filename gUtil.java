/**
 * Some helper methods to translate simulation coordinates to screen
 * coordinates
 * @author ferrie
 *
 */
public class gUtil {
	private static final int WIDTH = 1200; // n.b. screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT/100; // Pixels/meter
	/**
	 * X coordinate to screen x
	 * @param X
	 * @return x screen coordinate - integer
	 */

	static int XtoScreen(double X) {
		return (int) (X * SCALE);
	}

	/**
	 * Y coordinate to screen y
	 * @param Y
	 * @return y screen coordinate - integer
	 */

	static int YtoScreen(double Y) {
		return (int) (HEIGHT - Y * SCALE);
	}

	/**
	 * Length to screen length
	 * @param length - double
	 * @return sLen - integer
	 */

	static int LtoScreen(double length) {
		return (int) (length * SCALE);
	}

	/**
	 * Delay for <int> milliseconds
	 * @param int time
	 * @return void
	 */
	static void delay (long time) {
		long start = System.currentTimeMillis();
		while (true) {
			long current = System.currentTimeMillis();
			long delta = current - start;
			if (delta >= time) break;
		}
	}
}