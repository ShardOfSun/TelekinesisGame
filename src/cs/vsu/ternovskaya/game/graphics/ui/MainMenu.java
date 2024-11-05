package cs.vsu.ternovskaya.game.graphics.ui;

import cs.vsu.ternovskaya.game.Game;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    public MainMenu(Game game) {
        setLayout(new GridLayout(3, 1, 10, 10));
        add(createStartButton());
        add(createSettingsButton(game));
        add(createExitButton());
    }

    private JButton createStartButton() {
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            // Логика для старта игры
            System.out.println("Starting the game...");
        });
        return startButton;
    }

    private JButton createSettingsButton(Game game) {
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> game.showSettingsMenu());
        return settingsButton;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        return exitButton;
    }
}
