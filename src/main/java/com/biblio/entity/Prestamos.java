package com.biblio.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Prestamos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "codAlumno")
    private int codAlumno;
    @Basic
    @Column(name = "codLibros")
    private int codLibros;
    @Basic
    @Column(name = "FechaPrestamo")
    private Date fechaPrestamo;
    @Basic
    @Column(name = "FechaDevolucion")
    private Date fechaDevolucion;
    @Basic
    @Column(name = "estado")
    private String estado;
    @ManyToOne
    @JoinColumn(name = "codAlumno", referencedColumnName = "registro", nullable = false, updatable = false, insertable = false)
    private Alumnos alumnosByCodAlumno;
    @ManyToOne
    @JoinColumn(name = "codLibros", referencedColumnName = "codigo", nullable = false, updatable = false, insertable = false)
    private Libros librosByCodLibros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }

    public int getCodLibros() {
        return codLibros;
    }

    public void setCodLibros(int codLibros) {
        this.codLibros = codLibros;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamos prestamos = (Prestamos) o;
        return id == prestamos.id && codAlumno == prestamos.codAlumno && codLibros == prestamos.codLibros && Objects.equals(fechaPrestamo, prestamos.fechaPrestamo) && Objects.equals(fechaDevolucion, prestamos.fechaDevolucion) && Objects.equals(estado, prestamos.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codAlumno, codLibros, fechaPrestamo, fechaDevolucion, estado);
    }

    public Alumnos getAlumnosByCodAlumno() {
        return alumnosByCodAlumno;
    }

    public void setAlumnosByCodAlumno(Alumnos alumnosByCodAlumno) {
        this.alumnosByCodAlumno = alumnosByCodAlumno;
    }

    public Libros getLibrosByCodLibros() {
        return librosByCodLibros;
    }

    public void setLibrosByCodLibros(Libros librosByCodLibros) {
        this.librosByCodLibros = librosByCodLibros;
    }
}
