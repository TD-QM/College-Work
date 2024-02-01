-- Query 1
SELECT P_ID, P_Name, SUM(L_Quantity) AS Total_Quantity FROM Product, LineItem WHERE P_ID = L_Product GROUP BY P_ID, P_Name HAVING SUM(L_Quantity) > 30 ORDER BY P_ID;

-- Query 2
SELECT * FROM Customer WHERE C_Referrer IN (SELECT C_ID FROM Customer WHERE C_Referrer = 'C15');

-- Query 3
SELECT C_ID, C_Name FROM Customer, LineItem, Orders WHERE C_ID = O_Cust AND O_Number = L_Order AND L_Product = (SELECT P_ID FROM Product WHERE P_Price = (SELECT MIN(P_Price) FROM Product));

-- Query 4
SELECT P_ID, P_Name, AVG(R_Rating) FROM Product, Review WHERE P_ID = R_Product GROUP BY P_ID, P_Name;

-- Query 5
DROP VIEW TotalProductCost;
CREATE VIEW TotalProductCost AS 
    (SELECT L_Order, L_Quantity*P_Price AS Product_Cost 
     FROM LineItem, Product 
     WHERE P_ID = L_Product);
SELECT O_Number, SUM(Product_Cost) AS Total_Cost FROM Orders, TotalProductCost WHERE O_Number = L_Order GROUP BY O_Number HAVING SUM(Product_Cost) > 1000;

-- Query 6
DROP VIEW TotalOrderCost;
CREATE VIEW TotalOrderCost AS 
    (SELECT O_Number, SUM(Product_Cost) AS Order_Cost 
     FROM Orders, TotalProductCost 
     WHERE L_Order = O_Number 
     GROUP BY O_Number);
SELECT C_ID, C_Name, SUM(Order_Cost) AS Total_Order_Cost FROM Customer, Orders, TotalOrderCost WHERE C_ID = O_Cust AND Orders.O_Number = TotalOrderCost.O_Number GROUP BY C_ID, C_Name HAVING SUM(Order_Cost) > 1000 ORDER BY C_ID;

-- Query 7
SELECT COUNT(*) FROM Customer, Orders WHERE C_ID = O_Cust AND C_Referrer = (SELECT C_ID FROM Customer WHERE C_Name = 'Margot Robbie');

-- Query 8
SELECT * FROM Customer WHERE REGEXP_LIKE( LOWER(C_Name), '^(a|e|i|o|u)[a-z]*\s(a|e|i|o|u)[a-z]*$' );