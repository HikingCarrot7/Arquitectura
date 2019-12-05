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
 * @author Naomi García Sanchez.
 */
public class Calificacion implements Drawable
{

    private String calif = "";
    private Rectangle asignarCalif;
    private boolean calificacionAsignada;

    public Calificacion()
    {
        asignarCalif = new Rectangle(45 + 710 / 3, 400, 710 / 3, 70);
    }

    @Override
    public void tick()
    {

    }

    /**
     * Dibujamos todos los objetos que conforman esta interfaz.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
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

    /**
     * Dibujamos el título.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
    private void dibujarTitulo(Graphics2D g)
    {

        g.setColor(Color.black);

        if (!isCalificacionAsignada())
        {
            g.setFont(new Font("Peach Milk", Font.PLAIN, 63));
            g.drawString("Calificación para este proyecto", Panel.ANCHO / 2 - "Calificación para este proyecto".length() / 2 * 22.1f, 100);

        } else
        {
            g.setFont(new Font("Peach Milk", Font.BOLD, 85));
            g.drawString("¡Calificación asignada!", Panel.ANCHO / 2 - 370, 300);

        }

    }

    private void dibujarCalificacion(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", Font.PLAIN, 40));
        g.drawString(calif, Panel.ANCHO / 2 - calif.length() / 2 * 17.68f, 200);

    }

    /**
     * Dibujamos el rectángulo "Asignar".
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
    private void dibujarBox(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", Font.PLAIN, 55));
        g.drawRect(asignarCalif.x, asignarCalif.y, asignarCalif.width, asignarCalif.height);
        g.drawString("Asignar", asignarCalif.x + 50, asignarCalif.y + 55);
        g.setStroke(new BasicStroke(3));
        g.setColor(new Color(0, 228, 255));
        g.drawRect(asignarCalif.x, asignarCalif.y, asignarCalif.width, asignarCalif.height);

    }

    /**
     * Escribimos la calificación.
     *
     * @param e El objeto KeyEvent que se crea al presionar una tecla.
     */
    public void escribirCalificacion(KeyEvent e)
    {

        if (calif.length() > 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            calif = calif.length() == 1 ? "" : calif.substring(0, calif.length() - 1);

        if (e.getKeyCode() >= 48 && e.getKeyCode() <= 57)
            if (calif.length() <= 6)
                calif += e.getKeyChar();

    }

    /**
     * Revisamos que el valor no esté vacío.
     */
    public void checarCalificacion()
    {
        if (!calif.trim().equals(""))
            setCalificacionAsignada(true);

    }

    /**
     * Reiniciamos el valor que se había puesto por el usuario.
     */
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
