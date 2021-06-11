package com.bruno.carlisting.dtos.response.user;

import com.bruno.carlisting.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserPrivateResponseDTO extends UserPublicResponseDTO {

    private Long userId;
    private String password;

    private UserPrivateResponseDTO(Long userId, String username, String password, String displayName, String contact,
                                  List<Long> userListingsIds, List<Integer> rolesIds) {
        super(username, displayName, contact, userListingsIds, rolesIds);
        this.userId = userId;
        this.password = password;
    }

    public static UserPrivateResponseDTO toUserPrivateDTO(User user) {
        return new UserPrivateResponseDTO(user.getUserId(), user.getUsername(), user.getPassword(),
                user.getDisplayName(), user.getContact(), getUserListingsIds(user), getUserRolesIds(user));
    }
}
