package com.guilherme.price_tracker.exception;

public class InvalidProductUrlException extends RuntimeException {

    public InvalidProductUrlException(String url) {
        super("Could not extract a Mercado Livre item id from URL: " + url);
    }
}
