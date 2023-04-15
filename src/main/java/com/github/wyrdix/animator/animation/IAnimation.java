package com.github.wyrdix.animator.animation;

import java.awt.*;

public interface IAnimation {
    /**
     * Determine how many ticks there are in one second
     * @return the speed
     */
    int rate();

    void drawFrame(int frame, Graphics g);
}
