package moneycalculator;

import java.util.List;
import control.MCController;
import model.Currency;
import persistence.files.CurrencyLoaderFromFile;
import persistence.rest.ExchangeRateLoaderFromWebService;
import view.swing.MoneyCalculatorSwing;

public class MoneyCalculator {

    public static void main(String[] args) {
        CurrencyLoaderFromFile currencyLoaderFromFile = new CurrencyLoaderFromFile("currencies.txt");
        List<Currency> currencies = currencyLoaderFromFile.currencyLoader();
        
        ExchangeRateLoaderFromWebService exchangeRateLoaderFromWebService = new ExchangeRateLoaderFromWebService();

        MoneyCalculatorSwing dialogSwing = new MoneyCalculatorSwing(currencies);
        
        new MCController(dialogSwing, 
                         exchangeRateLoaderFromWebService);
    }
    
}
