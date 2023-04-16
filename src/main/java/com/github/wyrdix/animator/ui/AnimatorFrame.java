package com.github.wyrdix.animator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnimatorFrame extends JFrame {

    boolean closed = false;

    public AnimatorFrame() throws HeadlessException {
        super("Workspace");
    }

    public boolean isClosed() {
        return closed;
    }

    public void init() {
        setLocationRelativeTo(null);
        setSize(400, 400);

        Container pane = getContentPane();
        pane.add(new AnimatorPane());
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closed = true;
            }
        });
    }
}
