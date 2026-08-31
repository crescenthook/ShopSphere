package com.shopshere.category_service.Exception;

public class DuplicateCategoryException extends RuntimeException{

    public DuplicateCategoryException(String message){
        super(message);
    }
}
