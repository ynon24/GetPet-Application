package com.example.GetPet.database;

public class DbConstants {

    public static final String DB_CONN_STRING = "jdbc:sqlite:animals_adop.db";
    public static final String ID = "id";
    // ----------------------------------------------------------------------------
    public static final String ACCOUNT_TABLE = "accounts";
    public static final String FIRST_NAME = "f_name";
    public static final String LAST_NAME = "l_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUM = "phone_num";
    public static final String PASSWORD = "password";
    public static final String FAVORITES = "favorites";
    // ----------------------------------------------------------------------------
    public static final String POSTS_TABLE = "posts";
    public static final String OWNER = "owner";
    public static final String PET_NAME = "pet_name";
    public static final String CATEGORY = "category";
    public static final String IMAGES = "images";
    public static final String SHORT_DESC = "short_desc";
    public static final String LONG_DESC = "long_desc";
    // ----------------------------------------------------------------------------
    public static final String ANIMAL_CATEGORY_TABLE = "animal_category";
    public static final String CATEGORY_NAME = "name";
    // ----------------------------------------------------------------------------
    public static final String MESSAGES_TABLE = "messages";
    public static final String SENDER = "sender";
    public static final String RECEIVER = "receiver";
    public static final String CONTENT = "content";
    public static final String SUBJECT = "subject";
    // ----------------------------------------------------------------------------
    public static final String IMAGES_TABLE = "images";
    public static final String UUID = "uuid";
    public static final String DATA = "data";
}
