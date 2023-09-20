DROP TABLE Review;
DROP TABLE LineItem;
DROP TABLE Address;
DROP TABLE Order;
DROP TABLE Product;
DROP TABLE Customer;

CREATE TABLE Customer (
    C_ID CHAR,
    C_Name VARCHAR(20),
    C_DOB DATE,
    C_Referrer CHAR,
    CONSTRAINT CustomerPK PRIMARY KEY (C_ID),
    CONSTRAINT C_CustomerFK FOREIGN KEY (C_Referrer) REFERENCES Customer(C_ID)
);

CREATE TABLE Product {
    P_ID CHAR,
    P_Name VARCHAR(20),
    P_Price NUMBER(6,2),
    CONSTRAINT ProductPK PRIMARY KEY (P_ID)
};

CREATE TABLE Order{
    O_Number CHAR,
    O_Date DATE,
    O_Cust CHAR,
    CONSTRAINT OrderPK PRIMARY KEY (O_Number),
    CONSTRAINT O_CustomerFK FOREIGN KEY (O_Cust) REFERENCES Customer(C_ID)
};

CREATE TABLE Address{
    A_Cust CHAR,
    A_Addr CHAR,
    CONSTRAINT AddressPK PRIMARY KEY (A_Cust, A_Addr),
    CONSTRAINT A_CustomerFK FOREIGN KEY (A_Cust) REFERENCES Customer(C_ID)
};

CREATE TABLE LineItem{
    L_Cust CHAR,
    L_Product CHAR,
    L_Quantity NUMBER,
    CONSTRAINT LineItemPK PRIMARY KEY (L_Cust, L_Product),
    CONSTRAINT L_CustomerFK FOREIGN KEY (L_Cust) REFERENCES Customer(C_ID),
    CONSTRAINT L_ProductFK FOREIGN KEY (L_Product) REFERENCES Product(P_ID)
};

CREATE TABLE Review{
    R_Product CHAR,
    R_Number NUMBER,
    R_Rating NUMBER(5),
    R_Text CHAR,
    CONSTRAINT ReviewPK PRIMARY KEY (R_Product, R_Number),
    CONSTRAINT R_ProductFK FOREIGN KEY (R_Product) REFERENCES Product(P_ID)
};