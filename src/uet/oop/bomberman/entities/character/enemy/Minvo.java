package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Minvo extends AIMedium {
    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img,
                Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3,
                Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3
        );
    }
}
