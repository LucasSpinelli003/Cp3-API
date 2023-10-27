create table tb_empresa (
	cd_empresa int primary key NOT NULL,
	nm_empresa varchar2(100) NOT NULL,
 	nr_funcionario int
);

create table tb_vaga (
	cd_vaga int primary key NOT NULL,
	ds_titulo varchar2(50) NOT NULL,
 	ds_vaga varchar2(255),
    vl_salario number(10,2),
    dt_publicacao date
);

alter table tb_vaga 
add cd_empresa number 
constraint fk_cd_empresa
REFERENCES tb_empresa(cd_empresa);
    
