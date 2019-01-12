package domain;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SimpleCookiejar implements CookieJar {
    private List<Cookie> storage = new ArrayList<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        storage.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {

        storage.removeIf(cookie -> cookie.expiresAt() < System.currentTimeMillis());

        return storage.stream().filter(cookie -> cookie.matches(url)).collect(Collectors.toList());
    }}
