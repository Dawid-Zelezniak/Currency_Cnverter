package org.example.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Account {

    private Integer id;
    private String firstName;
    private String lastName;
    private AccountBalance balance;
}
