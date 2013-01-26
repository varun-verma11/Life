package life;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import life.Life.Colour;

public class Cell extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Colour colour;

	public Cell()
	{
		colour = Colour.GREY;
		this.setBackground(Color.GRAY);
		this.addMouseListener(new CellMouseListener());
	}

	public boolean isDead()
	{
		return colour == Colour.GREY;
	}

	public void setColour(Colour colour)
	{
		this.colour = colour;
		super.setBackground(getColour(colour));
	}

	private Color getColour(Colour colour)
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

	public Colour getColour()
	{
		return colour;
	}

	private class CellMouseListener extends MouseAdapter
	{
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
