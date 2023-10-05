SELECT DISTINCT C_Name FROM Customer, Address WHERE C_ID = A_CUST;

SELECT P_ID, L_Quantity FROM Product, LineItem WHERE P_ID = L_Product;

SELECT P_Name FROM Product LEFT OUTER JOIN Review ON P_ID = R_Product WHERE R_Number IS NULL;

SELECT C_Name FROM Customer WHERE C_Referrer = (SELECT C_ID FROM Customer WHERE C_Name = 'Margot Robbie');