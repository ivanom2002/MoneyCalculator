package view.swing;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import control.MCController;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Currency;
import model.Money;
import view.Dialog;
import view.Display;

public class MoneyCalculatorSwing extends JFrame implements Dialog, Display {

    private final List<Currency> currencies;
    
    private JPanel mainPanel;
    private JLabel moneyLabel, resultLabel;
    private JTextField moneyField;
    private JComboBox<Currency> currencyFromComboBox;
    private JComboBox<Currency> currencyToComboBox;
    private JButton calculateButton;
    
    private void initComponents() {
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        moneyLabel = new JLabel("Money:");
        moneyField = new JTextField(10);
    
        currencyFromComboBox = new JComboBox<>();
        currencyToComboBox = new JComboBox<>();
        for (Currency currency : this.currencies) {
            currencyFromComboBox.addItem(currency);
            currencyToComboBox.addItem(currency);
        }
        calculateButton = new JButton("Calculate");
        calculateButton.setEnabled(false);
        
        moneyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInput();
            }

            private void checkInput() {
                try {
                    Double.parseDouble(moneyField.getText());
                    calculateButton.setEnabled(true); 
                } catch (NumberFormatException ex) {
                    calculateButton.setEnabled(false); 
                }
            }
        });
        
        resultLabel = new JLabel("");

        mainPanel.add(moneyLabel);
        mainPanel.add(moneyField);
        mainPanel.add(currencyFromComboBox);
        mainPanel.add(currencyToComboBox);
        mainPanel.add(calculateButton);
        mainPanel.add(resultLabel);

        this.add(mainPanel);
        this.setTitle("Convertidor de divisas");

        this.setResizable(false);
        this.setSize(600, 125);
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        this.setLocation(x, y);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public MoneyCalculatorSwing(List<Currency> currencies) {
        this.currencies = currencies;
        initComponents();
        setVisible(true);
    }

    @Override
    public Money getMoney() {
        return new Money(Double.parseDouble(this.moneyField.getText()), 
                         this.getCurrencyFrom());
    }

    @Override
    public Currency getCurrencyTo() {
        return (Currency) this.currencyToComboBox.getSelectedItem();
    }
    
    private Currency getCurrencyFrom() {
        return (Currency) this.currencyFromComboBox.getSelectedItem();
    }

    public void registerController(MCController mCController) {
        this.calculateButton.addActionListener((ActionListener) mCController);        
    }

    @Override
    public void refreshMoney(Money money) {
        this.resultLabel.setText("Result = " + 
                             money.getAmount() + 
                             " " + 
                             money.getCurrency().getSymbol());
    }
        
}

