CREATE TABLE `delivery` (
  `deliveryId` bigint NOT NULL AUTO_INCREMENT,
  `createData` date DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `trackingCode` varchar(40) NOT NULL,
  `status` varchar(40) NOT NULL,
  `clientId` bigint DEFAULT NULL,
  `warehouseId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  `supplierId` bigint DEFAULT NULL,
  PRIMARY KEY (`deliveryId`)
)