package br.com.sicredi.votacao.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean voto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSessaoVotacao")
    private SessaoVotacao sessaoVotacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCooperativado")
    private Cooperativado cooperativado;

    public Boolean foiDeCooperativado(Cooperativado cooperativado) {
        return this.cooperativado.getId().equals(cooperativado.getId());
    }
}
