package meu.booking_rebuild_ver2.exception;

public class GenericResponseException extends Exception{
    private static final long serialVersionUID = -1360953961105975949L;
    public GenericResponseException(String messeage){
        super("The request has failed. Details: " + messeage);
    }
}
