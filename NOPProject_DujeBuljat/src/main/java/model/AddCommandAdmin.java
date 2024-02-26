package model;

import view.AdminFormPanel;

public class AddCommandAdmin implements Command{

    private final AdminFormPanel form;

    public AddCommandAdmin(AdminFormPanel form) {
        this.form = form;
    }

    @Override
    public void executeCommand() {
        form.addReservation();
    }
}
