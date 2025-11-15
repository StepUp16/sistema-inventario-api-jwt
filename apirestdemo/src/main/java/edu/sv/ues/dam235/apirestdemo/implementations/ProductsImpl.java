package edu.sv.ues.dam235.apirestdemo.implementations;

import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;
import edu.sv.ues.dam235.apirestdemo.entities.Product;
import edu.sv.ues.dam235.apirestdemo.repositories.ProductRepository;
import edu.sv.ues.dam235.apirestdemo.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductsImpl implements ProductServices {

    private final ProductRepository productRepository;

    // Inyección por constructor
    public ProductsImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductsDTO> getAllProducts() {
        List<ProductsDTO> result = new ArrayList<>();
        List<Product> items = this.productRepository.findAll();
        for (Product item : items) {
            // Convertimos Entidad -> DTO
            result.add(new ProductsDTO(item.getCode(), item.getName(), "", item.isStatus()));
        }
        return result;
    }

    @Override
    public ProductsDTO save(ProductsDTO productDto) {
        // Convertimos DTO -> Entidad para guardar
        Product entidad = new Product();
        entidad.setName(productDto.getName());
        entidad.setStatus(productDto.isStatus());

        // Guardamos en BD
        Product guardado = this.productRepository.save(entidad);

        // Retornamos el DTO con el ID generado
        return new ProductsDTO(guardado.getCode(), guardado.getName(), "Producto creado exitosamente", guardado.isStatus());
    }

    @Override
    public ProductsDTO update(ProductsDTO productDto) {
        // Buscamos si existe el producto por su ID (Code)
        if (this.productRepository.existsById(productDto.getCode())) {
            Product entidad = new Product();
            entidad.setCode(productDto.getCode()); // Importante mantener el ID
            entidad.setName(productDto.getName());
            entidad.setStatus(productDto.isStatus());

            Product actualizado = this.productRepository.save(entidad);
            return new ProductsDTO(actualizado.getCode(), actualizado.getName(), "Producto actualizado exitosamente", actualizado.isStatus());
        }
        return null; // O podrías lanzar una excepción si no existe
    }

    @Override
    public void delete(Integer id) {
        if (this.productRepository.existsById(id)) {
            this.productRepository.deleteById(id);
        }
    }
}