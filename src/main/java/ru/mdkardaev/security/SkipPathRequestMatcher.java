package ru.mdkardaev.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher publicPathMatcher;
    private RequestMatcher rotectedPathMatcher;

    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        Assert.notEmpty(pathsToSkip, "Argument [pathToSkip] is required and must be not empty");

        List<RequestMatcher> m = pathsToSkip.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());

        publicPathMatcher = new OrRequestMatcher(m);
        rotectedPathMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (publicPathMatcher.matches(request)) {
            return false;
        }
        return rotectedPathMatcher.matches(request);
    }
}
