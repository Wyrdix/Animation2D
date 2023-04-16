package com.github.wyrdix.animator.ui;

import com.github.wyrdix.animator.Animation;
import com.github.wyrdix.animator.GifSequenceWriter;
import com.github.wyrdix.animator.Main;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimatorPane extends JLabel {

    private final Animation animation;
    private int tick;
    private long last = -1;

    public AnimatorPane() {
        animation = new Animation();
        tick = 0;

        try {

            ImageOutputStream output = new FileImageOutputStream(new File("Z:/Misc/Animator2D/target/my_animated_image.gif"));

            GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, 1000/animation.rate(), true);

            new Thread(() -> {
                while (!Main.frame.isClosed() && tick < 1400){
                    if(System.currentTimeMillis() - last < 1000/animation.rate()) continue;

                    BufferedImage bi = new BufferedImage(Main.frame.getWidth(), Main.frame.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics g = bi.createGraphics();
                    g.translate(getWidth()/2, getHeight()/2);
                    animate(g);
                    g.dispose();

                    repaint();

                    try {
                        writer.writeToSequence(bi);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    last = System.currentTimeMillis();
                    tick++;
                }

                try {
                    writer.close();
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] convertImageToArray(BufferedImage bufferedImage) {
        int[][] rgbArray = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                rgbArray[i][j] = bufferedImage.getRGB(j, i);
            }
        }
        return rgbArray;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(getWidth()/2, getHeight()/2);
        animate(g);
    }

    public void animate(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
        animation.drawFrame(tick, g);
    }
}
