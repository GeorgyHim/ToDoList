package util.permission;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Permission {
    void check(HttpServletRequest request);
}
