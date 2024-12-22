package com.sistemaOficina.backend.core.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100) 
    private String username;

    @Column(name = "senha", nullable = false, unique = false , length = 100)
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = false, length = 25)
    private Role role;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "data_atualizacao", nullable = true)
    private String criadoPor;

    @Column(name = "data_atualizacao", nullable = true)
    private String atualizadoPor;

    public enum Role {
        ROLE_ADMIN, ROLE_USER
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + "]";
    }


    
}
