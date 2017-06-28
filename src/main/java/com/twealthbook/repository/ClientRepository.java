package com.twealthbook.repository;


import com.twealthbook.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findByClientId(Long clientId);
    public Client findByClientFirstNameAndClientMiddleNameAndClientLastName(String clientFirstName, String clientMiddleName, String clientLastName);
}
