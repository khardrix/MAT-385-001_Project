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
    private static Random dice = new Random();


    public static void main(String[] args) {
        // Local variables

/*
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
*/

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
    private static void createFile(String currentFileName) {
        File fileToCreate = new File(currentFileName);

        if (!fileToCreate.exists()) {
            try {
                fileToCreate.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private static void printKeyValueToFile(int shiftValue) {
        try (FileWriter fw = new FileWriter(keyFileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(shiftValue);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
}
