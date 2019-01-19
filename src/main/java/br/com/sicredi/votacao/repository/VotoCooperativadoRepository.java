package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.VotoCooperativado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoCooperativadoRepository extends JpaRepository<VotoCooperativado, Long> {
}
