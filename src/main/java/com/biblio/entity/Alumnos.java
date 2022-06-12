package com.biblio.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Alumnos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "registro")
    private int registro;
    @Basic
    @Column(name = "curso")
    private String curso;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @Basic
    @Column(name = "casa")
    private String casa;
    @OneToMany(mappedBy = "alumnosByCodAlumno")
    private Collection<Prestamos> prestamosByRegistro;

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumnos alumnos = (Alumnos) o;
        return registro == alumnos.registro && Objects.equals(curso, alumnos.curso) && Objects.equals(nombre, alumnos.nombre) && Objects.equals(apellido, alumnos.apellido) && Objects.equals(casa, alumnos.casa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registro, curso, nombre, apellido, casa);
    }

    public Collection<Prestamos> getPrestamosByRegistro() {
        return prestamosByRegistro;
    }

    public void setPrestamosByRegistro(Collection<Prestamos> prestamosByRegistro) {
        this.prestamosByRegistro = prestamosByRegistro;
    }
}
