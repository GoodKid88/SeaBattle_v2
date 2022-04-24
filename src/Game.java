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
                shipsName = " четырехпалубного";
                format = "(формат: x,y;x,y;x,y;x,y)";
            }
            if (counter == 1 || counter == 2) {
                shipsName = " трехпалубного";
                format = "(формат: x,y;x,y;x,y)";

            } else if (counter == 3 || counter == 4 || counter == 5) {
                shipsName = " двухпалубного";
                format = "(формат: x,y;x,y)";

            } else if (counter == 6 || counter == 7 || counter == 8 || counter == 9) {
                shipsName = " однопалубного";
                format = "(формат: x,y)";

            }
            try {
                System.out.println("Введите координаты" + shipsName + " корабля" + format);
                String line = scanner.nextLine();
                String[] coordinates = line.split(("[.,:;()?!\"\\s–]+"));

                Ship ship = new Ship(coordinatesParseInt(coordinates));

                if (ship.isShipValid()) {
                    if (player.getOwnFild().isShipAdded(ship)) {
                        player.getOwnFild().addOreol(ship);
                        player.getShips().add(ship);
                        counter++;
                    } else {
                        System.out.println("Ячейки заняты");
                    }
                } else {
                    System.out.println("Корабль не валидный!!!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неверный формат!");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Неверный ввод!");
            }
        }
    }


    public void startGame() {
        System.out.println("Начнем расставлять корабли на поле " + player1.getName() + "! Другой игрок, не смотри!");
        addAllShips(player1);

        System.out.println("Начнем расставлять корабли на поле " + player2.getName() + "! Другой игрок, не смотри!");
        addAllShips(player2);

        player1.getOwnFild().showFild();
        System.out.println();
        player2.getOwnFild().showFild();


        System.out.println("Корабли расставлены! Бой начинается!");

        while (player1.getShips().size() != 0 || player2.getShips().size() != 0) {
            try {
                while (player1.isShotResult()) {
                    System.out.println(player1.getName() + " Твой ход");
                    player1.attack(player2, scanner.nextInt(), scanner.nextInt());
                }
                player2.setShotResult();
                player2.getEnemyFild().showFild();

                while (player2.isShotResult()) {
                    System.out.println(player2.getName() + " Твой ход");
                    player2.attack(player1, scanner.nextInt(), scanner.nextInt());
                }
                player1.setShotResult();
                player1.getEnemyFild().showFild();
            } catch (InputMismatchException ex) {
                System.out.println("Неверный ввод!");
            }
        }

        if (player1.getShips().size() == 0) {
            System.out.println(player2.getName() + " Выиграл!");
        } else {
            System.out.println(player1.getName() + " Выиграл!");
        }
    }
}

//1. попробовать реализовать ореол через обработку исключений
//2. добавление ореола после потопленного корабля