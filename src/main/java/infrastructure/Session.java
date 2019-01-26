package infrastructure;

import domain.Repository;
import domain.SimpleCookiejar;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.List;

class Session {
    private String securityToken;
    private final SimpleCookiejar simpleCookiejar;
    private final Repository repository;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public Session(SimpleCookiejar simpleCookiejar, Repository repository) {
        this.simpleCookiejar = simpleCookiejar;
        this.repository = repository;
    }

    public String buildCookieHeader(String url) {
        List<Cookie> cookies = simpleCookiejar.loadForRequest(HttpUrl.get(url));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.uid"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.sessionid"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.id"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.lang"))) {
                stringBuilder.append(cookies.get(i).name());
                stringBuilder.append("=");
                stringBuilder.append(cookies.get(i).value());
                if (i < 2) {
                    stringBuilder.append(";");
                    stringBuilder.append(" ");
                }
            }
        }
        return stringBuilder.toString();
    }

}
