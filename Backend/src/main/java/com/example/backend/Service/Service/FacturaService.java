package com.example.backend.Service.Service;

import com.example.backend.Model.Factura;
import com.example.backend.Repository.FacturaJPA;
import com.example.backend.Service.IMP.FacturaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaService implements FacturaServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(FacturaService.class);

    private final FacturaJPA facturaJPA;

    @Override
    public List<Factura> getAll() {
        logger.info("Obteniendo listado de todas las facturas");
        return facturaJPA.findAll();
    }

    @Override
    public Optional<Factura> getById(Long id) {
        logger.info("Buscando factura por ID: {}", id);
        return facturaJPA.findById(id);
    }

    @Override
    public Factura save(Factura factura) {
        logger.info("Guardando factura: {}", factura.getNumeroFactura());
        return facturaJPA.save(factura);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando factura con ID: {}", id);
        if (!facturaJPA.existsById(id)) {
            logger.warn("No se puede eliminar, factura no encontrada con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada con ID: " + id);
        }
        facturaJPA.deleteById(id);
    }
}
