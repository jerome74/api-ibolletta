drop table IF EXISTS Bolletta;

create TABLE Bolletta(id INT PRIMARY KEY
                    , email VARCHAR(250)  NOT NULL
                    , cc VARCHAR(250)  NOT NULL
                    , importo VARCHAR(250)  NOT NULL
                    , scadenza VARCHAR(250)  NOT NULL
                    , numero VARCHAR(250)  NOT NULL
                    , owner VARCHAR(250)  NOT NULL
                    , td VARCHAR(250)  NOT NULL);

commit;