package com.campuslite.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Relación estudiante-curso.
 */
public class Enrollment {

    private Student student;
    private Course course;

    private List<Evaluation> evaluations;

    public Enrollment(Student student,
                      Course course) {

        this.student = student;
        this.course = course;

        evaluations = new ArrayList<>();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    /**
     * Agrega evaluación.
     */
    public void addEvaluation(
            Evaluation evaluation
    ) {

        if (evaluation == null) {
            return;
        }

        validatePercentage(evaluation);

        evaluations.add(evaluation);
    }

    /**
     * Actualiza evaluación.
     */
    public void updateEvaluation(
            int index,
            Evaluation newEvaluation
    ) {

        if (index < 0
                || index >= evaluations.size()) {

            return;
        }

        double total = 0;

        for (int i = 0;
             i < evaluations.size();
             i++) {

            if (i == index) {
                continue;
            }

            total += evaluations.get(i)
                    .getPercentage();
        }

        total += newEvaluation.getPercentage();

        if (total > 100) {

            throw new IllegalArgumentException(
                    "La suma de porcentajes no puede superar 100%."
            );
        }

        evaluations.set(index, newEvaluation);
    }

    /**
     * Elimina evaluación.
     */
    public void removeEvaluation(
            Evaluation evaluation
    ) {

        evaluations.remove(evaluation);
    }

    /**
     * Calcula promedio final.
     */
    public double calculateAverage() {

        double total = 0;

        for (Evaluation evaluation :
                evaluations) {

            total +=
                    evaluation.calculateContribution();
        }

        return total;
    }

    /**
     * Valida porcentaje total.
     */
    public void validatePercentage(
            Evaluation evaluation
    ) {

        double total = 0;

        for (Evaluation current :
                evaluations) {

            total += current.getPercentage();
        }

        total += evaluation.getPercentage();

        if (total > 100) {

            throw new IllegalArgumentException(
                    "La suma de porcentajes no puede superar 100%."
            );
        }
    }

    @Override
    public String toString() {

        return getEnrollmentCode()
                + " | "
                + student.getFirstName()
                + " "
                + student.getLastName()
                + " | "
                + course.getName();
    }

    /**
     * Genera código dinámico de inscripción.
     */
    public String getEnrollmentCode() {

        int year =
                java.time.LocalDate.now()
                        .getYear();

        return student.getStudentCode()
                + "-"
                + year
                + "-"
                + course.getCourseCode();
    }
}