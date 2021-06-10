package com.bruno.carlisting.dtos.response.user;

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
    private List<Long> userListingsIds;
    private List<Integer> rolesIds;

    public static UserPublicResponseDTO toUserPublicDTO(User user) {
        List<Long> userListingsIds = new ArrayList<>();
        List<Integer> rolesIds = new ArrayList<>();
        user.getUserListings().forEach(userListing -> userListingsIds.add(userListing.getListingId()));
        user.getRoles().forEach(role -> rolesIds.add(role.getRoleId()));
        return new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(), user.getContact(),
                userListingsIds, rolesIds);
    }

    public static Page<UserPublicResponseDTO> toUsersPagePublicDTO(Page<User> usersPage) {
        List<UserPublicResponseDTO> usersListDTO = new ArrayList<>();
        usersPage.forEach(user -> {
            List<Long> userListingsIds = new ArrayList<>();
            List<Integer> rolesIds = new ArrayList<>();
            user.getUserListings().forEach(userListing -> userListingsIds.add(userListing.getListingId()));
            user.getRoles().forEach(role -> rolesIds.add(role.getRoleId()));
            var userDTO = new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(),
                    user.getContact(), userListingsIds, rolesIds);
            usersListDTO.add(userDTO);
        });
        return new PageImpl<>(usersListDTO, usersPage.getPageable(), usersPage.getTotalElements());
    }
}
