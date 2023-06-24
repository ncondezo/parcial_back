db.createUser(
    {
      user: "usr-catalog",
      pwd: "pwd-catalog",
      roles: [
            {
              role: "readWrite",
              db: "catalog"
            }
        ]
    }
);
db.Movie.insertMany([
{name: "J Edgar", genre:"Drama", urlStream:"www.netflix.com/j-edgar"},
{name: "Manchester by the sea", genre:"Drama", urlStream:"www.netflix.com/m-b-s"},
{name: "Joker", genre:"Drama", urlStream:"www.netflix.com/joker"}
]);

db.Series.insertMany([
{"name":"The Sopranos","genre":"Drama","seasons":[{"seasonNumber":4,"chapters":[{"name":"The weight","number":4,"urlStream":"pending"}]}]},
{"name":"Silo","genre":"Drama","seasons":[{"seasonNumber":1,"chapters":[{"name":"Hanna","number":8,"urlStream":"pending"}]}]},
{"name":"Black mirror","genre":"Drama","seasons":[{"seasonNumber":3,"chapters":[{"name":"Men against fire","number":5,"urlStream":"pending"}]}]}
]);

