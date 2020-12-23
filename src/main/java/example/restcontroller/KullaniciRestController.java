package example.restcontroller;

import example.dao.CalisanDAO;
import example.entity.Kullanicilar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kullanici")
public class KullaniciRestController {
    private CalisanDAO calisanDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public KullaniciRestController(CalisanDAO calisanDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.calisanDAO = calisanDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/okulKaydet", method = RequestMethod.POST)
    public void okulKaydet(@RequestBody Kullanicilar kullanicilar) {
        Kullanicilar kullanicilar1 = calisanDAO.findById(kullanicilar.getId());
        if (kullanicilar1 == null)
            kullanicilar1 = new Kullanicilar();
        kullanicilar1.setKullaniciAdi(kullanicilar.getKullaniciAdi());
        kullanicilar1.setOgretmenAdi(kullanicilar.getOgretmenAdi());
        kullanicilar1.setOkulAdi(kullanicilar.getOkulAdi());
        kullanicilar1.setYetki("1");
        if (kullanicilar.getSifre() != null) {
            kullanicilar1.setSifre(bCryptPasswordEncoder.encode(kullanicilar.getSifre()));
        }
        calisanDAO.save(kullanicilar1);
    }

    @RequestMapping(value = "/ogretmenKaydet", method = RequestMethod.POST)
    public void ogretmenKaydet(@RequestBody Kullanicilar kullanicilar) {
        Kullanicilar kullanicilar1 = calisanDAO.findById(kullanicilar.getId());
        if (kullanicilar1 == null)
            kullanicilar1 = new Kullanicilar();
        kullanicilar1.setKullaniciAdi(kullanicilar.getKullaniciAdi());
        kullanicilar1.setOgretmenAdi(kullanicilar.getOgretmenAdi());
        kullanicilar1.setOkulAdi(kullanicilar.getOkulAdi());
        kullanicilar1.setYetki("2");
        if (kullanicilar.getSifre() != null) {
            kullanicilar1.setSifre(bCryptPasswordEncoder.encode(kullanicilar.getSifre()));
        }
        calisanDAO.save(kullanicilar1);
    }

    @RequestMapping(value = "/sil/{id}", method = RequestMethod.GET)
    public void sil(@PathVariable("id") Long id) {
        calisanDAO.delete(calisanDAO.findById(id));
    }

    @RequestMapping(value = "/hepsiniGetir", method = RequestMethod.GET)
    public ResponseEntity<List<Kullanicilar>> hepsiniGetir() {
        return new ResponseEntity<>(calisanDAO.findAllByDurum(1), HttpStatus.OK);
    }

    @RequestMapping(value = "/getir/{id}", method = RequestMethod.GET)
    public ResponseEntity<Kullanicilar> getir(@PathVariable("id") Long id) {
        return new ResponseEntity<>(calisanDAO.findById(id), HttpStatus.OK);
    }
}
