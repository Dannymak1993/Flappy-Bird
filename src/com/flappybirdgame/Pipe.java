package com.flappybirdgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pipe {
    int x, height, gap, width;
    BufferedImage topPipeImage;
    BufferedImage bottomPipeImage;

    public Pipe(int x, int height, int gap, int width) {
        this.x = x;
        this.height = height;
        this.gap = gap;
        this.width = width;
        loadPipeImages();
    }

    public void move() {
        x -= 5;
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, width, height);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, height + gap, width, MyFlappyBirdGame.HEIGHT - (height + gap));
    }

    private void loadPipeImages() {
        try {
            // Load images for the top and bottom pipes
            topPipeImage = ImageIO.read(getClass().getResource("top_pipe_image.png"));
            bottomPipeImage = ImageIO.read(getClass().getResource("bottom_pipe_image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}