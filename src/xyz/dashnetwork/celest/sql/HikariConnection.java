package xyz.dashnetwork.celest.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.dashnetwork.celest.chat.ChatChannel;
import xyz.dashnetwork.celest.log.Logger;
import xyz.dashnetwork.celest.sql.data.UserData;
import xyz.dashnetwork.celest.util.Configuration;

import java.sql.*;
import java.util.*;

public final class HikariConnection {

    private final HikariDataSource hikari;

    public HikariConnection() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("celest-hikari");

        String host = Configuration.get(String.class, "sql.host");
        String database = Configuration.get(String.class, "sql.database");

        config.setJdbcUrl("jdbc:mysql://" + host + "/" + database);
        config.setUsername(Configuration.get(String.class, "sql.username"));
        config.setPassword(Configuration.get(String.class, "sql.password"));

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);
        config.setMaxLifetime(1800000);
        config.setKeepaliveTime(0);
        config.setConnectionTimeout(5000);

        this.hikari = new HikariDataSource(config);

        try {
            createTables();
        } catch (SQLException exception) {
            Logger.throwable(exception);
        }
    }

    public Connection getConnection() throws SQLException {
        if (hikari == null)
            throw new SQLException("Unable to get a connection from the pool. (hikari is null)");

        Connection connection = hikari.getConnection();

        if (connection == null)
            throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");

        return connection;
    }

    public void createTables() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS punishment (
                	punishment_id INT AUTO_INCREMENT PRIMARY KEY,
                	type ENUM('ban', 'mute') NOT NULL,
                	judge UUID,
                	expiration TIMESTAMP,
                	reason VARCHAR(64)
                )
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS address (
                	ipv4_address INET4 PRIMARY KEY,
                )
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS user (
                	uuid UUID PRIMARY KEY,
                	ipv4_address INET4 NOT NULL,
                	username VARCHAR(16) NOT NULL,
                	channel ENUM('global', 'local', 'staff', 'admin') NOT NULL DEFAULT 'global',
                	nickname VARCHAR(32),
                	commandspy BOOLEAN NOT NULL DEFAULT FALSE,
                	pingspy BOOLEAN NOT NULL DEFAULT FALSE,
                	serverspy BOOLEAN NOT NULL DEFAULT FALSE,
                	altspy BOOLEAN NOT NULL DEFAULT FALSE,
                	signspy BOOLEAN NOT NULL DEFAULT FALSE,
                	streamermode BOOLEAN NOT NULL DEFAULT FALSE,
                	CONSTRAINT fk_address FOREIGN KEY (ipv4_address) REFERENCES address(ipv4_address),
                )
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS user_punishment (
                	uuid UUID,
                	punishment_id INT,
                	PRIMARY KEY (uuid, punishment_id),
                	FOREIGN KEY (uuid) REFERENCES user(uuid),
                	FOREIGN KEY (punishment_id) REFERENCES punishment(punishment_id)
                )
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS address_punishment (
                	ipv4_address INET4,
                	punishment_id INT,
                	PRIMARY KEY (ipv4_address, punishment_id),
                	FOREIGN KEY (ipv4_address) REFERENCES address(ipv4_address)
                	FOREIGN KEY (punishment_id) REFERENCES punishment(punishment_id)
                )
                """);
        }
    }

    public int insertUser(UserData userData) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO user (uuid, ipv4_address, username, channel, nickname," +
                     "commandspy, pingspy, serverspy, altspy, signspy, streamermode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
             )) {
            statement.setObject(1, userData.getUuid());
            statement.setObject(2, userData.getAddress());
            statement.setObject(3, userData.getUsername());
            statement.setObject(4, userData.getChannel().name().toLowerCase());
            statement.setObject(5, userData.getNickname());
            statement.setBoolean(6, userData.isCommandSpy());
            statement.setBoolean(7, userData.isPingSpy());
            statement.setBoolean(8, userData.isServerSpy());
            statement.setBoolean(9, userData.isAltSpy());
            statement.setBoolean(10, userData.isSignSpy());
            statement.setBoolean(11, userData.isStreamerMode());
            return statement.executeUpdate();
        }
    }

    public Optional<UserData> selectUser(UUID uuid) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE uuid = ? LIMIT 1")) {
            statement.setObject(1, uuid);
            return parseUser(statement.executeQuery());
        }
    }

    public Optional<UserData> selectUser(String username) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ? LIMIT 1")) {
            statement.setObject(1, username);
            return parseUser(statement.executeQuery());
        }
    }

    private Optional<UserData> parseUser(ResultSet result) throws SQLException {
        if (!result.next())
            return Optional.empty();

        return Optional.of(new UserData(
                (UUID) result.getObject("uuid"),
                result.getString("username"),
                (String) result.getObject("ipv4_address"),
                result.getString("nickname"),
                ChatChannel.valueOf(result.getString("channel").toUpperCase()),
                result.getBoolean("altspy"),
                result.getBoolean("commandspy"),
                result.getBoolean("pingspy"),
                result.getBoolean("signspy"),
                result.getBoolean("serverspy"),
                result.getBoolean("streamermode")
        ));
    }

}
