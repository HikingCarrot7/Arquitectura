package com.sw.io;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.sw.controller.DataController;
import com.sw.menus.GraficaTemperatura;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Mohammed
 */
public class Conexion implements SerialPortEventListener
{

    public static int DELAY_CONEXION = 300;
    private PanamaHitek_Arduino ino;
    private GraficaTemperatura graficaTemperatura;
    private DataController dataController;

    public Conexion(GraficaTemperatura graficaTemperatura, DataController dataController)
    {

        ino = new PanamaHitek_Arduino();

        this.graficaTemperatura = graficaTemperatura;
        this.dataController = dataController;

    }

    public void iniciarConexion()
    {

        new Thread(() ->
        {

            try
            {

                ino.arduinoRX("COM5", 115200, this);

            } catch (ArduinoException | SerialPortException ex)
            {
                System.out.println(ex.getMessage());
            }

        }).start();

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {

        try
        {

            if (ino.isMessageAvailable())
            {

                String[] data = ino.printMessage().split(",");

                dataController.setDataControlMenu(Integer.parseInt(data[0]));
                dataController.setDataGraficaTemperatura(Integer.parseInt(data[1]));
                dataController.gestionarEnter(Integer.parseInt(data[2]));
                dataController.gestionarEsc(Integer.parseInt(data[3]));

            }

        } catch (SerialPortException | ArduinoException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
