package example.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import example.dao.BaseDAO;
import example.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController <T extends BaseEntity,D extends BaseDAO<T>> {
    private D d;

    public BaseController(D d) {
        this.d = d;
    }

    @RequestMapping(value = "/kaydet", method = RequestMethod.POST)
    public void kaydet(@RequestBody T t) {
        d.save(t);
    }

    @RequestMapping(value = "/hepsiniGetir", method = RequestMethod.GET)
    public ResponseEntity<List<T>> hepsiniGetir() {
        List<T> datas = d.findAll();
        List<T> result = new ArrayList<>();
        for(T t:datas)
            if(t.getDurum().equals(1))
                result.add(t);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/getir/{id}", method = RequestMethod.GET)
    public ResponseEntity<T> getir(@PathVariable("id") Long id) {
        return new ResponseEntity<>(d.findOne(id),HttpStatus.OK);
    }
}
