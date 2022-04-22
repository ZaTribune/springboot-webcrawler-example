package com.zatribune.webcrawler.db.entities;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String info;

    @Enumerated(value = EnumType.STRING)
    private OperationStatus status;
    private Integer numOfResults;


    @CreationTimestamp
    private LocalDateTime addTimestamp;
}
