package com.example.os.service;

import com.google.firebase.database.annotations.NotNull;

public class CloudContract {


    public static final String COLUMN_ID = "_id";

    @NotNull
    public static final String COLUMN_AUTHOR = "author";

    @NotNull
    public static final String COLUMN_AUTHOR_KEY = "authorKey";

    @NotNull
    public static final String COLUMN_MESSAGE = "message";

    @NotNull
    public static final String COLUMN_DATE = "date";
}
