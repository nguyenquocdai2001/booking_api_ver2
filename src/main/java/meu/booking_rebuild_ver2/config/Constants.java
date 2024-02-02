package meu.booking_rebuild_ver2.config;

public class Constants {


    private Constants() {
    }
    // Constant to announce the status of valid token
    public static final String MESSAGE_INVALID_TOKEN = "Invalid JWT token: {}";
    public static final String MESSAGE_TOKEN_EXPIRED = "JWT token is expired: {}";
    public static final String MESSAGE_TOKEN_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String MESSAGE_TOKEN_CLAIM_EMPTY = "JWT claims string is empty: {}";

    public static final String MESSAGE_REGISTER_WELCOME = "Registered! Welcome.";
    public static final String MESSAGE_LOGIN_SUCCESS = "Login Successfully!.";
    public static final String MESSAGE_INVALID_USERNAME = "Invalid username.";
    public static final String MESSAGE_INVALID_PASSWORD = "Invalid password.";
    public static final String MESSAGE_INVALID_MATCH_PASSWORD = "Passwords don't match.";

    public static final String MESSAGE_ALL_SAVE = "All saved!";

    public static final String MESSAGE_THANK_YOU_REVIEW = "Thanks for your review!";
    public static final String MESSAGE_CANNOT_ADD_REVIEW = "Could not add review : ";
    public static final String MESSAGE_CANNOT_EDIT_REVIEW = "Could not edit review : ";
    public static final String MESSAGE_CAN_NOT_SAVE_OBJECTIVE = "Could not save new objectives";

    //nqd11 bs-17
    public static final String MESSAGE_STATUS_ADD_SUCCESS = "Adding a status successfully!";
    public static final String MESSAGE_STATUS_FIND_ALL_SUCCESS = "Find all status successfully!";
    public static final String MESSAGE_STATUS_FIND_STATUS_SUCCESS = "Find status successfully!";
    public static final String MESSAGE_STATUS_FIND_STATUS_FAILED = "Status Null";
    public static final String MESSAGE_STATUS_UPDATE_STATUS_SUCCESS = "Updating a status successfully!";
    public static final String MESSAGE_STATUS_DELETE_STATUS_SUCCESS = "Deleting a status successfully!";


    //dqdat
    public static final String MESSAGE_STATUS_ADD_TIME_SUCCESS = "Adding a time successfully!";
    public static final String MESSAGE_STATUS_GET_ALL_TIME_SUCCESS = "Getting all times successfully!";
    public static final String MESSAGE_TIME_FIND_SUCCESS = "Find time successfully!";
    public static final String MESSAGE_UPDATE_TIME_SUCCESS = "Updating time successfully!";
    public static final String MESSAGE_SOMETHING_WENT_WRONG = "Something went wrong!";
    public static final String MESSAGE_DELETE_SUCCESS = "Delete successfully!";

    public static final String MESSAGE_STATUS_ADD_ROUTES_SUCCESS = "Adding routes successfully!";
    public static final String MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS = "Getting all routes successfully!";
    public static final String MESSAGE_ROUTES_FIND_SUCCESS = "Find routes successfully!";
    public static final String MESSAGE_UPDATE_ROUTES_SUCCESS = "Updating routes successfully!";

    public static final String MESSAGE_STATUS_ADD_ROUTE_TIME_SUCCESS = "Adding route time successfully!";
    public static final String MESSAGE_STATUS_GET_ALL_ROUTES_TIME_SUCCESS = "Getting all route time successfully!";
    public static final String MESSAGE_ROUTES_TIME_FIND_SUCCESS = "Find route time successfully!";
    public static final String MESSAGE_UPDATE_ROUTES_TIME_SUCCESS = "Updating route time successfully!";

    public static final String MESSAGE_ROUTES_TIME_STILL_HAS = "Route Time still has ID, can't delete!";

    //ndq11 bs-9
    public static final String MESSAGE_BUS_TYPES_ADD_SUCCESS = "Adding a bus type successfully!";
    public static final String MESSAGE_BUS_TYPES_DUPLICATE_LICENSE_PLATE = "Duplicate License Plate!";
    public static final String MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS = "Find all bus types successfully!";
    public static final String MESSAGE_BUS_TYPES_FIND_ALL_FAILED = "Find all bus types failed!";
    public static final String MESSAGE_BUS_TYPE_FIND_SUCCESS = "Find bus type successfully!";
    public static final String MESSAGE_BUS_TYPE_FIND_FAILED = "Find bus type failed!";
    public static final String MESSAGE_FIND_BUS_TYPE_FAILED = "Bus type Null";
    public static final String MESSAGE_BUS_TYPES_UPDATE_SUCCESS = "Updating a bus type successfully!";
    public static final String MESSAGE_DELETE_BUS_TYPE_SUCCESS = "Deleting a bus type successfully!";

    //nqd11 bs-8
    public static final String MESSAGE_BUS_SEAT_ADD_SUCCESS = "Adding a bus seat successfully!";
    public static final String MESSAGE_BUS_SEATS_FIND_ALL_SUCCESS = "Find all bus seats successfully!";
    public static final String MESSAGE_BUS_SEAT_FIND_SUCCESS = "Find bus seat successfully!";
    public static final String MESSAGE_BUS_SEAT_FIND_FAILED = "Find bus seat failed!";
    public static final String MESSAGE_BUS_SEAT_UPDATE_SUCCESS = "Updating a bus seat successfully!";
    public static final String MESSAGE_DELETE_BUS_SEAT_SUCCESS = "Deleting a bus seat successfully!";


    //dqdat BS-10
    public static final String MESSAGE_STATUS_ADD_PRICE_SUCCESS = "Adding a price successfully!";
    public static final String MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS = "Getting all prices successfully!";
    public static final String MESSAGE_PRICE_FIND_SUCCESS = "Find prices successfully!";
    public static final String MESSAGE_UPDATE_PRICE_SUCCESS = "Updating price successfully!";

    // author: Nguyen Minh Tam. Constant to announce the succes of bs-2 and bs-3 and common announce

    public static final String MESSAGE_GET_SUCCESSFULL = "The get request has been Satisfied";
    public static final String MESSAGE_ADD_LOYALTY_SUCCESS = "Added a new loyalty successfully!";
    public static final String MESSAGE_ADD_RANK_FAILED = "The rank has been exit";
    public static final String MESSAGE_ADD_DISCOUNT_FAILED = "The discount has been exit";
    public static final String MESSAGE_UPDATE_LOYALTY_SUCCESS = "The loyalty has been updated successfully";
    public static final String MESSAGE_DELETED_SUCCESS = "Deleted successfully";
    public static final String MESSAGE_GET_NOT_FOUND = "NOT FOUND";

    public static final String MESSAGE_GET_LOYALTY_FAILED = ". Can not get loyalty with ";
    public static final String MESSAGE_ADD_CUSTOMER_FAILED = "The customer has been exit! ";
    public static final String MESSAGE_DUPLICATE_PHONE_CUSTOMER = "The phone number of customer has been exit! ";
    public static final String MESSAGE_ADDED_CUSTOMER_SUCCESSFULLY = "The customer has been added successfully! ";
    public static final String MESSAGE_UPDATED_CUSTOMER_SUCCESSFULLY = "The customer has been updated successfully! ";
    public static final String MESSAGE_PHONE_FORMAT_WRONG = "Phone number format is incorrect! ";
    public static final String MESSAGE_DELETED_SUCCESSFULLY = "Deleted object successfully! ";
    /*
     * Payment
     * */
    public static final String MESSAGE_GET_PAYMENT_FAILED = ". Can not get payment ";
    public static final String MESSAGE_ADD_PAYMENT_FAILED = "The customer has been exit";
    public static final String MESSAGE_ADDED_PAYMENT_SUCCESSFULLY = "The payment has been added successfully";
    public static final String MESSAGE_UPDATED_PAYMENT_SUCCESSFULLY = "The payment has been updated successfully";
    public static final String MESSAGE_DELETE_PAYMENT_SUCCESSFULLY = "Deleted payment successfully!";
    public static final String MESSAGE_STATUS_GET_PAYMENT_SUCCESS = "Getting payment successfully!";
    public static final String MESSAGE_EMPTY_LIST= "List is empty!";
    public static final String MESSAGE_INVALID_DATA = "Invalid data!";
    public static final String MESSAGE_ID_NOT_FOUND = "ID not found!";

    //nqd11 bs-11
    public static final String MESSAGE_DRIVER_DUPLICATE_PHONE = "Phone is exsisted!";
    public static final String MESSAGE_ADDED_DRIVER_SUCCESS = "The driver has been added successfully";
    public static final String MESSAGE_UPDATED_DRIVER_SUCCESS = "The driver has been updated successfully";
    public static final String MESSAGE_DELETED_DRIVER_SUCCESS = "The driver has been deleted successfully";
    public static final String MESSAGE_GET_ALL_DRIVER_SUCCESS = "Get all drivers successfully";
    public static final String MESSAGE_GET_DRIVER_FAILED = "Get drivers failed";
    public static final String MESSAGE_GET_DRIVER_SUCCESS = "Get drivers successfully";
    public static final String MESSAGE_DRIVER_NULL= "Driver null";

    //nqd11 bs-4
    public static final String MESSAGE_LOCATION_ADD_SUCCESS = "Adding a location successfully!";
    public static final String MESSAGE_LOCATION_UPDATE_SUCCESS = "Updating a location successfully!";
    public static final String MESSAGE_LOCATION_DELETE_SUCCESS = "Deleting a location successfully!";
    public static final String MESSAGE_LOCATION_GET_ALL_SUCCESS = "Get all locations successfully!";
    public static final String MESSAGE_LOCATION_GET_LOCATION_SUCCESS = "Get locations successfully!";
    public static final String MESSAGE_LOCATION_GET_NULL = "Not found!";



    public static final String MESSAGE_ADDED_PAYMENT_TYPE_SUCCESSFULLY = "The payment type has been added successfully";
    public static final String MESSAGE_UPDATED_PAYMENT_TYPE_SUCCESSFULLY = "The payment type has been updated successfully";
    public static final String MESSAGE_DELETE_PAYMENT_TYPE_SUCCESSFULLY = "Deleted payment type successfully!";
    public static final String MESSAGE_STATUS_GET_PAYMENT_TYPE_SUCCESS = "Getting payment type successfully!";

}
