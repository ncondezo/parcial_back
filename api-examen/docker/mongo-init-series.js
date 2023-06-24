db.createUser(
    {
      user: "usr-serie",
      pwd: "pwd-serie",
      roles: [
            {
              role: "readWrite",
              db: "series"
            }
        ]
    }
);