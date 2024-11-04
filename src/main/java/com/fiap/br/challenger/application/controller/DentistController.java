package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.service.DentistService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/dentists")
public class DentistController {

    @Autowired
    private DentistService dentistService;

    @GetMapping
    public ResponseEntity<List<EntityModel<DentistResponseDTO>>> listDentist() {
        List<DentistResponseDTO> dentists = dentistService.getAllDentists();

        List<EntityModel<DentistResponseDTO>> dentistModels = dentists.stream()
                .map(dentist -> {
                    Link selfLink = linkTo(methodOn(DentistController.class).getDentistsByUUID(dentist.getId())).withSelfRel();
                    return EntityModel.of(dentist, selfLink);
                })
                .toList();

        return ResponseEntity.ok(dentistModels);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DentistResponseDTO>> getDentistsByUUID(@PathVariable UUID id) {
        Optional<DentistResponseDTO> responseDTO = dentistService.getDentistsByUUID(id);

        if (responseDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DentistResponseDTO dentist = responseDTO.get();
        EntityModel<DentistResponseDTO> model = EntityModel.of(dentist);

        Link selfLink = linkTo(methodOn(DentistController.class).getDentistsByUUID(id)).withSelfRel();
        model.add(selfLink);

        Link linkToAll = linkTo(methodOn(DentistController.class).listDentist()).withRel("allDentists");
        model.add(linkToAll);

        Link linkToDelete = linkTo(methodOn(DentistController.class).deleteDentist(id)).withRel("deleteDentist");
        model.add(linkToDelete);

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<DentistResponseDTO>> createDentist(@RequestBody @Valid DentistRequestDTO dentistRequestDTO) {
        try {
            DentistResponseDTO responseDTO = dentistService.createDentist(dentistRequestDTO);
            EntityModel<DentistResponseDTO> model = EntityModel.of(responseDTO);

            Link selfLink = linkTo(methodOn(DentistController.class).getDentistsByUUID(responseDTO.getId())).withSelfRel();
            model.add(selfLink);

            Link linkToAll = linkTo(methodOn(DentistController.class).listDentist()).withRel("allDentists");
            model.add(linkToAll);

            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable UUID id) {
        try {
            dentistService.deleteDentist(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
