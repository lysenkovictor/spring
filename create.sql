
    create table category_has_item (
       id_item bigint not null,
        id_category bigint not null,
        primary key (id_item, id_category)
    ) engine=InnoDB

    create table t_category (
       id bigint not null,
        category_name varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table t_item (
       id bigint not null,
        count integer,
        date_add datetime(6),
        price decimal(19,2),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table t_product_owner (
       id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table t_category_has_t_item (
       id_category bigint not null,
        id_item bigint not null,
        primary key (id_category, id_item)
    ) engine=InnoDB

    create table t_contact (
       id bigint not null,
        company varchar(255),
        email_private varchar(255),
        email_work varchar(255),
        id_roduct_owner bigint,
        phone_private varchar(255),
        phone_work varchar(255),
        primary key (id)
    ) engine=InnoDB

    alter table category_has_item 
       add constraint FK41ryqs9m8ltauoj1e9scoj9s3 
       foreign key (id_category) 
       references t_category (id)

    alter table category_has_item 
       add constraint FK4h0um6vo3go57ovaw3eywlw2c 
       foreign key (id_item) 
       references t_item (id)

    alter table t_item 
       add constraint FK9oxhggkiftn8o5r4csrp6c8x4 
       foreign key (id) 
       references t_product_owner (id)

    alter table t_category_has_t_item 
       add constraint FKq5oskxcawt8ikkoy54jxgk3ff 
       foreign key (id_item) 
       references t_item (id)

    alter table t_category_has_t_item 
       add constraint FK3mdjenomrwkjpxa6ofx87o6ha 
       foreign key (id_category) 
       references t_category (id)
