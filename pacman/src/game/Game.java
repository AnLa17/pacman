package game;

import game.objects.creatures.Player;
import game.objects.creatures.enemy.ChasingEnemy;
import game.objects.creatures.enemy.CuttingEnemy;
import game.objects.creatures.enemy.RandomEnemy;
import game.objects.creatures.enemy.Enemy;

import javax.swing.*; // Swing ist ein Toolkit für die Erstellung von GUIs
import java.awt.*; // Erstellung von GUIs und grundlegende Grafikoperationen (Abstract Window Toolkit), Swing baut darauf auf
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Game extends JFrame {
    private final Display display;

    private final GameMap map;

    private final Player player;

    private boolean won;

    private final Enemy[] enemies;

    public Game() {
        super("Game");

        display = new Display(this);
        map = new GameMap(40);
        player = new Player(this, 13.5, 10.5, 0.375, 0.07);
        addKeyListener(player);

        enemies = new Enemy[] {
                new ChasingEnemy(this, player, 12.5, 8.5, 0.375, 0.06, new Color(0xED1212)), // #ed1212
                new CuttingEnemy(this, player, 13.5, 8.5, 0.375, 0.065, new Color(0xF09F10)), // #fo9f10
                new RandomEnemy(this, player, 14.5, 8.5, 0.375, 0.07, new Color(0x374F59))  // #374f59
        };

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
        for (Enemy enemy : enemies) {
            enemy.reset();
        }
    }

    public void win() {
        won = true;
    }

    public void lose() {
        JOptionPane.showMessageDialog(null, "Game Over!");
        reset();
    }

    private void tick() {
        if (won) {
            JOptionPane.showMessageDialog(null, "Du hast gewonnen!");
            reset();
        }
        player.tick();
        for (Enemy enemy : enemies) {
            enemy.tick();
        }
    }

    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int tileSize = map.getTileSize();

        map.render(g2, tileSize);
        player.render(g2, tileSize);
        for (Enemy enemy : enemies) {
            enemy.render(g2, tileSize);
        }
    }

    public GameMap getMap() {
        return map;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public static void main(String[] args) {
        new Game();
    }
}

