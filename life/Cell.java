package life;

import java.awt.Color;

import javax.swing.JButton;

import life.Life.Colour;

public class Cell extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Colour colour;

	public Cell()
	{
		colour = Colour.GREY;
		this.setBackground(Color.GRAY);
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
}
