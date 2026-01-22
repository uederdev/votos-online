package br.com.ueder.votoonline.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Setores")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String controle;
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 80)
    private String descricao;

    public Setor() {
    }

    public Setor(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
