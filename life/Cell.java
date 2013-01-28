package life;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import life.Life.Colour;

/**
 * The class cell is a sub-class of JButton and is used to display individual
 * cells on the board for the game of life
 * 
 * @author varun
 * 
 */
public class Cell extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Colour colour;

	/**
	 * This constructor initialises each cell being dead, i.e. having grey
	 * colour
	 */
	public Cell()
	{
		colour = Colour.GREY;
		this.setBackground(Color.GRAY);
		this.addMouseListener(new CellMouseListener());
	}

	/**
	 * This method returns true if and only if the current cell is dead and
	 * false otherwise
	 * 
	 * @return
	 */
	public boolean isDead()
	{
		return colour == Colour.GREY;
	}

	/**
	 * This function is used to update the colour of the current cell
	 * 
	 * @param colour
	 *            : specifies the updated colour for the cell
	 */
	public void setColour(Colour colour)
	{
		this.colour = colour;
		super.setBackground(getColourMappingForCellBackground(colour));
	}

	/**
	 * This method is returns the Color for the background of the cell. This
	 * maps the given colour to the type which can be passed in as background
	 * colour field
	 * 
	 * @param colour
	 *            : specifies the colour
	 * @return : returns the type of colour which can be passed in as described
	 *         above
	 */
	private Color getColourMappingForCellBackground(Colour colour)
	{
		switch (colour)
		{
		case RED:
			return Color.RED;
		case GREEN:
			return Color.GREEN;
		case GREY:
			return Color.GRAY;
		}
		return null;
	}

	/**
	 * this method returns the colour of the current cell
	 * 
	 * @return : colour of the cell
	 */
	public Colour getColour()
	{
		return colour;
	}

	/**
	 * This inner class is used for the action listener for each of the cell.
	 * 
	 * @author varun
	 * 
	 */
	private class CellMouseListener extends MouseAdapter
	{
		/**
		 * This method updates the colour of the cell according to the following
		 * rules:
		 * RED iff left mouse button is clicked 
		 * GREEN iff right mouse button is clicked 
		 * GREY iff middle mouse button is clicked
		 */
		@Override
		public void mouseClicked(MouseEvent event)
		{
			final Cell cell = (Cell) event.getSource();
			if (SwingUtilities.isLeftMouseButton(event))
			{
				cell.setColour(Colour.RED);
			} else if (SwingUtilities.isRightMouseButton(event))
			{
				cell.setColour(Colour.GREEN);
			} else if (SwingUtilities.isMiddleMouseButton(event))
			{
				cell.setColour(Colour.GREY);
			}

		}
	}
}
