package com.tecsup.service;

import com.tecsup.model.DetalleVenta;
import com.tecsup.model.Producto;
import com.tecsup.model.Venta;
import com.tecsup.repository.DetalleVentaRepository;
import com.tecsup.repository.ProductoRepository;
import com.tecsup.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository repo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private VentaRepository ventaRepo;

    public List<DetalleVenta> listar() {
        return repo.findAll();
    }

    public List<DetalleVenta> listarPorVenta(Long idVenta) {
        return repo.findByVenta_IdVenta(idVenta);
    }

    public DetalleVenta obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    // Registra un detalle: calcula el subtotal, descuenta stock del
    // producto y actualiza el total acumulado de la venta.
    public DetalleVenta guardar(DetalleVenta d) {
        Producto producto = productoRepo.findById(d.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Venta venta = ventaRepo.findById(d.getVenta().getIdVenta())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        d.setPrecio(producto.getPrecio());
        d.setSubtotal(d.getPrecio() * d.getCantidad());

        producto.setStock(producto.getStock() - d.getCantidad());
        productoRepo.save(producto);

        DetalleVenta guardado = repo.save(d);

        venta.setTotal((venta.getTotal() == null ? 0 : venta.getTotal()) + d.getSubtotal());
        ventaRepo.save(venta);

        return guardado;
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
