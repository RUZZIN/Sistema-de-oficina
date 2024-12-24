package com.sistemaOficina.backend.entity;
import lombok.Data;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Oficina {
        
        private Long id;
        private String nome;
        private String email;
        private String logradouro;
        private String complemento;
        private String ddi1;
        private String ddd1;
        private String numero1;
        private String ddi2;
        private String ddd2;
        private String numero2;
    
}
