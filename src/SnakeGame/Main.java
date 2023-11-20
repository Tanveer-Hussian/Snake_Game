package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10,10,912,700);
      //  frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        panel.setBackground(Color.DARK_GRAY);

        frame.add(panel);


        frame.setVisible(true);
        frame.revalidate();

    }
}
