package life;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Life extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_COLUMN_SIZE = 30;
	public static final int DEFAULT_ROW_SIZE = 30;
	private static final int MAX_SPEED = 10;
	private static final int MIN_SPEED = 0;
	private static final int DEFAULT_SPEED = 0;
	JPanel grid; 
	
	public Life()
	{
		grid = new Grid();
	}

	// enum for managing the basic three colours of the cells
	// RED and GREEN cells are alive, GREY cells are dead
	public enum Colour {
		RED, GREEN, GREY;
	}

	// called by the html page to draw the applet
	public void paint(Graphics g) {
		// draw the applet
		grid.setLayout(new GridLayout(DEFAULT_ROW_SIZE, DEFAULT_COLUMN_SIZE));
		((Grid) grid).addCellsToPanel();
		
		
		JSlider speed_slider = new JSlider(JSlider.VERTICAL,
               MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
		speed_slider.setMajorTickSpacing(2);
		speed_slider.setMinorTickSpacing(1);
		speed_slider.setPaintTicks(true);
		speed_slider.setPaintLabels(true);
		
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setTitle("Game of Life");
		
		frame.getContentPane().add(grid, BorderLayout.CENTER);
		frame.getContentPane().add(speed_slider, BorderLayout.EAST);
		
		JPanel header = new JPanel();
		JLabel turns = new JLabel("Turns: 0");
		header.add(turns,BorderLayout.CENTER);
		frame.getContentPane().add(header, BorderLayout.NORTH);
		
		JPanel buttons_footer = new JPanel();
		JButton clear = new JButton("Clear");
		JButton step = new JButton("Step");
		JButton run = new JButton("Run");
		buttons_footer.add(clear, BorderLayout.WEST);
		buttons_footer.add(step, BorderLayout.CENTER);
		buttons_footer.add(run, BorderLayout.EAST);
		frame.getContentPane().add(buttons_footer, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		
		//g.drawString("ToDo: write a Life applet and a nice webpage", 25, 50);
	}

	/*
	 * The autotest will assume the following interfaces on the class life.Life
	 * You are free to implement these methods in any way you wish Note: we are
	 * assuming a standard coordinate system, that is with (0,0) referring to
	 * the bottom left cell, x being col no. and y being row no.
	 */

	// read the colour of the cell at coord (x,y)
	public Colour readCell(int x, int y) {
		return null;
	}

	// read the turn count
	public int readTurn() {
		return 0;
	}

	// kill the cell at coord (x,y)
	public void kill(int x, int y) {

	}

	// create a live cell with the specified colour at coord (x,y)
	public void resurrect(int x, int y, Colour c) {

	}

	// clear the board and reset the turn count to 0
	public void clear() {

	}

	// step the simulation through one application of the rules and increment
	// the turn counter
	public void step() {

	}
	
	
}