package com.nisum.userregister.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phone {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    private UUID uuid;

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "uuid")
    @ToString.Exclude
    @JsonBackReference
    private UserNisum user;
}