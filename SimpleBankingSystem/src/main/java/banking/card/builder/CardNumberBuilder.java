package banking.card.builder;

import banking.card.CardNumber;

public interface CardNumberBuilder {
    void setMII(int MII);
    void setBIN(int BIN);
    void setCustomerID(long customerID);
    void setCheckSum(int checkSum);
    CardNumber getResult();
}
