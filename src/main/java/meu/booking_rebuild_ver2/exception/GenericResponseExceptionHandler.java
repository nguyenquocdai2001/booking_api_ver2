package meu.booking_rebuild_ver2.exception;

public class GenericResponseExceptionHandler extends Exception{
    private static final long serialVersionUID = -1360953961105975949L;
    public GenericResponseExceptionHandler(String messeage){
        super("The request has failed. Details: " + messeage);
    }
}
