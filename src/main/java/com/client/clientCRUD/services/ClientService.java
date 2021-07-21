package com.client.clientCRUD.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.client.clientCRUD.dto.ClientDTO;
import com.client.clientCRUD.entities.Client;
import com.client.clientCRUD.repositories.ClientRepository;
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







}
