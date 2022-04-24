import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private GameFild ownFild;
    private GameFild enemyFild;
    private boolean shotResult;
    private List<Ship> ships;

    public Player(String name) {
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.name = name;
        this.ships = new ArrayList<>();
        this.shotResult = true;
    }

    public String getName() {
        return name;
    }

    public GameFild getOwnFild() {
        return ownFild;
    }

    public GameFild getEnemyFild() {
        return enemyFild;
    }

    public boolean isShotResult() {
        return shotResult;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void attack(Player player, int x, int y) {
        try {
            Coordinate coordinate = new Coordinate(x, y);

            if (player.ownFild.isCellEmpty(x, y) || player.ownFild.getField()[x][y].equals(player.ownFild.getOreolSymbol())) {
                System.out.println("Мимо");
                shotResult = false;
                player.enemyFild.getField()[x][y] = player.ownFild.getMissSymbol();
            } else {

                for (Ship ship : player.ships) {
                    if (ship.getCoordinates().contains(coordinate)) {
                        ship.getCoordinates().remove(coordinate);
                        if (ship.getCoordinates().size() != 0) {
                            System.out.println("Попал");
                        } else {
                            System.out.println("Потопил");
                            player.ships.remove(ship);
                        }
                        shotResult = true;
                        break;
                    }
                }
                player.enemyFild.getField()[x][y] = player.ownFild.getHitlSymbol();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Введены неверные координаты");
        }
//            for (int i = 0; i < player.ships.size(); i++) {
//                for (int j = 0; j < player.ships.get(i).getCoordinates().size(); j++) {
//                    if (player.ships.get(i).getCoordinates().get(j).equals(coordinate)) {
//                        player.ships.get(i).getCoordinates().remove(j);
//                        if (player.ships.get(i).getCoordinates().size() != 0) {
//                            System.out.println("Попал");
//                        } else {
//                            System.out.println("Потопил");
//                        }
//                        shotResult = true;
//                    }
//                }
    }

    public void setShotResult() {
        this.shotResult = true;
    }
}