package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.rest.dto.CargoDTO;
import ucsal.cauzy.rest.mapper.CargoMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cargos")
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j
public class CargoController implements GenericController {

    private final CargoService cargoService;

    private final CargoMapper cargoMapper;

    @GetMapping
    @Operation(summary = "Listar", description = "Listar todos os cargos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
    public ResponseEntity<List<CargoDTO>> listar() {
        log.info("Listando todos os cargos");

        List<CargoDTO> resultado = cargoService.findAll()
                .stream()
                .map(cargoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Por ID", description = "Pesquisar Cargo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<CargoDTO> getCargoById(@PathVariable Integer id) {

        CargoDTO cargoResultado = cargoMapper.toDTO(cargoService.findById(id));

        return ResponseEntity.ok(cargoResultado);
    }

    @PostMapping
    @Operation(summary = "Criar ", description = "Criar um Cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
    })
    public ResponseEntity<CargoDTO> createCargo(@RequestBody @Valid CargoDTO cargoDTO) {
        log.info("Cadastrando novo cargo: {}", cargoDTO.nomeCargo());

        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargoService.save(cargo);
        URI location = gerarHeaderLocation(cargo.getIdCargo());
        return ResponseEntity.created(location).build();
    }

    // PUT /api/cargos/{id} - Atualiza um cargo existente
    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> updateCargo(@PathVariable Integer id, @RequestBody CargoDTO cargoDTO) {
        try {
            CargoDTO updatedCargo = cargoService.update(id, cargoDTO);
            return ResponseEntity.ok(updatedCargo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/cargos/{id} - Exclui um cargo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Integer id) {
        try {
            cargoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

