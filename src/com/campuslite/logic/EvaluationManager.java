package com.campuslite.logic;

import com.campuslite.domain.Course;
import com.campuslite.domain.Evaluation;

import java.util.List;

/**
 * Maneja evaluaciones.
 */
public class EvaluationManager {

    /**
     * Agrega evaluación a un curso.
     */
    public void addEvaluationToCourse(Course course,
                                      Evaluation evaluation) {

        validatePercentage(course, evaluation);

        course.addEvaluation(evaluation);
    }

    /**
     * Elimina evaluación.
     */
    public void removeEvaluation(Course course,
                                 Evaluation evaluation) {

        course.getEvaluations().remove(evaluation);
    }

    /**
     * Obtiene evaluaciones.
     */
    public List<Evaluation> getEvaluations(Course course) {

        return course.getEvaluations();
    }

    /**
     * Valida que el porcentaje total no supere 100.
     */
    private void validatePercentage(Course course,
                                    Evaluation evaluation) {

        double total = course.getTotalPercentage()
                + evaluation.getPercentage();

        if (total > 100) {
            throw new IllegalArgumentException(
                    "La suma de porcentajes no puede superar 100%."
            );
        }
    }

}