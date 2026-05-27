package com.campuslite.domain;

/**
 * Evaluación tipo examen escrito.
 */
public class WrittenExam extends Evaluation {

    public WrittenExam(String evaluationName,
                       double score,
                       double percentage) {

        super(evaluationName, score, percentage);
    }

    /**
     * Método sobrescrito.
     */
    @Override
    public double calculateContribution() {

        return (getScore() * getPercentage()) / 100.0;
    }

    @Override
    public String toString() {

        return "Examen - " + getEvaluationName();
    }
    @Override
    public String getTypeName() {

        return "Examen";
    }

}