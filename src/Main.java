/*******************************************************************************************************************
 *******************************************************************************************************************
 *****                                       Random Value Caesar Cipher                                        *****
 *****---------------------------------------------------------------------------------------------------------*****
 *****                                          Student: Ryan Huffman                                          *****
 *****                                            Class: MAT-385-001                                           *****
 *****                                          Professor: Andrew Long                                         *****
 *****                                           Due Date: 05/05/2022                                          *****
 *****_________________________________________________________________________________________________________*****
 *****                                                                                                         *****
 *****         This JavaFX Application generates 1,000 random integer values that are between 0 and 25         *****
 *****            and prints those values (one value per line) in the key text file that it creates.           *****
 *****      These key values are used to both encrypt and decrypt text by shifting the current character       *****
 *****                                     in the appropriate text String.                                     *****
 *****                                                                                                         *****
 *****      Encrypt: Add the current integer shift value to the current integer value of the current char      *****
 *****                                          in a wrapped alphabet.                                         *****
 *****      Then convert that integer value to a char value and append it to the encrypted message String      *****
 *****    Decrypt: Subtract the current integer shift value to the current integer value of the current char   *****
 *****                                          in a wrapped alphabet.                                         *****
 *****      Then convert that integer value to a char value and append it to the decrypted message String      *****
 *****_________________________________________________________________________________________________________*****
 *****                                                                                                         *****
 *****                               INSTRUCTIONS ON HOW TO USE THIS APPLICATION                               *****
 *****---------------------------------------------------------------------------------------------------------*****
 *****          NOTE: This application can only encrypt or decrypt 1,000 character text messages, but          *****
 *****             that number can be changed in the integer constant value in the Class variables.            *****
 *****                                                                                                         *****
 *****    NOTE: Created .txt text files will not appear in project directory until the application is closed   *****
 *****                                                                                                         *****
 *****                                    After each use of this application:                                  *****
 *****           Do what you want with the originalMessage, encryptedMessage and key text files and            *****
 *****   then delete these files from the project directory to prepare for the next use of this application.   *****
 *****    If this is not done, no new key values will be generated and the advantage of random shift values    *****
 *****             will be lost. As the same shift values will be used as the last application use.            *****
 *****                                                                                                         *****
 *****                                          To encrypt a message:                                          *****
 *****              1) Type or paste a message inside the TextArea labeled as "Original Message:"              *****
 *****                                      2) Click the "Encrypt" button                                      *****
 *****                                               Option A:                                                 *****
 *****                         i) Copy and paste the encrypted message and key values                          *****
 *****      ii) Send the encrypted message and key values to the intended recipient so they can overwrite      *****
 *****     the values in the key text file, paste the encrypted message in the encrypted message TextArea      *****
 *****                 and click the "Decrypt" button to read the original decrypted message.                  *****
 *****                                               Option B:                                                 *****
 *****                                 i) Click the "Save Encrypted" button                                    *****
 *****           ii) Send the encryptedMessage text file and the key file to the intended recipient.           *****
 *****                                                                                                         *****
 *****                                          To decrypt a message:                                          *****
 *****               1) Paste encrypted message into the TextArea labeled as "Encrypted Message:"              *****
 *****                                      2) Click the "Decrypt" button                                      *****
 *****                                                                                                         *****
 *****_________________________________________________________________________________________________________*****
 *****                                                 Buttons                                                 *****
 *****---------------------------------------------------------------------------------------------------------*****
 *****                   Encrypt: Encrypts the text in the "Original Message" TextArea and                     *****
 *****                             displays it in the "Encrypted Message" TextArea                             *****
 *****                     Decrypt: Decrypts the text in the "Encrypted Text" TextArea and                     *****
 *****                              displays it in the "Original Message" TextArea                             *****
 *****           Save Original: Creates the originalMessage text file in the project directory and             *****
 *****                       saves all the text in the "Original Message" TextArea to it                       *****
 *****           Save Encrypted: Creates the encryptedMessage text file in the project directory and           *****
 *****                       saves all the text in the "Encrypted Message" TextArea to it                      *****
 *****                Exit: Closes the FileReader and BufferedReader for the key text file and                 *****
 *****                                closes and exits this JavaFX Application                                 *****
 *******************************************************************************************************************
 *******************************************************************************************************************/

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;


public class Main extends Application {

    // Class variables
    private static FileReader fileReaderKey;
    private static BufferedReader bufferedReaderKey;
    private static final int NUM_OF_KEY_VALUES = 1_000;


    // Launch the JavaFX applet
    public static void main(String[] args) throws Exception {
        launch(args);
    }


    // Method that controls the JavaFX applet
    @Override
    public void start(Stage primaryStage) {
        // Create the absolute path for the key file and create the key file
        String keyFileName = createAbsolutePath("key");
        createFile(keyFileName);

        // Generate preset number of random key values from 0 - 25 and print them to the key file
        generateAndPrintKeyValuesNTimes(keyFileName, NUM_OF_KEY_VALUES);

        // Try to instantiate the FileReader and BufferedReader for the key file and
        // print the error to the console if there is one
        resetKeyBufferedReader();

        // Create font for title Label
        Font font_title = Font.font("Arial", FontWeight.EXTRA_BOLD, 36);

        // Create title Label and set its font
        Label lbl_Title = new Label("Random Value Caesar Cipher");
        lbl_Title.setFont(font_title);

        // Create HBox for Title and set its alignment to the center
        HBox hBoxTitle = new HBox(lbl_Title);
        hBoxTitle.setAlignment(Pos.CENTER);

        // Create the Label to the original message TextArea and set its padding
        Label lbl_OriginalMessage = new Label("Original Message: ");
        lbl_OriginalMessage.setPadding(new Insets(0, 5, 0, 10));

        // Create the TextArea for the original message, set the row and column count and
        // set the text wrap property to true
        TextArea txtArea_OriginalMessage = new TextArea();
        txtArea_OriginalMessage.setPrefColumnCount(10);
        txtArea_OriginalMessage.setPrefRowCount(10);
        txtArea_OriginalMessage.wrapTextProperty().setValue(Boolean.TRUE);

        // Create the Label to the encrypted message TextArea and set its padding
        Label lbl_EncryptedMessage = new Label("Encrypted Message: ");
        lbl_EncryptedMessage.setPadding(new Insets(0, 5, 0, 30));

        // Create the TextArea for the encrypted message, set the row and column count and
        // set the text wrap property to true
        TextArea txtArea_EncryptedMessage = new TextArea();
        txtArea_EncryptedMessage.setPrefColumnCount(10);
        txtArea_EncryptedMessage.setPrefRowCount(10);
        txtArea_EncryptedMessage.wrapTextProperty().setValue(Boolean.TRUE);

        // Create the HBox that holds all the TextAreas and their respective Labels
        HBox hBoxTextAreas = new HBox(
                lbl_OriginalMessage, txtArea_OriginalMessage, lbl_EncryptedMessage, txtArea_EncryptedMessage);

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

        // Create the HBox that holds all the Buttons, aligns the HBox in the center and
        // sets the padding between Buttons
        HBox hBoxButtons = new HBox(10, btnEncrypt, btnDecrypt, btnSaveOriginal,
                btnSaveEncrypted, btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setPadding(new Insets(50, 20, 50, 20));

        // Create RowConstraints (one for each HBox)
        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();

        // Set the row height for each HBox
        row0.setPercentHeight(25);        // Height for the HBox that holds the title Label
        row1.setPercentHeight(50);        // Height for the HBox that holds the TextAreas and respective Labels
        row2.setPercentHeight(25);        // Height for the HBox that holds the Buttons

        // Create and Initialize the GridPane that holds all the nodes, adds all the HBoxes to it and
        // adds the RowConstraints
        GridPane mainPane = new GridPane();
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


        // Event handler for when a user clicks the "Encrypt" button
        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                encryptOrDecrypt(txtArea_OriginalMessage, txtArea_EncryptedMessage, true);
            }
        });


        // Event handler for when a user clicks the "Decrypt" button
        btnDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                encryptOrDecrypt(txtArea_EncryptedMessage, txtArea_OriginalMessage, false);
            }
        });


        // Event handler for when a user clicks the "Save Original" button
        btnSaveOriginal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveTextToFile("originalMessage", txtArea_OriginalMessage);
            }
        });


        // Event handler for when a user clicks the "Save Encrypted" button
        btnSaveEncrypted.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveTextToFile("encryptedMessage", txtArea_EncryptedMessage);
            }
        });


        // Event handler for when a user clicks the "Exit" button
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Close all the FileReaders and BufferedReaders and then close and exit the JavaFX application
                closeReaders();
                Platform.exit();
            }
        });
    }


    // Returns absolute path for the specified file
    private static String getFileName(String currentFile) {
        return createAbsolutePath(currentFile);
    }


    // Creates and returns a String with the full absolute file path
    private static String createAbsolutePath(String fileName) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        return s + "\\" + fileName + ".txt";
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


    // Generates a random int value from 0 - 25
    private static int generateKeyValue() {
        Random dice = new Random();
        return dice.nextInt(26);
    }


    // Prints int shift value to the key text file
    private static boolean printKeyValueToFile(String fileName, int shiftValue) {
        try (FileWriter fw = new FileWriter(fileName, true);
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
    private static boolean printTextToFile(String fileName, String text) {
        try (FileWriter fw = new FileWriter(fileName, true);
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


    // Loops int n times, every loop generates a key value and prints that value to key text file
    private static void generateAndPrintKeyValuesNTimes(String fileName, int n) {
        for (int i = 0; i < n; i++) {
            printKeyValueToFile(fileName, generateKeyValue());
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


    // Shifts uppercase letters around a wrapped alphabet from A - Z to encrypt them
    private static int encryptUppercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 90) ? (((charValue + shiftValue) - 91) + 65) : (charValue + shiftValue);
    }


    // Shifts lowercase letters around a wrapped alphabet from a - z to encrypt them
    private static int encryptLowercaseLetter(int charValue, int shiftValue) {
        return (charValue + shiftValue > 122) ? (((charValue + shiftValue) - 123) + 97) : (charValue + shiftValue);
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


    // Reads in the current line from a File using a BufferedReader and returns the String
    private static String getCurrentLineFromFile(String fileName) throws IOException {
        return bufferedReaderKey.readLine();
    }


    // Reads in the current line from the key file and returns the String as an int
    private static int getCurrentKeyValueFromFile() throws IOException {
        return Integer.parseInt(getCurrentLineFromFile("key"));
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


    // Closes FileReader and BufferedReader
    private static void closeReaders() {
        try {
            fileReaderKey.close();
            bufferedReaderKey.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // Encrypt or decrypt text based on the shouldEncrypt parameter and print it to the appropriate TextArea
    private static void encryptOrDecrypt(TextArea textAreaGet, TextArea textAreaPost, boolean shouldEncrypt) {
        // Gets the text from the TextArea and stores it
        String text = textAreaGet.getText();
        // Create and initialize StringBuilder to build the output text
        StringBuilder stringBuilder = new StringBuilder();
        // Create and initialize char variable to keep track of the current char being shifted
        char currentChar = ' ';
        // Create and initialize int variable to keep track of the current shift value
        // to encrypt or decrypt the current char
        int currentShiftValue = 0;
        // Create and initialize char variable to keep track of the current shifted char
        // to append to the StringBuilder to make the output text String
        char currentShiftedChar = ' ';

        // for loop to loop through all the chars in the text String to shift them one at a time
        for (int i = 0; i < text.length(); i++) {
            // Get the current char being shifted in the text String
            currentChar = text.charAt(i);

            // try / catch block to try to get the current int shift value from the key text file and
            // store it in the currentShiftValue variable.
            // Print the IOException to the console if there is an error
            try {
                currentShiftValue = getCurrentKeyValueFromFile();
            } catch (IOException e) {
                System.out.println(e);
            }

            if (shouldEncrypt) {
                // Encrypt the current char and append it to the StringBuilder to build the
                // encrypted message text String
                currentShiftedChar = encryptCurrentChar((int) currentChar, currentShiftValue);
                stringBuilder.append(currentShiftedChar);
            } else {
                // Decrypt the current char and append it to the StringBuilder to build the
                // decrypted message text String
                currentShiftedChar = decryptCurrentChar((int) currentChar, currentShiftValue);
                stringBuilder.append(currentShiftedChar);
            }
        }

        // Clear the TextArea and then print the text inside the appropriate TextArea
        textAreaPost.clear();
        textAreaPost.setText(stringBuilder.toString());

        // Reset the BufferedReader so the next time it is used it starts from the beginning of
        // the key text file again
        resetKeyBufferedReader();
    }


    // Creates a text file to save the respective original message or encrypted message
    private static void saveTextToFile(String fileName, TextArea textArea) {
        // Create the absolute path for the text file and create the text file
        String fileNameToSaveTo = createAbsolutePath(fileName);
        createFile(fileNameToSaveTo);

        // Get the text from the TextArea and save it to the text file
        String messageToSave = textArea.getText();
        printTextToFile(fileNameToSaveTo, messageToSave);
    }
}
