create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

create table t_users (
    id          int AUTO_INCREMENT not null,
    username    varchar(50) not null,
    password    varchar(500) not null,
    enabled     tinyint(1) not null default '1',
    locked      tinyint(1) not null default '0',
    mobile      varchar(11) not null,
    roles       varchar(500),
    primary key(id)
);


