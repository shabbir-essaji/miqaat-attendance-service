package com.project.miqaatattendanceservice.miqaatattendance.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiqaatRepository extends MongoRepository<Miqaat, String> {

}
