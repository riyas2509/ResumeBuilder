# ResumeBuilder-Java

A Java Swing-based Resume Builder desktop application with:

- Clean GUI interface
- Form field validations (email, phone, required sections)
- ATS Score Checker (based on keyword and section match)
- Export as `.txt` file (PDF export coming soon)

---

## ✨ Features

- 🖥️ Java Swing GUI for interactive form input
- 📄 Save Resume as `.txt` file (with all fields included)
- 🧠 ATS Score Checker: Section presence + keyword match
- 📲 Dynamic platform rows (e.g., GitHub, Portfolio)
- ✅ Input validation: 
  - Email must end with `@gmail.com`
  - Phone must be 10-digit
  - All sections (except Extra Skills) are mandatory

---

## 📷 GUI Preview

> (Add screenshots of your app's GUI here in the future)

---

## 🧪 ATS Score Logic

- ✅ **Sections** (Profile, Education, Projects, etc.): +4 pts each
- ✅ **Keywords** (Java, Python, Canva, DSA...): +3 pts each
- ✅ **Platforms** (GitHub, Portfolio...): up to +7 pts each
- Max score = **100**

---

## 📁 Folder Structure

ResumeBuilder-Java/
├── src/ # Java source code
├── assets/ # Images / background for PDF
├── lib/ # iText JAR for PDF export (optional)
├── output/ # Exported files
└── README.md


---

## 🚀 How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/YOUR_USERNAME/ResumeBuilder-Java.git
   cd ResumeBuilder-Java
Open in BlueJ or any IDE supporting Java Swing

Compile and run ResumeBuilder.java

🔧 Dependencies
Java 8 or above

Swing (comes with JDK)

(Optional) iText PDF library if PDF export is enabled

📦 Future Enhancements
 Export as PDF with modern CV layout

 Color selection or theme options

 Import/export JSON resume data

 Improved ATS score breakdown with visual chart

📄 License
This project is licensed under the MIT License

🙋‍♀️ Developed By
Riya Shah
ResumeBuilder Java Project

