package com.github.wyrdix.animator.animation;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface IField<T> extends Function<Integer, T> {
    static <T> IField<T> constant(T value) {
        return (v) -> value;
    }

    @Override
    T apply(Integer tick);

    @Override
    default <V> IField<V> andThen(Function<? super T, ? extends V> after) {
        return tick -> after.apply(apply(tick));
    }

    static <T,U,V> IField<V> fuse(IField<T> first, IField<U> other, BiFunction<T,U,V> fusion){
        return tick -> fusion.apply(first.apply(tick), other.apply(tick));
    }
}
