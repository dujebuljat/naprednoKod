package model;

public class CreditCardPayment implements PaymentStrategy{
    @Override
    public String pay() {
        return "Credit Card";
    }
}
