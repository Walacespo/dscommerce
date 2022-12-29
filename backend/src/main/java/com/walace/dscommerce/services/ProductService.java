package com.walace.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll (Pageable pageable) {
		Page<Product> product =  productRepository.findAll(pageable);
		return product.map(x -> new ProductDTO(x));
	}


}
