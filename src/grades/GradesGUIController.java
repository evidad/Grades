/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package grades;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 *
 * @author errol
 */
public class GradesGUIController implements Initializable {

    private ArrayList<String> m_gradesFromFile;

    private FileHandler fileHandler;

    private Map<String, Double> gradeToGPA;

    @FXML
    private Label GPALabel;

    @FXML
    private Button calculateGPAButton;

    @FXML
    private Button readGradeFilesButton;

    @FXML
    private ComboBox<String> gradesBox;

    @FXML
    private Button readGradesButton;

    @FXML
    private Button calcGPAButton;

    @FXML
    private TextField readGradesTextField;
    
    @FXML
    private Button okButton;
    
    @FXML
    private Button minimizeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_gradesFromFile = new ArrayList<>();

        fileHandler = new FileHandler("grades_read.txt", "grades_write.txt");

        gradeToGPA = new HashMap<>();
        gradeToGPA.put("A", 4.0);
        gradeToGPA.put("A-", 3.7);
        gradeToGPA.put("B+", 3.3);
        gradeToGPA.put("B", 3.0);
        gradeToGPA.put("B-", 2.7);
        gradeToGPA.put("C+", 2.3);
        gradeToGPA.put("C", 2.0);
        gradeToGPA.put("C-", 1.7);
        gradeToGPA.put("D+", 1.3);
        gradeToGPA.put("D", 1.0);
        gradeToGPA.put("D-", 0.7);
        gradeToGPA.put("F", 0.0);

    }

    @FXML
    private void handleReadGrades(ActionEvent event) {
        try {
            String readFileName = readGradesTextField.getText();

            if (!readFileName.isEmpty()) {
                fileHandler = new FileHandler(readFileName, "grades_write.txt");

                // First, open the file for reading
                boolean fileOpened = fileHandler.openFileandRead();

                if (fileOpened) {
                    boolean fileRead = fileHandler.readFile(m_gradesFromFile);

                    if (fileRead) {
                        gradesBox.getItems().clear();
                        gradesBox.getItems().addAll(m_gradesFromFile);
                        GPALabel.setText("Grades loaded successfully.");
                    } else {
                        GPALabel.setText("Error reading file.");
                    }
                } else {
                    GPALabel.setText("Error opening file.");
                }
            } else {
                GPALabel.setText("Please enter a valid file name.");
            }
        } catch (Exception e) {
            GPALabel.setText("Error reading file.");
            e.printStackTrace();
        }
    }
    @FXML
    private String convertGradeToGPA(String grade) {
        if (gradeToGPA.containsKey(grade)) {
            return String.valueOf(gradeToGPA.get(grade));
        } else {
            return "Invalid grade";
        }
    }
    
    @FXML
    private void handleCalcGPA(ActionEvent event) {
        String selectedGrade = gradesBox.getSelectionModel().getSelectedItem();

        if (selectedGrade != null) {
            String gpa = convertGradeToGPA(selectedGrade);
            GPALabel.setText("GPA: " + gpa);
        } else {
            GPALabel.setText("Please select a grade.");
        }
    }
    
    @FXML
    private void handleOKPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMinimizePressed(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
