package com.kinevid.backend.ModUser.Dto;

import com.kinevid.backend.ModUser.Entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameOrEmailDto {

    private Long id;
    private String username;
    private String email;

    public UsernameOrEmailDto (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
