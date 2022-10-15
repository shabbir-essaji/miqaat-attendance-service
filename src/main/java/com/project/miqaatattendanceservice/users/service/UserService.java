package com.project.miqaatattendanceservice.users.service;

import com.project.miqaatattendanceservice.users.domain.User;
import com.project.miqaatattendanceservice.users.domain.UsersRepository;
import com.project.miqaatattendanceservice.users.dto.UserDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void save(List<User> users) {
        usersRepository.saveAll(users);
    }

    public void removeExisting() {
        usersRepository.deleteAll();
    }

    public Set<UserDTO> getMembersForHOF(Integer hofId) {
        Set<User> members = usersRepository.findByHofIts(hofId);
        return members.stream().map(UserDTO::new).collect(Collectors.toSet());
    }

    public Set<UserDTO> getUsersById(Set<Integer> ITS) {
        Iterable<User> users = usersRepository.findAllById(ITS);
        return StreamSupport.stream(users.spliterator(), false).map(UserDTO::new).collect(Collectors.toSet());
    }
}
