package com.biblio.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Libros {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo")
    private int codigo;
    @Basic
    @Column(name = "Titulo")
    private String titulo;
    @Basic
    @Column(name = "Autor")
    private String autor;
    @Basic
    @Column(name = "Editorial")
    private String editorial;
    @Basic
    @Column(name = "Asignatura")
    private String asignatura;
    @Basic
    @Column(name = "estado")
    private String estado;
    @Basic
    @Column(name = "disponible")
    private byte disponible;
    @OneToMany(mappedBy = "librosByCodLibros")
    private Collection<Prestamos> prestamosByCodigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte getDisponible() {
        return disponible;
    }

    public void setDisponible(byte disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libros libros = (Libros) o;
        return codigo == libros.codigo && disponible == libros.disponible && Objects.equals(titulo, libros.titulo) && Objects.equals(autor, libros.autor) && Objects.equals(editorial, libros.editorial) && Objects.equals(asignatura, libros.asignatura) && Objects.equals(estado, libros.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, titulo, autor, editorial, asignatura, estado, disponible);
    }

    public Collection<Prestamos> getPrestamosByCodigo() {
        return prestamosByCodigo;
    }

    public void setPrestamosByCodigo(Collection<Prestamos> prestamosByCodigo) {
        this.prestamosByCodigo = prestamosByCodigo;
    }
}
