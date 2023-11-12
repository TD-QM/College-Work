CREATE OR REPLACE PROCEDURE GenerateInvoice(CustID Customer.C_ID%Type, InvoiceDate Date)
AS
    CustName Customer.C_Name%Type;
    CURSOR CustomerOrdersCursor IS (SELECT L_Quantity, P_Name, P_Price 
                                    FROM Orders, LineItem, Product 
                                    WHERE CustID = O_Cust
                                    AND O_Number = L_Order
                                    AND L_Product = P_ID
                                    AND O_Date = InvoiceDate);
    InvoiceRecord CustomerOrdersCursor%RowType;
    RecordCount INTEGER := 0;
    ProductTotal Product.P_Price%Type;
    OrderTotal Product.P_Price%Type := 0;
    ShippingCost Product.P_Price%Type := 0;
BEGIN
    FOR InvoiceRecord IN CustomerOrdersCursor LOOP
        RecordCount := RecordCount + 1;
    END LOOP;

    IF RecordCount = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No invoice to generate.');
    ELSE
        SELECT C_Name INTO CustName FROM Customer WHERE C_ID = CustID;
        
        DBMS_OUTPUT.PUT_LINE(InvoiceDate || ' Invoice for ' || CustID || ': ' || CustName);
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE(RPAD('Product', 31)|| ' Quantity' || LPAD('Price/Unit', 12) || LPAD('Total', 12));
        DBMS_OUTPUT.PUT_LINE(RPAD('-', 65, '-'));
        
        FOR InvoiceInfo IN CustomerOrdersCursor LOOP
            ProductTotal := InvoiceInfo.L_Quantity * InvoiceInfo.P_Price;
            OrderTotal := OrderTotal + ProductTotal;
            
            DBMS_OUTPUT.PUT_LINE(RPAD(InvoiceInfo.P_Name, 31)|| 
                                 LPAD(InvoiceInfo.L_Quantity, 9) || '  ' ||
                                 '$' || LPAD(TO_CHAR(InvoiceInfo.P_Price, '9999.99'), 9) || '  ' ||
                                 '$' || LPAD(TO_CHAR(ProductTotal, '9999.99'), 9));
        END LOOP;
        
        IF OrderTotal < 35 THEN
            ShippingCost := 10;
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE(RPAD('Total Product Cost:', 20) || '$' || LPAD(TO_CHAR(OrderTotal, '9999.99'), 8));
        DBMS_OUTPUT.PUT_LINE(RPAD('Shipping Cost:', 20) || '$' || LPAD(TO_CHAR(ShippingCost, '9999.99'), 8));
        OrderTotal := OrderTotal + ShippingCost;
        DBMS_OUTPUT.PUT_LINE(RPAD('Total Due:', 20) || '$' || LPAD(TO_CHAR(OrderTotal, '9999.99'), 8));
    END IF;
    DBMS_OUTPUT.PUT_LINE('');
END;
/

call GenerateInvoice('C01', '30-JAN-23');
call GenerateInvoice('C07', '30-JAN-23');
call GenerateInvoice('C04', '03-FEB-23');
call GenerateInvoice('C08', '30-JAN-23');
