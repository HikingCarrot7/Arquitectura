package com.sw.input;

import com.sw.main.Panel;
import com.sw.menus.Calificacion;
import com.sw.menus.GraficaTemperatura;
import com.sw.menus.MainMenu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mohammed
 */
public class KeyInput extends KeyAdapter
{

    private MainMenu mainMenu;
    private GraficaTemperatura interfazTemperatura;
    private Calificacion calificacion;
    private int index;

    public KeyInput(MainMenu mainMenu, GraficaTemperatura interfazTemperatura, Calificacion calificacion)
    {
        this.mainMenu = mainMenu;
        this.interfazTemperatura = interfazTemperatura;
        this.calificacion = calificacion;

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
            gestionarEnter(index);

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            gestionarEsc(index);

        gestionarMenu(index, e);

    }

    private void gestionarMenu(int index, KeyEvent e)
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

            case Calificacion:

                calificacion.escribirCalificacion(e.getKeyChar());

                break;

            default:
                break;

        }

    }

    private void gestionarEnter(int index)
    {

        switch (index)
        {

            case 0:

                Panel.status = Panel.STATUS.Temperatura;

                break;

            case 1:

                Panel.status = Panel.STATUS.Calificacion;

                break;

            case 2:

                System.exit(1);

            default:
                break;

        }

    }

    private void gestionarEsc(int index)
    {

        switch (Panel.status)
        {

            case Temperatura:

                interfazTemperatura.reiniciarGrafica();
                Panel.status = Panel.STATUS.Menu;

                break;

            case Calificacion:

                Panel.status = Panel.STATUS.Menu;

                break;

            default:
                break;

        }

    }

}
