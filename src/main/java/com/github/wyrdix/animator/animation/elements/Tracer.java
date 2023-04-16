package com.github.wyrdix.animator.animation.elements;

import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Tracer extends Object {
    IField<Vector2> tracer();

    IField<Integer> particles();

    IField<Boolean> fading();

    List<Vector2> trace();

    @Override
    default List<Vector2> apply(Integer tick){
        Vector2 next = tracer().apply(tick);
        int n = particles().apply(tick);

        assert n >= 0;

        List<Vector2> trace = trace();
        if(n != 0 && trace.size() > n) while (trace.size() > n) trace.remove(0);
        if(n == 0) trace.clear();
        else{
            trace.add(next);
        }

        return trace;
    }

    @Override
    default void accept(Graphics g, Integer tick) {
        g.setColor(color().apply(tick));
        boolean border = border().apply(tick);
        boolean fill = fill().apply(tick);
        List<Vector2> list = apply(tick);
        if(fill){
            Polygon polygon = new Polygon();
            for (Vector2 p : list) {
                polygon.addPoint(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue());
            }

            g.fillPolygon(polygon);
        }
        if (border) {
            Vector2 previous = list.get(0);
            Vector2 v;
            for (int i = 1; i < list.size() - 1; i++) {
                v = list.get(i);

                g.drawLine(previous.x_field().apply(tick).intValue(),
                        previous.y_field().apply(tick).intValue(),
                        v.x_field().apply(tick).intValue(),
                        v.y_field().apply(tick).intValue());

                previous = v;
            }
        } else {
            for (Vector2 p : list) {
                g.drawRect(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue(), 1, 1);
            }
        }
    }

    static TracerBuilder builder(){
        return new TracerBuilder();
    }

    class TracerBuilder extends ObjectBuilder<Tracer, TracerBuilder> {

        IField<Vector2> tracer;
        IField<Integer> particles;
        IField<Boolean> fading;

        public IField<Vector2> getTracer() {
            return tracer;
        }

        public TracerBuilder setTracer(IField<Vector2> tracer) {
            this.tracer = tracer;
            return this;
        }

        public IField<Integer> getParticles() {
            return particles;
        }

        public TracerBuilder setParticles(IField<Integer> particles) {
            this.particles = particles;
            return this;
        }

        public IField<Boolean> getFading() {
            return fading;
        }

        public TracerBuilder setFading(IField<Boolean> fading) {
            this.fading = fading;
            return this;
        }

        @Override
        public Tracer build() {
            return new Tracer() {

                final List<Vector2> trace = new ArrayList<>();

                @Override
                public IField<Vector2> tracer() {
                    return tracer;
                }

                @Override
                public IField<Integer> particles() {
                    return particles;
                }

                @Override
                public IField<Boolean> fading() {
                    return fading;
                }

                @Override
                public List<Vector2> trace() {
                    return trace;
                }

                @Override
                public IField<Boolean> border() {
                    return border;
                }

                @Override
                public IField<Boolean> fill() {
                    return fill;
                }

                @Override
                public IField<Color> color() {
                    return color;
                }
            };
        }
    }
}
