package com.printlab.orderservice.config.redis;

import com.printlab.orderservice.constant.AppConstant;
import com.printlab.orderservice.exception.AppErrorCode;
import com.printlab.orderservice.exception.AppException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;
    private static final Duration CODE_TTL = Duration.ofMinutes(AppConstant.Redis.OTP_EXPIRATION_MINUTES);

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isResendAllowed(String email, String actionType) {
        String key = AppConstant.Redis.COOLDOWN_PREFIX + actionType + ":" + email;
        return !Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setResendCooldown(String email, String actionType) {
        String key = AppConstant.Redis.COOLDOWN_PREFIX + actionType + ":" + email;
        redisTemplate.opsForValue().set(
                key,
                "1",
                AppConstant.Redis.RESEND_COOLDOWN_SECONDS,
                TimeUnit.SECONDS
        );
    }

    public long getRemainingCooldown(String email, String actionType) {
        String key = AppConstant.Redis.COOLDOWN_PREFIX + actionType + ":" + email;
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return (expire != null && expire > 0) ? expire : 0;
    }

    public void updateRegistrationVerificationCode(String email, String newVerificationCode) {
        Map<Object, Object> currentData = getRegistrationData(email);
        if (currentData.isEmpty()) {
            throw AppException.of(AppErrorCode.REGISTRATION_SESSION_EXPIRED);
        }

        String inviteCode = (String) currentData.get("inviteCode");
        String address = (String) currentData.get("address");
        storeRegistrationData(email, inviteCode, newVerificationCode, address);
    }

    public void updateAdminLoginCode(String email, String newVerificationCode) {
        if (getLoginAdminCode(email) == null) {
            throw AppException.of(AppErrorCode.SESSION_EXPIRED);
        }
        storeLoginAdminData(email, newVerificationCode);
    }

    public void updateResetCode(String email, String newCode) {
        if (getResetCode(email) == null) {
            throw AppException.of(AppErrorCode.RESET_SESSION_EXPIRED);
        }
        storeResetCode(email, newCode);
    }

    public void storeLoginAdminData(String email, String verificationCode) {
        String key = AppConstant.Redis.ADMIN_LOGIN_PREFIX + email;
        redisTemplate.opsForHash().putAll(key, Map.of(
                "verificationCode", verificationCode != null ? verificationCode : ""
        ));
        redisTemplate.expire(key, CODE_TTL);
    }

    public String getLoginAdminCode(String email) {
        Object code = redisTemplate.opsForHash().get(AppConstant.Redis.ADMIN_LOGIN_PREFIX + email, "verificationCode");
        return code != null ? code.toString() : null;
    }

    public void deleteLoginAdminData(String email) {
        redisTemplate.delete(AppConstant.Redis.ADMIN_LOGIN_PREFIX + email);
    }

    public void storeRegistrationData(String email, String inviteCode, String verificationCode, String address) {
        String key = AppConstant.Redis.REG_CODE_PREFIX + email;
        redisTemplate.opsForHash().putAll(key, Map.of(
                "inviteCode", inviteCode != null ? inviteCode : "",
                "verificationCode", verificationCode != null ? verificationCode : "",
                "address", address != null ? address : ""
        ));
        redisTemplate.expire(key, CODE_TTL);
    }

    public Map<Object, Object> getRegistrationData(String email) {
        return redisTemplate.opsForHash().entries(AppConstant.Redis.REG_CODE_PREFIX + email);
    }

    public void deleteRegistrationData(String email) {
        redisTemplate.delete(AppConstant.Redis.REG_CODE_PREFIX + email);
    }

    public void storeResetCode(String email, String code) {
        redisTemplate.opsForValue().set(
                AppConstant.Redis.RESET_PASSWORD_PREFIX + email,
                code,
                CODE_TTL.toMinutes(),
                TimeUnit.MINUTES
        );
    }

    public String getResetCode(String email) {
        return redisTemplate.opsForValue().get(AppConstant.Redis.RESET_PASSWORD_PREFIX + email);
    }

    public void deleteResetCode(String email) {
        redisTemplate.delete(AppConstant.Redis.RESET_PASSWORD_PREFIX + email);
    }
}