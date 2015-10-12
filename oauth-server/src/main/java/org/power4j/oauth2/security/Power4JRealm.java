package org.power4j.oauth2.security;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.InitializingBean;

/**
 * ClassName: org.power4j.oauth2.security.Power4JRealm <br>
 *
 * @author Kuo Hong
 */
public class Power4JRealm extends JdbcRealm implements InitializingBean {

    public static final String AUTHENTICATION_QUERY = "select password from users where archived = 0 and username = ?";

    public static final String USER_ROLES_QUERY = "select r.role_name from user_roles ur,users u,roles r  where ur.users_id = u.id and ur.roles_id = r.id and u.username = ?";

    public static final String PERMISSIONS_QUERY = "select rp.permission from roles_permissions rp,roles r where r.id = rp.roles_id and r.role_name = ?";


    public Power4JRealm() {
        super();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //override
        setAuthenticationQuery(AUTHENTICATION_QUERY);
        setUserRolesQuery(USER_ROLES_QUERY);
        setPermissionsQuery(PERMISSIONS_QUERY);
    }
}
