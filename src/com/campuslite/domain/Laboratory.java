package com.campuslite.domain;

/**
 * Evaluación tipo laboratorio.
 */
public class Laboratory extends Evaluation {

    public Laboratory(String evaluationName,
                      double score,
                      double percentage) {

        super(evaluationName, score, percentage);
    }

    @Override
    public double calculateContribution() {

        return (getScore() * getPercentage()) / 100.0;
    }

    @Override
    public String toString() {

        return "Laboratorio - " + getEvaluationName();
    }
    @Override
    public String getTypeName() {

        return "Laboratorio";
    }

}