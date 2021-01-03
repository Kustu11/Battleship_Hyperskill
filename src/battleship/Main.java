package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Position{
    public int x;
    public int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

}
class Ship{
    public Position startPos;
    public Position endPos;

    public Ship(Position startPos, Position endPos){
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public boolean isSunken(String[][] activePlayground, int x, int y) {
        int x1 = startPos.x;
        int x2 = endPos.x;
        int y1 = startPos.y;
        int y2 = endPos.y;
        boolean isThisShip = false;
        for (int i = x1; i <= x2; i++){
            for (int j = y1; j <= y2; j++){
                if (x+1 == i && y == j){
                    isThisShip = true;
                }
                if (activePlayground[i-1][j-1].equals("O")){
                    return false;

                }

            }
        }
        return isThisShip;

    }
    public boolean isAllSunken(String[][] activePlayground, int x, int y){
        int x1 = startPos.x;
        int x2 = endPos.x;
        int y1 = startPos.y;
        int y2 = endPos.y;
        for (int i = x1; i <= x2; i++){
            for (int j = y1; j <= y2; j++){
                if (activePlayground[i-1][j-1].equals("O")){
                    return false;
                }
            }
        }
        return true;

    }


}

class Playground {
    String[][] activePlayground = new String[10][10];
    String[][] fogPlayground = new String[10][10];
    char[] bigChar = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private List<Ship> ships = new ArrayList<>();

    public Playground() {
        for (String[] rida : activePlayground) {
            Arrays.fill(rida, "~");
        }
        for (String[] rida : fogPlayground) {
            Arrays.fill(rida, "~");
        }




    }

    public void printPlayground() {
        for (int i = 0; i < activePlayground.length; i++) {
            if (i != 0) {
                System.out.print(bigChar[i] + " ");
            }
            for (int j = 0; j < activePlayground[i].length; j++) {
                if (i == 0 & j == 0) {
                    System.out.print(" ");
                    for (int a = 0; a < activePlayground[i].length; a++) {
                        System.out.print(" " + (a + 1));
                    }
                    System.out.println();
                    System.out.print(bigChar[i] + " ");
                }

                System.out.print(activePlayground[i][j] + " ");
            }
            System.out.print("\n");
        }

    }
    public void printFogPlayground(){
        for (int i = 0; i < fogPlayground.length; i++) {
            if (i != 0) {
                System.out.print(bigChar[i] + " ");
            }
            for (int j = 0; j < fogPlayground[i].length; j++) {
                if (i == 0 & j == 0) {
                    System.out.print(" ");
                    for (int a = 0; a < fogPlayground[i].length; a++) {
                        System.out.print(" " + (a + 1));
                    }
                    System.out.println();
                    System.out.print(bigChar[i] + " ");
                }

                System.out.print(fogPlayground[i][j] + " ");
            }
            System.out.print("\n");
        }
    }




    public boolean shotShip(int shotRow, int shotCol) {

        boolean sanked = false;
        boolean allSunken = true;
        if (activePlayground[shotRow][shotCol-1].equals("~")){
            activePlayground[shotRow][shotCol-1] = "M" ;
            fogPlayground[shotRow][shotCol-1] = "M";
/*            System.out.println();
            printFogPlayground();
            System.out.print("\n");*/
            System.out.println("You missed!\n");
            // printPlayground();
        } else if (activePlayground[shotRow][shotCol-1].equals("O")) {
            activePlayground[shotRow][shotCol - 1] = "X";
            fogPlayground[shotRow][shotCol - 1] = "X";
            for (Ship ship : ships) {
                if (!ship.isAllSunken(activePlayground, shotRow, shotCol)) {
                    allSunken = false;
                }
            }
            if (!allSunken) {

                for (Ship ship: ships){
                    if (ship.isSunken(activePlayground, shotRow, shotCol)) {
/*                        System.out.println();
                        printFogPlayground();
                        System.out.print("\n");*/
                        System.out.println("You sank a ship\n");
                        sanked = true;

                    }
                }
                if (!sanked) {
/*                    System.out.println();
                    printFogPlayground();
                    System.out.print("\n");*/
                    System.out.println("You hit a ship!\n");
                }

            } else {
/*                System.out.println();
                printFogPlayground();
                System.out.print("\n");*/
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
                return true;
            }
        }else {
/*            System.out.println();
            printFogPlayground();
            System.out.print("\n");*/
            System.out.println("Already shot");
        }

        return false;
    }

    public void newShipPosition(){
        int[] shipLengths = {5, 4, 3, 3, 2};
        int counter = 0;
        String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser","Destroyer"};
        Scanner myObj = new Scanner(System.in);
        printPlayground();
        System.out.print("\n");
        for (int ship : shipLengths){

            System.out.printf("Enter the coordinated of the %s (%d cells): %n\n", shipNames[counter], ship);
            while (true){
            String shipFrom = myObj.next();
            String shipTo = myObj.next();

            int lenFrom = shipFrom.length();
            int lenTo = shipTo.length();
            int rowFrom = Arrays.binarySearch(bigChar,shipFrom.charAt(0))+1;
            int rowTo = Arrays.binarySearch(bigChar,shipTo.charAt(0))+1;
            int columnTo, columnFrom;
            if (lenFrom == 2) {
                columnFrom = Integer.parseInt(String.valueOf(shipFrom.charAt(1)));
            } else {
                columnFrom = 10;
            }
            if (lenTo == 2) {
                columnTo = Integer.parseInt(String.valueOf(shipTo.charAt(1)));
            } else {
                columnTo = 10;
            }
            if (columnTo < columnFrom){
                int columnFrom1 = columnTo;
                columnTo = columnFrom;
                columnFrom = columnFrom1;
            }
            if (rowTo < rowFrom){
                int rowFrom1 = rowTo;
                rowTo = rowFrom;
                rowFrom = rowFrom1;
            }
            if (isCorrectShipLength(ship,columnFrom,columnTo,rowFrom,rowTo)){
                Position startPos = new Position(rowFrom,columnFrom);
                Position endPos = new Position(rowTo,columnTo);
                Ship ship1 = new Ship(startPos,endPos);
                ships.add(ship1);
                addShipToPlayground(columnFrom,columnTo,rowFrom,rowTo);

                break;
            } else {
                System.out.println("\nError, wrong position! Try again\n");
            }}
        counter += 1;
        System.out.println();
        printPlayground();
        System.out.print("\n");
        }

    }

    public boolean isFree(int column, int row){
        return (activePlayground[row - 1][column - 1].equals("~"));
    }

    public boolean freeRound(int columnFrom, int columnTo, int rowFrom, int rowTo){
        boolean isTrue = true;
        for (int i = columnFrom; i<= columnTo; i++){
            for (int j = rowFrom; j <= rowTo; j++) {
                for (int k : new int[]{-1, 0, 1}){
                    for (int l : new int[]{-1, 0, 1}){
                        if (i+k > 0 & i+k < 10 & j+l > 0 & j+l < 10)
                            if (!isFree(i+k, j+l)){
                                isTrue = false;
                            }
                    }
                }

            }

        }
        return isTrue;
    }



    public boolean isCorrectShipLength(int shipLength, int columnFrom, int columnTo, int rowFrom, int rowTo){
        int columnDiff = Math.abs(columnFrom - columnTo)+1;
        int rowDiff = Math.abs(rowFrom-rowTo)+1;
        //System.out.println(columnFrom+" "+columnTo+" "+rowFrom+" "+rowTo);

        if ((columnDiff == shipLength & rowDiff == 1) | (rowDiff == shipLength & columnDiff == 1)){
            return freeRound(columnFrom, columnTo, rowFrom, rowTo);
        } else return false;




    }

    public void addShipToPlayground(int columnFrom, int columnTo, int rowFrom, int rowTo){
        //System.out.println(columnFrom+" "+columnTo+" "+rowFrom+" "+rowTo);


        for (int i = columnFrom; i<= columnTo; i++) {
            for (int j = rowFrom; j <= rowTo; j++) {
                //System.out.println(i+" "+j);
                activePlayground[j-1][i-1] = "O";
                //printPlayground();
            }
        }

    }


}

public class Main {

    public static boolean chooseKillingCoordinates(Playground playground, Playground playground1, String player){
        System.out.print("\n");
        playground1.printFogPlayground();
        System.out.println("---------------------");
        playground.printPlayground();
        System.out.print("\n");




        System.out.printf("%s, it's your turn:\n\n", player);
        char[] bigChar = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        boolean gameEnd = false;
            while (true){
                Scanner myObj = new Scanner(System.in);
                String shotLocation = myObj.nextLine();
                int shotRow = Arrays.binarySearch(bigChar, shotLocation.charAt(0));
                int shotCol = -11;
                if (shotLocation.length() == 3) {
                    if (shotLocation.charAt(1) == '1' & shotLocation.charAt(2) == '0'){
                        shotCol = 10;
                    } else shotRow = -11;
                }else {
                    shotCol = Integer.parseInt(String.valueOf(shotLocation.charAt(1)));
                }
                if (shotRow != -11){
                    gameEnd = playground1.shotShip(shotRow,shotCol);
                    return gameEnd;
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again: \n");
                }
            }
        }


    public static void main(String[] args) {
        Playground playground1 = new Playground();
        Playground playground2 = new Playground();
        Scanner myObj = new Scanner(System.in);
        System.out.println("Player 1, place your ships on the game field\n");
        playground1.newShipPosition();
        System.out.println();
        System.out.println("Press Enter and pass the move to another player\n");
        myObj.nextLine();
        System.out.println("Player 2, place your ships on the game field\n");
        playground2.newShipPosition();
        System.out.println();
        System.out.println("Press Enter and pass the move to another player\n");
        myObj.nextLine();

        int i = 0;
        boolean endGame = false;
        while (i !=2){
            if (i == 0){
                endGame = chooseKillingCoordinates(playground1, playground2,  "Player 1");
                if (!endGame){
                    System.out.println();
                    System.out.println("Press Enter and pass the move to another player\n");
                    myObj.nextLine();
                    i = 1;
                } else {
                    i = 2;
                }

            } else {
                endGame = chooseKillingCoordinates(playground2, playground1,"Player 2");
                if (!endGame){
                    System.out.println();
                    System.out.println("Press Enter and pass the move to another player\n");
                    myObj.nextLine();
                    i = 0;
                } else {
                    i = 2;
                }
            }
        }
    }
}
