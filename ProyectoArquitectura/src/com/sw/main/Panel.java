package com.sw.main;

import com.sw.graphics.Camera;
import com.sw.input.KeyInput;
import com.sw.menus.InterfazTemperatura;
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

    private MainMenu mainMenu;
    private Camera camera;
    private InterfazTemperatura temperatura;

    public static enum STATUS
    {

        Menu,
        Temperatura,
        Calificacion,

    }

    public static STATUS status = STATUS.Temperatura;

    public void init()
    {

        createBufferStrategy(3);

        mainMenu = new MainMenu();
        camera = new Camera(0, 0);
        temperatura = new InterfazTemperatura(camera);

        requestFocus();
        addKeyListener(new KeyInput(mainMenu));

    }

    public synchronized void stop()
    {
        System.exit(1);
    }

    public void tick()
    {
        mainMenu.tick();
        temperatura.tick();
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

                break;

            default:
                break;

        }

        g.dispose();
        bs.show();

    }

}
