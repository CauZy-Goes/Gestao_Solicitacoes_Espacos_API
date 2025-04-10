package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.AbstractController;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.service.StatusService;
import ucsal.cauzy.rest.dto.StatusDTO;
import ucsal.cauzy.rest.mapper.StatusMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("status")
@RequiredArgsConstructor
@Tag(name = "Status")
@Slf4j
public class StatusController implements GenericController {

    private final StatusService statusService;

    private final StatusMapper statusMapper;

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Buscar todos os status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargos Listados com Sucesso")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<StatusDTO>> findAll() {
        List<StatusDTO> listaStatus = statusService.findAll()
                .stream()
                .map(statusMapper::toDTO)
                .toList();

        return ResponseEntity.ok(listaStatus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pelo ID", description = "Buscar status pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Status não encontrado")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<StatusDTO> findById(@PathVariable Integer id){
        StatusDTO statusDTO = statusMapper.toDTO(statusService.findById(id));
        return ResponseEntity.ok(statusDTO);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar Status")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Status Criado Com Sucesso")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> save(@RequestBody @Valid StatusDTO statusDTO){
        Status status = statusMapper.toEntity(statusDTO);
        statusService.save(status);
        URI uri =  gerarHeaderLocation(status.getIdStatus());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar", description = "Alterar Status")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status Alterado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Status não encotrado")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody StatusDTO statusDTO){
        Status status = statusMapper.toEntity(statusDTO);
        statusService.update(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar Status")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status Deletado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Status não encotrado")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

