package com.github.wyrdix.animator.animation;

public record Vector2(double x, double y) {

    public double lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public Vector2 translate(Vector2 vec){
        return new Vector2(x + vec.x, y + vec.y);
    }

    public Vector2 getRotated(double angle) {
        double newAngle = angle() + angle;
        double length = length();
        return fromPolar(length, newAngle);
    }

    public static Vector2 fromPolar(double range, double angle){
        return new Vector2(Math.cos(angle) * range, Math.sin(angle) * range);
    }
}
