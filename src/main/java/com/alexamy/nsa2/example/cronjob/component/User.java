package com.alexamy.nsa2.example.cronjob.component;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String username;
    private boolean enabled;
}
