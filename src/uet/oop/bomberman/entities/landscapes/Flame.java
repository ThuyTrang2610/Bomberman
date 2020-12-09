package uet.oop.bomberman.entities.landscapes;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.utilities.MyTimer;

public class Flame extends Entity {
    private boolean isDead;

    private MyTimer flameTimer;

    public Flame(int x, int y, Image img) {
        super(x, y, img);
        flameTimer = new MyTimer(BombermanGame.FLAME_EXIST_TIME);
    }


    public boolean isDead() {
        return flameTimer.getTimeLeft() < 1500;
    }

    @Override
    public void update() {

    }
}
