package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.Categoria;
import com.tecsup.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Categoria> guardar(@RequestBody Categoria entidad) {
        return ResponseEntity.status(201).body(service.guardar(entidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        Categoria item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria c) {
        Categoria existente = service.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(c.getNombre());
        existente.setDescripcion(c.getDescripcion());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Categoria item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
