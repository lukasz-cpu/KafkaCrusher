package com.example.kafkacrusher.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
}