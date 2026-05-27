package com.campuslite.domain;

/**
 * Relación estudiante-curso.
 */
public class Enrollment {

    private Student student;

    private Course course;
    private String enrollmentCode;

    public Enrollment(Student student,
                      Course course) {

        this.student = student;

        this.course = course;
        
        generateEnrollmentCode();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
    
    /**
     * Genera código de inscripción.
     */
    private void generateEnrollmentCode() {

        int year =
                java.time.LocalDate.now()
                        .getYear();

        enrollmentCode =
                student.getStudentCode()
                + "-"
                + year
                + "-"
                + course.getCourseCode();
    }
    
    /**
     * Código de inscripción.
     */
    public String getEnrollmentCode() {

        return enrollmentCode;
    }
}