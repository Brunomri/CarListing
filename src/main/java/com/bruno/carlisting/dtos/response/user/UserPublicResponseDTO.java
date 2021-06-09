package com.bruno.carlisting.dtos.response.user;

import com.bruno.carlisting.domain.Listing;
import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserPublicResponseDTO {

    private static final long serialVersionUID = 1L;

    private String username;
    private String displayName;
    private String contact;

//    todo: create listing DTO to avoid displaying id
    private List<Listing> userListings;
    private List<Role> roles;

    public static UserPublicResponseDTO toUserPublicDTO(User user) {
        return new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(), user.getContact(),
                user.getUserListings(), user.getRoles());
    }

    public static Page<UserPublicResponseDTO> toUsersPagePublicDTO(Page<User> usersPage) {
        List<UserPublicResponseDTO> usersListDTO = new ArrayList<>();
        usersPage.forEach(user -> {
            var userDTO = new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(),
                    user.getContact(), user.getUserListings(), user.getRoles());
            usersListDTO.add(userDTO);
        });
        return new PageImpl<>(usersListDTO, usersPage.getPageable(), usersPage.getTotalElements());
    }
}
