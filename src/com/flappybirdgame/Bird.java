package com.flappybirdgame;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird {
    int x, y, size;
    BufferedImage birdImage;

    public Bird(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        loadBirdImage();
    }

    private void loadBirdImage() {
        try {
            birdImage = ImageIO.read(getClass().getResource("bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jump() {
        y -= 30;
    }

    public void move() {
        y += 5;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}