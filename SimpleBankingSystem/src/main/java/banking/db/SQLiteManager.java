package banking.db;

import org.sqlite.SQLiteDataSource;

import java.io.FileNotFoundException;
import java.net.URL;

// TODO Add logger for SQL operations
// TODO Add logger for errors
public abstract class SQLiteManager {
    public SQLiteDataSource dataSource;

    public void setDataSource(String source) throws FileNotFoundException {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + source);
    }
}
