package com.example.kafkacrusher.connection.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class ActiveStatus {
   public String activeStatus;
}