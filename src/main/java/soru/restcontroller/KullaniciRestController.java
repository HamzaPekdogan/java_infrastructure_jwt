package soru.restcontroller;

import soru.dao.CalisanDAO;
import soru.entity.Kullanici;
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
    public void okulKaydet(@RequestBody Kullanici kullanici) {
        Kullanici kullanici1 = calisanDAO.findById(kullanici.getId());
        if (kullanici1 == null)
            kullanici1 = new Kullanici();
        kullanici1.setKullaniciAdi(kullanici.getKullaniciAdi());
        kullanici1.setOgretmenAdi(kullanici.getOgretmenAdi());
        kullanici1.setOkulAdi(kullanici.getOkulAdi());
        kullanici1.setYetki("1");
        if (kullanici.getSifre() != null) {
            kullanici1.setSifre(bCryptPasswordEncoder.encode(kullanici.getSifre()));
        }
        calisanDAO.save(kullanici1);
    }

    @RequestMapping(value = "/ogretmenKaydet", method = RequestMethod.POST)
    public void ogretmenKaydet(@RequestBody Kullanici kullanici) {
        Kullanici kullanici1 = calisanDAO.findById(kullanici.getId());
        if (kullanici1 == null)
            kullanici1 = new Kullanici();
        kullanici1.setKullaniciAdi(kullanici.getKullaniciAdi());
        kullanici1.setOgretmenAdi(kullanici.getOgretmenAdi());
        kullanici1.setOkulAdi(kullanici.getOkulAdi());
        kullanici1.setYetki("2");
        if (kullanici.getSifre() != null) {
            kullanici1.setSifre(bCryptPasswordEncoder.encode(kullanici.getSifre()));
        }
        calisanDAO.save(kullanici1);
    }

    @RequestMapping(value = "/sil/{id}", method = RequestMethod.GET)
    public void sil(@PathVariable("id") Long id) {
        calisanDAO.delete(calisanDAO.findById(id));
    }

    @RequestMapping(value = "/hepsiniGetir", method = RequestMethod.GET)
    public ResponseEntity<List<Kullanici>> hepsiniGetir() {
        return new ResponseEntity<>(calisanDAO.findAllByDurum(1), HttpStatus.OK);
    }

    @RequestMapping(value = "/getir/{id}", method = RequestMethod.GET)
    public ResponseEntity<Kullanici> getir(@PathVariable("id") Long id) {
        return new ResponseEntity<>(calisanDAO.findById(id), HttpStatus.OK);
    }
}
