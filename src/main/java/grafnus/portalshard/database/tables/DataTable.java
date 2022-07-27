package grafnus.portalshard.database.tables;

import grafnus.portalshard.database.DataSource;

import java.sql.PreparedStatement;

public abstract class DataTable {

    private String tableName;

    public DataTable(String tableName, String query) {
        this.tableName = tableName;
        DataSource.getInstance().execute( (conn) -> {
            PreparedStatement p = conn.prepareStatement(query);
            int status = p.executeUpdate();
            // TODO: Add Debugger
            return null;
        });
    }

    public String getTableName() {
        return tableName;
    }
}
