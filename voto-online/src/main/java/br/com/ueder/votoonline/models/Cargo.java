package br.com.ueder.votoonline.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String controle;
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 80)
    private String descricao;
    @Column
    private boolean excluido = false;

    public Cargo() {
    }

    public Cargo(Long id, String descricao) {
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

    public boolean isExcluido() {
        return excluido;
    }

    public void excluir(){
        this.excluido = true;
    }
}
