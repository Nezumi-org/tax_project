
**About**:
Tax project is a web app which models data and logic of Itemized Deductions (SCHEDULE A) Tax Form, built by using Maven and Spring Boot. 

**Current functionality**:
The program loads initial data of tax payers and expense data from file system to H2 database, calculates deductable amount based on the tax category, and displays a report per user or all users:

A View for an indididual tax payer is loaded by http://localhost:8080/tax/report/{taxpayerid}/{year}:

![image](https://user-images.githubusercontent.com/1413297/217440891-6c72a8d3-795d-4854-8cee-9cbe5a4da884.png)

**Future Steps**:
- Complete DeductionItem by adding other deductions categories to the model.
- Enhance DeductionItem by adding a receipt to each expense record.
- Add abilities to modify expense records. 
