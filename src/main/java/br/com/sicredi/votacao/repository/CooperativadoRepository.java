package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.Cooperativado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperativadoRepository extends JpaRepository<Cooperativado, Long> {
}
