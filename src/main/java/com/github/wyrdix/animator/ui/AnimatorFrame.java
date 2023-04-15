package com.github.wyrdix.animator.ui;

import javax.swing.*;
import java.awt.*;

public class AnimatorFrame extends JFrame {
    public AnimatorFrame() throws HeadlessException {
        super("Workspace");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 400);

        Container pane = getContentPane();
        pane.add(new AnimatorPane());
        setVisible(true);
    }
}
