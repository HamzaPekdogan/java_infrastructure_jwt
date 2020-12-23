package example.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import example.dao.CalisanDAO;
import example.entity.Kullanicilar;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private CalisanDAO calisanDAO;

    public UserDetailsServiceImpl(CalisanDAO calisanDAO) {
        this.calisanDAO = calisanDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String kulAdi) throws UsernameNotFoundException {
        Kullanicilar kullanicilar = calisanDAO.findByKullaniciAdi(kulAdi);
        if (kullanicilar == null) {
            throw new UsernameNotFoundException(kulAdi);
        }
        return new User(kullanicilar.getKullaniciAdi(), kullanicilar.getSifre(), emptyList());
    }
}