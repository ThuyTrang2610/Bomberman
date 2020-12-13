package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.entities.character.enemy.ai.AIRandom;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Kondoria extends AIRandom {
    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img,
                Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3,
                Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3
        );
    }

    public boolean canMove(int x, int y) {
        int x1 = x / Sprite.SCALED_SIZE;
        int y1 = (y + height) / Sprite.SCALED_SIZE;
        int x2 = (x + fat - 1) / Sprite.SCALED_SIZE;
        int y2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
        return (BombermanGame.map[y1][x1] != '#'
                && BombermanGame.map[y2][x1] != '#'
                && BombermanGame.map[y1][x2] != '#'
                && BombermanGame.map[y2][x2] != '#'
        );
    }
}
