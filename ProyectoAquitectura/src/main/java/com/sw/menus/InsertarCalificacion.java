package com.sw.menus;

import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author HikingC7
 */
public class InsertarCalificacion implements Drawable
{

    private String calif = "";
    private Rectangle asignarBoton;
    private boolean calificacionAsignada;

    public InsertarCalificacion()
    {
        asignarBoton = new Rectangle(45 + 710 / 3, 400, 710 / 3, 70);
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics2D g)
    {

        if (!isCalificacionAsignada())
        {
            dibujarBox(g);
            dibujarCalificacion(g);

        }

        dibujarTitulo(g);

    }

    private void dibujarTitulo(Graphics2D g)
    {

        g.setColor(Color.black);

        if (!isCalificacionAsignada())
        {
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("Calificación para este proyecto", Panel.ANCHO / 2 - "Calificación para este proyecto".length() / 2 * 22.1f, 100);

        } else
        {
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("¡Calificación asignada!", Panel.ANCHO / 2 - 285, 300);

        }

    }

    private void dibujarCalificacion(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawString(calif, Panel.ANCHO / 2 - calif.length() / 2 * 17.68f, 200);

    }

    private void dibujarBox(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawRect(asignarBoton.x, asignarBoton.y, asignarBoton.width, asignarBoton.height);
        g.drawString("Asignar", asignarBoton.x + 55, asignarBoton.y + 45);
        g.setStroke(new BasicStroke(3));
        g.setColor(new Color(0, 228, 255));
        g.drawRect(asignarBoton.x, asignarBoton.y, asignarBoton.width, asignarBoton.height);

    }

    public void escribirCalificacion(KeyEvent e)
    {

        if (calif.length() > 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            calif = calif.length() == 1 ? "" : calif.substring(0, calif.length() - 1);

        if (e.getKeyCode() >= 48 && e.getKeyCode() <= 57)
            if (calif.length() <= 6)
                calif += e.getKeyChar();

    }

    public void reiniciarCalif()
    {
        calif = "";
        setCalificacionAsignada(false);

    }

    public boolean isCalificacionAsignada()
    {
        return calificacionAsignada;
    }

    public void setCalificacionAsignada(boolean calificacionAsignada)
    {
        this.calificacionAsignada = calificacionAsignada;
    }

}
