package com.github.wyrdix.animator.animation;

import java.util.function.Function;

public interface IField<T> extends Function<Integer, T> {
    static <T> IField<T> constant(T value) {
        return (v) -> value;
    }
}
