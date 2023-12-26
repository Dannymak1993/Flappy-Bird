package com.flappybirdgame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {
    private BufferedImage backgroundImage;

    public Background(String imagePath) {
        try {
            // Print the resource path for debugging
            System.out.println("Background Image Path: " + getClass().getResource(imagePath));

            backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, int width, int height) {
        g2d.drawImage(backgroundImage, 0, 0, width, height, null);
    }
}