package com.sw.menus;

import com.sw.graphics.Animation;
import com.sw.graphics.Camera;
import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import com.sw.utilities.Linea;
import com.sw.utilities.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Eusebio Ajax Santos.
 */
public class GraficaTemperatura implements Drawable
{

    private ArrayList<Linea> lineas;
    private ArrayList<Animation> animaciones;
    private Camera camera;
    private Point lastPoint;
    private double data;
    private boolean dataAvailable;

    public GraficaTemperatura(Camera camera)
    {

        this.camera = camera;
        lastPoint = new Point(60, Panel.ALTO);
        lineas = new ArrayList<>();
        animaciones = new ArrayList<>();

    }

    /**
     * Actualizamos todos los objetos que conforman esta interfaz.
     *
     */
    @Override
    public void tick()
    {

        for (int i = 0; i < animaciones.size(); i++)
            animaciones.get(i).tick();

    }

    /**
     * Dibujamos todos los objetos que conforman esta interfaz.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     *
     */
    @Override
    public void render(Graphics2D g)
    {

        camera.setCameraStopped(false);

        dibujarRegla(g);

        dibujarLineas(g);

        if (isDataAvailable())
            anadirLinea();

        for (int i = 0; i < animaciones.size(); i++)
            animaciones.get(i).render(g);

    }

    /**
     * Dibujamos la regla de guía para la temperatura.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
    private void dibujarRegla(Graphics2D g)
    {

        int y = Panel.ALTO;
        int aux = 0;

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", Font.PLAIN, 20));
        g.drawLine(camera.getX(), Panel.ALTO - 1, camera.getX() + 30, Panel.ALTO - 1);

        for (int i = 0; i < Panel.ALTO; i += 15, y -= 15)
        {

            g.drawLine(camera.getX(), y, camera.getX() + (y % 150 != 0 ? 15 : 30), y);

            if (y % 150 == 0)
                g.drawString(++aux * 150 / 15 + "", camera.getX() + 35, y);

        }

        g.drawLine(camera.getX(), 0, camera.getX() + 30, 0);
        g.drawString("50", camera.getX() + 30, 15);

    }

    /**
     * Dibujamos las líneas en la gráfica.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
    private void dibujarLineas(Graphics2D g)
    {

        g.setStroke(new BasicStroke(3));

        try
        {

            for (int i = 0; i < lineas.size(); i++)
            {

                g.setColor(new Color(0, 228, 255));
                g.drawLine(lineas.get(i).getP1().getIntegerX(), lineas.get(i).getP1().getIntegerY(), lineas.get(i).getP2().getIntegerX(), lineas.get(i).getP2().getIntegerY());
                g.setColor(Color.black);
                g.setFont(new Font("Peach Milk", Font.PLAIN, 12));
                g.drawString(((lineas.get(i).getP2().getY() - Panel.ALTO - 150) / -15) + "", (float) lineas.get(i).getP2().getX(), (float) lineas.get(i).getP2().getY() - 10);

            }

        } catch (NullPointerException | IndexOutOfBoundsException ex)
        {
        }

    }

    /**
     * Añadimos una línea cada vez que hay datos disponible.
     *
     */
    private void anadirLinea()
    {

        Point nextPoint = getNextPoint();

        lineas.add(new Linea(lastPoint, nextPoint));

        animaciones.clear();
        anadirAnimacion(lastPoint, nextPoint);

        setDataAvailable(false);

        if (lineas.size() >= 10)
            camera.moveCamera();

        lastPoint = nextPoint;

    }

    private void anadirAnimacion(Point p1, Point p2)
    {
        animaciones.add(new Animation(p1, p2));
    }

    /**
     *
     * Obtenemos el siguiente punto con los datos que se establecieron en el <tt>SerialDataController</tt>
     *
     * @return El siguiente punto.
     *
     * @see SerialDataController
     *
     */
    private Point getNextPoint()
    {
        return new Point(lastPoint.getIntegerX() + 40, Panel.ALTO - (getData() * 15) + 150);
    }

    /**
     * Se reinicia la gráfica y todos sus componentes.
     */
    public void reiniciarGrafica()
    {

        lineas.clear();
        animaciones.clear();
        camera.setCameraStopped(true);
        camera.setX(0);

        lastPoint = new Point(60, Panel.ALTO);

    }

    public double getData()
    {
        return data;
    }

    public void setData(double data)
    {
        this.data = data;
    }

    public void setDataAvailable(boolean dataAvailable)
    {
        this.dataAvailable = dataAvailable;
    }

    public boolean isDataAvailable()
    {
        return dataAvailable;
    }

}
