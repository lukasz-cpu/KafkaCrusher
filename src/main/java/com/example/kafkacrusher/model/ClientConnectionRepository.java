package com.example.kafkacrusher.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
}