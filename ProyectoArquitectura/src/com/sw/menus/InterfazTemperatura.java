package com.sw.menus;

import com.sw.graphics.Animation;
import com.sw.graphics.Camera;
import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import com.sw.utilities.Linea;
import com.sw.utilities.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mohammed
 */
public class InterfazTemperatura implements Drawable, Runnable
{

    private final int DELAY = 1200;
    private int data;
    private boolean dataAvailable;
    private Point lastPoint;
    private Camera camera;
    private ArrayList<Linea> lineas;
    private ArrayList<Animation> animaciones;

    public InterfazTemperatura(Camera camera)
    {

        this.camera = camera;
        lastPoint = new Point(60, Panel.ALTO);
        lineas = new ArrayList<>();
        animaciones = new ArrayList<>();

        new Thread(this).start();

    }

    @Override
    public void tick()
    {

        for (int i = 0; i < animaciones.size(); i++)
            animaciones.get(i).tick();

    }

    @Override
    public void render(Graphics2D g)
    {

        dibujarRegla(g);

        dibujarLineas(g);

        if (isDataAvailable())
            anadirLinea();

        for (int i = 0; i < animaciones.size(); i++)
            animaciones.get(i).render(g);

    }

    private void dibujarRegla(Graphics2D g)
    {

        int y = Panel.ALTO;
        int aux = 0;

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
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

    private void dibujarLineas(Graphics2D g)
    {

        g.setColor(Color.white);

        for (int i = 0; i < lineas.size(); i++)
        {
            g.drawLine(lineas.get(i).getP1().getIntegerX(), lineas.get(i).getP1().getIntegerY(), lineas.get(i).getP2().getIntegerX(), lineas.get(i).getP2().getIntegerY());
            g.drawString(((lineas.get(i).getP2().getY() - Panel.ALTO - 150) / -15) + "", lineas.get(i).getP2().getX(), lineas.get(i).getP2().getY());

        }

    }

    private void anadirLinea()
    {

        Point nextPoint = getNextPoint();

        lineas.add(new Linea(lastPoint, nextPoint));

        animaciones.clear();
        anadirAnimacion(lastPoint, nextPoint);

        setDataAvailable(false);

        moveCamera();

        lastPoint = nextPoint;

    }

    private void moveCamera()
    {

        if (lineas.size() >= 10)
            new Thread(() ->
            {

                for (int i = 0; i < 40; i++)
                    try
                    {

                        Thread.sleep(DELAY / 40);

                        camera.setX(camera.getX() + 1);

                    } catch (InterruptedException ex)
                    {
                        System.out.println(ex.getMessage());
                    }

            }).start();

    }

    private void anadirAnimacion(Point p1, Point p2)
    {
        animaciones.add(new Animation(p1, p2));
    }

    private Point getNextPoint()
    {
        return new Point(lastPoint.getIntegerX() + 40, Panel.ALTO - (getData() * 15) + 150);
    }

    public int getData()
    {
        return data;
    }

    public void setData(int data)
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

    @Override
    public void run()
    {

        for (; !false;)
            try
            {

                Thread.sleep(DELAY);
                setData(new Random().nextInt(30) + 15);
                setDataAvailable(true);

            } catch (InterruptedException ex)
            {
                System.out.println(ex.getMessage());
            }

    }

}
