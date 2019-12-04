package com.sw.menus;

import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author TODOS
 */
public class MainMenu implements Drawable
{

    private static final int WIDTH = 710 / 3;

    private Rectangle temperatura;
    private Rectangle calif;
    private Rectangle salir;

    private boolean tempSelected = true;
    private boolean califSelected;
    private boolean salirSelected;

    public MainMenu()
    {

        temperatura = new Rectangle(15, 400, WIDTH, 70);
        calif = new Rectangle(45 + WIDTH, 400, WIDTH, 70);
        salir = new Rectangle(75 + 2 * WIDTH, 400, WIDTH, 70);

    }

    @Override
    public void tick()
    {

        if (isTempSelected())
            hacerAnimacionCrecimiento(temperatura, 15);

        else
            hacerAnimacionDecrecimiento(temperatura, 15);

        if (isCalifSelected())
            hacerAnimacionCrecimiento(calif, 45 + WIDTH);

        else
            hacerAnimacionDecrecimiento(calif, 45 + WIDTH);

        if (isSalirSelected())
            hacerAnimacionCrecimiento(salir, 75 + 2 * WIDTH);

        else
            hacerAnimacionDecrecimiento(salir, 75 + 2 * WIDTH);

    }

    @Override
    public void render(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", 0, 120));
        g.drawString("¡Bienvenido!", Panel.ANCHO / 2 - 230, 150);

        renderBox(g, temperatura, isTempSelected(), "Gráfica de temperatura", 30);

        renderBox(g, calif, isCalifSelected(), "Asignar calificación", 35);

        renderBox(g, salir, isSalirSelected(), "Ver nuestra calificación", 20);

    }

    private void renderBox(Graphics2D g, Rectangle rect, boolean isSelected, String text, int offSet)
    {

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", Font.PLAIN, 25));
        g.drawString(text, rect.x + offSet, rect.y + 40);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setStroke(new BasicStroke(3));
        g.setColor(isSelected ? new Color(0, 228, 255) : Color.black);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);

    }

    private void hacerAnimacionCrecimiento(Rectangle rect, int originalX)
    {

        if (rect.x >= originalX - 10)
            rect.x--;

        if (rect.width <= WIDTH + 20)
            rect.width += 2;

    }

    private void hacerAnimacionDecrecimiento(Rectangle rect, int originalX)
    {

        if (rect.x <= originalX)
            rect.x++;

        if (rect.width >= WIDTH)
            rect.width -= 2;

    }

    public boolean isTempSelected()
    {
        return tempSelected;
    }

    public void setTempSelected(boolean tempSelected)
    {
        this.tempSelected = tempSelected;
    }

    public boolean isCalifSelected()
    {
        return califSelected;
    }

    public void setCalifSelected(boolean califSelected)
    {
        this.califSelected = califSelected;
    }

    public boolean isSalirSelected()
    {
        return salirSelected;
    }

    public void setSalirSelected(boolean salirSelected)
    {
        this.salirSelected = salirSelected;
    }

}
