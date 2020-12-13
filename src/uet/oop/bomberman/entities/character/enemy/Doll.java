package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.entities.character.enemy.ai.AIRandom;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Doll extends AIRandom {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img,
                Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3,
                Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3
        );
        setSpeed(3);
    }
}
