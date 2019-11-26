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
 * @author Mohammed
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

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 70));
        g.drawString("Bienvenido!", Panel.ANCHO / 2 - 180, 100);

        renderTempBox(g);

        renderCalifBox(g);

        renderSalirBox(g);

    }

    private void renderTempBox(Graphics2D g)
    {

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawRect(temperatura.x, temperatura.y, temperatura.width, temperatura.height);
        g.setStroke(new BasicStroke(3));
        g.setColor(isTempSelected() ? Color.red : Color.white);
        g.drawRect(temperatura.x, temperatura.y, temperatura.width, temperatura.height);

    }

    private void renderCalifBox(Graphics2D g)
    {

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawRect(calif.x, calif.y, calif.width, calif.height);
        g.setStroke(new BasicStroke(3));
        g.setColor(isCalifSelected() ? Color.red : Color.white);
        g.drawRect(calif.x, calif.y, calif.width, calif.height);

    }

    private void renderSalirBox(Graphics2D g)
    {

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawRect(salir.x, salir.y, salir.width, salir.height);
        g.setStroke(new BasicStroke(3));
        g.setColor(isSalirSelected() ? Color.red : Color.white);
        g.drawRect(salir.x, salir.y, salir.width, salir.height);

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
