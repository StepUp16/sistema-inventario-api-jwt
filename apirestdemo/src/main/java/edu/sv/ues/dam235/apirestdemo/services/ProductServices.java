package edu.sv.ues.dam235.apirestdemo.services;

import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;
import java.util.List;

public interface ProductServices {
    // Método existente
    public List<ProductsDTO> getAllProducts();

    // --- NUEVOS MÉTODOS PARA EL CRUD ---
    public ProductsDTO save(ProductsDTO product);
    public ProductsDTO update(ProductsDTO product);
    public void delete(Integer id);
}