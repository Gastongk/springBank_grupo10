package com.cac.bankspringboot.services;


import com.cac.bankspringboot.exceptions.UserNotExistsException;
import com.cac.bankspringboot.mappers.UserMapper;
import com.cac.bankspringboot.models.User;
import com.cac.bankspringboot.models.dtos.UserDTO;
import com.cac.bankspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    // Inyectar una instancia del Repositorio
    @Autowired
    private UserRepository repository;

    // Metodos

    public List<UserDTO> getUsers(){
        // Obtengo la lista de la entidad usuario de la db
        List<User> users = repository.findAll();
        // Mapear cada usuario de la lista hacia un dto
        List<UserDTO> usersDtos = users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
        return usersDtos;
    }

    public UserDTO createUser(UserDTO userDto){
        User userValidated = validateUserByEmail(userDto);
        if  (userValidated == null){
            User userSaved = repository.save(UserMapper.dtoToUser(userDto));
            return UserMapper.userToDto(userSaved);
        } else {
            throw new UserNotExistsException("Email ya registrado " + userDto.getEmail());
        }
    }


    public UserDTO getUserById(Long id) {
        User entity = repository.findById(id).get();
        return UserMapper.userToDto(entity);
    }

    public String deleteUser(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            throw new UserNotExistsException("El usuario a eliminar elegido no existe");
        }

    }

    public UserDTO updateUser (Long id, UserDTO dto) {
        User userToModify = repository.findById(id).get();

        if (repository.existsById(id)) {
            if (dto.getName() != null) {
                userToModify.setName(dto.getName());
            }
            if (dto.getSurname() != null) {
                userToModify.setSurname(dto.getSurname());
            }
            if (dto.getEmail() != null) {
                userToModify.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                userToModify.setPassword(dto.getPassword());
            }
            if (dto.getDni() != null) {
                userToModify.setDni(dto.getDni());
            }
            User userModified = repository.save(userToModify);
            return UserMapper.userToDto(userModified);
        }
        return null;
    }

    public User validateUserByEmail(UserDTO dto){
        return repository.findByEmail(dto.getEmail());
    }
}