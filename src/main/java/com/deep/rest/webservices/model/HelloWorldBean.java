package com.deep.rest.webservices.model;

/*
*  Getter Method is required other it will throw the below error
* org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.deep.rest.webservices.model.HelloWorldBean
*
 * */
public class HelloWorldBean {
    private String message;

    public HelloWorldBean(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
