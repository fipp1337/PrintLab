//package com.printlab.orderservice.security;
//
//import com.printlab.orderservice.exception.AppErrorCode;
//import com.printlab.orderservice.exception.AppException;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//import java.util.UUID;
//
//public final class SecurityUtils {
//
//    private SecurityUtils() {
//    }
//
//    public static Optional<SecurityFamily> optionalCurrentFamily() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof SecurityFamily securityFamily) {
//            return Optional.of(securityFamily);
//        }
//        return Optional.empty();
//    }
//
//    public static SecurityFamily currentFamily() {
//        return optionalCurrentFamily()
//                .orElseThrow(() -> AppException.of(AppErrorCode.INVALID_CREDENTIALS));
//    }
//
//    public static UUID currentFamilyId() {
//        return currentFamily().getId();
//    }
//
//    public static boolean hasRole(Role role) {
//        return optionalCurrentFamily()
//                .map(family -> family.getRoles().contains(role))
//                .orElse(false);
//    }
//
//    public static boolean isSuperAdmin() {
//        return hasRole(Role.SUPER_ADMIN);
//    }
//
//    public static void assertOwnerOrSuperAdmin(UUID familyId) {
//        if (!isSuperAdmin() && !currentFamilyId().equals(familyId)) {
//            throw AppException.of(AppErrorCode.ACCESS_DENIED);
//        }
//    }
//    public static void assertOwnerOrSuperAdmin(UUID currentFamilyId, UUID resourceOwnerId) {
//        if (isSuperAdmin()) {
//            return;
//        }
//
//        if (!currentFamilyId.equals(resourceOwnerId)) {
//            throw AppException.of(AppErrorCode.ACCESS_DENIED);
//        }
//    }
//}
