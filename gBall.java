import java.awt.Color;
import acm.graphics.GOval;
/**
 * This class provides a single instance of a ball falling under the influence
 * of gravity. Because it is an extension of the Thread class, each instance
 * will run concurrently, with animations on the screen as a side effect. We take
 * advantage here of the fact that the run method associated with the Graphics
 * Program class runs in a separate thread.
 *
 * @author ferrie
 *
 * Updates: added new methods to support Assigment 3
 *
 * void setBState(boolean state) // Enables (true) or disables (false) simulation
 * void moveTo(double x, double y) // Forces ball to new (x,y) location where
 * // x and y are in simulation coordinates
 */
public class gBall extends Thread {
	/**
	 * The constructor specifies the parameters for simulation. They are
	 *
	 * @param Xi double The initial X position of the center of the ball
	 * @param Yi double The initial Y position of the center of the ball
	 * @param bSize double The radius of the ball in simulation units
	 * @param bColor Color The initial color of the ball
	 * @param bLoss double Fraction [0,1] of the energy lost on each bounce
	 * @param bVel double X velocity of ball
	 */
	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel) {

		this.Xi = Xi; // Get simulation parameters
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;

		// Create instance of ball using specified parameters
		// Remember to offset X and Y by the radius to locate the
		// bounding box
		myBall = new GOval(gUtil.XtoScreen(Xi-bSize),gUtil.YtoScreen(Yi+bSize),gUtil.LtoScreen(2*bSize),gUtil.LtoScreen(2*bSize));
		myBall.setFilled(true);
		myBall.setFillColor(bColor);
		bState=true; // Simulation on by default

	}

	/**
	 * The run method implements the simulation from Assignment 1. Once the start
	 * method is called on the gBall instance, the code in the run method is
	 * executed concurrently with the main program.
	 * @param void
	 * @return void
	 */

	public void run() {
		// Run the same simulation code as for Assignment 1

		double time = 0; // Simulation clock
		double total_time = 0; // Tracks time from beginning
		double vt = Math.sqrt(2*G*Yi); // Terminal velocity
		double height = Yi; // Initial height of drop
		int dir = 0; // 0 down, 1 up
		double last_top = Yi; // Height of last drop
		double el = Math.sqrt(1.0-bLoss); // Energy loss scale factor for velocity
		// This while loop computes height:
		// dir=0: falling under gravity --> height = h0 - 0.5*g*t^2
		//dir=1: vertical projectile motion --> height = vt*t - 0.5*g*t^2

		while (bState) { // Simulation can be halted
			if (dir == 0) {
				height = last_top - 0.5*G*time*time;
				if (height <= bSize) {
					dir=1;
					last_top = bSize;
					time=0;
					vt = vt*el;
				}
			}
			else {
				height = bSize + vt*time -0.5*G*time*time;
				if (height < last_top) {
					if (height <= bSize) break; // Stop simulation when top of
					dir=0; // last excursion is ball diameter.
					time=0;
				}
				last_top = height;
			}

			// Determine the current position of the ball in simulation coordinates
			// and update the position on the screen

			Yt = Math.max(bSize,height); // Stay above ground!
			Xt = Xi + bVel*total_time;
			myBall.setLocation(gUtil.XtoScreen(Xt-bSize),gUtil.YtoScreen(Yt+bSize));

			// Delay and update clocks

			try {
				Thread.sleep((long) (TICK*500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time+=TICK;
			total_time+=TICK;
		}
		bState=false; // Can determine when simulation finished
	}
	//Class Methods
	/**
	 * Enable/Disable simulation asynchronously
	 * @param boolean state
	 * @return void
	 */
	void setBState(boolean state) {
		bState=state;
	}
	/**
	 * Move the ball to specified simulation coordinates.
	 * Update the position of the ball on the screen to match.
	 * @param double x
	 * @param double y
	 * @return void
	 */
	void moveTo(double x, double y) {
		Xt=x;
		Yt=y;
		myBall.setLocation(gUtil.XtoScreen(Xt),gUtil.YtoScreen(Yt));
	}
	/**
	 * Instance Variables & Class Parameters
	 */
	public GOval myBall;
	private double Xi;
	private double Yi;
	public double bSize;
	private Color bColor;
	private double bLoss;
	private double bVel;
	public boolean bState;
	private double Xt;
	private double Yt;
	public static final double G=9.8; // Gravitational acceleration
	private static final double TICK = 0.1; // Clock tick duration
}