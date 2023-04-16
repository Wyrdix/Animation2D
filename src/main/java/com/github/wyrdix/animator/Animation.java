package com.github.wyrdix.animator;

import com.github.wyrdix.animator.animation.IAnimation;
import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;
import com.github.wyrdix.animator.animation.objects.Ellipse;
import com.github.wyrdix.animator.animation.objects.Object;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Animation implements IAnimation {

    private final IField<List<Object>> objectList = tick -> {
        List<Object> objects = new ArrayList<>();

        Ellipse build = Ellipse.builder()
                .setBorder(IField.constant(false))
                .setParticles(IField.constant(40))
                .setLength(IField.constant(100d))
                .setPhase(t -> Math.cos(Math.PI / 40 * t) * Math.PI * 2)
                .setCenter(new Vector2(0, 0)).build();

        List<Vector2> list = build.apply(tick);

        for (int i = 0; i < list.size(); i++) {
            Vector2 vector2 = list.get(i);
            int finalI = i;
            objects.add(Ellipse.builder()
                    .setColor(t-> {
                        double v = 1d/list.size() * (t + finalI) * 255;
                        int v_ = (int) v % 255;
                        return new Color(v_,v_,v_);
                    })
                    .setBorder(IField.constant(true))
                    .setParticles(IField.constant(4))
                    .setLength(IField.constant(10d))
                    .setPhase(t -> finalI * 1d / list.size() * Math.PI * 2)
                    .setCenter(vector2)
                    .build());
        }

        return objects;
    };

    public Animation() {}

    @Override
    public int rate() {
        return 20;
    }

    @Override
    public void drawFrame(int tick, Graphics g) {
        IField<List<Object>> list = this.objectList;
        List<Object> objectList = list.apply(tick);
        for (Object object : objectList) {
            g.setColor(object.color().apply(tick));
            boolean border = object.border().apply(tick);
            boolean fill = object.fill().apply(tick);
            if(border || fill){
                Polygon polygon = new Polygon();
                for (Vector2 p : object.apply(tick)) {
                    polygon.addPoint(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue());
                }
                if(border) g.drawPolygon(polygon);
                else g.fillPolygon(polygon);
            }else {
                for (Vector2 p : object.apply(tick)) {
                    g.drawRect(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue(), 1, 1);
                }
            }

        }
    }
}
