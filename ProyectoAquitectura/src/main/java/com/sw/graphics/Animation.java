package com.sw.graphics;

import com.sw.main.Panel;
import com.sw.main.Panel.STATUS;
import com.sw.utilities.Point;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author TODOS
 */
public class Animation implements Drawable
{

    private ArrayList<Trail> trails;
    private Point p1;
    private Point p2;
    private Color color;
    private float velX;
    private float velY;

    public Animation(Point p1, Point p2)
    {

        this.p1 = new Point(p1.getX(), p1.getY());
        this.p2 = new Point(p2.getX(), p2.getY());

        color = getRandomColor();

        trails = new ArrayList<>();

        calculateVelocity((float) p1.getX(), (float) p1.getY(), (float) p2.getX(), (float) p2.getY());

    }

    @Override
    public void tick()
    {

        if (Panel.status.equals(STATUS.Temperatura) || Panel.status.equals(STATUS.NuestraCalificacion))
            trails.add(new Trail((float) p1.x, (float) p1.y, color, 4, 4, 0.04f));

        p1.x += velX;
        p1.y += velY;

        for (int i = 0; i < trails.size(); i++)
        {

            Trail temp = trails.get(i);

            temp.tick();

        }

    }

    private Color getRandomColor()
    {

        Random rand = new Random();

        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

    }

    @Override
    public void render(Graphics2D g)
    {

        if (Panel.status.equals(STATUS.Temperatura) || Panel.status.equals(STATUS.NuestraCalificacion))
        {
            g.setColor(color);
            g.fillRect(p1.getIntegerX(), p1.getIntegerY(), 4, 4);

        }

        trails.forEach((t) ->
        {
            t.render(g);
        });

    }

    private void calculateVelocity(float fromX, float fromY, float toX, float toY)
    {

        double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        double speed = 10; //set the speed in [2,n)  n should be < 20 for normal speed
        //find Y
        velY = (float) ((toY - fromY) * speed / distance);
        //find X
        velX = (float) ((toX - fromX) * speed / distance);

    }

    private class Trail implements Drawable
    {

        private Color color;
        private Point point;
        private int w, h;
        private float alpha = 1;
        private float life;

        public Trail(float x, float y, Color color, int w, int h, float life)
        {

            this.color = color;
            this.w = w;
            this.h = h;
            this.life = life;
            point = new Point(x, y);

        }

        @Override
        public void tick()
        {

            if (alpha > life)
                alpha -= (life - 0.0001f);

            else
                trails.remove(this);

        }

        @Override
        public void render(Graphics2D g)
        {

            g.setComposite(makeTransparent(alpha));
            g.setColor(color);
            g.fillRect(point.getIntegerX(), point.getIntegerY(), w, h);
            g.setComposite(makeTransparent(1));

        }

        private AlphaComposite makeTransparent(float alpha)
        {
            return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        }

    }

}
