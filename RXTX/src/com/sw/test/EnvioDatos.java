package com.sw.test;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TooManyListenersException;

/**
 *
 * @author Mohammed
 */
public class EnvioDatos implements SerialPortEventListener
{

    private final String PUERTO = "COM4";
    private final int TIMEOUT = 2000;
    private final int DATA_RATE = 9600;
    private BufferedReader input;
    private OutputStream out;
    private SerialPort serialPort;

    public EnvioDatos()
    {
        iniciarConexion();
    }

    private CommPortIdentifier getPuertoID()
    {

        Enumeration<?> puerto = CommPortIdentifier.getPortIdentifiers();

        while (puerto.hasMoreElements())
        {

            CommPortIdentifier puertoID = (CommPortIdentifier) puerto.nextElement();

            if (puertoID.getName().equals(PUERTO))
                return puertoID;

        }

        throw new NoSuchElementException("No se encontró el puerto");

    }

    private void iniciarConexion()
    {

        try
        {

            serialPort = (SerialPort) getPuertoID().open(getClass().getName(), TIMEOUT);

            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

        } catch (PortInUseException | UnsupportedCommOperationException | TooManyListenersException | IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    private void enviarDatos(String data)
    {
        try
        {

            out = serialPort.getOutputStream();

            out.write(data.getBytes());

        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    private void recibeDatos()
    {
        try
        {
            Scanner in = new Scanner(serialPort.getInputStream());

            while (!in.hasNextInt())
            {

                in.close();
                in = new Scanner(serialPort.getInputStream());

            }

            System.out.println(in.nextInt());

        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args)
    {
        new EnvioDatos().recibeDatos();
    }

    @Override
    public void serialEvent(SerialPortEvent spe)
    {
        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE)
            try
            {

                System.out.println(input.readLine());

                //Thread.sleep(1000);
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }

    }

}
