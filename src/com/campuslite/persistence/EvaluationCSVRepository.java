package com.campuslite.persistence;

import com.campuslite.domain.*;

import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * Persistencia CSV de evaluaciones.
 */
public class EvaluationCSVRepository {

    /**
     * Guarda evaluaciones.
     */
    public void saveEvaluations(List<Course> courses) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     FilePaths.EVALUATIONS_FILE
                             ))) {

            writer.println(
                    "courseCode,type,name,score,percentage"
            );

            for (Course course : courses) {

                for (Evaluation evaluation :
                        course.getEvaluations()) {

                    writer.println(
                            course.getCourseCode() + "," +
                            evaluation.getTypeName() + "," +
                            evaluation.getEvaluationName() + "," +
                            evaluation.getScore() + "," +
                            evaluation.getPercentage()
                    );
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar evaluaciones.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    /**
     * Carga evaluaciones desde CSV.
     */
    public void loadEvaluations(List<Course> courses) {

        File file = new File(
                FilePaths.EVALUATIONS_FILE
        );

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            /**
             * Saltar encabezado.
             */
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String courseCode = data[0];

                String type = data[1];

                String name = data[2];

                double score =
                        Double.parseDouble(data[3]);

                double percentage =
                        Double.parseDouble(data[4]);

                /**
                 * Buscar curso correcto.
                 */
                for (Course course : courses) {

                    if (course.getCourseCode()
                            .equals(courseCode)) {

                        Evaluation evaluation =
                                createEvaluation(
                                        type,
                                        name,
                                        score,
                                        percentage
                                );

                        if (evaluation != null) {

                            course.addEvaluation(
                                    evaluation
                            );
                        }

                        break;
                    }
                }
            }

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error cargando evaluaciones.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    /**
     * Crea evaluación según tipo.
     */
    private Evaluation createEvaluation(String type,
                                        String name,
                                        double score,
                                        double percentage) {

        switch (type) {

            case "Examen":

                return new WrittenExam(
                        name,
                        score,
                        percentage
                );

            case "Laboratorio":

                return new Laboratory(
                        name,
                        score,
                        percentage
                );

            case "Proyecto":

                return new ProjectEvaluation(
                        name,
                        score,
                        percentage
                );

            default:
                return null;
        }
    }

}