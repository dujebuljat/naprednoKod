package model;

public class GooglePayPayment implements PaymentStrategy{
    @Override
    public String pay() {
        return "Google Pay";
    }
}
