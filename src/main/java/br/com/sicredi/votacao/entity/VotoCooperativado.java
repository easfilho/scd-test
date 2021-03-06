package br.com.sicredi.votacao.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VotoCooperativado {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
