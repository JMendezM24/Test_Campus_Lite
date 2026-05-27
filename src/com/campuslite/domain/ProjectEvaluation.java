package com.campuslite.domain;

/**
 * Evaluación tipo proyecto.
 */
public class ProjectEvaluation extends Evaluation {

    public ProjectEvaluation(String evaluationName,
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

        return "Proyecto - " + getEvaluationName();
    }
    @Override
    public String getTypeName() {

        return "Proyecto";
    }

}