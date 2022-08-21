package com.github.bb9leko.ifood.cadastro;

import javax.persistence.*;

@Entity
@Table(name = "prato")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    public String nome;

    public String descricao;

    @ManyToOne
    public Restaurante restaurante;
}
