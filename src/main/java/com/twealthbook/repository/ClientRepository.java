package com.twealthbook.repository;


import com.twealthbook.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findByClientId(Long clientId);
    public Client findByClientFirstNameAndClientMiddleNameAndClientLastName(String clientFirstName, String clientMiddleName, String clientLastName);
}
