/*********************************************************************************************************************
 *********************************************************************************************************************
 *****                    CURRENTLY WORKING ON DELETING KEY FILE AFTER CREATING THE KEY FILE                     *****
 *****               DOES NOT SEEM TO BE ASSIGNING THE CREATED KEY FILE TO ITS RESPECTIVE VARIABLE               *****
 *********************************************************************************************************************
 *********************************************************************************************************************/

// IMPORTS
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Random;


public class Main extends Application {

    // Class variables
    private static Scanner input = new Scanner(System.in);
    private static String keyFileName = "";
    private static String originalMessageFileName = "";
    private static String encryptedMessageFileName = "";
    private static File keyFile;
    private static File originalMessageFile;
    private static File encryptedMessageFile;
    private static FileReader fileReaderKey;
    private static FileReader fileReaderOriginal;
    private static FileReader fileReaderEncrypted;
    private static BufferedReader bufferedReaderKey;


    public static void main(String[] args) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        keyFileName = createAbsolutePath("key");
        keyFile = createFile(keyFileName);
        generateAndPrintKeyValuesNTimes(1000);

        try {
            fileReaderKey = new FileReader(getFileName("key"));
            bufferedReaderKey = new BufferedReader(fileReaderKey);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Create font for title Label
        Font font_title = Font.font("Arial", FontWeight.EXTRA_BOLD, 36);

        // Create title Label
        Label lbl_Title = new Label("Random Value Caesar Cipher");
        lbl_Title.setFont(font_title);

        // Create HBox for Title
        HBox hBoxTitle = new HBox();
        hBoxTitle.getChildren().add(lbl_Title);
        hBoxTitle.setAlignment(Pos.CENTER);


        Label lbl_OriginalMessage = new Label("Original Message: ");
        lbl_OriginalMessage.setPadding(new Insets(0, 5, 0, 10));

        TextArea txtArea_OriginalMessage = new TextArea();
        txtArea_OriginalMessage.setPrefColumnCount(10);
        txtArea_OriginalMessage.setPrefRowCount(10);
        txtArea_OriginalMessage.wrapTextProperty().setValue(Boolean.TRUE);

        Label lbl_EncryptedMessage = new Label("Encrypted Message: ");
        lbl_EncryptedMessage.setPadding(new Insets(0, 5, 0, 30));

        TextArea txtArea_EncryptedMessage = new TextArea();
        txtArea_EncryptedMessage.setPrefColumnCount(10);
        txtArea_EncryptedMessage.setPrefRowCount(10);
        txtArea_EncryptedMessage.wrapTextProperty().setValue(Boolean.TRUE);

        HBox hBoxTextAreas = new HBox();
        hBoxTextAreas.getChildren().addAll(lbl_OriginalMessage, txtArea_OriginalMessage,
                lbl_EncryptedMessage, txtArea_EncryptedMessage);

        // Create all the Buttons
        Button btnEncrypt = new Button("Encrypt");
        Button btnDecrypt = new Button("Decrypt");
        Button btnSaveOriginal = new Button("Save Original");
        Button btnSaveEncrypted = new Button("Save Encrypted");
        Button btnExit = new Button("Exit");

        // Set size of all the buttons
        btnEncrypt.setPrefSize(100, 40);
        btnDecrypt.setPrefSize(100, 40);
        btnSaveOriginal.setPrefSize(100, 40);
        btnSaveEncrypted.setPrefSize(100, 40);
        btnExit.setPrefSize(100, 40);

        HBox hBoxButtons = new HBox(10, btnEncrypt, btnDecrypt, btnSaveOriginal,
                btnSaveEncrypted, btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setPadding(new Insets(50, 20, 50, 20));

/*
        // Create Labels to go above TextAreas
        Label lbl_OriginalMessage = new Label("Original Message");
        Label lbl_EncryptedMessage = new Label("Encrypted Message");
        // Label lbl_KeyValues = new Label("Key Values");

        HBox hBoxTextAreasLabels = new HBox(100);
        hBoxTextAreasLabels.getChildren().addAll(lbl_OriginalMessage, lbl_EncryptedMessage);

        // Create original text message TextArea and set the properties
        TextArea txtArea_OriginalMessage = new TextArea();
        txtArea_OriginalMessage.setPrefColumnCount(10);
        txtArea_OriginalMessage.setPrefRowCount(10);
        txtArea_OriginalMessage.wrapTextProperty().setValue(Boolean.TRUE);

        // Create encrypted text message textArea and set the properties
        TextArea txtArea_EncryptedMessage = new TextArea();
        txtArea_EncryptedMessage.setPrefColumnCount(10);
        txtArea_EncryptedMessage.setPrefRowCount(10);
        txtArea_EncryptedMessage.wrapTextProperty().setValue(Boolean.TRUE);
*/
/*        // Create key text values textArea and set the properties
        TextArea txtArea_KeyValues = new TextArea();
        txtArea_KeyValues.setPrefColumnCount(1);
        txtArea_KeyValues.setPrefRowCount(10);
        txtArea_KeyValues.wrapTextProperty().setValue(Boolean.TRUE); */

        // HBox hboxTextAreas = new HBox();
        // hboxTextAreas.getChildren().addAll(txtArea_OriginalMessage, txtArea_EncryptedMessage);

        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();

        row0.setPercentHeight(25);
        row1.setPercentHeight(50);
        row2.setPercentHeight(25);

        // Create and Initialize the GridPane that holds all the nodes
        GridPane mainPane = new GridPane();
    /*    mainPane.setConstraints(txtArea_OriginalMessage, 0, 0);
        mainPane.setConstraints(txtArea_EncryptedMessage, 1, 0);
        mainPane.setConstraints(txtArea_KeyValues, 2, 0);
        mainPane.getChildren().add(txtArea_OriginalMessage);
        mainPane.getChildren().add(txtArea_EncryptedMessage);
        mainPane.getChildren().add(txtArea_KeyValues);
        mainPane.hgapProperty().setValue(20);                            */
        // mainPane.getChildren().add(hBoxTextAreasLabels);
        // mainPane.getChildren().add(hboxTextAreas);
        // mainPane.setAlignment(Pos.CENTER);
        // ColumnConstraints column1 = new ColumnConstraints();
        // column1.setPercentWidth(50);
        // ColumnConstraints column2 = new ColumnConstraints();
        // column2.setPercentWidth(50);
        // ColumnConstraints column3 = new ColumnConstraints();
        // column3.setPercentWidth(33.3);
        // mainPane.setConstraints(hBoxTitle, 0, 0);
        // mainPane.setColumnSpan(hBoxTitle, 4);
        // mainPane.setConstraints(hBoxTextAreasLabels, 0, 1);
        // mainPane.setConstraints(hboxTextAreas, 0, 2);
        // mainPane.getChildren().addAll(hBoxTitle, hBoxTextAreasLabels, hboxTextAreas);
        // mainPane.setAlignment(Pos.CENTER);
        // mainPane.getColumnConstraints().addAll(column1, column2);
        mainPane.getRowConstraints().addAll(row0, row1, row2);
        mainPane.setConstraints(hBoxTitle, 0, 0);
        mainPane.setConstraints(hBoxTextAreas, 0, 1);
        mainPane.setConstraints(hBoxButtons, 0, 2);
        mainPane.getChildren().addAll(hBoxTitle, hBoxTextAreas, hBoxButtons);



        // Create and Initialize the Scene
        Scene scene = new Scene(mainPane, 580, 625);

        // Set the Title of the Stage, Set the Scene to the Stage and Show the Stage
        primaryStage.setTitle("Random Value Caesar Cipher - Ryan Huffman");
        primaryStage.setScene(scene);
        primaryStage.show();


        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String originalMessageText = txtArea_OriginalMessage.getText();
                System.out.println("originalMessageText = " + originalMessageText); /////////////TEST ////////////
                System.out.println("The length of the string is: " + originalMessageText.length()); ///// TEST /////

                StringBuilder encryptedString = new StringBuilder();
                char currentChar = ' ';
                int currentShiftValue = 0;
                char currentEncryptedChar = ' ';

                for (int i = 0; i < originalMessageText.length(); i++) {
                    currentChar = originalMessageText.charAt(i);
                    try {
                        currentShiftValue = getCurrentKeyValueFromFile();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    System.out.println("currentChar = " + currentChar); /////////////TEST ////////////
                    System.out.println("currentShiftValue = " + currentShiftValue); /////////////TEST ////////////

                    currentEncryptedChar = encryptCurrentChar((int) currentChar, currentShiftValue);

                    encryptedString.append(currentEncryptedChar);
                }

                System.out.println("encryptedString = " + encryptedString); /////////////TEST ////////////

                txtArea_EncryptedMessage.clear();
                txtArea_EncryptedMessage.setText(encryptedString.toString());

                resetKeyBufferedReader();
            }
        });


        btnDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String encryptedMessageText = txtArea_EncryptedMessage.getText();
                System.out.println("encryptedMessageText = " + encryptedMessageText); /////////////TEST ////////////
                System.out.println("The length of the string is: " + encryptedMessageText.length()); ///// TEST /////

                StringBuilder decryptedString = new StringBuilder();
                char currentChar = ' ';
                int currentShiftValue = 0;
                char currentDecryptedChar = ' ';

                for (int i = 0; i < encryptedMessageText.length(); i++) {
                    currentChar = encryptedMessageText.charAt(i);
                    try {
                        currentShiftValue = getCurrentKeyValueFromFile();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    System.out.println("currentChar = " + currentChar); /////////////TEST ////////////
                    System.out.println("currentShiftValue = " + currentShiftValue); /////////////TEST ////////////

                    currentDecryptedChar = decryptCurrentChar((int) currentChar, currentShiftValue);

                    decryptedString.append(currentDecryptedChar);
                }

                System.out.println("decryptedString = " + decryptedString); /////////////TEST ////////////

                txtArea_OriginalMessage.clear();
                txtArea_OriginalMessage.setText(decryptedString.toString());

                resetKeyBufferedReader();
            }
        });


        btnSaveOriginal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                originalMessageFileName = createAbsolutePath("originalMessage");
                originalMessageFile = createFile(originalMessageFileName);

                String originalMessage = txtArea_OriginalMessage.getText();
                printOriginalMessageToFile(originalMessage);
            }
        });

        btnSaveEncrypted.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                encryptedMessageFileName = createAbsolutePath("encryptedMessage");
                encryptedMessageFile = createFile(encryptedMessageFileName);

                String encryptedMessage = txtArea_EncryptedMessage.getText();
                printEncryptedMessageToFile(encryptedMessage);
            }
        });


        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeReaders();
                Platform.exit();
            }
        });
    }
/*
        // Local variables


        // Create message file (to encrypt)
        originalMessageFileName = getFileName("original message");
        originalMessageFile = createFile(originalMessageFileName);

        // Creates key file
        keyFileName = getFileName("key");
        keyFile = createFile(keyFileName);

        // Creates encrypted message file
        encryptedMessageFileName = getFileName("encrypted message");
        encryptedMessageFile = createFile(encryptedMessageFileName);

        generateAndPrintKeyValuesNTimes(100);

 */
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
/*
        fileReader = new FileReader(getFileName("key"));
        bufferedReader = new BufferedReader(fileReader);

 */
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
/*
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
*/
        /*
        System.out.println("The key file exists = " + keyFile.exists());

        System.out.print("\n\nType something here: "); /////////////////////////////////// TEST ////////////////////
        String userSelectedFile = input.nextLine(); ////////////////////////////////////// TEST ////////////////////
        System.out.println("Key file name is = " + keyFile.getName()); /////////////////// TEST ////////////////////
        deleteSelectedFile(keyFile); ///////////////////////////////////////////////////// TEST ////////////////////

        System.out.println("keyFileName = " + keyFileName); ////////////////////////////// TEST ////////////////////
        System.out.println("keyFileName = " + keyFile.getName()); //////////////////////// TEST ////////////////////
        System.out.println("keyFile exists = " + keyFile.exists()); ////////////////////// TEST ////////////////////
        deleteSelectedFile(keyFile); // NOT WORKING ////////////////////////////////////// TEST ////////////////////


        System.out.print("Delete key file, press any button: ");
        input.nextLine();
        // manually deleting key file
        keyFile.delete();

         */
/*
        // Close Scanner, FileReader and BufferedReader
        input.close();
        fileReader.close();
        bufferedReader.close();


    }
*/

    // Prompts user for file name for the key file
    private static String getFileName(String currentFile) {
        // System.out.print("Enter file name to create the " + currentFile + " file \n" +
        //         "(make sure file name is unique so it does not overwrite another file, " +
        //         "including original message file): ");
        // String fileName = input.nextLine();

        return createAbsolutePath(currentFile);
    }


    // Creates file in project directory
    private static File createFile(String fileName) {
        File fileToCreate = new File(fileName);

        if (!fileToCreate.exists()) {
            try {
                fileToCreate.createNewFile();
                return fileToCreate;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    // Creates a String with the full absolute file path
    private static String createAbsolutePath(String fileName) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        return s + "\\" + fileName + ".txt";
    }


    // Generates a random int value from 0 - 25
    private static int generateKeyValue() {
        Random dice = new Random();
        return dice.nextInt(26);
    }


    // Prints int shift value to the key text file
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


    // Prints the original message to the originalMessage text file
    private static boolean printOriginalMessageToFile(String text) {
        try (FileWriter fw = new FileWriter(originalMessageFileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Prints the encrypted message to the encryptedMessage text file
    private static boolean printEncryptedMessageToFile(String text) {
        try (FileWriter fw = new FileWriter(encryptedMessageFileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
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


    // Subtracts int shift value to current char around wrapped alphabet to decrypt the current char
    private static char decryptCurrentChar(int charValue, int shiftValue) {
        int decryptedValue = 0;
        if(charValue >= 65 && charValue <= 90) {
            decryptedValue = decryptUppercaseLetter(charValue, shiftValue);
        } else if (charValue >= 97 && charValue <= 122) {
            decryptedValue = decryptLowercaseLetter(charValue, shiftValue);
        } else {
            decryptedValue = charValue;
        }

        return ((char) decryptedValue);
    }


    // Shifts uppercase letters around a wrapped alphabet from A - Z to decrypt them
    private static int decryptUppercaseLetter(int charValue, int shiftValue) {
        return (charValue - shiftValue < 65 ) ? (((charValue - shiftValue) + 91) - 65) : (charValue - shiftValue);
    }


    // Shifts lowercase letters around a wrapped alphabet from a - z to decrypt them
    private static int decryptLowercaseLetter(int charValue, int shiftValue) {
        return (charValue - shiftValue < 97) ? (((charValue - shiftValue) + 123) - 97) : (charValue - shiftValue);
    }


    // Shifts uppercase letters around a wrapped alphabet from A - Z to encrypt them
    private static int encryptUppercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 90) ? (((charValue + shiftValue) - 91) + 65) : (charValue + shiftValue);
    }


    // Shifts lowercase letters around a wrapped alphabet from a - z to encrypt them
    private static int encryptLowercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 122) ? (((charValue + shiftValue) - 123) + 97) : (charValue + shiftValue);
    }


    // Reads in the current line from a File using a BufferedReader and returns the String
    private static String getCurrentLineFromFile(String fileName) throws IOException {
        return bufferedReaderKey.readLine();
    }


    // Reads in the current line from the key file and returns the String as an int
    private static int getCurrentKeyValueFromFile() throws IOException {
        return Integer.parseInt(getCurrentLineFromFile("key"));
    }


    // Reads in the current character from a File using a BufferedReader and returns it as a char
    private static String getCurrentCharacterFromFile(String fileName) throws IOException {
        int currentCharIntValue = bufferedReaderKey.read();

        return (currentCharIntValue == -1) ? ("-1") : (String.valueOf((char) currentCharIntValue));
    }


    // Deletes the user selected file
    private static boolean deleteSelectedFile(File file) {
        /*boolean didDelete = true;
        if (file.exists()) {
            System.out.println("The file DOES EXIST!"); //////////////////////////////////// TEST /////////////////////
            return file.delete();
        }
        System.out.println("didDelete = " + didDelete); //////////////////////////////////// TEST /////////////////////
        return false;
         */

        Path currentPath = Paths.get(file.toString());

        try {
            System.out.println("currentPath = " + currentPath.toString());
            Files.delete(currentPath);
            System.out.println("File successfully deleted");
            return true;
        } catch (Exception e) {
            System.out.println("File not deleted: \n" + e);
        }

        return false;
    }


    // Reset bufferedReaderKey back to the beginning of the key file
    private static void resetKeyBufferedReader(){
        try {
            fileReaderKey = new FileReader(getFileName("key"));
            bufferedReaderKey = new BufferedReader(fileReaderKey);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // Closes FileReader, BufferedReader and Scanner
    private static void closeReaders() {
        try {
            fileReaderKey.close();
            bufferedReaderKey.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
