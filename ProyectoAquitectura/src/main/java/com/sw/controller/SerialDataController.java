package com.sw.controller;

import com.sw.io.Conexion;
import com.sw.main.Panel;
import com.sw.menus.Calificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import com.sw.menus.NuestraCalificacion;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Ricardo Nicolás Canul Ibarra.
 */
public class SerialDataController extends KeyAdapter
{

    private MainMenu mainMenu;
    private GraficaTemperatura graficaTemperatura;
    private Calificacion calificacion;
    private NuestraCalificacion nuestraCalificacion;

    private final int OFFSETREAD = 200; //Desfase de lectura del potenciómetro.
    private int lastData = 0; //Último dato que se ha recibido del potenciometro.
    private int indice; // Índice para saber cuál de los cuadros está seleccionado.

    private boolean indexSet;

    public SerialDataController(MainMenu mainMenu, GraficaTemperatura graficaTemperatura, Calificacion calificacion, NuestraCalificacion nuestraCalificacion)
    {
        this.mainMenu = mainMenu;
        this.graficaTemperatura = graficaTemperatura;
        this.calificacion = calificacion;
        this.nuestraCalificacion = nuestraCalificacion;

    }

    /**
     * Gestiona los datos que se reciben del puerto serial del microcontrolador.
     *
     * Los datos se reciben en el siguiente orden:
     *
     * sensor low (8 bits),sensor high (2 bits),potenciómetro low(8 bits),potenciómetro high(2 bits),botón 1 (atrás),botón 2 (enter),
     *
     * --.-,--,---,--,-,-,
     *
     * @param serialData Los datos (String) que se reciben del microcontrolador.
     * @throws InterruptedException En caso de que este hilo (retraso de lectura de los datos) sea interrumpido (nunca pasará).
     */
    public void gestionarSerialData(String serialData) throws InterruptedException
    {

        String[] data = serialData.split(","); // Partimos los datos y los guardamos en un array.

        /**
         * Leemos la parte low del sensor (primera posición) y le sumamos la parte high (segunda posición) multiplicada por 256.
         */
        double temperatura = Double.parseDouble(data[0]) + Double.parseDouble(data[1]) * 256;

        /**
         * Leemos la parte low del potenciometro (tercera posición) y le sumamos la parte high (cuarta posición) multiplicada por 256. De esta forma, leemos un valor que va desde 0 hasta 1023.
         */
        int potenciometro = Integer.parseInt(data[2]) + Integer.parseInt(data[3]) * 256;

        setDataGraficaTemperatura(temperatura); // Establecemos los datos a la gráfica de la temperatura.

        setDataControlMenu(potenciometro); // Manipulamos el menú con los datos del potenciómetro.

        gestionarSalir(Integer.parseInt(data[4])); // botón 1.
        gestionarEnter(Integer.parseInt(data[5])); // botón 2.

        Thread.sleep(Conexion.DELAY_CONEXION); // Retraso de 300 milisegundos (para evitar problemas con la lectura de los datos).

    }

    /**
     *
     * Manipula el menú con el potenciometro.
     *
     * @param data Los datos (entero) del potenciómetro en un rango de 0 - 1023.
     *
     */
    public void setDataControlMenu(int data)
    {

        /**
         * Establecemos el índice por defecto cuando la aplicación inicia (dependiendo de la posición del potenciómetro se selecciona un índice u otro).
         *
         * Este bloque sólo se ejecuta una vez y su misión es darle un valor inicial al índice (cuadro seleccionado del menú) cuando se inicia la aplicación.
         */
        if (!isIndexSet())
        {

            // Obtenemos el índice con los datos que se leen en este momento del potenciómetro.
            indice = Math.abs((int) Math.ceil(data / 341) - 3);

            if (indice == 3)
                indice = 2;

            lastData = data; // Guardamos el último dato que se leyó del potenciómetro.
            setIndexSet(true); // Nunca volveremos a entrar en este bloque.

        }

        if (data >= 900) // Si el dato es mayor a 900 se selecciona el primer cuadro del menú.
            indice = 0;

        if (data <= 100) // Si el dato es menor a 100 se selecciona el último cuadro del menú.
            indice = 2;

        /**
         * Lo que básicamente hacen los siguientes bloques es actulizar el índice dependiendo del valor
         *
         * actual del potenciómetro y el último valor que se leyó cuado se actulizó el índice.
         *
         * Por ejemplo, si cuando iniciamos la aplicación el valor de potenciómetro es 150 (se pocisionará en el índice 2) el último dato guardado será 150
         *
         * y hasta que el potenciómetro no marque un valor mayor a 150 + 200 (200 es nuestro desfase de lectura) el índice no se actualizará.
         *
         *
         * Esto se implementó de esta forma para que potenciómetro no sea hipersensible al manipular el menú.
         *
         */
        if (data >= lastData + OFFSETREAD)
            if (indice > 0)
            {
                indice--;
                lastData = data;

            }

        if (data <= lastData - OFFSETREAD)
            if (indice < 2)
            {
                indice++;
                lastData = data;

            }

        gestionarMenu(); // Gestionamos el menú con el índice actulizado.

    }

    /**
     * Establecemos los datos a graficar.
     *
     * @param data La temperatura (double) a graficar.
     */
    public void setDataGraficaTemperatura(double data)
    {

        graficaTemperatura.setData(data); // Establecemos los datos.
        graficaTemperatura.setDataAvailable(true); // Hay datos disponible a graficar!

    }

    /**
     * Gestionamos la entrada desde el teclado (ignorar).
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e)
    {

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            if (indice > 0)
                indice--;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            if (indice < 2)
                indice++;

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            gestionarEnter(1);

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            gestionarSalir(1);

        gestionarMenu();

        if (Panel.status.equals(Panel.STATUS.Calificacion))
            calificacion.escribirCalificacion(e);

    }

    /**
     * Gestionamos el menú con el índice actualizado.
     */
    private void gestionarMenu()
    {

        switch (Panel.status)
        {

            case Menu:
                switch (indice)
                {

                    case 0: // La primera opción está seleccionada (ver la gráfica de temperatura).

                        mainMenu.setTempSelected(true);
                        mainMenu.setCalifSelected(false);
                        mainMenu.setSalirSelected(false);

                        break;

                    case 1: // La segunda opción está seleccionada (establecer la calificación).

                        mainMenu.setTempSelected(false);
                        mainMenu.setCalifSelected(true);
                        mainMenu.setSalirSelected(false);

                        break;

                    case 2: // La tercera opción está seleccionada (ver nuestra calificación).

                        mainMenu.setTempSelected(false);
                        mainMenu.setCalifSelected(false);
                        mainMenu.setSalirSelected(true);

                        break;

                    default:
                        break;

                }

                break;

            default:
                break;

        }

    }

    /**
     * Gestionamos el enter (botón 2).
     *
     * @param data El valor actual del botón (0 o 1).
     */
    public void gestionarEnter(int data)
    {

        if (data == 0)
            return; // Si el valor es 0, regresa.

        /**
         * Si no estoy en el menú y presionó enter...
         */
        if (!Panel.status.equals(Panel.STATUS.Menu))
        {

            switch (Panel.status)
            {

                case Calificacion: // Checamos si la calificación asignada es válida.

                    calificacion.checarCalificacion();

                    break;

                case NuestraCalificacion:

                    nuestraCalificacion.updateClics(); // Actualizamos los clics para abrir el regalo.

                    break;

                default:
                    break;

            }

            return;

        }

        switch (indice) //  Si estamos en el menú.
        {

            case 0:

                Panel.status = Panel.STATUS.Temperatura; // El estado del programa cambia, ahora estamos gráficando la temperatura del sensor.

                break;

            case 1:

                Panel.status = Panel.STATUS.Calificacion; // Estamos asignando la calificación.

                calificacion.reiniciarCalif();

                break;

            case 2:

                Panel.status = Panel.STATUS.NuestraCalificacion; // Estamos presenciando nuestra posible calificación.

                nuestraCalificacion.reiniciarAnimaciones();

            default:
                break;

        }

    }

    /**
     * Cuando se presione salir (botón 1) siempre se regresa al menú.
     *
     * @param data El valor actual del botón 1.
     */
    public void gestionarSalir(int data)
    {

        if (data != 0)
            switch (Panel.status)
            {

                case Temperatura:

                    Panel.status = Panel.STATUS.Menu;
                    graficaTemperatura.reiniciarGrafica();

                    break;

                case Calificacion:

                    Panel.status = Panel.STATUS.Menu;

                    break;

                case NuestraCalificacion:

                    Panel.status = Panel.STATUS.Menu;

                    break;

                default:
                    break;

            }

    }

    public boolean isIndexSet()
    {
        return indexSet;
    }

    public void setIndexSet(boolean indexSet)
    {
        this.indexSet = indexSet;
    }

}
