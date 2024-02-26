package model;

public class CashPayment implements PaymentStrategy{

    @Override
    public String pay() {
        return "Cash";
    }
}
