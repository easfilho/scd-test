package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessacaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
}
