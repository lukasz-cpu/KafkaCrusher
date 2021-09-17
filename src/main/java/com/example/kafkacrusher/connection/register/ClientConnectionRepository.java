package com.example.kafkacrusher.connection.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
}