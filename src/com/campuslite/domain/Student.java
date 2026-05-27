package com.campuslite.domain;

/**
 * Clase Student.
 * Hereda de Person.
 */
public class Student extends Person {

    private String studentCode;

    /**
     * Constructor vacío.
     */
    public Student() {
    }

    /**
     * Constructor completo.
     */
    public Student(String studentCode, String firstName, String lastName) {

        super(
                studentCode,
                firstName,
                lastName,
                generateEmail(
                        firstName,
                        lastName,
                        studentCode
                )        );

        setStudentCode(studentCode);
    }

    /**
     * Sobrecarga de constructor.
     */
    public Student(String studentCode,
                   String firstName,
                   String lastName,
                   String email) {

        super(studentCode, firstName, lastName, email);

        setStudentCode(studentCode);
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {

        if (studentCode == null || studentCode.trim().isEmpty()) {
            throw new IllegalArgumentException("El carnet no puede estar vacío.");
        }

        this.studentCode = studentCode.trim();
    }

    /**
     * Genera automáticamente el correo institucional.
     */
    private static String generateEmail(String firstName,
            String lastName,
            String studentCode) {

        String cleanName = firstName
                .trim()
                .toLowerCase()
                .replace(" ", "");

        String cleanLastName = lastName
                .trim()
                .toLowerCase()
                .replace(" ", "");

        return cleanName + "."
        + cleanLastName + "."
        + studentCode
        + "@campuslite.com";    }
    
    public void refreshEmail() {

        setEmail(
                generateEmail(
                        getFirstName(),
                        getLastName(),
                        studentCode
                )
        );
    }
    
   
    /**
     * Método sobrescrito.
     */
    @Override
    public String getFullInfo() {

        return studentCode + " - "
                + getFirstName() + " "
                + getLastName();
    }

    /**
     * Método toString sobrescrito.
     */
    @Override
    public String toString() {
        return getFullInfo();
    }
    
   

}