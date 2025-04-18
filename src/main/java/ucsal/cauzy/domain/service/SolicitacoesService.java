package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;
import ucsal.cauzy.rest.validator.SolicitacoesValidator;

import java.util.List;
import java.util.stream.Collectors;

import static ucsal.cauzy.domain.repository.Specs.SolicitacoesSpecs.*;

@Service
@RequiredArgsConstructor
public class SolicitacoesService {

    private final SolicitacoesRepository solicitacoesRepository;

    private final SolicitacoesValidator solicitacoesValidator;

    public Solicitacoes findById(Integer id) {
        solicitacoesValidator.existsSolicitacao(id);
        return solicitacoesRepository.findById(id).get();
    }

    public List<Solicitacoes> findAll(){
        return solicitacoesRepository.findAll();
    }

    public Solicitacoes salvar(Solicitacoes solicitacoes) {
        return solicitacoesRepository.save(solicitacoes);
    }

    public void update (Integer id, Solicitacoes solicitacoes) {
        solicitacoesValidator.existsSolicitacao(id);
        solicitacoes.setIdSolicitacoes(id);
        solicitacoesRepository.save(solicitacoes);
    }

    public void excluir(Integer id) {
        solicitacoesValidator.existsSolicitacao(id);
        solicitacoesRepository.deleteById(id);
    }

    public Page<Solicitacoes> pesquisa(
            Integer usuarioAvaliador,
            Integer usuarioSolicitante,
            Integer espacoFisico,
            Integer status,
            Integer pagina,
            Integer tamanhoPagina){

        Specification specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(usuarioAvaliador != null){
            specs = specs.and(usuarioAvaliadorEqual(usuarioAvaliador));
        }

        if (usuarioSolicitante != null) {
            specs = specs.and(usuarioSolicitanteEqual(usuarioSolicitante));
        }

        if (espacoFisico != null) {
            specs = specs.and(usuarioEspacoFisicoEqual(espacoFisico));
        }

        if (status != null) {
            specs = specs.and(usuarioStatusEqual(status));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return solicitacoesRepository.findAll(specs, pageRequest);
    }
}
