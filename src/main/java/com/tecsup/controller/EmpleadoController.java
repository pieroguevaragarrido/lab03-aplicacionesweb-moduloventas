package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.Empleado;
import com.tecsup.service.EmpleadoService;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    public List<Empleado> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado entidad) {
        return ResponseEntity.status(201).body(service.guardar(entidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
        Empleado item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado e) {
        Empleado existente = service.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(e.getNombre());
        existente.setApellido(e.getApellido());
        existente.setCargo(e.getCargo());
        existente.setTelefono(e.getTelefono());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Empleado item = service.obtener(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
