package edu.sv.ues.dam235.apirestdemo.controllers;

import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;
import edu.sv.ues.dam235.apirestdemo.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    // 1. LEER (GET)
    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllItems() {
        try {
            List<ProductsDTO> items = productServices.getAllProducts();
            if (items.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.ok(items); // 200 OK
            }
        } catch (Exception e) {
            log.error("Error al consultar productos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 2. CREAR (POST)
    @PostMapping
    public ResponseEntity<ProductsDTO> createItem(@RequestBody ProductsDTO product) {
        try {
            ProductsDTO created = productServices.save(product);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            log.error("Error al crear producto", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 3. ACTUALIZAR (PUT)
    @PutMapping
    public ResponseEntity<ProductsDTO> updateItem(@RequestBody ProductsDTO product) {
        try {
            ProductsDTO updated = productServices.update(product);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build(); // 404 si no existe el ID
            }
        } catch (Exception e) {
            log.error("Error al actualizar producto", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 4. ELIMINAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Integer id) {
        try {
            productServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar producto", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
