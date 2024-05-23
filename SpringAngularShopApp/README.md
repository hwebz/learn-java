## Spring Boot API + Angular Shop App

Initialize Spring Boot project using start.spring.io
```
Project Management Tool: Maven
Language: Java
Spring Boot Version: 3.1.10
Java Version: 17
Dependencies:
  Spring Web
  Spring Data JPA
  Loombok
  MySQL Driver
  Validation
```


## SQL Query to populate product's thumbnail
```sql
DROP PROCEDURE IF EXISTS InsertProductImages;

-- Assuming your table structure is as follows:
-- products (id, name, ...)
-- product_images (id, product_id, image_url, ...)

-- Create a stored procedure to insert 3 images for each product
DELIMITER //

CREATE PROCEDURE InsertProductImages()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE productId INT;
    DECLARE i INT;

    -- Declare a cursor to loop through all product IDs
    DECLARE productCursor CURSOR FOR SELECT id FROM products;
    
    -- Declare a handler to handle the end of the cursor loop
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    -- Create a temporary table to hold image names
    CREATE TEMPORARY TABLE TempImages (
        image_id INT AUTO_INCREMENT PRIMARY KEY,
        image_url VARCHAR(255)
    );
    
    -- Insert image names into the temporary table
    INSERT INTO TempImages (image_url) VALUES 
        ('product-image-00001.jpeg'), ('product-image-00002.jpeg'), 
        ('product-image-00003.jpeg'), ('product-image-00004.jpeg'), 
        ('product-image-00005.jpeg'), ('product-image-00006.jpeg'), 
        ('product-image-00007.jpeg'), ('product-image-00008.jpeg'), 
        ('product-image-00009.jpeg'), ('product-image-00010.jpeg'), 
        ('product-image-00011.jpeg'), ('product-image-00012.jpeg');
    
    -- Open the cursor
    OPEN productCursor;
    
    -- Loop through all product IDs
    read_loop: LOOP
        FETCH productCursor INTO productId;
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Insert 3 random images for each product
        SET i = 1;
        WHILE i <= 3 DO
            -- Get a random image from the temporary table
            SET @imageUrl = (SELECT image_url FROM TempImages ORDER BY RAND() LIMIT 1);
            
            -- Insert the image into the product_images table
            INSERT INTO product_images (product_id, image_url) VALUES (productId, @imageUrl);
            
            SET i = i + 1;
        END WHILE;
        -- Insert random product images inserted above to the product.thumbnail
		SET @imageUrl = (SELECT image_url FROM product_images WHERE product_id = productId ORDER BY RAND() LIMIT 1);
        UPDATE products SET thumbnail = @imageUrl WHERE id = productId;
        
    END LOOP;

    -- Close the cursor
    CLOSE productCursor;
END //

DELIMITER ;

-- Call the stored procedure to insert the product images
CALL InsertProductImages();
```