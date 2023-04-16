package com.github.wyrdix.animator.animation.elements;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Element extends BiConsumer<Graphics, Integer> {
    @Override
    void accept(Graphics g, Integer tick);
}
