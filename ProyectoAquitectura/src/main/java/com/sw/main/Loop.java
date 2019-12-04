package com.sw.main;

/**
 *
 * @author TODOS
 */
public class Loop implements Runnable
{

    private Panel panel;
    private Window window;

    public Loop(Panel panel, Window window)
    {

        this.panel = panel;
        this.window = window;

        new Thread(this).start();

    }

    @Override
    public void run()
    {

        panel.init();

        long lastTime = System.nanoTime();
        final double amountOfThicks = 60.0;
        double ns = 1000000000 / amountOfThicks;
        double delta = 0;

        int update = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (true)
        {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1)
            {
                panel.tick();
                update++;
                delta--;

            }

            panel.render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000)
            {

                window.setTitle("TICKS: " + update + " FPS: " + frames);
                timer += 1000;
                update = 0;
                frames = 0;

            }

        }

    }

}
