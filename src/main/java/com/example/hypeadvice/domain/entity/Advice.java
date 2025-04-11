package com.example.hypeadvice.domain.entity;

import com.google.gson.annotations.Expose;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@javax.persistence.Entity
@Table(name = "advice")
public class Advice extends Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Expose
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(name = "NOME", length = 100)
    private String nome;

    @Expose
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres.")
    @Column(name = "DESCRICAO", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull(message = "O tipo de conselho é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false)
    private TipoConselho tipo;

    public Advice(String adviceStr) {
        this.descricao = adviceStr;
    }

    public Advice() {
    }
}
