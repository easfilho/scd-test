package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query("select sessao from SessaoVotacao sessao inner join fetch sessao.pauta " +
            "where sessao.id = :id")
    Optional<SessaoVotacao> findByIdWithPauta(@Param("id") Long id);
}
