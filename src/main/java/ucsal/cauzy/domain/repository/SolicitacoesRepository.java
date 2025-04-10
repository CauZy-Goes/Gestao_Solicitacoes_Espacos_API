package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;

import java.util.List;
import java.util.Optional;

public interface SolicitacoesRepository extends JpaRepository<Solicitacoes, Integer>, JpaSpecificationExecutor<Solicitacoes> {

    // Buscar todas as solicitações feitas por um professor específico
     List<Solicitacoes> findByUsuarioSolicitante_IdUsuario(Integer idUsuarioSolicitante);

    List<Solicitacoes> findByStatus(Status status);

    List<Solicitacoes> findByEspacoFisico(EspacoFisico espacoFisico);

    @Modifying
    @Query("DELETE FROM Solicitacoes s WHERE s.espacoFisico.idEspacoFisico = :idEspaco")
    void deleteByEspacoFisicoId(@Param("idEspaco") Integer idEspaco);

    @Modifying
    @Query("DELETE FROM Solicitacoes s WHERE s.usuarioAvaliador.idUsuario = :idUsuario OR s.usuarioSolicitante.idUsuario = :idUsuario")
    void deleteByUsuarioId(@Param("idUsuario") Integer idUsuario);

    @Modifying
    @Query("SELECT s FROM Solicitacoes s WHERE s.usuarioAvaliador.idUsuario = :idUsuario OR s.usuarioSolicitante.idUsuario = :idUsuario")
    List<Solicitacoes> findByUsuarioId(@Param("idUsuario") Integer idUsuario);
}

