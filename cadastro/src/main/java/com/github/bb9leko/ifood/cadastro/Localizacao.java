package com.github.bb9leko.ifood.cadastro;

import javax.persistence.*;
@Entity
@Table(name = "localizacao")
public class Localizacao {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public long id;

    public Double latitude;

    public Double longitude;


}
