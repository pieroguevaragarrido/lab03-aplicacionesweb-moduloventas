package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.DetalleVenta;
import com.tecsup.service.DetalleVentaService;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService service;

    @GetMapping
    public List<DetalleVenta> listar() {
        return service.listar();
    }

    // Devuelve solo los detalles de una venta puntual
    @GetMapping("/venta/{idVenta}")
    public List<DetalleVenta> listarPorVenta(@PathVariable Long idVenta) {
        return service.listarPorVenta(idVenta);
    }

    // Registra el detalle: calcula subtotal, descuenta stock del
    // producto y suma el total en la venta correspondiente.
    @PostMapping
    public ResponseEntity<DetalleVenta> guardar(@RequestBody DetalleVenta detalle) {
        return ResponseEntity.status(201).body(service.guardar(detalle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> obtener(@PathVariable Long id) {
        DetalleVenta d = service.obtener(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        DetalleVenta d = service.obtener(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
