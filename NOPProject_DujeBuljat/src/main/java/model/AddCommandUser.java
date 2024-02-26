package model;

import view.ReservationFormPanel;

public class AddCommandUser implements Command{

    private final ReservationFormPanel form;

    public AddCommandUser(ReservationFormPanel form) {
        this.form = form;
    }

    @Override
    public void executeCommand() {
        form.addReservation();
    }
}
