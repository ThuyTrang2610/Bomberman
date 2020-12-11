package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class Flame extends Entity {
    private long birthTime;
    private boolean dead = false;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        BombermanGame.getFlames().add(this);
        birthTime = System.currentTimeMillis();
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - birthTime >= 1000) {
            dead = true;
        }
        else {
            for(int i = 0; i < BombermanGame.getEntities().size(); i ++) {
                Mob m = (Mob) BombermanGame.getEntities().get(i);
                if (m.meet(this)) {
                    m.setDead(true);
                }
            }
        }
    }
}
