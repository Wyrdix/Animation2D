package com.github.wyrdix.animator.ui;

import com.github.wyrdix.animator.Animation;

import javax.swing.*;
import java.awt.*;

public class AnimatorPane extends JPanel {

    private final Animation animation;
    private int tick;
    private long last = -1;

    public AnimatorPane() {
        animation = new Animation();
        tick = 0;
        new Thread(() -> {
            while (true){
                if(System.currentTimeMillis() - last < 1000/animation.rate()) continue;
                repaint();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if(System.currentTimeMillis() - last < 1000/animation.rate()) return;

        super.paintComponent(g);
        g.translate(getWidth()/2, getHeight()/2);
        g.setColor(Color.BLACK);

        animation.drawFrame(tick++, g);

        last = System.currentTimeMillis();
    }
}
