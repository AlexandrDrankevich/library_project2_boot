package by.it.spring.util;

public class PersonErrorResponse {

    private String message;

    public PersonErrorResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
