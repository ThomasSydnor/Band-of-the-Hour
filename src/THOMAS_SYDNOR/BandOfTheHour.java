package THOMAS_SYDNOR;
import java.util.Scanner;
/**
 * This class allows uses to organize bands based off of the stadium they're in
 * taking into account the weight of the musicians
 * @author
 *      Thomas Sydnor
 * @Version
 *      1.0
 */
public class BandOfTheHour {
    private static Scanner keyboard = new Scanner(System.in);
    private static final int MAX_ROWS = 10;
    private static final int MAX_POSITIONS = 8;
    private static final double MAX_POSITION_WEIGHT = 100.0;
    public static double [][] arenaSeating;
    /**
     * This method sets up the main array that is used in this program by creating its size
     * and scope based on the user's input
     */
    public static double[][] setup(){
        int numberRows;
        System.out.println("Please enter number of rows               :");
        numberRows = keyboard.nextInt();
        while(numberRows <= 0 || numberRows > MAX_ROWS){
            System.out.println("ERROR: Out of range, try again            :");
            numberRows = keyboard.nextInt();
        }//end of the user validation of numberRows
        double [][] bandSeats = new double[numberRows][];

        int index;
        for (index = 0;index < numberRows; ++index ){
            char rowLetter = 'A';
            rowLetter = (char) (rowLetter + index);
            int numPositions;
            System.out.println("Please enter number of positions in row "+ rowLetter + " :");
            numPositions = keyboard.nextInt();

            while(numPositions <= 0 || numPositions > MAX_POSITIONS){
                System.out.println("ERROR: Out of range, try again            :");
                numPositions = keyboard.nextInt();
            }//end of the validation

            bandSeats[index] = new double [numPositions];
        }//end of the for loop for creating the array

        return bandSeats;
    }//end of the setup method for creating the arena seating

    /**
     * This method is the method used to create the menu that allows the user
     * to choose what option they want by calling other methods based on user
     * input
     */
    private static void menu() {
        char menuChoice;
        do {
            System.out.println("(A)dd, (R)emove, (P)rint,          e(X)it :");
            //ensures that menuChoice is not changed by case sensitiviy
            menuChoice = keyboard.next().toUpperCase().charAt(0);
            switch (menuChoice) {
                case 'A':
                    addMusician();
                    break;
                case 'R':
                    removeMusician();
                    break;
                case 'P':
                    printSeating();
                    break;
                case 'X':
                    break;
            }//menu options
        } while (menuChoice != 'X');//end of the menu selection
    }//end of the menu method
    /**
     * This is used to add a musician to a specific spot in the array via calling two other
     * methods
     */
    private static void addMusician(){
        int musicianRow = getRow(arenaSeating);
        int musicianPosition = getPosition(arenaSeating,musicianRow);
        if (arenaSeating[musicianRow][musicianPosition] != 0.0){
            System.out.println("ERROR: There is already a musician there.");
            return;
        }//position is already full

        System.out.println("Please enter weight (45.0 to 200.0)");
        double weight = keyboard.nextDouble();
        while (weight < 45.0 || weight > 200.0){
            System.out.println("ERROR: Out of range, try again : ");
            weight = keyboard.nextDouble();
        }//end of weight verification

        arenaSeating[musicianRow][musicianPosition] = weight;
        double total = 0;
        int index;
        for(index = 0; index < arenaSeating[musicianRow].length; ++index){
            total = total + arenaSeating[musicianRow][index];
        }//end of the totaling
        if(total > arenaSeating[musicianRow].length * 100){
            System.out.println("ERROR: That would exceed the average weight limit.");
            arenaSeating[musicianRow][musicianPosition] = 0.0;
        }//ensures that the limit is not exceeded
    }//end of the AddMusician method
    /**
     * Method gets and retrieves the specific row the user is trying to access
     * @param arenaSeating
     *      passes the array into the method to be used to access the correct row
     */
    private static int getRow(double [][] arenaSeating){
        System.out.println("Please enter row letter                   : ");
        char userRow = keyboard.next().toUpperCase().charAt(0);
        int userRowIndex = (char) (userRow - 'A');
        while( userRowIndex < 0 || userRowIndex > arenaSeating.length){
            System.out.println("ERROR: Out of range, try again            : ");
            userRow = keyboard.next().toUpperCase().charAt(0);
            userRowIndex = (char) (userRow - 'A');
        }
        return userRowIndex;
    }
    /**
     * Method gets and retrieves the specific row the user is trying to access
     * @param arenaSeating
     *      passes the array into the method to be used to access the correct position
     * @param row
     *      passes the current row from the perivous method to access the correct element
     */
    private static int getPosition(double[][] arenaSeating, int row){
        int positionLength = arenaSeating[row].length;
        System.out.println("Please enter position number (1 to " + positionLength + ")     : ");
        int positionChoice = keyboard.nextInt();
        while(positionChoice < 1 || positionChoice > positionLength){
            System.out.println("ERROR: Out of range, try again            : ");
            positionChoice = keyboard.nextInt();
        }//end of validation
        return positionChoice - 1;
    }//end of the Get Position
    /**
     * Removes data enteried into the array to allow edits to be made
     */
    private static void removeMusician(){
        int musicianRow = getRow(arenaSeating);
        int musicianPosition = getPosition(arenaSeating,musicianRow);
        if(arenaSeating[musicianRow][musicianPosition] == 0.0){
            System.out.println("ERROR: That position is vacant");
            return;
        }//end of if for vacancy
        arenaSeating[musicianRow][musicianPosition] = 0.0;
        System.out.println("****** Musician removed.");
    }//end of Remove method
    /**
     * Prints out the information of the array for the user in a clean and formatted way
     */
    private static void printSeating() {
        for (int count = 0; count < arenaSeating.length; ++count) {
            char rowLetter = (char) ('A' + count);
            String rowElements = "";
            double sum = 0;
            for (int index = 0; index < arenaSeating[count].length; ++index) {
                rowElements += String.format("%6.1f ", arenaSeating[count][index]);
                sum += arenaSeating[count][index];
            }
            double average = sum / arenaSeating[count].length;
            String sumAverageFormat = "[%7.1f, %7.1f]";
            String paddedRowElements = String.format("%-49s", rowElements);
            String formattedOutput = String.format("%c: %s%s", rowLetter, paddedRowElements, String.format(sumAverageFormat, sum, average));
            System.out.printf("%-70s\n", formattedOutput);
        }
    }


    /**
     * Just used to call the first two initial functions
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-------------------------------");
        arenaSeating = setup();
        menu();
    }//end of the main function
}//end of the class BandOfTheHour
