drop database if exists hashbrownhash;
create database hashbrownhash;
use hashbrownhash;

-- create tables and relationships
create table user_roles (
    role_id int primary key auto_increment,
    role_title varchar(30) not null
);

create table tags (
    tag_id int primary key auto_increment,
    tag_name varchar(30) not null
);

create table recipe_users (
    user_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    username varchar(50) not null,
    password_hash varchar(250) not null,
    email varchar(250) not null,
    role_id int not null,
    constraint fk_user_role
    foreign key (role_id)
    references user_roles(role_id)
    on delete cascade
);

create table recipes (
    recipe_id int primary key auto_increment,
    recipe_name varchar(250) not null,
    difficulty tinyint(1) not null,
    spiciness tinyint(1) not null,
    prep_time smallint(1) not null,
    image_link varchar(400),
    -- short description of recipe
    recipe_desc varchar(1000) not null,
    -- actual recipe
    recipe_text varchar(5000) not null,
    -- who posted recipe
    user_id int not null,
    time_posted datetime not null,
    time_updated datetime,
    constraint fk_user_recipe
    foreign key (user_id)
    references recipe_users(user_id)
    on delete cascade
);

create table recipe_tags (
 tag_id int not null,
 recipe_id int not null,
 constraint pk_recipe_tag
 primary key (recipe_id, tag_id),
 constraint fk_tag_recipe
 foreign key (tag_id)
 references tags(tag_id)
 on delete cascade,
 constraint fk_recipe_tag
 foreign key (recipe_id)
 references recipes(recipe_id)
on delete cascade
);

create table reviews (
    review_id int not null primary key auto_increment,
    user_id int not null,
    recipe_id int not null,
    review_title varchar(250),
    review_desc varchar(1000),
    rating tinyint(1) not null,
    constraint fk_review_user_id
        foreign key (user_id)
        references recipe_users(user_id)
		on delete cascade,
    constraint fk_review_recipe_id
        foreign key (recipe_id)
        references recipes(recipe_id)
		on delete cascade
);
