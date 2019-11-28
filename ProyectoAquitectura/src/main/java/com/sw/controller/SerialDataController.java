package com.sw.controller;

import com.sw.main.Panel;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.Calificacion;
import com.sw.menus.MainMenu;
import com.sw.menus.NuestraCalificacion;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mohammed
 */
public class SerialDataController extends KeyAdapter
{

    private MainMenu mainMenu;
    private GraficaTemperatura graficaTemperatura;
    private Calificacion calificacion;
    private NuestraCalificacion nuestraCalificacion;

    private final int OFFSETREAD = 200;
    private int lastData = 0;
    private int index;

    private boolean indexSet;

    public SerialDataController(MainMenu mainMenu, GraficaTemperatura graficaTemperatura, Calificacion calificacion, NuestraCalificacion nuestraCalificacion)
    {
        this.mainMenu = mainMenu;
        this.graficaTemperatura = graficaTemperatura;
        this.calificacion = calificacion;
        this.nuestraCalificacion = nuestraCalificacion;

    }

    public void gestionarSerialData(String serialData) throws InterruptedException
    {

        String[] data = serialData.split(",");

        double temperatura = Double.parseDouble(data[0]) + Double.parseDouble(data[1]) * 256;
        int potenciometro = Integer.parseInt(data[2]) + Integer.parseInt(data[3]) * 256;

        setDataGraficaTemperatura(temperatura);

        setDataControlMenu(potenciometro);

        gestionarEnter(Integer.parseInt(data[4]));
        gestionarEsc(Integer.parseInt(data[5]));

        Thread.sleep(300);

    }

    public void setDataControlMenu(int data)
    {

        if (!isIndexSet())
        {

            index = Math.abs((int) Math.ceil(data / 341) - 3);

            if (index == 3)
                index = 2;

            lastData = data;
            setIndexSet(true);

        }

        if (data >= 900)
            index = 0;

        if (data <= 100)
            index = 2;

        if (data >= lastData + OFFSETREAD)
            if (index > 0)
            {
                index--;
                lastData = data;

            }

        if (data <= lastData - OFFSETREAD)
            if (index < 2)
            {
                index++;
                lastData = data;

            }

        gestionarMenu();

    }

    public void setDataGraficaTemperatura(double data)
    {

        graficaTemperatura.setData(data);
        graficaTemperatura.setDataAvailable(true);

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            if (index > 0)
                index--;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            if (index < 2)
                index++;

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            gestionarEnter(1);

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            gestionarEsc(1);

        gestionarMenu();

        if (Panel.status.equals(Panel.STATUS.Calificacion))
            calificacion.escribirCalificacion(e);

    }

    private void gestionarMenu()
    {

        switch (Panel.status)
        {

            case Menu:
                switch (index)
                {

                    case 0:

                        mainMenu.setTempSelected(true);
                        mainMenu.setCalifSelected(false);
                        mainMenu.setSalirSelected(false);

                        break;

                    case 1:

                        mainMenu.setTempSelected(false);
                        mainMenu.setCalifSelected(true);
                        mainMenu.setSalirSelected(false);

                        break;

                    case 2:

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

    public void gestionarEnter(int data)
    {

        if (data == 0)
            return;

        if (!Panel.status.equals(Panel.STATUS.Menu))
        {

            switch (Panel.status)
            {

                case Calificacion:

                    calificacion.setCalificacionAsignada(true);

                    break;

                case NuestraCalificacion:

                    nuestraCalificacion.updateClics();

                    break;

                default:
                    break;

            }

            return;

        }

        switch (index)
        {

            case 0:

                Panel.status = Panel.STATUS.Temperatura;

                break;

            case 1:

                Panel.status = Panel.STATUS.Calificacion;

                calificacion.reiniciarCalif();

                break;

            case 2:

                Panel.status = Panel.STATUS.NuestraCalificacion;

                nuestraCalificacion.reiniciarAnimaciones();

            default:
                break;

        }

    }

    public void gestionarEsc(int data)
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
