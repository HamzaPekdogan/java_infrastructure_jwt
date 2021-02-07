package soru.config;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import soru.dao.CalisanDAO;
import soru.dao.KulRolDao;
import soru.dao.RolDao;
import soru.entity.KullaniciRol;
import soru.entity.Kullanici;
import soru.entity.Rol;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static soru.config.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private KulRolDao kulRolDao;
    private RolDao rolDao;
    private CalisanDAO calisanDAO;

    public JWTAuthorizationFilter(AuthenticationManager authManager, KulRolDao kulRolDao, RolDao rolDao, CalisanDAO calisanDAO) {
        super(authManager);
    }

    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
            return null;
    }
}