package com.client.clientCRUD.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.client.clientCRUD.dto.ClientDTO;
import com.client.clientCRUD.entities.Client;
import com.client.clientCRUD.repositories.ClientRepository;
import com.client.clientCRUD.services.exceptions.DatabaseException;
import com.client.clientCRUD.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = clientRepository
				.findAll(pageRequest);
		return list.map(x-> new ClientDTO(x));
		

		
		
		

	}
@Transactional(readOnly = true)
public ClientDTO findbyId(Long id) {
	Optional<Client> obj = clientRepository.findById(id);
	
	Client entity = obj.orElseThrow(()-> new ResourceNotFoundException("Product not Found"));
	
	return new ClientDTO(entity);
}


@Transactional
public ClientDTO insert(ClientDTO dto) {
	
	Client entity = new Client();
	copyDToToEntity(dto,entity);
	
	entity = clientRepository.save(entity);
	
	return new ClientDTO(entity);
	
}


public void copyDToToEntity(ClientDTO dto, Client entity) {
	entity.setName(dto.getName());
	entity.setBirthDate(dto.getBirthDate());
	entity.setChildren(dto.getChildren());
	entity.setCpf(dto.getCpf());
	entity.setIncome(dto.getIncome());

	
}
@Transactional
public ClientDTO update(Long id, ClientDTO dto) {
try {
	
	Client entity = clientRepository.getOne(id);
	copyDToToEntity(dto, entity);
	entity = clientRepository.save(entity);
	
	return new ClientDTO(entity);
} catch (EntityNotFoundException e) {
	throw new ResourceNotFoundException("ID not found: "+id);
}
}
public void delete(Long id) {
try {
	clientRepository.deleteById(id);
	
} catch (EmptyResultDataAccessException e) {
	throw new ResourceNotFoundException("ID not found: "+id);
}catch(DataIntegrityViolationException e) {
	throw new DatabaseException("Integrity violation");
}
	
}







}
