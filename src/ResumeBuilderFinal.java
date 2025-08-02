import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.imageio.ImageIO;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;

public class ResumeBuilderFinal {
    JFrame frame;
    JTextField nameField, designationField, locationField, emailField, phoneField;
    JComboBox<String> countryCodeBox;
    JTextArea profileInfoArea, educationArea, technicalSkillsArea, softSkillsArea,
              projectsArea, experienceArea, languagesArea, interestsArea;
    ArrayList<JTextField> platformNames = new ArrayList<>();
    ArrayList<JTextField> platformLinks = new ArrayList<>();
    JPanel platformPanel;

    public ResumeBuilderFinal() {
        frame = new JFrame("Resume Builder");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 2));

        nameField = new JTextField();
        designationField = new JTextField();
        locationField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        countryCodeBox = new JComboBox<>(new String[]{"+91", "+1", "+44"});

        profileInfoArea = new JTextArea();
        educationArea = new JTextArea();
        technicalSkillsArea = new JTextArea();
        softSkillsArea = new JTextArea();
        projectsArea = new JTextArea();
        experienceArea = new JTextArea();
        languagesArea = new JTextArea();
        interestsArea = new JTextArea();

        platformPanel = new JPanel();
        platformPanel.setLayout(new GridLayout(0, 2));
        addPlatformField();

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Designation:"));
        frame.add(designationField);
        frame.add(new JLabel("Location:"));
        frame.add(locationField);
        frame.add(new JLabel("Country Code:"));
        frame.add(countryCodeBox);
        frame.add(new JLabel("Phone:"));
        frame.add(phoneField);
        frame.add(new JLabel("Email:"));
        frame.add(emailField);

        frame.add(new JLabel("Profile Info:"));
        frame.add(profileInfoArea);
        frame.add(new JLabel("Education:"));
        frame.add(educationArea);
        frame.add(new JLabel("Technical Skills:"));
        frame.add(technicalSkillsArea);
        frame.add(new JLabel("Soft Skills:"));
        frame.add(softSkillsArea);
        frame.add(new JLabel("Projects:"));
        frame.add(projectsArea);
        frame.add(new JLabel("Experience:"));
        frame.add(experienceArea);
        frame.add(new JLabel("Languages:"));
        frame.add(languagesArea);
        frame.add(new JLabel("Interests:"));
        frame.add(interestsArea);
        frame.add(new JLabel("Platforms (name/link):"));
        frame.add(platformPanel);

        JButton addPlatformBtn = new JButton("Add Platform");
        addPlatformBtn.addActionListener(e -> addPlatformField());
        frame.add(addPlatformBtn);

        JButton saveBtn = new JButton("Save as TXT");
        saveBtn.addActionListener(e -> saveAsText());
        frame.add(saveBtn);

        JButton exportBtn = new JButton("Export to PDF");
        exportBtn.addActionListener(e -> exportToPDF());
        frame.add(exportBtn);

        JButton atsBtn = new JButton("Check ATS Score");
        atsBtn.addActionListener(e -> showATSScore());
        frame.add(atsBtn);

        frame.setVisible(true);
    }

    private void addPlatformField() {
        JTextField name = new JTextField("Platform");
        JTextField link = new JTextField("Link");
        platformNames.add(name);
        platformLinks.add(link);
        platformPanel.add(name);
        platformPanel.add(link);
        frame.revalidate();
    }

    private void saveAsText() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Resume.txt"));
            writer.write(getFormattedText());
            writer.close();
            JOptionPane.showMessageDialog(frame, "Saved to Resume.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFormattedText() {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append(nameField.getText() + "\n");
        sb.append(designationField.getText() + "\n");
        sb.append(locationField.getText() + "\n");
        sb.append("==============================\n");
        sb.append("Phone: " + countryCodeBox.getSelectedItem() + " " + phoneField.getText() + "\n");
        sb.append("Email: " + emailField.getText() + "\n");
        for (int i = 0; i < platformNames.size(); i++) {
            sb.append(platformNames.get(i).getText() + ": " + platformLinks.get(i).getText() + "\n");
        }
        sb.append("\n------------ PROFILE INFO ------------\n");
        sb.append(profileInfoArea.getText() + "\n");
        sb.append("------------ EDUCATION ------------\n");
        sb.append(educationArea.getText() + "\n");
        sb.append("------------ SKILLS ------------\n");
        sb.append("Technical:\n" + technicalSkillsArea.getText() + "\nSoft:\n" + softSkillsArea.getText() + "\n");
        sb.append("------------ PROJECTS ------------\n");
        sb.append(projectsArea.getText() + "\n");
        sb.append("------------ EXPERIENCE ------------\n");
        sb.append(experienceArea.getText() + "\n");
        sb.append("------------ LANGUAGES ------------\n");
        sb.append(languagesArea.getText() + "\n");
        sb.append("------------ INTERESTS ------------\n");
        sb.append(interestsArea.getText());
        return sb.toString();
    }

    private void exportToPDF() {
        if (!validateFields()) return;

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Resume.pdf"));
            document.open();

            Image bg = Image.getInstance("bg.png");
            bg.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            bg.setAbsolutePosition(0, 0);
            document.add(bg);

            document.newPage();
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.WHITE);

            document.add(new Paragraph(nameField.getText(), headerFont));
            document.add(new Paragraph(designationField.getText(), regularFont));
            document.add(new Paragraph(locationField.getText(), regularFont));
            document.add(new Paragraph("Phone: " + countryCodeBox.getSelectedItem() + " " + phoneField.getText(), regularFont));
            document.add(new Paragraph("Email: " + emailField.getText(), regularFont));
            for (int i = 0; i < platformNames.size(); i++) {
                document.add(new Paragraph(platformNames.get(i).getText() + ": " + platformLinks.get(i).getText(), regularFont));
            }
            document.add(new Paragraph("\nProfile Info:\n" + profileInfoArea.getText(), regularFont));
            document.add(new Paragraph("\nEducation:\n" + educationArea.getText(), regularFont));
            document.add(new Paragraph("\nTechnical Skills:\n" + technicalSkillsArea.getText(), regularFont));
            document.add(new Paragraph("\nSoft Skills:\n" + softSkillsArea.getText(), regularFont));
            document.add(new Paragraph("\nProjects:\n" + projectsArea.getText(), regularFont));
            document.add(new Paragraph("\nExperience:\n" + experienceArea.getText(), regularFont));
            document.add(new Paragraph("\nLanguages:\n" + languagesArea.getText(), regularFont));
            document.add(new Paragraph("\nInterests:\n" + interestsArea.getText(), regularFont));

            document.close();
            JOptionPane.showMessageDialog(frame, "Exported to Resume.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (nameField.getText().isEmpty() || designationField.getText().isEmpty() || locationField.getText().isEmpty()
                || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || profileInfoArea.getText().isEmpty()
                || educationArea.getText().isEmpty() || technicalSkillsArea.getText().isEmpty()
                || softSkillsArea.getText().isEmpty() || projectsArea.getText().isEmpty()
                || experienceArea.getText().isEmpty() || languagesArea.getText().isEmpty()
                || interestsArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.");
            return false;
        }
        if (!emailField.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return false;
        }
        if (!phoneField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Phone must be 10 digits.");
            return false;
        }
        boolean validPlatform = false;
        for (int i = 0; i < platformNames.size(); i++) {
            if (!platformNames.get(i).getText().isEmpty() && !platformLinks.get(i).getText().isEmpty()) {
                validPlatform = true;
                break;
            }
        }
        if (!validPlatform) {
            JOptionPane.showMessageDialog(frame, "At least one platform is required.");
            return false;
        }
        return true;
    }

    private void showATSScore() {
        String allText = getFormattedText().toLowerCase();
        int score = 0;
        String[] keywords = {"c", "c++", "java", "ai", "machine learning", "git", "github", "oop", "canva", "python"};
        for (String key : keywords) {
            if (allText.contains(key)) score += 10;
        }
        if (score > 100) score = 100;
        JOptionPane.showMessageDialog(frame, "Your ATS Score: " + score + "%");
    }

    public static void main(String[] args) {
        new ResumeBuilderFinal();
    }
}
