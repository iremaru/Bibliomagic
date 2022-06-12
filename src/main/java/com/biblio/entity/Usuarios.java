package com.biblio.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Usuarios {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usuario")
    String usuario;
    @Basic
    @Column(name = "clave")
    String clave;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(usuario, usuarios.usuario) && Objects.equals(clave, usuarios.clave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, clave);
    }
}
