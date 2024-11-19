package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Acessorio {

    private Long id;
    private String nome;

}
