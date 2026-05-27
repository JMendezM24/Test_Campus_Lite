package com.campuslite.logic;

import com.campuslite.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Maneja toda la lógica de estudiantes.
 */
public class StudentManager {

    private final List<Student> students;

    public StudentManager() {

        students = new ArrayList<>();
    }

    /**
     * Registra un estudiante.
     */
    public void addStudent(Student student) {

        validateDuplicateCode(student.getStudentCode());

        students.add(student);
    }

    /**
     * Sobrecarga de método.
     */
    public void addStudent(String code,
                           String firstName,
                           String lastName) {

        Student student = new Student(code, firstName, lastName);

        addStudent(student);
    }

    /**
     * Elimina un estudiante.
     */
    public void removeStudent(String code) {

        Student student = findStudentByCode(code);

        if (student != null) {
            students.remove(student);
        }
    }

    /**
     * Busca estudiante por carnet.
     */
    public Student findStudentByCode(String code) {

        for (Student student : students) {

            if (student.getStudentCode().equalsIgnoreCase(code)) {
                return student;
            }
        }

        return null;
    }

    /**
     * Actualiza estudiante.
     */
    public void updateStudent(String originalCode,
                              Student updatedStudent) {

        for (Student current : students) {

            if (current.getStudentCode()
                    .equals(originalCode)) {

                /**
                 * Validar duplicados.
                 */
                for (Student other : students) {

                    if (other != current
                            &&
                            other.getStudentCode()
                                    .equalsIgnoreCase(
                                            updatedStudent
                                                    .getStudentCode()
                                    )) {

                        throw new IllegalArgumentException(
                                "Ya existe un carnet."
                        );
                    }
                }

                /**
                 * Actualizar MISMO objeto.
                 */
                current.setStudentCode(
                        updatedStudent.getStudentCode()
                );

                current.setFirstName(
                        updatedStudent.getFirstName()
                );

                current.setLastName(
                        updatedStudent.getLastName()
                );
                
                current.refreshEmail();
                
                return;
            }
        }

        throw new IllegalArgumentException(
                "Estudiante no encontrado."
        );
    }

    /**
     * Retorna todos los estudiantes.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Valida carnet duplicado.
     */
    private void validateDuplicateCode(String code) {

        if (findStudentByCode(code) != null) {
            throw new IllegalArgumentException("Ya existe un estudiante con ese carnet.");
        }
    }
    
    

}