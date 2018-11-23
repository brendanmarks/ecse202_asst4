/**
 * Implements a B-Tree class for storing gBall objects
 * @author ferrie
 *
 */
public class bTree {
	// Instance variables
	bNode root=null;
	/**
	 * addNode method - wrapper for rNode
	 */
	public void addNode(gBall data) {
		root=rNode(root,data);
	}
	/**
	 * rNode method - recursively adds a new entry into the B-Tree
	 */
	private bNode rNode(bNode root, gBall data) {
		if (root==null) {
			bNode node = new bNode();
			node.data = data;
			node.left = null;
			node.right = null;
			root=node;
			return root;
		}
		else if (data.bSize < root.data.bSize) {
			root.left = rNode(root.left,data);
		}
		else {
			root.right = rNode(root.right,data);
		}
		return root;
	}
	/**
	 * inorder method - inorder traversal via call to recursive method
	 */
	public void inorder() {
		traverse_inorder(root);
	}
	/**
	 * traverse_inorder method - recursively traverses tree in order and prints each node.
	 */
	private void traverse_inorder(bNode root) {
		if (root.left != null) traverse_inorder(root.left);
		System.out.println(root.data.bSize);
		if (root.right != null) traverse_inorder(root.right);
	}
	/**
	 * isRunning predicate - determines if simulation is still running
	 */
	boolean isRunning() {
		running=false;
		recScan(root);
		return running;
	}
	void recScan(bNode root) {
		if (root.left != null) recScan(root.left);
		if (root.data.bState) running=true;
		if (root.right != null) recScan(root.right);
	}
	/**
	 * clearBalls - removes all balls from display
	 * (note - you need to pass a reference to the display)
	 *
	 */
	void clearBalls(bSim display) {
		recClear(display,root);
	}
	void recClear(bSim display,bNode root) {
		if (root.left != null) recClear(display,root.left);
		display.remove (root.data.myBall);
		root.data.bState = false;
		root.data.bSize = 0;
		if (root.right != null) recClear(display,root.right);
	}
	
	void erase() {
		treeErase(root);
	}
	void treeErase(bNode root) {
		if (root.left != null) treeErase(root.left);
		root.data.bSize = 0;
		if (root.right != null) treeErase(root.right);
	}
	/**
	 * setRunning - restarts simulation when JComboBoxed changed to run
	 */
	void setRunning() {
		ballRun(root);
	}
	void ballRun(bNode root){
		if (root.left != null) ballRun(root.left);
		root.data.bState = true;
		
		if(root.right != null) ballRun(root.right);
	}
	/**
	 * 
	 * stopBalls - stops the current balls running
	 * 
	 */
	/*void stopBalls() {
		stop(root);
	}
	void stop(bNode root) {
		if (root.left != null) stop(root.left);
		root.data.bState = false;
		if (root.right != null) stop(root.right);
	}*/
	/**
	 * drawSort - sorts balls by size and plots from left to right on display
	 *
	 */
	void moveSort() {
		nextX=0;
		recMove(root);
	}
	void recMove(bNode root) {
		if (root.left != null) recMove(root.left);
		//
		// Plot ball along baseline
		//
		double X = nextX;
		double Y = root.data.bSize*2;
		nextX = X + root.data.bSize*2 + SEP;
		root.data.moveTo(X, Y);;
		if (root.right != null) recMove(root.right);
	}
	// Example of a nested class //
	public class bNode {
		gBall data;
		bNode left;
		bNode right;
	}
	// Instance variables
	boolean running;
	double nextX;
	// Parameters
	private static final double SEP = 0;
}