import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    private Player player1;
    private Player player2;

    public Game() {
        player1 = new Player("Player1");
        player2 = new Player("Player2");
    }

    public static List<Coordinate> coordinatesParseInt(String[] string_coordinates) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < string_coordinates.length; i += 2) {
            int j = i + 1;
            Coordinate coordinate = new Coordinate(Integer.parseInt(string_coordinates[i]), Integer.parseInt(string_coordinates[j]));
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    public void addAllShips(Player player) {
        String shipsName = "";
        String format = "";
        int counter = 0;
        while (counter < 10) {
            if (counter == 0) {
                shipsName = " four-deck";
                format = "(format: x,y;x,y;x,y;x,y)";
            }
            if (counter == 1 || counter == 2) {
                shipsName = " three-deck";
                format = "(format: x,y;x,y;x,y)";

            } else if (counter == 3 || counter == 4 || counter == 5) {
                shipsName = " two-deck";
                format = "(format: x,y;x,y)";

            } else if (counter == 6 || counter == 7 || counter == 8 || counter == 9) {
                shipsName = " one-deck";
                format = "(format: x,y)";

            }
            try {
                System.out.println("Please, enter the coordinates" + shipsName + " of ship" + format);
                String line = scanner.nextLine();
                String[] coordinates = line.split(("[.,:;()?!\"\\s–]+"));

                Ship ship = new Ship(coordinatesParseInt(coordinates));

                if (ship.isShipValid()) {
                    if (player.getOwnFild().isShipAdded(ship)) {
                        player.getOwnFild().addOreol(ship);
                        player.getShips().add(ship);
                        counter++;
                    } else {
                        System.out.println("Cells are occupied!");
                    }
                } else {
                    System.out.println("The ship is not valid!!!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect format!");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Incorrect input!");
            }
        }
    }


    public void startGame() {
        System.out.println("Let's start placing ships " + player1.getName() + " ! " + player2.getName() + " don't look!");
        addAllShips(player1);

        System.out.println("Let's start placing ships " + player2.getName() + " ! " + player1.getName() + " don't look!");
        addAllShips(player2);

        player1.getOwnFild().showFild();
        System.out.println();
        player2.getOwnFild().showFild();


        System.out.println("The ships hve been placed! The battle begins!");

        while (true) {
            try {
                while (player1.isShotResult()) {
                    if (player2.getShips().size() != 0) {
                        System.out.println(player1.getName() + " your turn");
                        player1.attack(player2, scanner.nextInt(), scanner.nextInt());
                    } else {
                        break;
                    }
                }
                player2.getEnemyFild().showFild();
                if (player2.getShips().size() == 0) {
                    System.out.println(player1.getName() + " Won!");
                    break;
                }

                while (player2.isShotResult()) {
                    if (player1.getShips().size() != 0) {
                        System.out.println(player2.getName() + " your turn");
                        player2.attack(player1, scanner.nextInt(), scanner.nextInt());
                    } else {
                        break;
                    }
                }
                player1.getEnemyFild().showFild();

                player1.setShotResult();
                player2.setShotResult();

                if (player1.getShips().size() == 0) {
                    System.out.println(player2.getName() + " Won!");
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Incorrect input!");
            }
        }
    }
}
