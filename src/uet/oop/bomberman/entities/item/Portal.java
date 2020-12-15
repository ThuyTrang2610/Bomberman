package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

import java.util.List;

public class Portal extends Item{

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(isMeet() && BombermanGame.getEntities().size() == 1) {
            List<Entity> tmp = BombermanGame.getItems();
            tmp.remove(this);
            BombermanGame.setItems(tmp);
            BombermanGame.setEnd(true);
            BombermanGame.setLevel(BombermanGame.getLevel() + 1);
        }
    }
}
