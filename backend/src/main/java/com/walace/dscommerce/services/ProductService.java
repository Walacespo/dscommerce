package com.walace.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.walace.dscommerce.dto.ProductDTO;
import com.walace.dscommerce.entities.Product;
import com.walace.dscommerce.repositories.ProductRepository;
import com.walace.dscommerce.services.exceptions.DatabaseException;
import com.walace.dscommerce.services.exceptions.ResouceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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
		Product product =  productRepository.findById(id).orElseThrow(
				()-> new ResouceNotFoundException("Recurso não encontrado"));
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll (Pageable pageable) {
		Page<Product> product =  productRepository.findAll(pageable);
		return product.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO insert (ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update (Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
			
		}catch(EntityNotFoundException e){
			throw new ResouceNotFoundException("Recurso não encontrado!");	
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete (Long id) {
		try {
			productRepository.deleteById(id);
			
		}catch (EmptyResultDataAccessException e){
			throw new ResouceNotFoundException("Recurso não encontrado!");	
		}
		catch (DataIntegrityViolationException e){
		throw new DatabaseException("Falha de integridade referencial!");	
	}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
	}
}
