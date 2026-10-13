package com.example.backend.Service.IMP;

import com.example.backend.DTO.ProductoDTO;
import com.example.backend.Model.Categoria;
import com.example.backend.Model.Producto;
import com.example.backend.Repository.Interface.CategoriaInterface;
import com.example.backend.Repository.Interface.ProductoInterface;
import com.example.backend.Service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoInterface productoRepository;
    private final CategoriaInterface categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listar() {
        return mapear(productoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO obtener(Long id) {
        return ProductoDTO.fromEntity(buscar(id));
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        if (dto.getCodigoProducto() == null || dto.getCodigoProducto().isBlank()
                || dto.getNombre() == null || dto.getNombre().isBlank()
                || dto.getPrecioVenta() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Codigo, nombre y precio de venta son obligatorios");
        }
        if (productoRepository.existsByCodigoProducto(dto.getCodigoProducto())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese codigo");
        }
        dto.setId(null);
        Producto producto = dto.toEntity();
        producto.setCategoria(buscarCategoria(dto.getCategoriaId()));
        return ProductoDTO.fromEntity(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = buscar(id);

        if (dto.getCodigoProducto() != null && !dto.getCodigoProducto().equals(existente.getCodigoProducto())) {
            if (productoRepository.existsByCodigoProducto(dto.getCodigoProducto())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese codigo");
            }
            existente.setCodigoProducto(dto.getCodigoProducto());
        }
        if (dto.getNombre() != null) existente.setNombre(dto.getNombre());
        if (dto.getPrecioCompra() != null) existente.setPrecioCompra(dto.getPrecioCompra());
        if (dto.getPrecioVenta() != null) existente.setPrecioVenta(dto.getPrecioVenta());
        if (dto.getStockActual() != null) existente.setStockActual(dto.getStockActual());
        if (dto.getStockMinimo() != null) existente.setStockMinimo(dto.getStockMinimo());
        if (dto.getActivo() != null) existente.setActivo(dto.getActivo());
        if (dto.getDescripcion() != null) existente.setDescripcion(dto.getDescripcion());
        if (dto.getCategoriaId() != null) existente.setCategoria(buscarCategoria(dto.getCategoriaId()));

        return ProductoDTO.fromEntity(productoRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return mapear(productoRepository.findByNombreContainingIgnoreCase(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarPorCategoria(Long categoriaId) {
        return mapear(productoRepository.findByCategoriaId(categoriaId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarActivos() {
        return mapear(productoRepository.findByActivoTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarStockBajo() {
        return mapear(productoRepository.findProductosStockBajo());
    }

    private List<ProductoDTO> mapear(List<Producto> productos) {
        return productos.stream().map(ProductoDTO::fromEntity).toList();
    }

    private Producto buscar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + id));
    }

    private Categoria buscarCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria es obligatoria");
        }
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Categoria no encontrada: " + categoriaId));
    }
}
