USE gestionare_reprezentanta_auto;
GO

--TABEL CLIENTI
CREATE TABLE clienti (
    id_client INT IDENTITY(1,1) PRIMARY KEY,
    nume VARCHAR(50) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    telefon VARCHAR(30),
    email VARCHAR(50)
);


--TABEL ANGAJATI
CREATE TABLE angajati (
    id_angajat INT IDENTITY(1,1) PRIMARY KEY,
    nume VARCHAR(50),
    prenume VARCHAR(50),
    functie VARCHAR(50),
    email VARCHAR(50),
    salariu DECIMAL(10,2)
);


--TABEL CARTE SERVICE
CREATE TABLE carte_service (
    id_carte_service INT IDENTITY(1,1) PRIMARY KEY,
    km_initiali FLOAT,
    ultima_revizie DATE,
    data_expirare_garantie DATE
);

--TABEL MASINI
CREATE TABLE masini (
    id_masina INT IDENTITY(1,1) PRIMARY KEY,
    marca VARCHAR(30),
    model VARCHAR(30),
    an_fabricatie INT,
    pret DECIMAL(10,2),

    id_client INT NULL,
    id_carte_service INT UNIQUE NULL,

    FOREIGN KEY (id_client) REFERENCES clienti(id_client),
    FOREIGN KEY (id_carte_service) REFERENCES carte_service(id_carte_service)
);

--TABEL VANZARI
CREATE TABLE vanzari (
    id_vanzare INT IDENTITY(1,1) PRIMARY KEY,
    id_masina INT UNIQUE,
    id_client INT,
    id_angajat INT,
    data_vanzare DATE,
    pret_final DECIMAL(10,2),

    FOREIGN KEY (id_masina) REFERENCES masini(id_masina),
    FOREIGN KEY (id_client) REFERENCES clienti(id_client),
    FOREIGN KEY (id_angajat) REFERENCES angajati(id_angajat)
);


--TABEL REVIZII

CREATE TABLE revizii (
    id_revizie INT IDENTITY(1,1) PRIMARY KEY,
    id_masina INT,
    data_revizie DATE,
    cost DECIMAL(10,2),
    descriere VARCHAR(200),

    FOREIGN KEY (id_masina) REFERENCES masini(id_masina)
);


--TABEL OPTIUNI
CREATE TABLE optiuni (
    id_optiune INT IDENTITY(1,1) PRIMARY KEY,
    nume_optiune VARCHAR(100)
);

--TABEL MASINA_OPTIUNE (N-N)
CREATE TABLE masina_optiune (
    id_masina INT,
    id_optiune INT,
    PRIMARY KEY (id_masina, id_optiune),

    FOREIGN KEY (id_masina) REFERENCES masini(id_masina),
    FOREIGN KEY (id_optiune) REFERENCES optiuni(id_optiune)
);

SELECT name FROM sys.tables;

INSERT INTO clienti (nume, prenume, telefon, email) VALUES ('Popescu', 'Ion', '0722111222', 'ion.popescu@email.com');
INSERT INTO angajati (nume, prenume, functie, salariu) VALUES ('Ionescu', 'Maria', 'Sales Manager', 5500.00);
INSERT INTO optiuni (nume_optiune) VALUES ('Climatronic'), ('Scaune Incalzite'), ('Senzori Parcare');
