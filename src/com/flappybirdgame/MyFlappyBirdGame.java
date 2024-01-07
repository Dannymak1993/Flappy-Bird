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
    private Background background;

    public MyFlappyBirdGame() {
        setTitle("Flappy Bird Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bird = new Bird(100, HEIGHT / 2, 30);
        pipes = new ArrayList<>();
        background = new Background("background.png");

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
        generatePipe();
        timer.start();
    }

    private void update() {
        bird.move();

        List<Pipe> pipesToRemove = new ArrayList<>();

        for (Pipe pipe : pipes) {
            pipe.move();
            if (pipe.x + pipe.width < 0) {
                pipesToRemove.add(pipe);
            }

            if (bird.getBounds().intersects(pipe.getTopBounds()) || bird.getBounds().intersects(pipe.getBottomBounds())) {
                gameOver();
                return;
            }
        }

        pipes.removeAll(pipesToRemove);

        if (bird.y > HEIGHT || bird.y < 0) {
            gameOver();
            return;
        }

        if (pipes.isEmpty()) {
            generatePipe();
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

    private void generatePipe() {
        Random random = new Random();
        int gap = 150;
        int pipeWidth = 50;

        // Check if there are less than two pipes
        if (pipes.size() < 2) {
            int pipeHeight = random.nextInt(HEIGHT - 200) + 50;
            Pipe pipe = new Pipe(WIDTH, pipeHeight, gap, pipeWidth);
            pipes.add(pipe);
        }
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw background using Background class
        background.draw(g2d, WIDTH, HEIGHT);

        // Draw bird
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
