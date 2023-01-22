package persistence.rest;

import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeRateLoaderFromWebService implements ExchangeRateLoader {

    @Override
    public ExchangeRate exchangerateLoader(Currency from, Currency to) {
        try {
            return new ExchangeRate(from, to, read(from.getCode(), to.getCode()));
        } catch (MalformedURLException exception) {
            System.out.println("ExchangeRateLoaderFromWebService :: exchangerateLoader, MalformedURLException" + exception.getMessage());
            return null;
        } catch (IOException exception) {
            System.out.println("ExchangeRateLoaderFromWebService :: exchangerateLoader, IOException" + exception.getMessage());
            return null;
        }
    }

    private Double read(String codeFrom, String codeTo) throws MalformedURLException, IOException {
        String line = read(new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + codeFrom.toLowerCase() + "/" + codeTo.toLowerCase() + ".json"));
        return Double.parseDouble(getStringRateFromJSONLine(line));
    }

    private String getStringRateFromJSONLine(String line) {
        String[] split = line.split(",");
        return split[1].substring(split[1].indexOf(":") + 1, split[1].indexOf("}") - 1);
    }

    private String read(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        byte[] buffer = new byte[1024];
        int lenght = inputStream.read(buffer);
        return new String(buffer, 0, lenght);
    }
}
