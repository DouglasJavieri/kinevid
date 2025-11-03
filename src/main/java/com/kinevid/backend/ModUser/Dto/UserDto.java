package com.kinevid.backend.ModUser.Dto;

import com.kinevid.backend.ModUser.Entity.User;
import com.kinevid.backend.ModUser.Enums.UserStatus;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String status;
    private String fullname;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.status = user.getStatus().getValue();
        this.fullname = user.getName() + " " + user.getLastname();
    }
}
