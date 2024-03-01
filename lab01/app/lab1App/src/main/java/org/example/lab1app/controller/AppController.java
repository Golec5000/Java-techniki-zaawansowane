package org.example.lab1app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.example.checkSum.CheckSumController;
import org.example.zipMaker.ZipFileMaker;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button pathSelectorForEndZipFile;

    @FXML
    private Button pathSelectorForFilesToZipButton;

    @FXML
    private Button zipCreatButton;

    @FXML
    private Button chekValidationButton;

    @FXML
    private TextField selectedPathToSetZipFile;

    @FXML
    private TextField zipFilePathTextField;

    @FXML
    private TextField md5FilePathTextField;

    @FXML
    private TextField zipFileName;

    @FXML
    private TextArea selectedPathsForFilesToZip;

    private final ZipFileMaker zipFileMaker = new ZipFileMaker();

    private final CheckSumController checkSumController = new CheckSumController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupDragAndDropFilesForCreatZip();
        setUpDragAndDropForCheckZip();
        setUpDragAndDropForCheckMD5();

        selectFilesToZip();
        selectPathForZipFile();
        createZip();
        checkZip();

    }

    private void setupDragAndDropFilesForCreatZip() {
        // Set the action when a drag gesture is detected over the TextArea
        selectedPathsForFilesToZip.setOnDragOver(event -> {
            // If the source of the drag gesture is not the TextArea itself and the dragboard contains files
            if (event.getGestureSource() != selectedPathsForFilesToZip && event.getDragboard().hasFiles()) {
                // Accept the drag gesture for copy or move transfer mode
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            // Consume the event to prevent it from being passed to other handlers
            event.consume();
        });

        // Set the action when a drag gesture is dropped over the TextArea
        selectedPathsForFilesToZip.setOnDragDropped(event -> {
            // Get the dragboard
            Dragboard db = event.getDragboard();
            boolean success = false;
            // If the dragboard contains files
            if (db.hasFiles()) {
                success = true;
                // For each file in the dragboard
                for (File file : db.getFiles()) {
                    // Append the file's absolute path to the TextArea
                    selectedPathsForFilesToZip.appendText(file.getAbsolutePath() + "\n");
                }
            }
            // Indicate whether the drop was successful
            event.setDropCompleted(success);
            // Consume the event to prevent it from being passed to other handlers
            event.consume();
        });
    }

    private void setUpDragAndDropForCheckZip(){
        zipFilePathTextField.setOnDragOver(event -> {
            if (event.getGestureSource() != zipFilePathTextField && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        zipFilePathTextField.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    zipFilePathTextField.setText(file.getAbsolutePath());
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void setUpDragAndDropForCheckMD5(){
        md5FilePathTextField.setOnDragOver(event -> {
            if (event.getGestureSource() != md5FilePathTextField && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        md5FilePathTextField.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    md5FilePathTextField.setText(file.getAbsolutePath());
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void selectFilesToZip() {
        pathSelectorForFilesToZipButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                selectedPathsForFilesToZip.appendText(selectedFile.getAbsolutePath() + "\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error selecting file");
                alert.setHeaderText("Error selecting file");
                alert.setContentText("Error selecting file");
                alert.showAndWait();
            }
        });
    }

    private void selectPathForZipFile() {
        pathSelectorForEndZipFile.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                selectedPathToSetZipFile.setText(selectedDirectory.getAbsolutePath());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error selecting directory");
                alert.setHeaderText("Error selecting directory");
                alert.setContentText("Error selecting directory");
                alert.showAndWait();
            }
        });
    }

    private void createZip() {
        zipCreatButton.setOnAction(event -> {

            String zipFileNameText = selectedPathToSetZipFile.getText() + "\\" + zipFileName.getText();

            if (zipFileNameText == null || zipFileNameText.isBlank() || zipFileNameText.isEmpty()) {
                System.out.println("Zip file name is empty");
                return;
            }

            if (!zipFileNameText.endsWith(".zip")) {
                zipFileNameText += ".zip";
            }

            zipFileMaker.setDestinationZipFile(zipFileNameText);

            if (zipFileMaker.universalZipper(selectedPathsForFilesToZip.getText().lines().toList())) {

                checkSumController.generateCheckSum(zipFileNameText);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zip file created");
                alert.setHeaderText("Zip file created");
                alert.setContentText("Zip file created at: " + zipFileNameText);
                alert.showAndWait();

                selectedPathsForFilesToZip.clear();
                selectedPathToSetZipFile.clear();
                zipFileName.clear();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error creating zip file");
                alert.setHeaderText("Error creating zip file");
                alert.setContentText("Error creating zip file");
                alert.showAndWait();
            }
        });
    }

    private void checkZip() {
        chekValidationButton.setOnAction(event -> {
            if (checkSumController.checkSum(zipFilePathTextField.getText(), md5FilePathTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zip file is valid");
                alert.setHeaderText("Zip file is valid");
                alert.setContentText("Zip file is valid");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Zip file is not valid");
                alert.setHeaderText("Zip file is not valid");
                alert.setContentText("Zip file is not valid");
                alert.showAndWait();
            }
        });
    }
}
