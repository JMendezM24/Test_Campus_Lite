package com.campuslite.persistence;

import com.campuslite.domain.Student;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia CSV de estudiantes.
 */
public class StudentCSVRepository {

    /**
     * Guarda estudiantes en CSV.
     */
    public void saveStudents(List<Student> students) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(FilePaths.STUDENTS_FILE))) {

            // Encabezado CSV
            writer.println("code,firstName,lastName,email");

            for (Student student : students) {

                writer.println(
                        student.getStudentCode() + "," +
                        student.getFirstName() + "," +
                        student.getLastName() + "," +
                        student.getEmail()
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar estudiantes.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    /**
     * Lee estudiantes desde CSV.
     */
    public List<Student> loadStudents() {

        List<Student> students = new ArrayList<>();

        File file = new File(FilePaths.STUDENTS_FILE);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            // Saltar encabezado
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length >= 4) {

                    Student student = new Student(
                            data[0],
                            data[1],
                            data[2],
                            data[3]
                    );

                    students.add(student);
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar estudiantes.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }

        return students;
    }

}