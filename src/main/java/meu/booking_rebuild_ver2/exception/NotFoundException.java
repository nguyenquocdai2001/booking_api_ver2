package meu.booking_rebuild_ver2.exception;

import meu.booking_rebuild_ver2.config.Constants;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = -1360953961105975949L;

    public NotFoundException() {
        super(Constants.MESSAGE_GET_NOT_FOUND);
    }

}
