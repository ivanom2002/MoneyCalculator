package view;

import model.Currency;
import model.Money;

public interface Dialog {
    public Money getMoney();
    public Currency getCurrencyTo();
}
