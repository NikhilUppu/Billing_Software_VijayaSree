CREATE TABLE company (
	id VARCHAR(50) PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE product (
	id VARCHAR(60) PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	volume VARCHAR(50),
	price DECIMAL(12,2),
	image_url TEXT,
	company_id VARCHAR(50),
	CONSTRAINT fk_product_company FOREIGN KEY (company_id) REFERENCES company(id)
);

INSERT INTO company (id, name, is_active) VALUES ('nakasa', 'Nakasa Crop Science', TRUE);

INSERT INTO product (id, name, volume, price, company_id) VALUES
('nakasa-cinza-100ml','Cinza','100 ml',520,'nakasa'),
('nakasa-cinza-250ml','Cinza','250 ml',1150,'nakasa'),
('nakasa-armani-100ml','Armani','100 ml',180,'nakasa');
