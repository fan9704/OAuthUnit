package com.ps.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by samzh on 2016/11/11.
 */
@Component
public class UserAuditorAware implements AuditorAware<String> {


    public Optional<String> getCurrentAuditor() {
        String userid = "";
        //正式環境應該用的寫法
//        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.isAuthenticated()==false) {
//            userid = null;
//        }else{
//            userid = authentication.getJwtCompact().getUserid();
//        }
        return Optional.of(userid);
    }
}
