package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.VotoCooperativado;
import br.com.sicredi.votacao.model.ContagemVotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoCooperativadoRepository extends JpaRepository<VotoCooperativado, Long> {

    @Query("  select count(votoCooperativado.id) as total, votoCooperativado.voto as voto " +
            " from VotoCooperativado votoCooperativado " +
            " inner join votoCooperativado.sessaoVotacao sessao " +
            " where sessao.id = :idSessao " +
            " group by votoCooperativado.voto ")
    List<ContagemVotos> contarVotos(@Param("idSessao") Long idSessao);
}
