package com.sw.input;

import com.sw.main.Panel;
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
    private int index;

    public KeyInput(MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
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

                break;

            default:
                break;

        }

    }

}
