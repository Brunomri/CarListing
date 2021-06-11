package com.bruno.carlisting.dtos.response.user;

import com.bruno.carlisting.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
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
        return new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(), user.getContact(),
                getUserListingsIds(user), getUserRolesIds(user));
    }

    public static Page<UserPublicResponseDTO> toUsersPagePublicDTO(Page<User> usersPage) {
        List<UserPublicResponseDTO> usersListDTO = new ArrayList<>();
        usersPage.forEach(user -> {
            var userDTO = new UserPublicResponseDTO(user.getUsername(), user.getDisplayName(),
                    user.getContact(), getUserListingsIds(user), getUserRolesIds(user));
            usersListDTO.add(userDTO);
        });
        return new PageImpl<>(usersListDTO, usersPage.getPageable(), usersPage.getTotalElements());
    }

    protected static List<Long> getUserListingsIds(User user) {
        List<Long> userListingsIds = new ArrayList<>();
        user.getUserListings().forEach(userListing -> userListingsIds.add(userListing.getListingId()));
        return userListingsIds;
    }

    protected static List<Integer> getUserRolesIds(User user) {
        List<Integer> rolesIds = new ArrayList<>();
        user.getRoles().forEach(role -> rolesIds.add(role.getRoleId()));
        return rolesIds;
    }
}
