package com.github.wyrdix.animator.animation.objects;

import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.Vector2;

import java.awt.*;
import java.util.List;

public interface Object extends IField<List<Vector2>> {
    IField<Boolean> border();

    IField<Boolean> fill();

    IField<Color> color();

    @Override
    List<Vector2> apply(Integer tick);

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
