package uet.oop.bomberman.entities.landscape;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private long birthTime;
    private boolean dead = false;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        birthTime = System.currentTimeMillis();
    }

    public void start() {
        BombermanGame.getFlames().add(this);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - birthTime >= 240) {
            dead = true;
        }
        else {
            for(int i = 0; i < BombermanGame.getEntities().size(); i ++) {
                Mob m = (Mob) BombermanGame.getEntities().get(i);
                if (m.meet(this)) {
                    m.setDead(true);
                }
            }

            for(int i = 0; i < BombermanGame.getBombs().size(); i ++) {
                Bomb m = (Bomb) BombermanGame.getBombs().get(i);
                if (!m.isExploded() && m.getX() == x && m.getY() == y) {
                    m.explode();
                }
            }
        }
    }
}
