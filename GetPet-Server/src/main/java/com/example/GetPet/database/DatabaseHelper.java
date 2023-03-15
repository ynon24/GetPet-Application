package com.example.GetPet.database;


import com.example.GetPet.database.exceptions.AddImageException;
import com.example.GetPet.database.exceptions.CategoryNotFoundException;
import com.example.GetPet.database.models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.example.GetPet.database.DbConstants.*;

import com.example.GetPet.database.models.Post;
import com.example.GetPet.database.models.AnimalCategory;
import com.example.GetPet.database.models.Message;
import com.example.GetPet.tools.Utils;
import com.example.GetPet.database.exceptions.AccountNotFoundException;
import com.example.GetPet.database.exceptions.ImageNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.sqlite.SQLiteConfig;

public class DatabaseHelper {

    private final int NO_ID = -1;

    private Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection(DB_CONN_STRING, config.toProperties());
    }
    public void createDB(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){

            statement.addBatch("CREATE TABLE IF NOT EXISTS " + ACCOUNT_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRST_NAME + " TEXT NOT NULL, " + LAST_NAME+ " TEXT NOT NULL, " + EMAIL + " TEXT UNIQUE NOT NULL, " +
                    PHONE_NUM + " TEXT NOT NULL, " + PASSWORD + " TEXT NOT NULL, " + FAVORITES + " TEXT)");

            statement.addBatch("CREATE TABLE IF NOT EXISTS " + ANIMAL_CATEGORY_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT NOT NULL)");

            statement.addBatch("CREATE TABLE IF NOT EXISTS " + POSTS_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    OWNER + " INTEGER NOT NULL, " + PET_NAME + " TEXT NOT NULL, " + CATEGORY + " INTEGER NOT NULL, " +
                    IMAGES + " TEXT NOT NULL, " + SHORT_DESC + " TEXT NOT NULL, " + LONG_DESC + " TEXT NOT NULL, " +
                    "CONSTRAINT fk_owner FOREIGN KEY (" + OWNER + ") REFERENCES " + ACCOUNT_TABLE + " (" + ID + "), " +
                    "CONSTRAINT fk_category FOREIGN KEY (" + CATEGORY + ") REFERENCES " + ANIMAL_CATEGORY_TABLE + " (" + ID + "))");

            statement.addBatch("CREATE TABLE IF NOT EXISTS " + MESSAGES_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SENDER + " INTEGER NOT NULL, " + RECEIVER + " INTEGER NOT NULL, " + SUBJECT + " TEXT NOT NULL, " +
                    CONTENT + " TEXT NOT NULL, " +
                    "CONSTRAINT fk_sender FOREIGN KEY (" + SENDER + ") REFERENCES " + ACCOUNT_TABLE + " (" + ID + "), " +
                    "CONSTRAINT fk_receiver FOREIGN KEY (" + RECEIVER + ") REFERENCES " + ACCOUNT_TABLE + " (" + ID + "))");

            statement.addBatch("CREATE TABLE IF NOT EXISTS " + IMAGES_TABLE + " (" + UUID + " TEXT PRIMARY KEY, " +
                    DATA + " TEXT NOT NULL)");

            statement.executeBatch();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public Account getAccountById(int id) throws AccountNotFoundException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + ID + "=?")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){ // id is primary key so should be only one if exists
                return new Account(rs.getInt(ID), rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(EMAIL),
                        rs.getString(PHONE_NUM), rs.getString(PASSWORD), Utils.stringToIntegerList(rs.getString(FAVORITES)));
            } else {
                throw new AccountNotFoundException();
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new AccountNotFoundException();
        }
    }

    public Account getAccountByEmail(String email) throws AccountNotFoundException{
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + EMAIL + "=?")){
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){ // email is unique so should be only one if exists
                return new Account(rs.getInt(ID), rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(EMAIL),
                        rs.getString(PHONE_NUM), rs.getString(PASSWORD), Utils.stringToIntegerList(rs.getString(FAVORITES)));
            } else {
                throw new AccountNotFoundException();
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new AccountNotFoundException();
        }
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM " + ACCOUNT_TABLE);
            while (rs.next()){
                accounts.add(new Account(rs.getInt(ID), rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(EMAIL),
                        rs.getString(PHONE_NUM), rs.getString(PASSWORD), Utils.stringToIntegerList(rs.getString(FAVORITES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return accounts;
    }

    public String getAccountNameById(int accountId) throws AccountNotFoundException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT " + FIRST_NAME + "," + LAST_NAME + " FROM " + ACCOUNT_TABLE + " WHERE " + ID + "=?")){
            statement.setInt(1, accountId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){ // email is unique so should be only one if exists
                return rs.getString(LAST_NAME) + " " + rs.getString(FIRST_NAME);
            } else {
                throw new AccountNotFoundException();
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new AccountNotFoundException();
        }
    }

    public void addAccount(String firstName, String lastName, String email, String phoneNumber, String password, ArrayList<Integer> favorites){
        addAccount(new Account(NO_ID, firstName, lastName, email, phoneNumber, password, favorites));
    }

    public void addAccount(@NotNull Account account){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + ACCOUNT_TABLE + " (" + FIRST_NAME +
                    ", " + LAST_NAME + ", " + EMAIL + ", " + PHONE_NUM + ", " + PASSWORD + ", " + FAVORITES + ") VALUES(?,?,?,?,?,?)")){
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPhoneNumber());
            statement.setString(5, account.getPassword());
            statement.setString(6, Utils.integerListToString(account.getFavorites()));
            if(statement.executeUpdate() < 1){
                //TODO: add throw exception
                System.err.println("Failed to add account.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void setAccountFavorites(int accountId, List<Integer> favorites){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE " + ACCOUNT_TABLE + " SET " + FAVORITES +
                    "=? WHERE " + ID + "=?")){
            statement.setString(1, Utils.integerListToString(favorites));
            statement.setInt(2, accountId);
            if(statement.executeUpdate() < 1){
                //TODO: add throw exception
                System.err.println("Failed to update account's favorites.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public List<Message> getMessagesForAccount(int accountId){
        List<Message> messages = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + MESSAGES_TABLE + " WHERE " + RECEIVER + "=?")){
            statement.setInt(1, accountId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                messages.add(new Message(rs.getInt(ID), rs.getInt(SENDER), rs.getInt(RECEIVER), rs.getString(SUBJECT), rs.getString(CONTENT)));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return messages;
    }

    public void addMessage(Message msg){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + MESSAGES_TABLE + " (" + SENDER +
                    ", " + RECEIVER + ", " + CONTENT + ", " + SUBJECT + ") VALUES(?,?,?,?)")){
            statement.setInt(1, msg.getSender());
            statement.setInt(2, msg.getReceiver());
            statement.setString(3, msg.getContent());
            statement.setString(4, msg.getSubject());
            if(statement.executeUpdate() < 1){
                //TODO: add throw exception
                System.err.println("Failed to add message.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void addMessage(int sender, int receiver, String subject, String content){
        addMessage(new Message(NO_ID, sender, receiver, subject, content));
    }

    public void deleteMessage(int id){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM " + MESSAGES_TABLE + " WHERE " + ID +
                     "=?")){
            statement.setInt(1, id);
            if(statement.executeUpdate() < 1){
                // TODO: throw exception
                System.err.println("Failed to delete message.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deleteMessage(Message msg){
        deleteMessage(msg.getId());
    }

    public List<AnimalCategory> getAllCategories(){
        List<AnimalCategory> categories = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM " + ANIMAL_CATEGORY_TABLE);
            while (rs.next()){
                categories.add(new AnimalCategory(rs.getInt(ID), rs.getString(CATEGORY_NAME)));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return categories;
    }

    public AnimalCategory getAnimalCategoryById(int categoryId) throws CategoryNotFoundException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                    ANIMAL_CATEGORY_TABLE + " WHERE " + ID + "=?")){
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return new AnimalCategory(rs.getInt(ID), rs.getString(CATEGORY_NAME));
            } else {
                throw new CategoryNotFoundException();
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new CategoryNotFoundException();
        }
    }

    public void addAnimalCategory(String catName){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + ANIMAL_CATEGORY_TABLE + " (" + CATEGORY_NAME +
                    ") VALUES(?)")){
            statement.setString(1, catName);
            if(statement.executeUpdate() < 1){
                //TODO: throw exception
                System.err.println("Failed to add category.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deleteAnimalCategory(int categoryId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + ANIMAL_CATEGORY_TABLE +
                " WHERE " + ID + "=?")){
            statement.setInt(1, categoryId);
            if(statement.executeUpdate() < 1){
                //TODO: throw exception
                System.err.println("Failed to delete category.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deleteAnimalCategory(AnimalCategory animalCategory){
        deleteAnimalCategory(animalCategory.getId());
    }

    public List<Post> getAllPosts(){
        List<Post> posts = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM " + POSTS_TABLE);
            while (rs.next()){
                posts.add(new Post(rs.getInt(ID), rs.getInt(OWNER), rs.getString(PET_NAME), rs.getInt(CATEGORY),
                        rs.getString(SHORT_DESC), rs.getString(LONG_DESC), Utils.stringToStringList(rs.getString(IMAGES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return posts;
    }

    public List<Integer> getAllPostsIds(){
        List<Integer> ids = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT " + ID + " FROM " + POSTS_TABLE);
            while (rs.next()){
                ids.add(rs.getInt(ID));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return ids;
    }

    public List<Post> getPostsByIds(List<Integer> ids){
        List<Post> posts = new ArrayList<>();
        String condition = "SELECT * FROM " + POSTS_TABLE + " WHERE " + ID + " IN (?" + ",?".repeat(Math.max(0, ids.size() - 1)) +
                ")";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(condition)){
            for(int i = 1; i <= ids.size(); i++){
                statement.setInt(i, ids.get(i - 1));
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                posts.add(new Post(rs.getInt(ID), rs.getInt(OWNER), rs.getString(PET_NAME), rs.getInt(CATEGORY),
                        rs.getString(SHORT_DESC), rs.getString(LONG_DESC), Utils.stringToStringList(rs.getString(IMAGES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return posts;
    }

    public List<Post> getPostsByCategory(int category){
        List<Post> posts = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + POSTS_TABLE + " WHERE " + CATEGORY + "=?")){
            statement.setInt(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                posts.add(new Post(rs.getInt(ID), rs.getInt(OWNER), rs.getString(PET_NAME), rs.getInt(CATEGORY),
                        rs.getString(SHORT_DESC), rs.getString(LONG_DESC), Utils.stringToStringList(rs.getString(IMAGES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return posts;
    }

    public List<Integer> getPostsIdsByCategory(int category){
        List<Integer> ids = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT " + ID + " FROM " + POSTS_TABLE + " WHERE " + CATEGORY + "=?")){
            statement.setInt(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ids.add(rs.getInt(ID));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return ids;
    }

    public List<Post> getAccountPosts(int accountId){
        List<Post> posts = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + POSTS_TABLE + " WHERE " + OWNER + "=?")){
            statement.setInt(1, accountId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                posts.add(new Post(rs.getInt(ID), rs.getInt(OWNER), rs.getString(PET_NAME), rs.getInt(CATEGORY),
                        rs.getString(SHORT_DESC), rs.getString(LONG_DESC), Utils.stringToStringList(rs.getString(IMAGES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return posts;
    }

    public List<Post> getAccountPostsByCategory(int accountId, int categoryId){
        List<Post> posts = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + POSTS_TABLE + " WHERE " + OWNER + "=? AND " + CATEGORY +
                    "=?")){
            statement.setInt(1, accountId);
            statement.setInt(2, categoryId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                posts.add(new Post(rs.getInt(ID), rs.getInt(OWNER), rs.getString(PET_NAME), rs.getInt(CATEGORY),
                        rs.getString(SHORT_DESC), rs.getString(LONG_DESC), Utils.stringToStringList(rs.getString(IMAGES))));
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return posts;
    }

    public void addPost(Post post){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + POSTS_TABLE + " (" + OWNER +
                    ", " + PET_NAME + ", " + CATEGORY + ", " + SHORT_DESC + ", " + LONG_DESC + ", " + IMAGES + ") VALUES(?,?,?,?,?,?)")){
            statement.setInt(1, post.getOwner());
            statement.setString(2, post.getName());
            statement.setInt(3, post.getCategory());
            statement.setString(4, post.getShortDescription());
            statement.setString(5, post.getLongDescription());
            statement.setString(6, Utils.stringListToString(post.getImages()));
            if(statement.executeUpdate() < 1){
                // TODO: throw exception
                System.err.println("Failed to add post.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void addPost(int owner, String name, int category, String shortDesc, String longDesc, List<String> images){
        addPost(new Post(NO_ID, owner, name, category, shortDesc, longDesc, images));
    }

    public void deletePost(int id){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + POSTS_TABLE +
                    " WHERE " + ID + "=?")){
            statement.setInt(1, id);
            if(statement.executeUpdate() < 1){
                //TODO: throw exception
                System.err.println("Failed to delete post.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deletePost(Post post){
        deletePost(post.getId());
    }

    public String getImage(String uuid) throws ImageNotFoundException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT " + DATA + " FROM " + IMAGES_TABLE + " WHERE " +
                    UUID + "=?")){
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getString(DATA);
            } else {
                throw new ImageNotFoundException();
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new ImageNotFoundException();
        }
    }

    public String addImage(String data) throws AddImageException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + IMAGES_TABLE + " (" + UUID + ", " +
                    DATA + ") VALUES (?,?)")){
            String uuid = java.util.UUID.randomUUID().toString();
            statement.setString(1, uuid);
            statement.setString(2, data);
            if(statement.executeUpdate() < 1){
                System.err.println("Failed to add image.");
                throw new AddImageException();
            }
            return uuid;
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
            throw new AddImageException();
        }
    }

    public void deleteImage(String uuid){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + IMAGES_TABLE +
                    " WHERE " + UUID + "=?")){
            statement.setString(1, uuid);
            if(statement.executeUpdate() < 1){
                //TODO: throw exception
                System.err.println("Failed to delete image.");
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }


}