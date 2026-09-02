package com.printlab.notificationsservice.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstant {

    @UtilityClass
    public class Redis {

        public final String REG_CODE_PREFIX = "reg:code:";
        public final String ADMIN_LOGIN_PREFIX = "admin:login:";
        public final String RESET_PASSWORD_PREFIX = "reset:code:";
        public final String COOLDOWN_PREFIX = "cooldown:";

        public final long OTP_EXPIRATION_MINUTES = 10;
        public final long RESEND_COOLDOWN_SECONDS = 60;
    }


    @UtilityClass
    public class Otp {

        public final int CODE_LENGTH = 6;
        public final int CODE_BOUND = 1_000_000;
    }
}