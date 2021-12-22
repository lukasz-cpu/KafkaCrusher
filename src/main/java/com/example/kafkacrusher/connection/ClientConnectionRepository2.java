package com.example.kafkacrusher.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientConnectionRepository2 extends JpaRepository<ClientConnection2, Long> {
}
