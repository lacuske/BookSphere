package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AuthorDao {
    protected ConnectionManager connectionManager;
    private static AuthorDao instance = null;

    public AuthorDao() {
        connectionManager = new ConnectionManager();
    }

}