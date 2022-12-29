package com.walace.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walace.dscommerce.dto.ProductDTO;
import com.walace.dscommerce.entities.Product;
import com.walace.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	/*
	 //Não resumida
	@Transactional(readOnly = true)
	public ProductDTO findById (Long id) {
		Optional<Product> result = productRepository.findById(id);
		Product product = result.get();
		ProductDTO dto = new ProductDTO(product);
		return dto;
	}
	*/
	
	//Forma resumida
	@Transactional(readOnly = true)
	public ProductDTO findById (Long id) {
		Product product =  productRepository.findById(id).get();
		return new ProductDTO(product);
	}

}
