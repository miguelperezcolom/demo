create table if not exists mapeado (
    id varchar(255) primary key,
    third_party varchar(1024),
    type varchar(1024),
    riu_code varchar(1024),
    tp_code varchar(1024)
);