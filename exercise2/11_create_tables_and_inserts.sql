CREATE TABLE estateAgent (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    login VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(40)
);

CREATE TABLE estate (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	city VARCHAR(255),
	postalCode VARCHAR(5),
	street VARCHAR(255),
	streetNumber INTEGER,
	squareArea VARCHAR(255),
	estateAgentId INTEGER,
	CONSTRAINT fkEstateAgentId FOREIGN KEY(estateAgentId) REFERENCES estateAgent(id)
);

CREATE TABLE apartment (
    id INTEGER UNIQUE NOT NULL,
    floor INTEGER,
    rent DECIMAL(12,2),
    rooms INTEGER,
    balcony CHARACTER(1),
    builtInKitchen CHARACTER(1),
    CONSTRAINT fkId FOREIGN KEY(id) REFERENCES estate(id)
);

CREATE TABLE house (
    id INTEGER UNIQUE NOT NULL,
    floors INTEGER,
    price DECIMAL(12,2),
    garden CHARACTER(1),
    CONSTRAINT fkId FOREIGN KEY(id) REFERENCES estate(id)
);

CREATE TABLE person (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
    firstName VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE contract (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
    contractNo INTEGER UNIQUE NOT NULL,
    date DATE,
    place VARCHAR(255)
);

CREATE TABLE tenancyContract (
    id INTEGER UNIQUE NOT NULL,
    startDate DATE,
    duration INTEGER,
    additionalCosts DECIMAL(12,2),
    apartmentId INTEGER,
    personId INTEGER,
    CONSTRAINT fkId FOREIGN KEY(id) REFERENCES contract(id),
    CONSTRAINT fkApartmentId FOREIGN KEY(apartmentId) REFERENCES apartment(id),
    CONSTRAINT fkPersonId FOREIGN KEY(personId) REFERENCES person(id)
);

CREATE TABLE purchaseContract (
    id INTEGER UNIQUE NOT NULL,
    noOfInstallments INTEGER,
    intrestRate DECIMAL(7,4),
    houseId INTEGER,
    personId INTEGER,
    CONSTRAINT fkId FOREIGN KEY(id) REFERENCES contract(id),
    CONSTRAINT fkHouseId FOREIGN KEY(houseId) REFERENCES house(id),
    CONSTRAINT fkPersonId FOREIGN KEY(personId) REFERENCES person(id)
);

INSERT INTO estateAgent (
    name,
    address,
    login,
    password
)
VALUES (
    'Muster',
    'Hamburg, 22547, Vogt-Kölln-Straße 30',
    'login',
    'password'
);

INSERT INTO estate (
    city,
    postalCode,
    street,
    streetNumber,
    squareArea,
    estateAgentId
)
VALUES (
    'Hamburg',
    '22547',
    'Vogt-Kölln-Straße',
    30,
    'Stellingen',
    1
);

INSERT INTO apartment (
    id,
    floor,
    rent,
    rooms,
    balcony,
    builtInKitchen
)
VALUES (
    1,
    5,
    '500.00',
    2,
    'X',
    'X'
);

INSERT INTO estate (
    city,
    postalCode,
    street,
    streetNumber,
    squareArea,
    estateAgentId
)
VALUES (
    'Hamburg',
    '22547',
    'Vogt-Kölln-Straße',
    31,
    'Stellingen',
    1
);
INSERT INTO house (
    id,
    floors,
    price,
    garden
)
VALUES (
    2,
    2,
    '400000.00',
    ' '
);

INSERT INTO person (
    firstName,
    name,
    address
)
VALUES (
    'Max',
    'Muster',
    'Hamburg, 22547, Vogt-Kölln-Straße 31'
);

INSERT INTO person (
    firstName,
    name,
    address
)
VALUES (
    'Hans',
    'Müller',
    'Hamburg, 22547, Vogt-Kölln-Straße 30'
);

INSERT INTO contract (
    contractNo,
    date,
    place
)
VALUES (
    111111,
    '2015-04-01',
    'Hamburg'
);

INSERT INTO tenancyContract (
    id,
    startDate,
    duration,
    additionalCosts,
    apartmentId,
    personId
)
VALUES (
    1,
    '2015-05-01',
    60,
    '90.00',
    1,
    2
);

INSERT INTO contract (
    contractNo,
    date,
    place
)
VALUES (
    111112,
    current date,
    'Hamburg'
);

INSERT INTO purchaseContract (
    id,
    noOfInstallments,
    intrestRate,
    houseId,
    personId
)
VALUES (
    2,
    24,
    0.07,
    2,
    1
);