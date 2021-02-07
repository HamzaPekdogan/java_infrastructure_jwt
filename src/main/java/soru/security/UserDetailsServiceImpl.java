package soru.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import soru.dao.CalisanDAO;
import soru.entity.Kullanici;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private CalisanDAO calisanDAO;

    public UserDetailsServiceImpl(CalisanDAO calisanDAO) {
        this.calisanDAO = calisanDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String kulAdi) throws UsernameNotFoundException {
        Kullanici kullanici = calisanDAO.findByKullaniciAdi(kulAdi);
        if (kullanici == null) {
            throw new UsernameNotFoundException(kulAdi);
        }
        return new User(kullanici.getKullaniciAdi(), kullanici.getSifre(), emptyList());
    }
}