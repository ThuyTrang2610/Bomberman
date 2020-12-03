package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Enemy1 extends Mob{

    private ArrayList<int> directions;
    public Enemy1 (int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public boolean canMove(int x, int y) {
        return false;
    }

    @Override
    public void update() {


    }
}
