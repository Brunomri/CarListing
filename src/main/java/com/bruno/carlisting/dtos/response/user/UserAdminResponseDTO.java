package com.bruno.carlisting.dtos.response.user;

import com.bruno.carlisting.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserAdminResponseDTO extends UserPublicResponseDTO {

    private Long userId;

    private UserAdminResponseDTO(String username, String displayName, String contact, List<Long> userListingsIds,
                                List<Integer> rolesIds, Long userId) {
        super(username, displayName, contact, userListingsIds, rolesIds);
        this.userId = userId;
    }

    public static UserAdminResponseDTO toUserAdminDTO(User user) {
        return new UserAdminResponseDTO(user.getUsername(), user.getDisplayName(), user.getContact(),
                getUserListingsIds(user), getUserRolesIds(user), user.getUserId());
    }
}
