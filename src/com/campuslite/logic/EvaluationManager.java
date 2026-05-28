package com.campuslite.logic;

import com.campuslite.domain.Enrollment;
import com.campuslite.domain.Evaluation;

import java.util.List;

/**
 * Maneja evaluaciones.
 */
public class EvaluationManager {

    /**
     * Agrega evaluación a inscripción.
     */
    public void addEvaluationToEnrollment(
            Enrollment enrollment,
            Evaluation evaluation
    ) {

        validatePercentage(
                enrollment,
                evaluation
        );

        enrollment.addEvaluation(evaluation);
    }

    /**
     * Elimina evaluación.
     */
    public void removeEvaluation(
            Enrollment enrollment,
            Evaluation evaluation
    ) {

        enrollment.removeEvaluation(evaluation);
    }

    /**
     * Obtiene evaluaciones.
     */
    public List<Evaluation> getEvaluations(
            Enrollment enrollment
    ) {

        return enrollment.getEvaluations();
    }

    /**
     * Valida porcentaje total.
     */
    private void validatePercentage(
            Enrollment enrollment,
            Evaluation evaluation
    ) {

        double total = 0;

        for (Evaluation current :
                enrollment.getEvaluations()) {

            total += current.getPercentage();
        }

        total += evaluation.getPercentage();

        if (total > 100) {

            throw new IllegalArgumentException(
                    "La suma de porcentajes no puede superar 100%."
            );
        }
    }
}