package com.sadhus.ezmoney.models;

import java.util.List;

public class WalletList {

    private List<Wallet> ListOfObjects;
    private String Status;
    private String Message;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Wallet> getListOfObjects() {
        return ListOfObjects;
    }

    public void setListOfObjects(List<Wallet> listOfObjects) {
        ListOfObjects = listOfObjects;
    }

    public String toString() {
        return this.getMessage();
    }
}
