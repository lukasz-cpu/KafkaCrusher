package com.example.kafkacrusher.connection.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class ActiveStatus {  //ENUM TODO
   public String activeStatus;
}