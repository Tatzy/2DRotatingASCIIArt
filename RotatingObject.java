
import java.io.File;
import java.util.Scanner;

public class RotatingObject {

    public static void displayObject(char[][] arr) {

        // final String PURPLE_FOREGROUND = "\u001b[38;5;129m";
        final String RESET = "\u001b[39;49m";
        final String CLEAR = "\u001bc";
        final String MOVE = "\u001b[H";

        System.out.print(CLEAR);

        // System.out.print(PURPLE_FOREGROUND);
        for (int j = arr.length / 3; j < arr.length * 2 / 3; j++) {
            for (int i = arr[0].length / 3; i < arr[0].length * 2 / 3; i++) {
                // System.out.print(" "+arr[i][j]);
                System.out.print("  " + arr[j][i]);
            }
            System.out.println();
        }
        System.out.print(MOVE);

    }

    public static void inputObject(char[][] arr, File file) {
        try {
            Scanner scanner = new Scanner(file);

            int j = arr.length / 3;
            while (scanner.hasNextLine() && j < arr.length * 2 / 3) {

                String line = scanner.nextLine();
                System.out.println(line);
                char[] lineChar = line.toCharArray();
                for (int i = 0; i < arr[0].length / 3 && i < lineChar.length; i++) {
                    arr[j][i + arr.length / 3] = lineChar[i];
                }
                j++;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static int calcSize(File file){
        int count = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error");
            System.exit(0);
        }
        
        return count;
    }

    public static void rotation2D(int sizing, int refreshTime, double angle, String input)
            throws InterruptedException {
        
        // size for ASCII characters that are off screen;
        char[][] object;
        int size;

        

        if (input.equals("") || input.equals("cube")) {
            size = sizing;
            object = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    object[i][j] = ' ';
                }
            }
            // creating square
            for (int i = size * 4 / 9; i < size * 5 / 9; i++) {
                object[i][size * 4 / 9] = '#';
                object[i][size * 5 / 9] = '#';
                object[size * 4 / 9][i] = '#';
                object[size * 5 / 9][i] = '#';
            }
        } else {
            File file = new File(input);
            size = calcSize(file)*6;
            object = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    object[i][j] = ' ';
                }
            }
            inputObject(object, file);
        }

        displayObject(object);
        Thread.sleep(refreshTime);

        char[][] newObject = new char[size][size];
        double[][] calcX = new double[size][size];
        double[][] calcY = new double[size][size];
        double radian = Math.toRadians(angle);

        // first run
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newObject[i][j] = ' ';
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (object[size - 1 - y][x] != ' ') {
                    double new_x = Math.round(x * Math.cos(radian) - (y) * Math.sin(radian)
                            - (size / 2 * Math.cos(radian) - size / 2 * Math.sin(radian)) + size / 2);
                    double new_y = Math.round(x * Math.sin(radian) + (y) * Math.cos(radian)
                            - (size / 2 * Math.sin(radian) + size / 2 * Math.cos(radian)) + size / 2);

                    if ((new_x >= 0 && new_y >= 0 && new_x < size - 1 && new_y < size - 1)) {
                        calcX[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = new_x;
                        calcY[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = new_y;
                        newObject[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = object[size - 1
                                - y][x];
                    }

                }
            }
        }
        Thread.sleep(refreshTime);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                object[i][j] = newObject[i][j];
            }
        }
        displayObject(object);

        while (true) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newObject[i][j] = ' ';
                }
            }

            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (object[size - 1 - y][x] != ' ') {
                        double new_x = ((calcX[size - 1 - y][x]) * Math.cos(radian)
                                - (calcY[size - 1 - y][x]) * Math.sin(radian)
                                - (size / 2 * Math.cos(radian) - size / 2 * Math.sin(radian)) + size / 2);
                        double new_y = ((calcX[size - 1 - y][x]) * Math.sin(radian)
                                + (calcY[size - 1 - y][x]) * Math.cos(radian)
                                - (size / 2 * Math.sin(radian) + size / 2 * Math.cos(radian)) + size / 2);

                        if (new_x >= 0 && new_y >= 0 && new_x < size - 1 && new_y < size - 1) {
                            calcX[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = new_x;
                            calcY[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = new_y;
                            newObject[(int) (Math.round(size - 1 - new_y))][(int) (Math.round(new_x))] = object[size - 1
                                    - y][x];
                        }
                    }
                }
            }
            Thread.sleep(refreshTime);

            // copy
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    object[i][j] = newObject[i][j];
                }
            }
            displayObject(object);
        }

    }

    public static void main(String[] args) throws InterruptedException {

        // Reading input for ascii document representing object
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name of text file in folder that gets displayed. For example: Triangle.txt");
        // Input needs to be file name in same directory, for example: Triangle.txt. If input is empty or "cube", display cube. 
        // Works best when the ascii representation of the object is big.
        String input = scanner.nextLine();
        scanner.close();
        // sizing is only important for the size of the example cube, input objects will automatically
        // scale
        rotation2D(600, 100, 10, input);
        // You might have to zoom out to get the whole display of the object in the output console

    }
}