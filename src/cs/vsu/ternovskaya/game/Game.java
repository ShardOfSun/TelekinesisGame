package cs.vsu.ternovskaya.game;

import cs.vsu.ternovskaya.game.graphics.ui.MainMenu;
import cs.vsu.ternovskaya.game.graphics.ui.SettingsMenu;
import cs.vsu.ternovskaya.game.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private volatile boolean running;
    private Thread gameThread;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BufferedImage backgroundMenu;

    public static void main(String[] args) {
        Game game = new Game();
        game.frame = game.createMainFrame();
        game.createMainPanel();

        game.frame.add(game);

        game.mainPanel.setOpaque(false);
        JPanel mainMenuWrapper = new JPanel(new BorderLayout());
        mainMenuWrapper.setOpaque(false);
        mainMenuWrapper.add(game.mainPanel, BorderLayout.EAST);
        mainMenuWrapper.setPreferredSize(new Dimension(300, 600));
        game.frame.add(mainMenuWrapper, BorderLayout.EAST);

        game.frame.setVisible(true);
        game.createBufferStrategy(3);
        game.start();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double nsPerTick = 1000000000.0 / 60.0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta -= 1;
            }

            render();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
            }
        }
    }

    private void update() {
        // Обновление состояния игры
        // Например, движение персонажа, обработка столкновений и т.д.
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        try {
            paint(g);
        } finally {
            g.dispose();
        }
        bs.show();
    }

    @Override
    public void paint(Graphics g) {
        if (backgroundMenu == null) {
            backgroundMenu = ResourceManager.loadImage("src/cs/vsu/ternovskaya/game/resources/images/backgrounds/back_menu.png");
        }

        if (backgroundMenu != null) {
            g.drawImage(backgroundMenu, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("My Game");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        return frame;
    }

    private void createMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MainMenu mainMenu = new MainMenu(this);
        SettingsMenu settingsMenu = new SettingsMenu(this);

        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(settingsMenu, "SettingsMenu");

        mainPanel.setOpaque(false);

        showMainMenu();
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
    }

    public void showSettingsMenu() {
        cardLayout.show(mainPanel, "SettingsMenu");
    }

    /* Управление окном */
    public void setWindowedMode() {
        frame.dispose();
        frame.setUndecorated(false);
        frame.setExtendedState(JFrame.NORMAL);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setVisible(true);
        createBufferStrategy(3);
    }

    public void setFullscreenWindowMode() {
        frame.dispose();
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        createBufferStrategy(3);
    }

    public void setFullscreenMode() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            frame.dispose();
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
        } else {
            System.out.println("Fullscreen not supported on this device.");
        }
        frame.setVisible(true);
        createBufferStrategy(3);
    }
}
