package com.github.wyrdix.animator;

import com.github.wyrdix.animator.ui.AnimatorFrame;

import java.util.concurrent.SubmissionPublisher;

public class Main {
    public static AnimatorFrame frame;

    public static void main(String[] args) {
        frame = new AnimatorFrame();
        frame.init();
    }
}
