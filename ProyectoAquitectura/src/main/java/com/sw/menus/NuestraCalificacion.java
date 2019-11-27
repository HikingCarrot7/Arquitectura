package com.sw.menus;

import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Mohammed
 */
public class NuestraCalificacion implements Drawable
{

    private int y;

    public NuestraCalificacion()
    {

    }

    @Override
    public void tick()
    {
        desplazarCortina();
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.PLAIN, 50));
        g.drawString("¿100?", Panel.ANCHO / 2 - 70, Panel.ALTO / 2);
        g.fillRect(0, y, Panel.ANCHO, Panel.ALTO);

    }

    private void desplazarCortina()
    {
        y -= 3;
    }

    public void reiniciarCortina()
    {
        y = 0;
    }

}
