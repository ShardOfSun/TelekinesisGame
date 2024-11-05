package cs.vsu.ternovskaya.game.graphics;

import cs.vsu.ternovskaya.game.entities.Player;

import java.awt.Graphics;

public class GameRenderer {
    private Player player;

    public GameRenderer(Player player) {
        this.player = player;
    }

    public void render(Graphics g) {
        player.draw(g);
    }
}
