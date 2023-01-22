package persistence.files;

import model.Currency;
import persistence.CurrencyLoader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyLoaderFromFile implements CurrencyLoader {
    
    private final String fileName;
    
    public CurrencyLoaderFromFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Currency> currencyLoader() {
        List<Currency> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(this.fileName)));
            IteratorReader iteratorReader = new IteratorReader(reader);
            for (String line: iteratorReader) {
                list.add(currencyOf(line));
            }

        } catch (FileNotFoundException exception) {
            System.out.println("CurrencyLoaderFromFile :: currencyLoader, FileNotFoundException " + exception.getMessage());
            return null;
        }
        return list;
    }

    private Currency currencyOf(String line) {
        String[] split = line.split(",");
        return new Currency(split[0], split[1], split[2]);
    }
}
