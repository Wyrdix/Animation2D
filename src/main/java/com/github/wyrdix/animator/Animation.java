package com.github.wyrdix.animator;

import com.github.wyrdix.animator.animation.IAnimation;
import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;
import com.github.wyrdix.animator.animation.objects.Ellipse;
import com.github.wyrdix.animator.animation.objects.Object;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animation implements IAnimation {

    private final List<Object> objectList = new ArrayList<>();

    public Animation() {
        objectList.add(
                Ellipse.builder()
                        .setColor((t)-> {
                            int r = (int) ((1 + Math.cos(t * Math.PI/100)) * 255) % 255;
                            int g = (int) ((1 + Math.cos(t * Math.PI/40)) * 255) % 255;
                            int b = (int) ((1 + Math.cos(t * Math.PI/200)) * 255) % 255;
                            return new Color(r, g, b);
                        })
                        .setFill((t)->Math.cos(t * Math.PI/100) < 0)
                        .setParticles(IField.constant(100))
                        .setLength((t)->Math.cos(t * Math.PI/100) * 100)
                        .setPhase(IField.constant(0d))
                        .setCenter((t)->new Vector2(50 * Math.cos(t * Math.PI/40), -50 * Math.cos(t *  Math.PI/40))).build());
        objectList.add(
                Ellipse.builder()
                        .setBorder(IField.constant(false))
                        .setParticles(IField.constant(100))
                        .setLength(IField.constant(100d))
                        .setPhase(IField.constant(0d))
                        .setCenter((t)->new Vector2(-50 * Math.cos(t * Math.PI/40), 50 * Math.cos(t *  Math.PI/40))).build());
        objectList.add(
                Ellipse.builder()
                        .setBorder(IField.constant(false))
                        .setParticles(IField.constant(100))
                        .setLength(IField.constant(100d))
                        .setPhase(IField.constant(0d))
                        .setCenter((t)->new Vector2(50 * Math.cos(t * Math.PI/40), 50 * Math.cos(t *  Math.PI/140))).build());
        objectList.add(
                Ellipse.builder()
                        .setBorder(IField.constant(false))
                        .setParticles(IField.constant(100))
                        .setLength(IField.constant(100d))
                        .setPhase(IField.constant(0d))
                        .setCenter((t)->new Vector2(-50 * Math.cos(t * Math.PI/140), -50 * Math.cos(t *  Math.PI/40))).build());
    }

    @Override
    public int rate() {
        return 20;
    }

    @Override
    public void drawFrame(int tick, Graphics g) {
        for (Object object : objectList) {
            g.setColor(object.color().apply(tick));
            boolean border = object.border().apply(tick);
            boolean fill = object.fill().apply(tick);
            if(border || fill){
                Polygon polygon = new Polygon();
                for (Vector2 p : object.apply(tick)) {
                    polygon.addPoint((int) p.x(), (int) p.y());
                }
                if(border) g.drawPolygon(polygon);
                else g.fillPolygon(polygon);
            }else {
                for (Vector2 p : object.apply(tick)) {
                    g.drawRect((int) p.x(), (int) p.y(), 1, 1);
                }
            }

        }
    }
}
