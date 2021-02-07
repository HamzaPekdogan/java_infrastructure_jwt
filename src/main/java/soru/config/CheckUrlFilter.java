package soru.config;

import io.jsonwebtoken.Jwts;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import soru.dao.CalisanDAO;
import soru.dao.KulRolDao;
import soru.dao.RolDao;
import soru.dao.UrlRolDao;
import soru.entity.Kullanici;
import soru.entity.KullaniciRol;
import soru.entity.Rol;
import soru.entity.RolUrl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static soru.config.SecurityConstants.*;

public class CheckUrlFilter extends BasicAuthenticationFilter {
    private KulRolDao kulRolDao;
    private RolDao rolDao;
    private CalisanDAO calisanDAO;
    private UrlRolDao urlRolDao;

    public CheckUrlFilter(AuthenticationManager authManager, KulRolDao kulRolDao, RolDao rolDao, CalisanDAO calisanDAO,UrlRolDao urlRolDao) {
        super(authManager);
        this.kulRolDao = kulRolDao;
        this.rolDao = rolDao;
        this.calisanDAO = calisanDAO;
        this.urlRolDao = urlRolDao;
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
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority:authentication.getAuthorities())
        {
            Rol rol = rolDao.findByRolAdi(grantedAuthority.getAuthority());
            if(rol != null)
            {
                List<RolUrl> rolUrls=urlRolDao.findAllByRolId(rol.getId());
                for(RolUrl rolUrl:rolUrls)
                    authorities.add(rolUrl.getUrlAdi());
            }
        }
        Boolean status = false;
        for(String url:authorities)
            if (url.equals(req.getServletPath()))
                status = true;
        for (GrantedAuthority grantedAuthority:authentication.getAuthorities())
            if(grantedAuthority.getAuthority().equals("admin"))
                status = true;
        if(!status)
            throw new PermissionDeniedDataAccessException("Error",new Throwable());
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
            if(user.equals("admin"))
            {
                authorities.add(new SimpleGrantedAuthority("admin"));
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            else if (user != null) {
                Kullanici k = calisanDAO.findByKullaniciAdi(user);
                if(k!=null)
                {
                    for (KullaniciRol kullaniciRol : kulRolDao.findAllByKulId(k.getId())) {
                        Rol rol = rolDao.findById(kullaniciRol.getRoleId());
                        if(rol != null)
                            authorities.add(new SimpleGrantedAuthority(rol.getRolAdi()));
                    }
                }
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}