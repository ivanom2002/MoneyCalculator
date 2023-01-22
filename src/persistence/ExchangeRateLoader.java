package persistence;

import model.Currency;
import model.ExchangeRate;

public interface ExchangeRateLoader {
    public ExchangeRate exchangerateLoader(Currency from, Currency to);
}