CREATE TABLE tbl_account(
	id serial PRIMARY Key,
	iban varchar(50) NOT NULL,
	bic_swift varchar(50) NOT NULL,
	client_id varchar(50) NOT NULL,
	CONSTRAINT unique_acc UNIQUE(client_id,iban,bic_swift)
);