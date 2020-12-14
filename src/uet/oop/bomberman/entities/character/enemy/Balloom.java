package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.enemy.ai.AIRandom;
import uet.oop.bomberman.graphics.Sprite;


public class Balloom extends AIRandom {
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img,
                Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3
        );
    }
}
