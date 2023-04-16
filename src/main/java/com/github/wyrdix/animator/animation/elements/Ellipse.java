package com.github.wyrdix.animator.animation.elements;

import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Ellipse extends Object {

    static EllipseBuilder builder() {
        return new EllipseBuilder();
    }

    IField<Vector2> center();

    IField<Integer> particles();

    IField<Double> length();

    IField<Double> phase();

    @Override
    default List<Vector2> apply(Integer tick) {

        int n = particles().apply(tick);

        double spacing = Math.PI * 2 / n;
        double phase = phase().apply(tick);
        double length = length().apply(tick);

        Vector2 center = center().apply(tick);

        List<Vector2> list = new ArrayList<>();

        for (int i = 0; i < n; i++)
            list.add(Vector2.fromPolar(IField.constant(length), IField.constant(spacing * i + phase)).translate(center));

        return list;
    }

    class EllipseBuilder extends ObjectBuilder<Ellipse, EllipseBuilder> {

        protected IField<Vector2> center = IField.constant(new Vector2(IField.constant(0d), IField.constant(0d)));
        private IField<Integer> particles;
        private IField<Double> length;
        private IField<Double> phase;
        public EllipseBuilder() {

        }

        public IField<Vector2> getCenter() {
            return center;
        }

        public EllipseBuilder setCenter(IField<Vector2> center) {
            this.center = center;
            return this;
        }

        public IField<Integer> getParticles() {
            return particles;
        }

        public EllipseBuilder setParticles(IField<Integer> particles) {
            this.particles = particles;
            return this;
        }

        public IField<Double> getLength() {
            return length;
        }

        public EllipseBuilder setLength(IField<Double> length) {
            this.length = length;
            return this;
        }

        public IField<Double> getPhase() {
            return phase;
        }

        public EllipseBuilder setPhase(IField<Double> phase) {
            this.phase = phase;
            return this;
        }

        @Override
        public Ellipse build() {
            return new Ellipse() {
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

                @Override
                public IField<Vector2> center() {
                    return center;
                }

                @Override
                public IField<Integer> particles() {
                    return particles;
                }

                @Override
                public IField<Double> length() {
                    return length;
                }

                @Override
                public IField<Double> phase() {
                    return phase;
                }
            };
        }
    }
}
