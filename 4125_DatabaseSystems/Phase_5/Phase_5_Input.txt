SELECT * FROM Product WHERE P_Price > 100;
SELECT * FROM Customer WHERE C_Referrer IS NULL;
SELECT AVG(R_Rating) AS "Average Rating" FROM Review;
SELECT * FROM Product WHERE P_Name Like '%Ticket' ORDER BY P_Price;
SELECT MIN(C_DOB) AS "Min DoB", MAX(C_DOB) AS "Max DoB" FROM Customer;
SELECT * FROM Review WHERE lower(R_Text) LIKE '%great%';
SELECT COUNT(*) FROM Review WHERE R_Product = 'P05' AND R_Rating BETWEEN 3 AND 5;
SELECT P_Name FROM Product WHERE P_Price = (SELECT MAX(P_Price) FROM Product);
SELECT P_Name FROM Product WHERE P_Price > (SELECT AVG(P_Price) FROM Product)*1.25;