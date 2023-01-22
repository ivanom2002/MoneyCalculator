package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Currency;
import model.ExchangeRate;
import model.Money;
import persistence.rest.ExchangeRateLoaderFromWebService;
import view.swing.MoneyCalculatorSwing;

public class MCController implements ActionListener {
    private final MoneyCalculatorSwing dialogSwing;
    private final ExchangeRateLoaderFromWebService exchangeRateLoaderFromWebService;

    public MCController(MoneyCalculatorSwing dialogSwing,
                        ExchangeRateLoaderFromWebService exchangeRateLoaderFromWebService) {
        this.dialogSwing = dialogSwing;
        this.exchangeRateLoaderFromWebService = exchangeRateLoaderFromWebService;
        this.dialogSwing.registerController(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       Money money = this.dialogSwing.getMoney();
       Currency currencyFrom = money.getCurrency();
       Currency currencyTo = this.dialogSwing.getCurrencyTo();
       ExchangeRate exchangeRate = this.exchangeRateLoaderFromWebService.exchangerateLoader(currencyFrom, currencyTo);
       
       this.dialogSwing.refreshMoney(new Money(exchangeRate.getRate() * money.getAmount(), currencyTo));
    }
}
