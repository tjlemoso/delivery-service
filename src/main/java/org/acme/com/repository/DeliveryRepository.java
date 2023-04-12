package org.acme.com.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.com.model.Delivery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class DeliveryRepository implements PanacheRepository<Delivery>{
    public Delivery findByCode(String code){
        return find("trackingCode", code).firstResult();
    }
}
