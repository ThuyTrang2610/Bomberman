package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class BombItem extends Item{
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(isMeet()) {
            ((Bomber)BombermanGame.getEntities().get(0))
                    .setCountBomb(((Bomber)BombermanGame.getEntities().get(0)).getCountBomb() + 1);
        }
        super.update();
    }
}
