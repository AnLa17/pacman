package game.objects.creatures.enemy;

import game.Game;
import game.objects.creatures.Creature;
import game.objects.creatures.Player;


import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Creature {

    protected Player player;
    protected int targetX;
    protected int targetY;

    public Enemy(Game game, Player player, double centerX, double centerY, double radius, double speed, Color color) {
        super(game, centerX, centerY, radius, speed  , color);
        this.player = player;
        targetX = (int) centerX;
        targetY = (int) centerY;
    }

    @Override
    public void render(Graphics2D g, int tileSize) {
        double centerXonScreen = centerX * tileSize;
        double centerYOnScreen = centerY * tileSize;
        double radiusOnScreen = radius * tileSize;
        double sizeOnScreen = radiusOnScreen * 2.0;

        g.setColor(color);
        g.fill(new Rectangle2D.Double(centerXonScreen - radiusOnScreen, centerYOnScreen - radiusOnScreen, sizeOnScreen, sizeOnScreen));

    }
}

