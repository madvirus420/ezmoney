package com.sadhus.ezmoney.models;

public class Wallet {
    private String WalletCode;
    private Long ID;
    private String Member;
    private Long CurrentBalance;
    private String TopupAccount;
    private String TopupFrequency;
    private String NextTopupDate;
    private Long MinimumBalance;
    private Boolean Active;
    private String CreatedDate;
    private String ModifiedDate;

    public String getWalletCode() {
        return WalletCode;
    }

    public void setWalletCode(String walletCode) {
        WalletCode = walletCode;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getMember() {
        return Member;
    }

    public void setMember(String member) {
        Member = member;
    }

    public Long getCurrentBalance() {
        return CurrentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        CurrentBalance = currentBalance;
    }

    public String getTopupAccount() {
        return TopupAccount;
    }

    public void setTopupAccount(String topupAccount) {
        TopupAccount = topupAccount;
    }

    public String getTopupFrequency() {
        return TopupFrequency;
    }

    public void setTopupFrequency(String topupFrequency) {
        TopupFrequency = topupFrequency;
    }

    public String getNextTopupDate() {
        return NextTopupDate;
    }

    public void setNextTopupDate(String nextTopupDate) {
        NextTopupDate = nextTopupDate;
    }

    public Long getMinimumBalance() {
        return MinimumBalance;
    }

    public void setMinimumBalance(Long minimumBalance) {
        MinimumBalance = minimumBalance;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }
}
