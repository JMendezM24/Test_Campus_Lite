package com.campuslite.persistence;

import com.campuslite.domain.Course;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia CSV de cursos.
 */
public class CourseCSVRepository {

    /**
     * Guarda cursos.
     */
    public void saveCourses(List<Course> courses) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(FilePaths.COURSES_FILE))) {

            writer.println("code,name,credits,capacity");

            for (Course course : courses) {

                writer.println(
                        course.getCourseCode() + "," +
                        course.getName() + "," +
                        course.getCredits() + "," +
                        course.getCapacity()
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar cursos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    /**
     * Carga cursos.
     */
    public List<Course> loadCourses() {

        List<Course> courses = new ArrayList<>();

        File file = new File(FilePaths.COURSES_FILE);

        if (!file.exists()) {
            return courses;
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

                    Course course = new Course(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2]),
                            Integer.parseInt(data[3])
                    );

                    courses.add(course);
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar cursos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }

        return courses;
    }

}