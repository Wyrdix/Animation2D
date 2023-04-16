package com.github.wyrdix.animator.animation;

import static com.github.wyrdix.animator.animation.IField.fuse;

public record Vector2(IField<Double> x_field, IField<Double> y_field) implements IField<Vector2>{

    public Vector2(double x, double y) {
        this(IField.constant(x), IField.constant(y));
    }

    public IField<Double> lengthSquared() {
        return fuse(x_field, y_field, (x, y)-> x * x + y * y);
    }

    public IField<Double> length() {
        return lengthSquared().andThen(Math::sqrt);
    }

    public IField<Double> angle() {
        return fuse(y_field, x_field, Math::atan2);
    }

    public Vector2 multiply(IField<Double> v_field){
        return new Vector2(fuse(x_field, v_field, (x,v)->x*v), fuse(y_field, v_field, (y,v)->y*v));
    }

    public Vector2 unit(){
        return ((Vector2) fuse(x_field, y_field, (x, y) -> {
            if (x == 0 && y == 0) throw new IllegalStateException("Null Vector");
            IField<Double> length_invert = length().andThen(s -> 1 / s);
            return multiply(length_invert);
        }));
    }

    public Vector2 translate(Vector2 vec){
        return new Vector2(fuse(x_field, vec.x_field, Double::sum), fuse(y_field, vec.y_field, Double::sum));
    }

    public Vector2 getRotated(IField<Double> angle) {
        return fromPolar(length(), fuse(angle(), angle, Double::sum));
    }

    public static Vector2 fromPolar(IField<Double> range, IField<Double> angle){
        return new Vector2(fuse(angle.andThen(Math::cos), range, (x,v)->x*v), fuse(angle.andThen(Math::sin), range, (x,v)->x*v));
    }

    @Override
    public Vector2 apply(Integer tick) {
        return this;
    }
}
