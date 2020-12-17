drop table IF EXISTS Users;
drop table IF EXISTS Profiles;


create TABLE Users(id INT AUTO_INCREMENT  PRIMARY KEY
                    , username VARCHAR(250)  NOT NULL
                    , email VARCHAR(250)  NOT NULL
                    , password VARCHAR(250)  NOT NULL
                    , active NUMERIC NOT NULL
                    , startdate TIMESTAMP NOT NULL
                    , enddate TIMESTAMP);

create TABLE Profiles(id INT AUTO_INCREMENT  PRIMARY KEY
                    , nickname VARCHAR(250)  NOT NULL
                    , email VARCHAR(250)  NOT NULL
                    , avatarname VARCHAR(250)  NOT NULL
                    , avatarcolor VARCHAR(250)  NOT NULL
                    , active NUMERIC NOT NULL
                    , startdate TIMESTAMP NOT NULL
                    , enddate TIMESTAMP);

     insert into Users (username, email, password, active , startdate ) values ('walter.longo74@gmail.com','walter.longo74@gmail.com','admin123', 1, CURRENT_TIMESTAMP);
     insert into Users (username, email, password, active , startdate ) values ('user','walter.longo74@gmail.com','user', 1, CURRENT_TIMESTAMP);

     insert into Profiles (nickname, email, avatarname ,avatarcolor, active , startdate ) values ('admin','walter.longo74@gmail.com','admin','[0.5, 0.5, 0.5, 1]', 1, CURRENT_TIMESTAMP);
     insert into Profiles (nickname, email, avatarname ,avatarcolor, active , startdate ) values ('users','walter.longo74@gmail.com','users','[0.5, 0.5, 0.5, 1]', 1, CURRENT_TIMESTAMP);

commit;