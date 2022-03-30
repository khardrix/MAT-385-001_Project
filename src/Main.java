/*********************************************************************************************************************
 *********************************************************************************************************************
 *****                    CURRENTLY WORKING ON DELETING KEY FILE AFTER CREATING THE KEY FILE                     *****
 *****               DOES NOT SEEM TO BE ASSIGNING THE CREATED KEY FILE TO ITS RESPECTIVE VARIABLE               *****
 *********************************************************************************************************************
 *********************************************************************************************************************/

// IMPORTS
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Random;


public class Main {

    // Class variables
    private static Scanner input = new Scanner(System.in);
    private static String keyFileName = "";
    private static String originalMessageFileName = "";
    private static String encryptedMessageFileName = "";
    private static File keyFile;
    private static File originalMessageFile;
    private static File encryptedMessageFile;
    private static Random dice = new Random();
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;


    public static void main(String[] args) throws Exception {

        // Local variables


        // Create message file (to encrypt)
        originalMessageFileName = getFileName("original message");
        createFile(originalMessageFileName);

        // Creates key file
        keyFileName = getFileName("key");
        createFile(keyFileName);

        // Creates encrypted message file
        encryptedMessageFileName = getFileName("encrypted message");
        createFile(encryptedMessageFileName);

        generateAndPrintKeyValuesNTimes(100);
/*

        String currentString = input.nextLine().toString();
        char currentCharArray[] = currentString.toCharArray();
        int shiftAmount;
        int currentCharIntValue;
        char encryptedChar;
        StringBuilder strBld = new StringBuilder();

        for (int i = 0; i < currentCharArray.length; i++) {
            currentCharIntValue = currentCharArray[i];
            shiftAmount = generateKeyValue();
            encryptedChar = encryptCurrentChar(currentCharIntValue, shiftAmount);
            strBld.append(encryptedChar);
            System.out.println("\n");
        }

        System.out.println("Encrypted String = " + strBld.toString());
*/
        fileReader = new FileReader(getFileName("key"));
        bufferedReader = new BufferedReader(fileReader);
/*
        int currentKeyValue;
        String currentLine;
        int i = 1;

        while ((currentLine = bufferedReader.readLine()) != null) {
            System.out.println("i = " + i);
            currentKeyValue = Integer.parseInt(currentLine);
            System.out.println("currentKeyValue = " + currentKeyValue);
            currentKeyValue += 10;
            System.out.println("currentKeyValue = " + currentKeyValue);
            System.out.println("");
            i++;
        }
*/

        int charValueAsInt = 65;
        char intValueAsChar = (char) charValueAsInt;
        String intValueAsString = String.valueOf(charValueAsInt);
        String intValueCastedAsCharToString = String.valueOf((char) charValueAsInt);

        System.out.println("charValueAsInt = " + charValueAsInt);                                     // 65 ///////////
        System.out.println("intValueAsChar = " + intValueAsChar);                                     // A ////////////
        System.out.println("intValueAsString = " + intValueAsString);                                 // 65 ///////////
        System.out.println("intValueCastedAsCharToString = " + intValueCastedAsCharToString);         // A ////////////
        System.out.println("\n\n");
        System.out.println("Current line from key file = " + getCurrentLineFromFile("key"));// 1st # in key /
        System.out.println("Current key value from key file = " + getCurrentKeyValueFromFile());      // 2nd # in key /
        System.out.println("Current String value of current char from key file " +
                getCurrentCharacterFromFile("key"));                                         // 3rd # 1st digit

        System.out.print("\n\nType something here: "); ////////////////////////////////////// TEST ////////////////////
        String userSelectedFile = input.nextLine(); ///////////////////////////////////////// TEST ////////////////////
        // System.out.println("Key file name is = " + keyFile.getName()); /////////////////// TEST ////////////////////
        // deleteSelectedFile(keyFile); ///////////////////////////////////////////////////// TEST ////////////////////
        // System.out.println("keyFileName = " + keyFileName); ////////////////////////////// TEST ////////////////////
        // System.out.println("keyFileName = " + keyFile.getName()); //////////////////////// TEST ////////////////////
        // System.out.println("keyFile exists = " + keyFile.exists()); ////////////////////// TEST ////////////////////
        // deleteSelectedFile(keyFile); // NOT WORKING ////////////////////////////////////// TEST ////////////////////
    }


    // Prompts user for file name for the key file
    private static String getFileName(String currentFile) {
        System.out.print("Enter file name to create the " + currentFile + " file \n" +
                "(make sure file name is unique so it does not overwrite another file, " +
                "including original message file): ");
        String fileName = input.nextLine();

        return createAbsolutePath(fileName);
    }


    // Creates file in project directory
    private static boolean createFile(String fileName) {
        File fileToCreate = new File(fileName);

        if (!fileToCreate.exists()) {
            try {
                return fileToCreate.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        assignFileToRespectiveVariable(fileToCreate, fileName);
        return false;
    }


    // Assign file to respective variable
    private static void assignFileToRespectiveVariable(File file, String fileName) {
        if (fileName.equals(originalMessageFileName)) {
            System.out.println("THIS IS THE ORIGINAL MESSAGE FILE!"); ////////////////////// TEST /////////////////////
            originalMessageFile = file;
        } else if (fileName.equals(keyFileName)) {
            System.out.println("THIS IS THE KEY FILE!"); /////////////////////////////////// TEST /////////////////////
            keyFile = file;
        } else if (fileName.equals(encryptedMessageFileName)) {
            System.out.println("THIS IS THE ENCRYPTED MESSAGE FILE!"); ///////////////////// TEST /////////////////////
            encryptedMessageFile = file;
        }
    }


    // Creates a String with the full absolute file path
    private static String createAbsolutePath(String fileName) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        return s + "\\" + fileName + ".txt";
    }


    // Generates a random int value from 0 - 25
    private static int generateKeyValue() {
        return dice.nextInt(26);
    }


    // Prints int shift value to specified .txt file
    private static boolean printKeyValueToFile(int shiftValue) {
        try (FileWriter fw = new FileWriter(keyFileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(shiftValue);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Loops int n times, every loop generates a key value and prints that value to specified .txt file
    private static void generateAndPrintKeyValuesNTimes(int n) {
        for (int i = 0; i < n; i++) {
            printKeyValueToFile(generateKeyValue());
        }
    }


    // Adds int shift value to current char around wrapped alphabet to encrypt the current char
    private static char encryptCurrentChar(int charValue, int shiftValue) {
        int encryptedValue = 0;

        if (charValue >= 65 && charValue <= 90) {
            encryptedValue = encryptUppercaseLetter(charValue, shiftValue);
        } else if (charValue >= 97 && charValue <= 122) {
            encryptedValue = encryptLowercaseLetter(charValue, shiftValue);
        } else {
            encryptedValue = charValue;
        }

        return ((char) encryptedValue);
    }


    // Shifts uppercase letters around a wrapped alphabet from A - Z
    private static int encryptUppercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 90) ? (((charValue + shiftValue) - 91) + 65) : (charValue + shiftValue);
    }


    // Shifts lowercase letters around a wrapped alphabet from a - z
    private static int encryptLowercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 122) ? (((charValue + shiftValue) - 123) + 97) : (charValue + shiftValue);
    }


    // Reads in the current line from a File using a BufferedReader and returns the String
    private static String getCurrentLineFromFile(String fileName) throws IOException {
        return bufferedReader.readLine();
    }


    // Reads in the current line from the key file and returns the String as an int
    private static int getCurrentKeyValueFromFile() throws IOException {
        return Integer.parseInt(getCurrentLineFromFile("key"));
    }


    // Reads in the current character from a File using a BufferedReader and returns it as a char
    private static String getCurrentCharacterFromFile(String fileName) throws IOException {
        int currentCharIntValue = bufferedReader.read();

        return (currentCharIntValue == -1) ? ("-1") : (String.valueOf((char) currentCharIntValue));
    }


    // Deletes the user selected file
    private static boolean deleteSelectedFile(File file) {
        boolean didDelete = true;
        if (file.exists()) {
            System.out.println("The file DOES EXIST!"); //////////////////////////////////// TEST /////////////////////
            return file.delete();
        }
        System.out.println("didDelete = " + didDelete); //////////////////////////////////// TEST /////////////////////
        return false;
    }
}
