CREATE TABLE `orders`
(
    `id` BIGINT(20) AUTO_INCREMENT,
    `order_number` VARCHAR(255) DEFAULT NULL,
    `sku_code` VARCHAR(255),
    `price` DECIMAL(19, 2),
    `quantity` INT(11),
    PRIMARY KEY (`id`)
)