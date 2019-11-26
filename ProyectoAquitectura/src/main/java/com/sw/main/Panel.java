package com.sw.main;

import com.sw.graphics.Camera;
import com.sw.input.KeyInput;
import com.sw.menus.Calificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Mohammed
 */
public class Panel extends Canvas
{

    public static int ANCHO = 800;
    public static int ALTO = 600;
    public static int DELAY = 500;

    private Camera camera;
    private MainMenu mainMenu;
    private GraficaTemperatura temperatura;
    private Calificacion calificacion;

    public static enum STATUS
    {

        Menu,
        Temperatura,
        Calificacion,

    }

    public static STATUS status = STATUS.Menu;

    public void init()
    {

        createBufferStrategy(3);

        camera = new Camera(0, 0);
        mainMenu = new MainMenu();
        temperatura = new GraficaTemperatura(camera);
        calificacion = new Calificacion();

        requestFocus();
        addKeyListener(new KeyInput(mainMenu, temperatura, calificacion));

    }

    public synchronized void stop()
    {
        System.exit(1);
    }

    public void tick()
    {

        switch (status)
        {
            case Menu:

                mainMenu.tick();

                break;

            case Temperatura:

                temperatura.tick();

                break;

            case Calificacion:

                calificacion.tick();

                break;

            default:
                break;

        }

    }

    public void render()
    {

        BufferStrategy bs = getBufferStrategy();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, ANCHO, ALTO);

        g.translate(-camera.getX(), 0);

        switch (status)
        {

            case Menu:

                mainMenu.render(g);

                break;

            case Temperatura:

                temperatura.render(g);

                break;

            case Calificacion:

                calificacion.render(g);

                break;

            default:
                break;

        }

        g.translate(camera.getX(), 0);

        g.dispose();
        bs.show();

    }

}
