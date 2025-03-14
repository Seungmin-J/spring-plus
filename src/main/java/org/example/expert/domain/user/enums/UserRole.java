package org.example.expert.domain.user.enums;

import lombok.Getter;
import org.example.expert.domain.common.exception.InvalidRequestException;

import java.util.Arrays;

@Getter
public enum UserRole {
    ROLE_USER(Authority.USER),
    ROLE_ADMIN(Authority.ADMIN);

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UerRole"));
    }

    public static class Authority {
        public static String USER = "ROLE_USER";
        public static String ADMIN = "ROLE_ADMIN";
    }
}
