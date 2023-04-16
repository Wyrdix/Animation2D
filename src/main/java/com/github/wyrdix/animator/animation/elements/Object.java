package com.github.wyrdix.animator.animation.elements;

import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;

import java.awt.*;
import java.util.List;

public interface Object extends IField<List<Vector2>>, Element {
    IField<Boolean> border();

    IField<Boolean> fill();

    IField<Color> color();

    @Override
    List<Vector2> apply(Integer tick);

    @Override
    default void accept(Graphics g,Integer tick){
        g.setColor(color().apply(tick));
        boolean border = border().apply(tick);
        boolean fill = fill().apply(tick);
        if (border || fill) {
            Polygon polygon = new Polygon();
            for (Vector2 p : apply(tick)) {
                polygon.addPoint(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue());
            }
            if (border) g.drawPolygon(polygon);
            else g.fillPolygon(polygon);
        } else {
            for (Vector2 p : apply(tick)) {
                g.drawRect(p.x_field().apply(tick).intValue(), p.y_field().apply(tick).intValue(), 1, 1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    abstract class ObjectBuilder<T extends Object, U extends ObjectBuilder<T, U>> {

        protected IField<Boolean> border = IField.constant(false);
        protected IField<Boolean> fill = IField.constant(false);
        protected IField<Color> color = IField.constant(Color.BLACK);

        public ObjectBuilder() {

        }

        public IField<Boolean> getBorder() {
            return border;
        }

        public U setBorder(IField<Boolean> border) {
            this.border = border;
            return (U) this;
        }

        public IField<Boolean> getFill() {
            return fill;
        }

        public U setFill(IField<Boolean> fill) {
            this.fill = fill;
            return (U) this;
        }

        public IField<Color> getColor() {
            return color;
        }

        public U setColor(IField<Color> color) {
            this.color = color;
            return (U) this;
        }

        public abstract T build();
    }
}
