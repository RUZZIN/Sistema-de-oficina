package com.sistemaOficina.backend.entidade;
import lombok.Data;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Oficina {
        
        private Long id;
        private String nome;
        private String endereco;
        private String telefone;
        private String email;

    
}
