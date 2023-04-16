package com.github.wyrdix.animator;

import com.github.wyrdix.animator.animation.IAnimation;
import com.github.wyrdix.animator.animation.IField;
import com.github.wyrdix.animator.animation.elements.Element;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Animation implements IAnimation {

    private final IField<List<Element>> elementList = tick -> Collections.emptyList();

    public Animation() {
    }

    @Override
    public int rate() {
        return 20;
    }

    @Override
    public void drawFrame(Graphics g, int tick) {
        elementList.apply(tick).forEach(s->s.accept(g, tick));
    }
}
