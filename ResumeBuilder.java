import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.text.JTextComponent;

public class ResumeBuilder extends JFrame {
    // GUI Components
    JTextField nameField, designationField, locationField, phoneField, emailField;
    JComboBox<String> countryCodeBox;
    JTextArea profileArea, educationArea, techSkillsArea, softSkillsArea, projectsArea,
              experienceArea, languageArea, interestsArea, extraSkillsArea;
    java.util.List<JTextField> platformNameFields = new ArrayList<>();
    java.util.List<JTextField> platformLinkFields = new ArrayList<>();
    JPanel platformPanel;
    JLabel atsScoreLabel;

    public ResumeBuilder() {
        setTitle("Resume Builder");
        setSize(900, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(880, 1500));
        JScrollPane scrollPaneMain = new JScrollPane(mainPanel);
        scrollPaneMain.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPaneMain, BorderLayout.CENTER);

        String[] labels = {
            "Name", "Designation", "Location", "Country Code", "Phone", "Email",
            "Profile Info", "Education", "Technical Skills", "Soft Skills",
            "Projects", "Experience", "Languages", "Interests", "Extra Skills"
        };

        JTextComponent[] fields = {
            nameField = new JTextField(), designationField = new JTextField(), locationField = new JTextField(),
            null, phoneField = new JTextField(), emailField = new JTextField(),
            profileArea = new JTextArea(), educationArea = new JTextArea(), techSkillsArea = new JTextArea(),
            softSkillsArea = new JTextArea(), projectsArea = new JTextArea(), experienceArea = new JTextArea(),
            languageArea = new JTextArea(), interestsArea = new JTextArea(), extraSkillsArea = new JTextArea()
        };

        int y = 10;
        for (int i = 0; i < labels.length; i++) {
            if (!labels[i].equals("Country Code")) {
                JLabel l = new JLabel(labels[i] + ":");
                l.setBounds(20, y, 120, 25);
                mainPanel.add(l);
            }

            if (labels[i].equals("Country Code")) {
                JLabel phoneLabel = new JLabel("Country Code:");
                phoneLabel.setBounds(20, y, 120, 25);
                mainPanel.add(phoneLabel);

                countryCodeBox = new JComboBox<>(new String[]{"+91", "+1", "+44", "+61", "+81"});

                JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                phonePanel.setBounds(150, y, 700, 30);
                phonePanel.add(countryCodeBox);
                phonePanel.add(phoneField);
                phonePanel.setBackground(Color.WHITE);

                mainPanel.add(phonePanel);
                y += 35;
            } else {
                if (fields[i] instanceof JTextArea) {
                    JScrollPane scroll = new JScrollPane(fields[i]);
                    scroll.setBounds(150, y, 700, 60);
                    ((JTextArea) fields[i]).setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    mainPanel.add(scroll);
                    y += 70;
                } else {
                    fields[i].setBounds(150, y, 700, 25);
                    fields[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    mainPanel.add(fields[i]);
                    y += 35;
                }
            }
        }

        JLabel platformLabel = new JLabel("Platforms (name/link):");
        platformLabel.setBounds(20, y, 160, 25);
        mainPanel.add(platformLabel);

        platformPanel = new JPanel();
        platformPanel.setLayout(new BoxLayout(platformPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(platformPanel);
        scrollPane.setBounds(150, y, 700, 100);
        mainPanel.add(scrollPane);

        addPlatformField(); // Add one default row

        JButton addPlatformBtn = new JButton("Add Platform");
        addPlatformBtn.setBounds(20, y + 110, 150, 25);
        mainPanel.add(addPlatformBtn);

        JButton saveTxtBtn = new JButton("Save as TXT");
        saveTxtBtn.setBounds(180, y + 110, 150, 25);
        mainPanel.add(saveTxtBtn);

        JButton atsBtn = new JButton("Check ATS Score");
        atsBtn.setBounds(500, y + 110, 160, 25);
        mainPanel.add(atsBtn);

        atsScoreLabel = new JLabel("ATS Score: ");
        atsScoreLabel.setBounds(670, y + 110, 180, 25);
        mainPanel.add(atsScoreLabel);

        // Event Listeners
        addPlatformBtn.addActionListener(e -> addPlatformField());
        saveTxtBtn.addActionListener(e -> saveAsTxt());
        atsBtn.addActionListener(e -> checkATSScore());

        setVisible(true);
    }

    private void addPlatformField() {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField name = new JTextField();
        JTextField link = new JTextField();
        name.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        link.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        platformNameFields.add(name);
        platformLinkFields.add(link);
        row.add(name);
        row.add(link);
        platformPanel.add(row);
        platformPanel.revalidate();
        platformPanel.repaint();
    }

    private boolean isValidInput() {
        if (nameField.getText().trim().isEmpty() || designationField.getText().trim().isEmpty()
            || locationField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()
            || emailField.getText().trim().isEmpty() || profileArea.getText().trim().isEmpty()
            || educationArea.getText().trim().isEmpty() || techSkillsArea.getText().trim().isEmpty()
            || softSkillsArea.getText().trim().isEmpty() || projectsArea.getText().trim().isEmpty()
            || experienceArea.getText().trim().isEmpty() || languageArea.getText().trim().isEmpty()
            || interestsArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields except Extra Skills are mandatory.");
            return false;
        }

        if (!phoneField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits.");
            return false;
        }

        if (!emailField.getText().matches("^\\S+@gmail\\.com$")) {
            JOptionPane.showMessageDialog(this, "Email must be valid and end with @gmail.com.");
            return false;
        }

        return true;
    }

    private void saveAsTxt() {
        if (!areFieldsValid() || !isValidInput()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields before exporting.");
            return;
        }

        try (PrintWriter writer = new PrintWriter("Resume.txt")) {
            writer.println("====================================");
            writer.println("              " + nameField.getText());
            writer.println("            " + designationField.getText());
            writer.println("         " + locationField.getText());
            writer.println("====================================");
            writer.println("Phone: " + countryCodeBox.getSelectedItem() + " " + phoneField.getText());
            writer.println("Email: " + emailField.getText());
            writer.println("Insta: riyas_2509");
            writer.println("------------ PROFILE INFO ------------");
            writer.println(profileArea.getText());

            writer.println("------------ EDUCATION ------------");
            writer.println(educationArea.getText());

            writer.println("------------ SKILLS ------------");
            writer.println("Technical Skills: \n" + techSkillsArea.getText());
            writer.println("Soft Skills: \n" + softSkillsArea.getText());
            if (!extraSkillsArea.getText().trim().isEmpty()) {
                writer.println("Extra Skills: \n" + extraSkillsArea.getText());
            }

            writer.println("------------ PROJECTS ------------");
            writer.println(projectsArea.getText());

            writer.println("------------ EXPERIENCE ------------");
            writer.println(experienceArea.getText());

            writer.println("------------ LANGUAGES ------------");
            writer.println(languageArea.getText());

            writer.println("------------ INTERESTS ------------");
            writer.println(interestsArea.getText());

            for (int i = 0; i < platformNameFields.size(); i++) {
                String name = platformNameFields.get(i).getText();
                String link = platformLinkFields.get(i).getText();
                if (!name.isEmpty() && !link.isEmpty()) {
                    writer.println("\nPlatform: " + name + " | Link: " + link);
                }
            }

            JOptionPane.showMessageDialog(this, "Resume saved as TXT successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving TXT file.");
        }
    }

    private void checkATSScore() {
        if (!isValidInput()) return;

        int score = 0;
        String content = (profileArea.getText() + educationArea.getText() + techSkillsArea.getText() +
                          softSkillsArea.getText() + projectsArea.getText() + experienceArea.getText() +
                          languageArea.getText() + interestsArea.getText()).toLowerCase();

        String[] sections = {"profile", "education", "technical", "skills", "projects", "experience", "languages", "interests"};
        String[] keywords = {
            "c", "c++", "java", "python", "ui/ux", "storytelling", "canva", "github",
            "dbms", "dsa", "debugging", "communication", "portfolio", "design", "internship", "open source", "visual"
        };

        int sectionScore = 0;
        for (String s : sections) {
            if (content.contains(s)) sectionScore += 4;
        }

        int keywordScore = 0;
        for (String kw : keywords) {
            if (content.contains(kw)) keywordScore += 3;
        }

        int platformScore = 0;
        for (int i = 0; i < platformNameFields.size(); i++) {
            String name = platformNameFields.get(i).getText().toLowerCase();
            if (!name.isEmpty()) {
                if (name.contains("github") || name.contains("canva") || name.contains("portfolio")) {
                    platformScore += 7;
                } else {
                    platformScore += 5;
                }
            }
        }

        int finalScore = sectionScore + keywordScore + platformScore;
        if (finalScore > 100) finalScore = 100;

        atsScoreLabel.setText("ATS Score: " + finalScore);
        JOptionPane.showMessageDialog(this, "Your ATS Score: " + finalScore);
    }

    public boolean areFieldsValid() {
        if (!emailField.getText().matches("^[\\w.-]+@gmail\\.com$")) return false;
        if (!phoneField.getText().matches("\\d{10}")) return false;

        if (nameField.getText().isEmpty() || profileArea.getText().isEmpty() ||
            experienceArea.getText().isEmpty() || educationArea.getText().isEmpty()) return false;

        if (platformPanel.getComponentCount() < 2) return false;
        return true;
    }

    public static void main(String[] args) {
        new ResumeBuilder();
    }
}