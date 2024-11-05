package cs.vsu.ternovskaya.game.graphics;

import cs.vsu.ternovskaya.game.entities.Player;

import java.awt.Graphics;
import java.awt.Color;

public class SceneRenderer {
    private Player player;
    private int sceneWidth, sceneHeight;

    public SceneRenderer(Player player, int sceneWidth, int sceneHeight) {
        this.player = player;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public void renderScene(Graphics g) {
        drawBackground(g);        // Отрисовка фона
        player.draw(g);           // Отрисовка игрока
        drawUI(g);                // Отрисовка интерфейса
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.CYAN);   // Пример простого фона
        g.fillRect(0, 0, sceneWidth, sceneHeight);
    }

    private void drawUI(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Health: 100", 10, 20);  // Пример элемента UI
    }
}
