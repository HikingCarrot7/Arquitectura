package com.sw.graphics;

import java.awt.Graphics2D;

/**
 * Las interfaces gráficas simplemente implementan esta interface.
 *
 * @author Carlos Álvarez Trejo.
 */
public interface Drawable
{

    public void tick();

    public void render(Graphics2D g);

}
