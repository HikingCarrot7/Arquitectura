package com.sw.main;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Mohammed
 */
public final class Window extends JFrame
{

    public Window(int w, int h, String title, Panel panel)
    {

        panel.setPreferredSize(new Dimension(w, h));
        panel.setMaximumSize(new Dimension(w, h));
        panel.setMinimumSize(new Dimension(w, h));

        setTitle(title);
        setResizable(false);
        setVisible(true);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Loop(panel, this);

    }

}
