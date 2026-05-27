package com.campuslite.domain;

/**
 * Clase abstracta base para las personas del sistema.
 * Aquí aplicamos herencia y encapsulamiento.
 */
public abstract class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    /**
     * Constructor vacío.
     */
    public Person() {
    }

    /**
     * Constructor principal.
     */
    public Person(String id, String firstName, String lastName, String email) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        this.id = id.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }

        this.lastName = lastName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Correo inválido.");
        }

        this.email = email.trim();
    }

    /**
     * Método abstracto obligatorio para las subclases.
     */
    public abstract String getFullInfo();

}