package cs.vsu.ternovskaya.game.graphics.ui;

import cs.vsu.ternovskaya.game.Game;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JPanel {
    private JLabel createSettingsLabel() {
        JLabel label = new JLabel("Settings");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JComboBox<String> createModeSwitch(Game game) {
        String[] modes = {"Windowed", "Fullscreen Window", "Fullscreen"};
        JComboBox<String> modeSwitch = new JComboBox<>(modes);
        modeSwitch.addActionListener(e -> {
            String selectedMode = (String) modeSwitch.getSelectedItem();
            switch (selectedMode) {
                case "Windowed":
                    game.setWindowedMode();
                    break;
                case "Fullscreen Window":
                    game.setFullscreenWindowMode();
                    break;
                case "Fullscreen":
                    game.setFullscreenMode();
                    break;
            }
        });
        return modeSwitch;
    }

    private JButton createBackButton(Game game) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> game.showMainMenu());
        return backButton;
    }

    public SettingsMenu(Game game) {
        setLayout(new GridLayout(3, 1, 10, 10));
        add(createSettingsLabel());
        add(createModeSwitch(game));
        add(createBackButton(game));
    }
}
