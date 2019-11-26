package com.sw.graphics;

import java.awt.Graphics2D;

/**
 *
 * @author Mohammed
 */
public interface Drawable
{

    public void tick();

    public void render(Graphics2D g);

}
