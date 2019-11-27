package com.sw.controller;

import com.sw.main.Panel;
import com.sw.menus.InsertarCalificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mohammed
 */
public class DataController extends KeyAdapter
{

    private MainMenu mainMenu;
    private GraficaTemperatura graficaTemperatura;
    private InsertarCalificacion calificacion;

    private final int OFFSETREAD = 40;
    private int lastData = 0;
    private int index;

    private boolean indexSet;

    public DataController(MainMenu mainMenu, GraficaTemperatura graficaTemperatura, InsertarCalificacion calificacion)
    {
        this.mainMenu = mainMenu;
        this.graficaTemperatura = graficaTemperatura;
        this.calificacion = calificacion;

    }

    public void setDataControlMenu(int data)
    {

        if (!isIndexSet())
        {
            index = (int) Math.ceil(data / 85) - 1;
            setIndexSet(true);

        }

        if (data > lastData + OFFSETREAD)
            if (index < 2)
            {
                index++;
                lastData = data;

            }

        if (data < lastData - OFFSETREAD)
            if (index > 0)
            {
                index--;
                lastData = data;

            }

        gestionarMenu();

    }

    public void setDataGraficaTemperatura(int data)
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

        if (data != 0)
            switch (index)
            {

                case 0:

                    Panel.status = Panel.STATUS.Temperatura;

                    break;

                case 1:

                    Panel.status = Panel.STATUS.Calificacion;

                    break;

                case 2:

                    Panel.status = Panel.STATUS.NuestraCalificacion;

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
