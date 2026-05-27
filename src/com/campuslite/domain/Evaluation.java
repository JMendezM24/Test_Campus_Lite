package com.campuslite.domain;

/**
 * Clase abstracta Evaluation.
 * Aquí aplicamos herencia, abstracción y polimorfismo.
 */
public abstract class Evaluation {

    private String evaluationName;
    private double score;
    private double percentage;

    public Evaluation() {
    }
    /**
     * Nombre visible del tipo.
     */
    public abstract String getTypeName();

    public Evaluation(String evaluationName,
                      double score,
                      double percentage) {

        setEvaluationName(evaluationName);
        setScore(score);
        setPercentage(percentage);
    }

    public String getEvaluationName() {
        return evaluationName;
    }

    public void setEvaluationName(String evaluationName) {

        if (evaluationName == null || evaluationName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la evaluación es obligatorio.");
        }

        this.evaluationName = evaluationName.trim();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100.");
        }

        this.score = score;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {

        if (percentage <= 0 || percentage > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 1 y 100.");
        }

        this.percentage = percentage;
    }

    /**
     * Método abstracto obligatorio.
     */
    public abstract double calculateContribution();

}