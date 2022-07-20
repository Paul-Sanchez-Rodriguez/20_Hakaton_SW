USE master
GO

DROP DATABASE IF EXISTS DBPizzahut
go

CREATE DATABASE DBPizzahut
GO

USE DBPizzahut
GO


CREATE TABLE CLIENTE (
    IDCLI int IDENTITY(1,1) NOT NULL,
    NOMCLI varchar(40)  NULL,
    APERCLI varchar(40)  NULL,
    DIRCLI varchar(60)  NULL,
    CELCLI char(9)  NULL,
    ESTCLI char(1)  NULL,
    CODUBI char(6)  NOT NULL,
    CONSTRAINT CLIENTE_pk PRIMARY KEY  (IDCLI)
);



-- Table: UBIGEO
CREATE TABLE UBIGEO (
    CODUBI char(6)  NOT NULL,
    PROVUBI varchar(20)  NULL,
    DEPUBI varchar(20)  NULL,
    DISUBI varchar(20)  NULL,
    CONSTRAINT UBIGEO_pk PRIMARY KEY  (CODUBI)
);



-- Table: EMPLEADO
CREATE TABLE EMPLEADO (
    IDEMP int IDENTITY(1,1) NOT NULL,
    NOMEMP varchar(60)  NULL,
    APEEMP varchar(60)  NULL,
    DIREMP varchar(60)  NULL,
    CELEMP char(9)  NULL,
    DNIEMP char(8)  NULL,
    ROLEMP char(1)  NULL,
    ESTEMP char(1)  NULL,
	IDSUR INT NULL,
    CODUBI Char(6)  NOT NULL,
    CONSTRAINT EMPLEADO_pk PRIMARY KEY  (IDEMP)
);


-- Table: PRODUCTO
CREATE TABLE PRODUCTO (
    CODPRO varchar(10)  NOT NULL,
    NOMPRO varchar(40)  NULL,
    DESPRO varchar(60)  NULL,
    PREPRO decimal(8,2)  NULL,
	STOCPRO int NOT NULL,
    ESTPRO char(1)  NULL,
    CONSTRAINT PRODUCTO_pk PRIMARY KEY  (CODPRO)
);



-- Table: SUCURSAL
CREATE TABLE SUCURSAL (
    IDSUR int IDENTITY(1,1) NOT NULL,
    NOMSUR varchar(60)  NULL,
    DIRSUR varchar(60)  NULL,
    CODUBI char(6)  NOT NULL,
    CONSTRAINT SUCURSAL_pk PRIMARY KEY  (IDSUR)
);



-- Table: VENTA
CREATE TABLE VENTA (
    IDVENT int IDENTITY(1,1) NOT NULL,
    FECVENT date  NULL,
    IDCLI int  NOT NULL,
    IDEMP int  NOT NULL,
    CONSTRAINT VENTA_pk PRIMARY KEY  (IDVENT)
);

-- Table: VENTA_DETALLE
CREATE TABLE VENTA_DETALLE (
    IDVENTDET int IDENTITY(1,1) NOT NULL,
    CANVENTDET int  NULL,
    CODPRO varchar(10)  NOT NULL,
    IDVENT int  NOT NULL,
    CONSTRAINT VENTA_DETALLE_pk PRIMARY KEY  (IDVENTDET)
);
GO

-- foreign keys
-- Reference: CLIENTE_UBIGEO (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: EMPLEADO_UBIGEO (table: EMPLEADO)
ALTER TABLE EMPLEADO ADD CONSTRAINT EMPLEADO_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: SUCURSAL_EMPLEADO (table: SUCURSAL)
ALTER TABLE EMPLEADO ADD CONSTRAINT SUCURSAL_EMPLEADO
    FOREIGN KEY (IDSUR)
    REFERENCES SUCURSAL (IDSUR);

-- Reference: SUCURSAL_UBIGEO (table: SUCURSAL)
ALTER TABLE SUCURSAL ADD CONSTRAINT SUCURSAL_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: VENTA_CLIENTE (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_CLIENTE
    FOREIGN KEY (IDCLI)
    REFERENCES CLIENTE (IDCLI);

-- Reference: VENTA_DETALLE_PRODUCTO (table: VENTA_DETALLE)
ALTER TABLE VENTA_DETALLE ADD CONSTRAINT VENTA_DETALLE_PRODUCTO
    FOREIGN KEY (CODPRO)
    REFERENCES PRODUCTO (CODPRO);

-- Reference: VENTA_DETALLE_VENTA (table: VENTA_DETALLE)
ALTER TABLE VENTA_DETALLE ADD CONSTRAINT VENTA_DETALLE_VENTA
    FOREIGN KEY (IDVENT)
    REFERENCES VENTA (IDVENT);

-- Reference: VENTA_EMPLEADO (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_EMPLEADO
    FOREIGN KEY (IDEMP)
    REFERENCES EMPLEADO (IDEMP)
	GO

INSERT INTO UBIGEO
(CODUBI,PROVUBI,DEPUBI,DISUBI)
VALUES
('101010', 'Lima', 'Lima', 'Lima'),
('101011', 'Lima', 'Lima', 'Ancon'),
('101012', 'Lima', 'Lima', 'Ate'),
('101013', 'Lima', 'Lima', 'Breña'),
('101014', 'Lima', 'Lima', 'Carabayllo'),
('101015', 'Lima', 'Lima', 'Comas'),
('101016', 'Lima', 'Lima', 'Chaclacayo'),
('101017', 'Lima', 'Lima', 'Chorrillos'),
('101018', 'Lima', 'Lima', 'La Victoria'),
('101019', 'Lima', 'Lima', 'La Molina');
GO

INSERT INTO CLIENTE
(NOMCLI,APERCLI,DIRCLI,CELCLI,ESTCLI,CODUBI)
VALUES
('Teresa','Maldini de Santos','Av. Alfonso Ugarte','943385483','A','101010'),
('Mrian','Medina','Av. Emancipacion','943385483','A','101010'),
('Amparo','Sebastian','AV. Benavides','943385483','A','101010'),
('Hortensia','Hernando','Av. Montalban','943385483','A','101010'),
('Zahara','Seco','Av. Morales','943385483','A','101010'),
('Marco','de Castro','Av. Argentina','943385483','A','101010'),
('Ibon','Riera','Av.Aviacion','943385483','A','101010'),
('Rafael','Lopez Aliaga','Av. Oscar Benavides','943385483','A','101010'),
('Marisol','Guillen','Av.Teodoro','943385483','A','101010'),
('Rita','Capdevila','Av. Santa Beatriz','943385483','A','101010');
GO

INSERT INTO SUCURSAL
(NOMSUR,DIRSUR,CODUBI)
VALUES
('Pizzahut','Av Manco Capac','101010'),
('Pizzahut ','Av Larcomar','101011'),
('Pizzahut','Av Benavides','101012'),
('Pizzahut','Av Montalban','101013'),
('Pizzahut','Av Condoray','101014');
GO


INSERT INTO EMPLEADO
(NOMEMP,APEEMP,DIREMP,CELEMP,DNIEMP,ROLEMP,ESTEMP,IDSUR,CODUBI)
VALUES
('Jeferson','Palomino Flores','AV. Miraflores','943385483','72530657','V','A','1','101010'),
('Pool','Sanchez Rodriguez','AV. Larcomar','943385494','72530668','D','A','2','101011'),
('Robert','Linares Rojas','AV. Montalban','943385594','72530768','V','A','3','101011'),
('Julia','Quispe Mamani','AV. Benavides','943385666','72530894','V','A','4','101012'),
('Diego','Huapaya Rivera','AV. Valcomar','943385777','72530905','D','A','5','101012'),
('Elser','Huapaya Flores','AV. 9 DE DICIEMBRE','943385888','72530769','V','A','5','101012'),
('Mia','Torres Quispe','AV. Barrios altos','943385999','72530666','V','A','4','101013'),
('Jose','Correa Palomino','AV. Condoray','943399887','72530123','V','A','3','101014'),
('Jesus','Sanchez Games','AV. San Jose','943389635','72535648','V','A','2','101015'),
('Juan','Cabos','AV. Linda Rosa','943326548','72532643','D','A','1','101016');
GO


INSERT INTO PRODUCTO
(CODPRO,NOMPRO,DESPRO,PREPRO,STOCPRO,ESTPRO)
VALUES
('C0101','Pizza Americana','Puro queso','35.00','10','A'),
('C0102','Pizza Mediana','con peperoni','36.00','15','A'),
('C0103','Pizza hut cheese','relleno de queso','29.00','5','A'),
('C0104','Pizza doble o nada','2 familiares','70.00','6','A'),
('C0105','Pizza Jahuayana','pura piña','27.00','10','A'),
('C0106','Pizza Americana mediana','pa compartir','45.00','13','A'),
('C0107','Pizza Americana grande','para toda la familia','90.00','7','A'),
('C0108','Pizza hut mediana','full queso','45.00','2','A'),
('C0109','Pizza ht grande','para que te llenes','80.00','8','A'),
('C0110','Pizza doble grande','para toda el barrio','99.00','5','A');
GO

INSERT INTO VENTA 
(FECVENT,IDCLI,IDEMP)
VALUES
('2022-07-17','1','1')
GO

INSERT INTO VENTA_DETALLE
(CANVENTDET,CODPRO,IDVENT)
VALUES
('1','C0108','1')
GO

CREATE PROCEDURE spInsertVent
(
@IDCLI INT,
@IDEMPL INT,

@CANVENT INT,
@CODPRO nvarchar(10)
)
AS
BEGIN
	SET NOCOUNT ON
	DECLARE
	@FECHVENT DATE;
	SELECT @FECHVENT = GETDATE();
	INSERT INTO VENTA (FECVENT,IDCLI,IDEMP)
	VALUES(@FECHVENT,@IDCLI,@IDEMPL);

	DECLARE @COMAX INT;
	SELECT @COMAX = MAX(IDVENT) FROM VENTA;

	INSERT INTO VENTA_DETALLE(CANVENTDET,IDVENT,CODPRO)
	VALUES(@CANVENT,@COMAX,@CODPRO);
END
GO

Create view VistaVentas
as select
ven.IDVENT as Venta,
ven.FECVENT as Fecha,
(cli.NOMCLI+ ' ' + cli.APERCLI) as cliente,
(emp.NOMEMP+ ' ' + emp.APEEMP )as empleado
from 
VENTA ven inner join  CLIENTE cli on ven.IDCLI = cli.IDCLI inner join 
EMPLEADO emp on ven.IDEMP = emp.IDEMP
go


CREATE VIEW BOLETAVENTA
AS SELECT 
ve.IDVENT,
cli.NOMCLI + ' ' +cli.APERCLI as datosCli,
em.NOMEMP + ' ' + em.APEEMP as datosEmp,
suc.DIRSUR as sucursal,
pro.NOMPRO as producto,
pro.PREPRO as precio,
vd.CANVENTDET as cantidad,
vd.CANVENTDET * pro.PREPRO as subtotal
FROM
VENTA ve inner join VENTA_DETALLE vd on ve.IDVENT = vd.IDVENT
inner join CLIENTE cli on ve.IDCLI=cli.IDCLI 
inner join EMPLEADO em on ve.IDEMP = em.IDEMP 
inner join PRODUCTO pro on  vd.CODPRO = pro.CODPRO
inner join SUCURSAL suc on suc.IDSUR = em.IDSUR
GO

CREATE VIEW Cliente_Venta 
AS SELECT
CLI.IDCLI,
cli.NOMCLI+ ' ' + CLI.APERCLI as NOMBRE_APELLIDO,
cli.DIRCLI as DIRCLI,
cli.CELCLI as CELCLI
FROM
CLIENTE CLI
GO
