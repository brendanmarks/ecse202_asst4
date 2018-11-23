import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram implements ChangeListener, ItemListener{




	// Parameters used in this program
	private static final int WIDTH = 1200; // n.b. screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int NUMBALLS = 15; // # balls to simulate
	private static final double MINSIZE = 1; // Minumum ball size
	private static final double MAXSIZE = 8; // Maximum ball size
	private static final double XMIN = 10; // Min X starting location
	private static final double XMAX = 50; // Max X starting location
	private static final double YMIN = 50; // Min Y starting location
	private static final double YMAX = 100; // Max Y starting location
	private static final double EMIN = 0.4; // Minimum loss coefficient
	private static final double EMAX = 0.9; // Maximum loss coefficient
	private static final double VMIN = 0.5; // Minimum X velocity
	private static final double VMAX = 3.0; // Maximum Y velocity



	private bTree myTree;
	private bTree newTree;
	public static void main(String[] args) { // Standalone Applet
		new bSim().start(args);
	}
	int PS_NumBalls = NUMBALLS; // setting default values for "run" 
	int minSizeVal = (int) MINSIZE;
	int xMinVal = (int) XMIN;
	int xMaxVal = (int) XMAX;
	int yMaxVal = (int) YMAX;
	int yMinVal = (int) YMIN;
	int lossMinVal = (int) (EMIN * 100);
	int lossMaxVal = (int) (EMAX * 100);
	int maxSizeVal = (int) MAXSIZE;
	int vMinVal = (int) VMIN;
	int vMaxVal = (int) VMAX;
	
	

	public void stateChanged(ChangeEvent e) {
		PS_NumBalls = numballsSlider.getISlider();
		minSizeVal = minSizeSlider.getISlider();
		xMinVal = xMinSlider.getISlider();
		xMaxVal = xMaxSlider.getISlider();
		yMaxVal = yMinSlider.getISlider();
		yMinVal = yMaxSlider.getISlider();
		lossMinVal = lossMinSlider.getISlider();
		lossMaxVal = lossMaxSlider.getISlider();
		maxSizeVal = maxSizeSlider.getISlider();
		vMinVal = vMinSlider.getISlider();
		vMaxVal = vMaxSlider.getISlider();
		
		JSlider source = (JSlider)e.getSource();
		if (source==numballsSlider.mySlider) {
			PS_NumBalls=numballsSlider.getISlider();
			numballsSlider.setISlider(PS_NumBalls);
		}
		else if( source== minSizeSlider.mySlider)
		{
			minSizeVal = minSizeSlider.getISlider();
			minSizeSlider.setISliderDoub(minSizeVal);
		}
		else if( source== maxSizeSlider.mySlider)
		{
			maxSizeVal = maxSizeSlider.getISlider();
			maxSizeSlider.setISliderDoub(maxSizeVal);
		}
		else if( source== xMinSlider.mySlider)
		{
			xMinVal = xMinSlider.getISlider();
			xMinSlider.setISliderDoub(xMinVal);
		}
		else if( source== xMaxSlider.mySlider)
		{
			xMaxVal = xMaxSlider.getISlider();
			xMaxSlider.setISliderDoub(xMaxVal);
		}
		else if( source== yMinSlider.mySlider)
		{
			yMinVal = yMinSlider.getISlider();
			yMinSlider.setISliderDoub(yMinVal);
		}
		else if( source== yMaxSlider.mySlider)
		{
			yMaxVal = yMaxSlider.getISlider();
			yMaxSlider.setISliderDoub(yMaxVal);
		}
		else if (source == lossMinSlider.mySlider) {
			lossMinVal = lossMinSlider.getISlider();
			lossMinSlider.setISliderDoub(lossMinVal);
		}
		else if (source == lossMaxSlider.mySlider) {
			lossMaxVal = lossMaxSlider.getISlider();
			lossMaxSlider.setISliderDoub(lossMaxVal);
		}
		else if (source == vMinSlider.mySlider) {
			vMinVal = vMinSlider.getISlider();
			vMinSlider.setISliderDoub(vMinVal);
		}
		else if (source == vMaxSlider.mySlider) {
			vMaxVal = vMaxSlider.getISlider();
			vMaxSlider.setISliderDoub(vMaxVal);
		}
		
	}
	sliderBox numballsSlider;
	sliderBox minSizeSlider;
	sliderBox xMinSlider;
	sliderBox xMaxSlider;
	sliderBox yMinSlider;
	sliderBox yMaxSlider;
	sliderBox lossMinSlider;
	sliderBox lossMaxSlider;
	sliderBox maxSizeSlider;
	sliderBox vMinSlider;
	sliderBox vMaxSlider;
	
	JComboBox<String> bSimC;
	
	void setChoosers() {
		bSimC = new JComboBox<String>();
		bSimC.addItem("bSim");
		bSimC.addItem("Run");
		bSimC.addItem("Clear");
		bSimC.addItem("Stop");
		bSimC.addItem("Quit");
		add(bSimC,NORTH);
		addJComboListeners();

	}
	void addJComboListeners() {
		bSimC.addItemListener((ItemListener)this);
	}
	public void run() {


		JPanel eastPannel = new JPanel();

		JLabel gen_sim = new JLabel("General Simulation Parameters");
		eastPannel.add(gen_sim);

		// change 10 -> 17 
		eastPannel.setLayout(new GridLayout(20,1));
		numballsSlider = new sliderBox("NUMBALLS", 1.0, NUMBALLS, 25.0);
		eastPannel.add(numballsSlider.myPanel,"EAST");
		numballsSlider.mySlider.addChangeListener((ChangeListener) this);

		minSizeSlider = new sliderBox("MIN SIZE", 1.0, MINSIZE, 25.0);
		eastPannel.add(minSizeSlider.myPanel,"EAST");
		minSizeSlider.mySlider.addChangeListener((ChangeListener) this);
		
		maxSizeSlider = new sliderBox("MAX SIZE", 1.0, MAXSIZE, 25.0);
		eastPannel.add(maxSizeSlider.myPanel,"EAST");
		maxSizeSlider.mySlider.addChangeListener((ChangeListener) this);
		
		xMinSlider = new sliderBox("MIN X", 1.0, XMIN, 200.0);
		eastPannel.add(xMinSlider.myPanel,"EAST");
		xMinSlider.mySlider.addChangeListener((ChangeListener) this);
		
		xMaxSlider = new sliderBox("MAX X", 1.0, XMAX, 200.0);
		eastPannel.add(xMaxSlider.myPanel,"EAST");
		xMaxSlider.mySlider.addChangeListener((ChangeListener) this);
		
		yMinSlider = new sliderBox("MIN Y", 1.0, YMIN, 100.0);
		eastPannel.add(yMinSlider.myPanel,"EAST");
		yMinSlider.mySlider.addChangeListener((ChangeListener) this);
		
		yMaxSlider = new sliderBox("MAX Y", 1.0, YMAX, 100.0);
		eastPannel.add(yMaxSlider.myPanel,"EAST");
		yMaxSlider.mySlider.addChangeListener((ChangeListener) this);
		
		lossMinSlider = new sliderBox("MIN % LOSS", 0.0, EMIN * 100, 100.0);
		eastPannel.add(lossMinSlider.myPanel, "EAST");
		lossMinSlider.mySlider.addChangeListener((ChangeListener) this);
		
		lossMaxSlider = new sliderBox("MAX % LOSS", 0.0, EMAX * 100, 100.0);
		eastPannel.add(lossMaxSlider.myPanel, "EAST");
		lossMaxSlider.mySlider.addChangeListener((ChangeListener) this);
		
		vMinSlider = new sliderBox("MIN V", 0.0, VMIN, 10.0);
		eastPannel.add(vMinSlider.myPanel, "EAST");
		vMinSlider.mySlider.addChangeListener((ChangeListener) this);
		
		vMaxSlider = new sliderBox("MAX V", 0.0, VMAX, 10.0);
		eastPannel.add(vMaxSlider.myPanel, "EAST");
		vMaxSlider.mySlider.addChangeListener((ChangeListener) this);
		
		add(eastPannel);


		setChoosers();
		
		this.resize(WIDTH,HEIGHT+OFFSET); // optional, initialize window size
		// Create the ground plane
		GRect gPlane = new GRect(0,HEIGHT,WIDTH,3);
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		add(gPlane);
		// Set up random number generator & B-Tree
		RandomGenerator rgen = RandomGenerator.getInstance();
		myTree = new bTree();
		
		// Generate a series of random gballs and let the simulation run till completion
		for (int i=0; i<NUMBALLS; i++) {
			double Xi = rgen.nextDouble(XMIN,XMAX); // Current Xi
			double Yi = rgen.nextDouble(YMIN,YMAX); // Current Yi
			double iSize = rgen.nextDouble(MINSIZE,MAXSIZE); // Current size
			Color iColor = rgen.nextColor(); // Current color
			double iLoss = rgen.nextDouble(EMIN,EMAX); // Current loss coefficient
			double iVel = rgen.nextDouble(VMIN,VMAX); // Current X velocity
			gBall iBall = new gBall(Xi,Yi,iSize,iColor,iLoss,iVel); // Generate instance
			add(iBall.myBall); // Add to display list
			myTree.addNode(iBall); // Save instance
			iBall.start(); // Start this instance
		}
		
		// Wait until simulation stops
		while(true) {
		pause(500);
		//while(running == false);
		while (myTree.isRunning()); // Block until simulation terminates
		//String CR = readLine("CR to continue");
		//For standalone application with no console, use graphics display
		GLabel myLabel = new GLabel("Click mouse to continue");
		myLabel.setLocation(WIDTH-myLabel.getWidth()-50,HEIGHT-myLabel.getHeight());
		myLabel.setColor(Color.RED);
		add(myLabel);
		waitForClick();
		myLabel.setLabel("All Sorted!");
		//myTree.clearBalls(this);
		//myTree.inorder(); // Echo sizes on console
		myTree.moveSort(); // Lay out balls from left to right in size order
		while(!(bSimC.getSelectedIndex() == 1));
		pause(4000);
		}
		
		
		
		
		
	}
	//public boolean running = false;
	@Override
	public void itemStateChanged(ItemEvent e) {
		RandomGenerator rgen = RandomGenerator.getInstance();
		//boolean newT = false;
		//System.out.println("itemStateChanged is called");
		// TODO Auto-generated method stub
		JComboBox source = (JComboBox)e.getSource();
		if (source==bSimC) {
			if (bSimC.getSelectedIndex()== 1) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
				//newTree = new bTree();
				System.out.println(PS_NumBalls);
				//running = false;
				myTree.clearBalls(this); //clearing display of old balls
				//myTree.erase(); //"erasing" old balls so new balls can be sorted properly (making old balls have size 0) 
				for (int i=0; i< (PS_NumBalls); i++) { 
					System.out.println("Starting simulation");
					//System.out.println(lossMinVal);
					double Xi = rgen.nextDouble(xMinVal,xMaxVal); // Current Xi
					double Yi = rgen.nextDouble(yMinVal,yMaxVal); // Current Yi
					double iSize = rgen.nextDouble(minSizeVal,maxSizeVal); // Current size
					Color iColor = rgen.nextColor(); // Current color
					double iLoss = rgen.nextDouble((double)lossMinVal/100,(double)lossMaxVal/100); // Current loss coefficient
					double iVel = rgen.nextDouble(vMinVal,vMaxVal); // Current X velocity
					gBall iBall = new gBall(Xi,Yi,iSize,iColor,iLoss,iVel); // Generate instance
					add(iBall.myBall); // Add to display list
					myTree.addNode(iBall); // Save instance
					iBall.start(); // Start this instance
					
				}
				//running = true;
				
				
				}
				
				
			}
			if (bSimC.getSelectedIndex() == 2) {
				//System.out.println(newT);
					myTree.clearBalls(this);
					//newTree.clearBalls(this);
			}
			/*if (bSim.getSelectedIndex() == 3) {
				myTree.stopBalls();
			}
			*/
			if (bSimC.getSelectedIndex() == 4) {
				System.exit(0);
			}

		}
	}
}