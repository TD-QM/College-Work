ALTER TABLE Product DROP COLUMN Inventory;

CREATE OR REPLACE TRIGGER InventoryCheck
BEFORE INSERT ON LineItem
FOR EACH ROW
DECLARE
    Difference Product.P_Inventory%TYPE;
    Inventory Product.P_Inventory%TYPE;
BEGIN    
    SELECT P_Inventory INTO Inventory FROM Product WHERE P_ID = :new.L_Product;
    Difference := Inventory - :new.L_Quantity;
    
    IF Inventory = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Product #' || TO_CHAR(:new.L_Product) || ' is out of stock!');
    ELSIF Difference >= 0 THEN
        UPDATE Product 
        SET P_Inventory = P_Inventory - :new.L_Quantity 
        WHERE Product.P_ID = :new.L_Product;
    ELSE
        RAISE_APPLICATION_ERROR(-20001, 'Product #' || TO_CHAR(:new.L_Product) || ' does not have enough stock for this order!');
    END IF;
END;
/

DELETE FROM LineItem;
@'C:\Users\mknguye2\Source\College-Work\MKNguye2_4125\Phase_4\lineitem.sql';
SELECT P_ID, P_Inventory FROM Product ORDER BY P_ID;