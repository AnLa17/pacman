package game;

import game.objects.creatures.Player;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game extends JFrame {
    private final Display display;

    private final GameMap map;

    private final Player player;

    private boolean won;

    public Game() {
        super("game.Game");

        display = new Display(this);
        map = new GameMap(40);
        player = new Player(this, 13.5, 10.5, 0.375, 0.07);
        addKeyListener(player);

        setSize(1096,759);
        setResizable(false); //Fenster kann nicht mehr vergrößert/verkleinert werden
        setDefaultCloseOperation(EXIT_ON_CLOSE); //sorgt dafür, dass Programm beendet wird
        setLocationRelativeTo(null); //Fenster zentriert
        setVisible(true);

        startGameLoop();

    }

    private void startGameLoop() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            tick();
            display.repaint();
        }, 0L, 1000L / 60L, TimeUnit.MILLISECONDS);
    }

    private void reset() {
        won = false;
        map.reset();
        player.reset();
    }

    public void win() {
        won = true;
    }

    private void tick() {
        if (won) {
            JOptionPane.showMessageDialog(null, "Du hast gewonnen!");
            reset();
        }
        player.tick();
    }

    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        map.render(g2, map.getTileSize());
        player.render(g2, map.getTileSize());
    }

    public GameMap getMap() {
        return map;
    }

    public static void main(String[] args) {
        new Game();
    }


}

