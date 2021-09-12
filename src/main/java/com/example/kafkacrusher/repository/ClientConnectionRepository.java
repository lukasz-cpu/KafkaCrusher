package com.example.kafkacrusher.repository;

import com.example.kafkacrusher.model.ClientConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
}