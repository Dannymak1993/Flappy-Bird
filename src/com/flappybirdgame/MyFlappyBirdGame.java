package com.flappybirdgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyFlappyBirdGame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Bird bird;
    private List<Pipe> pipes;
    private Timer timer;

    public MyFlappyBirdGame() {
        setTitle("Flappy Bird Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bird = new Bird(100, HEIGHT / 2, 30);
        pipes = new ArrayList<>();
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bird.jump();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        setFocusable(true);
        initGame();
    }

    private void initGame() {
        bird = new Bird(100, HEIGHT / 2, 30);
        pipes.clear();
        pipesGenerated = false;
        generatePipe(); // Add this line to generate the initial pipe
        timer.start();
    }

    private void update() {
        bird.move();

        for (Pipe pipe : pipes) {
            pipe.move();
            if (pipe.x + pipe.width < 0) {
                pipes.remove(pipe);
                generatePipe();
                generatePipe(); // Add another call to increase pipe generation frequency
                break;
            }

            if (bird.getBounds().intersects(pipe.getTopBounds()) || bird.getBounds().intersects(pipe.getBottomBounds())) {
                gameOver();
                return;
            }
        }

        if (bird.y > HEIGHT || bird.y < 0) {
            gameOver();
            return;
        }
    }

    private void gameOver() {
        timer.stop();
        int choice = JOptionPane.showConfirmDialog(this, "Game Over! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initGame();
        } else {
            System.exit(0);
        }
    }

    private boolean pipesGenerated = false;  // Add this variable

    private void generatePipe() {
        if (!pipesGenerated) {
            Random random = new Random();
            int numberOfPipes = 100; // Adjust this number based on your preference

            // Set minimum and maximum heights for the pipes
            int minHeight = 50;
            int maxHeight = HEIGHT - 150 - minHeight;

            for (int i = 0; i < numberOfPipes; i++) {
                int pipeHeight = random.nextInt(maxHeight - minHeight + 1) + minHeight;
                int gap = 150;
                int pipeWidth = 50;
                Pipe pipe = new Pipe(WIDTH + i * 300, pipeHeight, gap, pipeWidth);
                pipes.add(pipe);
            }

            System.out.println("Generated " + numberOfPipes + " pipes");
            pipesGenerated = true;  // Set the flag to true to prevent further generation
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw bird
        int birdSize = 60;
        g2d.drawImage(bird.birdImage, bird.x, bird.y, bird.size, bird.size, null);

        // Draw pipes
        for (Pipe pipe : pipes) {
            // Draw top pipe
            g2d.drawImage(pipe.topPipeImage, pipe.x, 0, pipe.width, pipe.height, null);

            // Draw bottom pipe
            g2d.drawImage(pipe.bottomPipeImage, pipe.x, pipe.height + pipe.gap, pipe.width, HEIGHT - (pipe.height + pipe.gap), null);
        }

        // Draw ground
        g2d.setColor(Color.orange);
        g2d.fillRect(0, HEIGHT - 20, WIDTH, 20);

        Toolkit.getDefaultToolkit().sync();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFlappyBirdGame().setVisible(true);
            }
        });
    }
}
