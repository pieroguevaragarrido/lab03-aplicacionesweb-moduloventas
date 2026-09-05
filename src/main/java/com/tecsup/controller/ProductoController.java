package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.Producto;
import com.tecsup.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto entidad) {
        return ResponseEntity.status(201).body(service.guardar(entidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        Producto existente = service.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(p.getNombre());
        existente.setDescripcion(p.getDescripcion());
        existente.setPrecio(p.getPrecio());
        existente.setStock(p.getStock());
        existente.setCategoria(p.getCategoria());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Producto item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
