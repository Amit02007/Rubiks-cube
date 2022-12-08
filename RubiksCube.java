import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class RubiksCube {
    /*
    *                               WHITE   WHITE  WHITE
    *                               WHITE   WHITE  WHITE
    *                               WHITE   WHITE  WHITE
    *      
    *  ORANGE  ORANGE  ORANGE       GREEN   GREEN   GREEN       RED     RED     RED        BLUE    BLUE    BLUE
    *  ORANGE  ORANGE  ORANGE       GREEN   GREEN   GREEN       RED     RED     RED        BLUE    BLUE    BLUE
    *  ORANGE  ORANGE  ORANGE       GREEN   GREEN   GREEN       RED     RED     RED        BLUE    BLUE    BLUE
    *      
    *                               YELLOW  YELLOW  YELLOW
    *                               YELLOW  YELLOW  YELLOW
    *                               YELLOW  YELLOW  YELLOW
    *                                                        
    */

    private Color[][][] cube = new Color[6][3][3];
    private int upSide = 2;
    private int currentSide = 0;


    public RubiksCube() {
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[0].length; j++) {
                for (int k = 0; k < cube[0][0].length; k++) {
                    cube[i][j][k] = Color.getColor(i);
                }
            }
        }
    }
    
    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\033[0;1m" + "INFO:" + "\u001B[0m" + 
                            "\nTo rotate the cube enter < ^ v >" +
                            "\nTo move the cube enter commands following this template:" +
                            "\nDirection(R/L/U/D/M/E) Tab/Without tab(') (times)" +
                            "\nExamples: R2  L'  U'2\n");
        printCurrentSide();
        
        String command = scanner.next();
        while (!command.equals("stop")) {
            switch (command) {
                case ">":
                    rotateRight();
                    break;

                case "<":
                    rotateLeft();
                    break;

                case "^":
                    rotateUp();
                    break;

                case "v":
                    rotateDown();
                    break;

                default:
                move(command);
                    break;
            }

            printCurrentSide();
            command = scanner.next();
        }

        scanner.close();
    }



    public void disarrayCube() {
        Random random = new Random();
        String[] directions = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "M", "M'", "E", "E'"};

        int times = random.nextInt(50);
        for (int i = 0; i < times; i++) {
            String command = directions[random.nextInt(12)] + random.nextInt(4);
            move(command);
        }

        printCurrentSide();
    }

    private int getLeftSide() {
        int[] faces = getOrderedFaces(currentSide, upSide);
        return faces[3];
    }

    private int getRightSide() {
        int[] faces = getOrderedFaces(currentSide, upSide);
        return faces[1];
    }

    private int getDownSide() {
        int[] faces = getOrderedFaces(currentSide, upSide);
        return faces[2];
    }


    public void printCurrentSide() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_ORANGE = "\033[38;2;255;140;0m";
        final String ANSI_WHITE = "\u001B[37m";

        for (int i = 0; i < cube[0].length; i++) {
            for (int j = 0; j < cube[0][0].length; j++) {
                String color = "";
                switch (cube[currentSide][i][j]) {
                    case WHITE:
                        color = ANSI_WHITE;
                        break;

                    case RED:
                        color = ANSI_RED;
                        break;

                    case BLUE:
                        color = ANSI_BLUE;
                        break;

                    case ORANGE:
                        color = ANSI_ORANGE;
                    break;

                    case GREEN:
                        color = ANSI_GREEN;
                        break;

                    case YELLOW:
                        color = ANSI_YELLOW;
                        break;
                }
                
                System.out.print(color + "■" + ANSI_RESET + " ");
            }
            System.out.println();
        }
        System.out.println();
    }



    private void rotateLeft() {

        currentSide = getRightSide();
        updateDataToCurrentPos(upSide, 1);
        updateDataToCurrentPos(getDownSide(), 3);
    }

    public void rotateLeftV() {
        rotateLeft();

        printCurrentSide();
    }

    private void rotateRight() {
        currentSide = getLeftSide();

        updateDataToCurrentPos(upSide, 3);
        updateDataToCurrentPos(getDownSide(), 1);
    }

    public void rotateRightV() {
        rotateRight();

        printCurrentSide();
    }

    private void rotateUp() {
        int tempCurrentSide = currentSide;

        updateDataToCurrentPos(upSide, 2);
        updateDataToCurrentPos(5 - currentSide, 2);

        currentSide = getDownSide();
        upSide = tempCurrentSide;
        
        updateDataToCurrentPos(getRightSide(), 1);
        updateDataToCurrentPos(getLeftSide(), 3);
    }

    public void rotateUpV() {
        rotateUp();

        printCurrentSide();
    }

    private void rotateDown() {
        int tempCurrentSide = currentSide;

        updateDataToCurrentPos(getDownSide(), 2);
        updateDataToCurrentPos(5 - currentSide, 2);

        currentSide = upSide;
        upSide = 5 - tempCurrentSide;

        updateDataToCurrentPos(getRightSide(), 3);
        updateDataToCurrentPos(getLeftSide(), 1);
    }

    public void rotateDownV() {
        rotateDown();

        printCurrentSide();
    }


    public void move(String command) {
        int times = Character.isDigit(command.charAt(command.length() - 1)) ? command.charAt(command.length() - 1) : 1;
        for (int i = 0; i < times; i++) {
            if (command.contains("R")) {
                if (command.contains("'")) {
                    spinVertical(2, "down");
                } else {
                    spinVertical(2, "up");
                }
            } else if (command.contains("L")) {
                if (command.contains("'")) {
                    spinVertical(0, "down");
                } else {
                    spinVertical(0, "up");

                }
            } else if (command.contains("U")) {
                if (command.contains("'")) {
                    spinHorizontal(0, "right");
                } else {
                    spinHorizontal(0, "left");
                }
            } else if (command.contains("D")) {
                if (command.contains("'")) {
                    spinHorizontal(2, "right");
                } else {
                    spinHorizontal(2, "left");
                }
            } else if (command.contains("M")) {
                if (command.contains("'")) {
                    spinVertical(0, "down");
                    spinVertical(2, "down");
                    rotateUp();
                } else {
                    spinVertical(0, "up");
                    spinVertical(2, "up");
                    rotateDown();
                }
            } else if (command.contains("E")) {
                if (command.contains("'")) {
                    spinHorizontal(2, "right");
                    spinHorizontal(0, "right");
                    rotateLeft();
                } else {
                    spinHorizontal(2, "left");
                    spinHorizontal(0, "left");
                    rotateRight();
                }
            }
        }
    }


    private void spinHorizontal(int rowIndex, String direction) {
        Color[] lastRow = {cube[currentSide][rowIndex][0], cube[currentSide][rowIndex][1], cube[currentSide][rowIndex][2]};

        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                for (int j = 0; j < 3; j++) {
                    if (direction == "left") {
                        cube[currentSide][rowIndex][j] = cube[getRightSide()][rowIndex][j];
                    } else {
                        cube[currentSide][rowIndex][j] = cube[getLeftSide()][rowIndex][j];
                    }
                }
            } else {
                cube[currentSide][rowIndex] = lastRow;
            }
            currentSide = direction == "left" ? getRightSide() : getLeftSide();
        }
        
        if (rowIndex == 0) {
            updateDataToCurrentPos(upSide, direction == "left" ? 1 : 3);
        } else {
            updateDataToCurrentPos(getDownSide(), direction == "left" ? 3 : 1);
        }

    }
    
    private void spinVertical(int columnIndex, String direction) {
        Color[] lastRow = {cube[currentSide][0][columnIndex], cube[currentSide][1][columnIndex], cube[currentSide][2][columnIndex]};

        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                for (int j = 0; j < 3; j++) {
                    if (direction == "up") {
                        cube[currentSide][j][columnIndex] = cube[getDownSide()][j][columnIndex];
                    } else {
                        cube[currentSide][j][columnIndex] = cube[upSide][j][columnIndex];
                    }
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    cube[currentSide][j][columnIndex] = lastRow[j];
                }
            }
            if (direction == "up") {
                updateDataToCurrentPos(upSide, 2);
                updateDataToCurrentPos(5 - currentSide, 2);
                int tempCurrentSide = currentSide;        
                currentSide = getDownSide();
                upSide = tempCurrentSide;
            } else {
                updateDataToCurrentPos(getDownSide(), 2);
                updateDataToCurrentPos(5 - currentSide, 2);
                int tempCurrentSide = currentSide;
                currentSide = upSide;
                upSide = 5 - tempCurrentSide;
            }
        }
        
        if (columnIndex == 0) {
            updateDataToCurrentPos(getLeftSide(), direction == "up" ? 3 : 1);
        } else {
            updateDataToCurrentPos(getRightSide(), direction == "up" ? 1 : 3);
        }

    }
    

    private void updateDataToCurrentPos(int side, int times) {
        Color[][] rotatedSide;
        for (int t = 0; t < times; t++) {
            rotatedSide = new Color[cube[0].length][cube[0][0].length];
            for (int i = 0; i < rotatedSide.length; i++) {
                for (int j = 0; j < rotatedSide.length; j++) {
                    rotatedSide[i][j] = cube[side][rotatedSide.length - j - 1][i];
                    
                }
            }
            cube[side] = rotatedSide;
        }
    }

    private int[] getFaces(int currentFace){   
        int[] allFaces = new int[6];
        for (int i = 0; i < allFaces.length; i++) {
            allFaces[i] = i;
        }

        int[] faces = new int[4];
        for (int i = 0, nextIndex = 0; i < allFaces.length; i++) {
            if (allFaces[i] != currentFace && allFaces[i] != 5 - currentFace) {
                faces[nextIndex] = allFaces[i];
                nextIndex++;
            }
        }

        return faces;
    }

    private int[] getOrderedFaces(int currentFace, int upFace){
        int angle = getAngle(currentFace, upFace);
        int[] faces = getFaces(currentFace);
        int[] orderedFaces = new int[4];
        orderedFaces[0] = faces[0];
        orderedFaces[1] = currentFace % 2 == 0 ? faces[2] : faces[1];
        orderedFaces[2] = faces[3];
        orderedFaces[3] = currentFace % 2 == 0 ? faces[1] : faces[2];
        ArrayList<Integer> facesByAngle = new ArrayList<>(
            Arrays.asList(orderedFaces[0], orderedFaces[1], orderedFaces[2], orderedFaces[3]));
 
        int a = 0;
        switch (angle) {
            case 270:
                a = facesByAngle.get(0);
                facesByAngle.remove(0);
                facesByAngle.add(a);

            case 180:
                a = facesByAngle.get(0);
                facesByAngle.remove(0);
                facesByAngle.add(a);

            case 90:
                a = facesByAngle.get(0);
                facesByAngle.remove(0);
                facesByAngle.add(a);
                break;
        }
        for (int i = 0; i < orderedFaces.length; i++) {
            orderedFaces[i] = facesByAngle.get(i);
        }

        return orderedFaces;
    }


    private int getAngle(int currentFace, int upFace){
        int angle = 0;
        int[] faces = getFaces(currentFace);
        if (upFace == faces[3]) {
            angle = 180;
        } else if (upFace == faces[1]) {
            angle = currentFace % 2 == 0 ? 270 : 90; 
        } else if (upFace == faces[2]) {
            angle = currentFace % 2 == 0 ? 90 : 270; 
        }

        return angle;
    }
}