package org.converter.account.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class Account {

    private Integer id;
    private String firstName;
    private String lastName;
    private AccountBalance balance;
}
