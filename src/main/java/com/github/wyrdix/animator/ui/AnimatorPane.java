package com.github.wyrdix.animator.ui;

import javax.swing.*;
import java.awt.*;

public class AnimatorPane extends JPanel {
    public AnimatorPane() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());

        g.translate(getWidth()/2, getHeight()/2);

        g.setColor(Color.BLACK);
        g.fillOval(0, 0, 10, 10);
    }
}
