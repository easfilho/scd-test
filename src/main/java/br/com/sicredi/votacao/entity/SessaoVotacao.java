package br.com.sicredi.votacao.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPauta")
    private Pauta pauta;
    private LocalDateTime validade;
    @OneToMany(mappedBy = "sessaoVotacao")
    private List<VotoCooperativado> votosCooperativados;

    public Boolean isAberta() {
        return validade.isAfter(LocalDateTime.now());
    }

    public Boolean cooperativadoAindaNaoVotou(Cooperativado cooperativado) {
        return votosCooperativados.isEmpty() || votosCooperativados.stream()
                .anyMatch(voto -> !voto.foiDeCooperativado(cooperativado));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
