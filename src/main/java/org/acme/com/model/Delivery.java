package org.acme.com.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery")
@Getter
@Setter
public class Delivery {

  @Id
  @Column(name = "deliveryId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long deliveryId;

  @Column(name = "createDate")
  private LocalDate createDate;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "trackingCode")
  private String trackingCode;

  @Column(name = "status")
  private String status;

  @Column(name = "clientId")
  private Long clientId;

  @Column(name = "warehouseId")
  private Long warehouseId;
  
  @Column(name = "productId")
  private Long productId;

  @Column(name = "supplierId")
  private Long supplierId;
}
