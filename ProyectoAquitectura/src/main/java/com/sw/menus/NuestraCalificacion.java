package com.sw.menus;

import com.sw.graphics.Animation;
import com.sw.graphics.BufferedImageLoader;
import com.sw.graphics.Drawable;
import com.sw.main.Panel;
import com.sw.utilities.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Naomi García Sanchez.
 */
public class NuestraCalificacion implements Drawable
{

    private int y;
    private int contClics = 0;
    private ArrayList<Animation> particles;
    private BufferedImage cortina;
    private BufferedImage regalo;

    public NuestraCalificacion()
    {

        cortina = new BufferedImageLoader().loadImage("src/main/java/res/cortina.png");
        regalo = new BufferedImageLoader().loadImage("src/main/java/res/regalo.png");

        particles = new ArrayList<>();

    }

    /**
     * Actualizamos los objetos de esta interfaz.
     */
    @Override
    public void tick()
    {

        desplazarCortina();

        if (contClics > 5)
            particles.forEach((p) ->
            {
                p.tick();

            });

    }

    /**
     * Dibujamos todos los objetos que conforman esta interfaz.
     *
     * @param g El objeto Graphics2D para dibujar en el Canvas.
     */
    @Override
    public void render(Graphics2D g)
    {

        g.setColor(Color.black);
        g.setFont(new Font("Peach Milk", Font.PLAIN, 55));
        g.drawString("¿100?", Panel.ANCHO / 2 - 55, Panel.ALTO / 2 - 10);

        if (contClics <= 5)
            g.drawImage(regalo, Panel.ANCHO / 2 - regalo.getWidth() / 2, 190, null);

        g.drawImage(cortina, 0, y, null);

        if (contClics > 5)
            particles.forEach((p) ->
            {

                p.render(g);

            });

    }

    private void desplazarCortina()
    {
        y -= 3;
    }

    public void reiniciarAnimaciones()
    {

        y = 0;
        contClics = 0;
        particles.clear();

        addParticles();

    }

    /**
     * Clics para hacer que el regalo reviente.
     */
    public void updateClics()
    {
        contClics++;
    }

    /**
     * Animación de explosión.
     */
    private void addParticles()
    {

        for (int i = 0; i < 100; i++)
            particles.add(new Animation(new Point(Panel.ANCHO / 2, 260),
                    new Point(new Random().nextInt(900), new Random().nextInt(900))));

    }

}
