DROP TABLE Review;
DROP TABLE LineItem;
DROP TABLE Address;
DROP TABLE Orders;
DROP TABLE Product;
DROP TABLE Customer;

CREATE TABLE Customer (
    C_ID CHAR(3),
    C_Name VARCHAR(20),
    C_DOB DATE,
    C_Referrer CHAR(3),
    CONSTRAINT CustomerPK PRIMARY KEY (C_ID),
    CONSTRAINT C_CustomerFK FOREIGN KEY (C_Referrer) REFERENCES Customer(C_ID)
);

CREATE TABLE Product (
    P_ID CHAR(3),
    P_Name VARCHAR(30),
    P_Price NUMBER(6,2),
    CONSTRAINT ProductPK PRIMARY KEY (P_ID)
);

CREATE TABLE Orders(
    O_Number CHAR(5),
    O_Date DATE,
    O_Cust CHAR(3),
    CONSTRAINT OrderPK PRIMARY KEY (O_Number),
    CONSTRAINT O_CustomerFK FOREIGN KEY (O_Cust) REFERENCES Customer(C_ID)
);

CREATE TABLE Address(
    A_Cust CHAR(3),
    A_Addr VARCHAR2(30),
    CONSTRAINT AddressPK PRIMARY KEY (A_Cust, A_Addr),
    CONSTRAINT A_CustomerFK FOREIGN KEY (A_Cust) REFERENCES Customer(C_ID)
);

CREATE TABLE LineItem(
    L_Order CHAR(5),
    L_Product CHAR(3),
    L_Quantity NUMBER,
    CONSTRAINT LineItemPK PRIMARY KEY (L_Order, L_Product),
    CONSTRAINT L_OrderFK FOREIGN KEY (L_Order) REFERENCES Orders(O_Number),
    CONSTRAINT L_ProductFK FOREIGN KEY (L_Product) REFERENCES Product(P_ID)
);

CREATE TABLE Review(
    R_Product CHAR(3),
    R_Number NUMBER,
    R_Rating NUMBER,
    R_Text VARCHAR2(500),
    CONSTRAINT ReviewPK PRIMARY KEY (R_Product, R_Number),
    CONSTRAINT R_ProductFK FOREIGN KEY (R_Product) REFERENCES Product(P_ID)
);