package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public abstract class Item extends Entity{

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected  boolean isMeet() {
        int x1 = x;
        int y1 = y;
        int x2 = (x + Sprite.SCALED_SIZE - 1);
        int y2 = (y + Sprite.SCALED_SIZE - 1);
        for(int i = 0; i < BombermanGame.getEntities().size(); i ++)
        {
            if(BombermanGame.getEntities().get(i) instanceof Bomber) {
                int x3 = BombermanGame.getEntities().get(i).getX();
                int y3 = BombermanGame.getEntities().get(i).getY();
                int x4 = BombermanGame.getEntities().get(i).getX() + Sprite.SCALED_SIZE - 1;
                int y4 = BombermanGame.getEntities().get(i).getY() + Sprite.SCALED_SIZE - 1;
                if (
                        (x2 >= x3 && x2 <= x4 && y1 >= y3 && y1 <= y4)
                                || (x2 >= x3 && x2 <= x4 && y2 >= y3 && y2 <= y4)
                                || (x1 >= x3 && x1 <= x4 && y1 >= y3 && y1 <= y4)
                                || (x1 >= x3 && x1 <= x4 && y2 >= y3 && y2 <= y4)

                ) return true;
                break;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(isMeet()) {
            List<Entity> tmp = BombermanGame.getItems();
            tmp.remove(this);
            BombermanGame.setItems(tmp) ;
        }
    }
}
