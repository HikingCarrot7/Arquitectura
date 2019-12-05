package com.sw.main;

import com.sw.controller.SerialDataController;
import com.sw.graphics.Camera;
import com.sw.io.Conexion;
import com.sw.menus.Calificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import com.sw.menus.NuestraCalificacion;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Carlos Álvarez Trejo.
 */
public class Panel extends Canvas
{

    public static int ANCHO = 800; // Ancho de la ventana.
    public static int ALTO = 600; // Alto de la ventana.

    /**
     * Declaración de objetos.
     */
    private Camera camera;
    private Conexion conexion;
    private SerialDataController dataController;
    private MainMenu mainMenu;
    private GraficaTemperatura temperatura;
    private Calificacion calificacion;
    private NuestraCalificacion nuestraCalificacion;

    /**
     * Enum que nos ayuda a administrar el estado del programa.
     */
    public static enum STATUS
    {

        Menu,
        Temperatura,
        Calificacion,
        NuestraCalificacion,

    }

    public static STATUS status = STATUS.Menu; // El estado del programa al inicio es el Menú.

    /**
     * Inicializamos los objetos.
     */
    public void init()
    {

        createBufferStrategy(3);

        camera = new Camera(0, 0);
        mainMenu = new MainMenu();
        temperatura = new GraficaTemperatura(camera);
        calificacion = new Calificacion();
        nuestraCalificacion = new NuestraCalificacion();
        dataController = new SerialDataController(mainMenu, temperatura, calificacion, nuestraCalificacion);
        conexion = new Conexion(temperatura, dataController);

        conexion.iniciarConexion(); // Iniciamos la conexión.

        try
        {

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/res/peach_milk.TTF"))); // a cool font.

        } catch (FontFormatException | IOException e)
        {
            System.out.println(e.getMessage());
        }

        requestFocus();
        addKeyListener(dataController);

    }

    public void tick() // 60 actualizaciones por segundo.
    {

        switch (status)// Actualizamos la interfaz depediendo del estado del programa.
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

    public void render() // Se hace siempre.
    {

        BufferStrategy bs = getBufferStrategy();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, ANCHO, ALTO);

        g.translate(-camera.getX(), 0); // Movemos la visión con respecto a las coordenadas de la cámara.

        switch (status) // Dibujamos la interfaz depediendo del estado del programa.
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
