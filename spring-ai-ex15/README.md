# Parse a PDF Invoice Document

This example loads and parses a PDF Invoice document, and allows access to all the fields,
and provides a way to chat with the invoice. A Java object is created to represent the Invoice.

Two endpoints:

/ask/rag    - allows a question to be asked about the invoice
/ask/entity - returns a Invoice Object instantiated fronm the PDF.

## Building and running

```
./mvnw spring-boot:run
```

## Access the endpoint

To get a string response to:

```shell 
http :8080/ask/rag question="What is total?"

```

```shell 
http :8080/ask/rag question="What are the part numbers?"
```


```shell 
http :8080/ask/rag question="What are the sections of the invoice ?"
```

```shell 
http :8080/ask/rag question="What is Jims phone number"

```

```shell 
http :8080/ask/entity question="Get the invoice?"

```

Try the examples Below:


question="What is the total?" 

The total amount on the invoice is $700.56.

———————————

question="What are the invoice parts?"

Based on the provided context, an invoice typically includes the following parts:

1. **Company Information**: The name and address of the company issuing the invoice.
   - Example: "The Big Company, 27 Little Street, Sydney, 2000, Phone: (061) 123-4567"

2. **Invoice Date**: The date when the invoice is issued.
   - Example: "21/2/2018"

3. **Terms**: Payment terms for the invoice.
   - Example: "Due Upon Receipt"

4. **Customer Information**: The name and address of the customer being billed.
   - Example: "Fred Smith, Small Company, 40A Big Street, Nice Ville, 3456, 0437 856342, jimy@small.com.au"

5. **Invoice Number**: A unique identifier for the invoice.
   - Example: "INVOICE # 2034"

6. **Customer ID**: A unique identifier for the customer.
   - Example: "CUSTOMER ID 564"

7. **Description of Items/Services**: Details of the items or services being billed, including quantity, unit price, and total amount.
   - Example: 
     - "Service fee, QTY 1, UNIT PRICE 200.00, AMOUNT 200.00"
     - "Labor: 5 hours at $75/hr, QTY 5, UNIT PRICE 75.00, AMOUNT 375.00"
     - "New client discount, (50.00)"
     - "Part Item 4523 - Big Nut, QTY 3, UNIT PRICE 45.00, AMOUNT 135.00"
     - "Part Item 3423 - Flexible cable, QTY 1, UNIT PRICE 12.00, AMOUNT 12.00"

8. **Subtotal**: The total amount before tax.
   - Example: "672.00"

9. **Tax Rate**: The applicable tax rate.
   - Example: "4.250%"

10. **Tax Amount**: The total tax amount calculated.
    - Example: "TAX 28.56"

11. **Total**: The total amount due including tax.
    - Example: "TOTAL 700.56$"

12. **Contact Information**: Contact details for queries regarding the invoice.
    - Example: "Jim, 0437 856342, jimy@small.com.au"

13. **Thank You Note**: A note of appreciation for the business.
    - Example: "Thank you for your business!"

If you have any other specific questions about invoice parts that are not covered here, please let me know!

——————————————————

question="What are the part items?"   

Based on the provided invoice, the part items listed are:

1. **Part Item 4523 - Big Nut**
   - Quantity: 3
   - Unit Price: $45.00
   - Amount: $135.00

2. **Part Item 3423 - Flexible cable**
   - Quantity: 1
   - Unit Price: $12.00
   - Amount: $12.00