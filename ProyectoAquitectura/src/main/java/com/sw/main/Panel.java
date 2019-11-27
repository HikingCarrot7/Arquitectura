package com.sw.main;

import com.sw.controller.DataController;
import com.sw.graphics.Camera;
import com.sw.io.Conexion;
import com.sw.menus.InsertarCalificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import com.sw.menus.NuestraCalificacion;
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

    private Camera camera;
    private Conexion conexion;
    private DataController dataController;
    private MainMenu mainMenu;
    private GraficaTemperatura temperatura;
    private InsertarCalificacion calificacion;
    private NuestraCalificacion nuestraCalificacion;

    public static enum STATUS
    {

        Menu,
        Temperatura,
        Calificacion,
        NuestraCalificacion,

    }

    public static STATUS status = STATUS.Menu;

    public void init()
    {

        createBufferStrategy(3);

        camera = new Camera(0, 0);
        mainMenu = new MainMenu();
        temperatura = new GraficaTemperatura(camera);
        calificacion = new InsertarCalificacion();
        nuestraCalificacion = new NuestraCalificacion();
        dataController = new DataController(mainMenu, temperatura, calificacion);
        conexion = new Conexion(temperatura, dataController);

        conexion.iniciarConexion();

        requestFocus();
        addKeyListener(dataController);

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

            case NuestraCalificacion:

                nuestraCalificacion.tick();

                break;

            default:
                break;

        }

    }

    public void render()
    {

        BufferStrategy bs = getBufferStrategy();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(new Color(206, 249, 254));
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

            case NuestraCalificacion:

                nuestraCalificacion.render(g);

                break;

            default:
                break;

        }

        g.translate(camera.getX(), 0);

        g.dispose();
        bs.show();

    }

}
