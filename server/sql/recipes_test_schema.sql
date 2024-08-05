drop database if exists hashbrownhash_test;
create database hashbrownhash_test;
use hashbrownhash_test;

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
);

create table recipe_tags (
 tag_id int not null,
 recipe_id int not null,
 constraint pk_recipe_tag
 primary key (recipe_id, tag_id),
 constraint fk_tag_recipe
 foreign key (tag_id)
 references tags(tag_id),
 constraint fk_recipe_tag
 foreign key (recipe_id)
 references recipes(recipe_id)
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
        references recipe_users(user_id),
    constraint fk_review_recipe_id
        foreign key (recipe_id)
        references recipes(recipe_id)
);
delimiter //
create procedure set_known_good_state()


begin

    delete from reviews;
    alter table reviews auto_increment = 1;
    delete from recipe_tags;
    delete from recipes;
    alter table recipes auto_increment = 1;
    delete from tags;
    alter table tags auto_increment = 1;
	delete from recipe_users;
    alter table recipe_users auto_increment = 1;
	delete from user_roles;
	alter table user_roles auto_increment = 1;

 insert into user_roles(role_title) values
        ('USER'),
        ('ADMIN');
        
	insert into tags (tag_name)
		values
	('Hot'),
    ('Healthy'),
    ('Juice'),
    ('Organic'),
    ('Fresh');

        
	insert into recipe_users 
		(first_name, last_name, username, password_hash, email, role_id) 
	values
('Leelah','Vickerstaff','vickerstaff0','$2a$04$k0lf/FP9baReMKDwXCVNcuehjJhOIagB6xm0UIL6v8laCkZvuV7bC','vickerstaff0@desdev.cn',1),
('Constantina','Gianasi','gianasi1','$2a$04$mADWCq5ZiNFay5VJFvLiG.hs8RbGJfzPIIHaMfOUp/69FOdBOenNi','gianasi1@springer.com',1),
('Samara','McKeney','mckeney2','$2a$04$3smH/AfDdZvKisIefX6djO28aqhKqavv0ePfW3xc17IqeDB80Dv4.','mckeney2@bing.com',1),
('Wyn','Du Plantier','duplantier3','$2a$04$3YOFz4ZhgBGDs7gBoQZm..PeQsRHfdZK4OfoVyw2Gda54bRlXB63G','duplantier3@angelfire.com',1),
('Vivien','Liell','liell4','$2a$04$ay9mGST1KiMgwaM8swhsgOQR.lLA4wuM5DTiDYdd2ldTCT9PfQq36','liell4@yale.edu',1),
('Mateo','Collough','collough5','$2a$04$Cfu.rRRSQ4iqxus9adlTUefOKwHNlLygYGg2VS/ACVGx6/c1J88Rm','collough5@hc360.com',1),
('Orland','Monni','monni6','$2a$04$SuCVDWABmalcJiPvjmmZCODupB3FMO1CBeAzFqoCL6sHp9gB7YFP6','monni6@washingtonpost.com',1),
('Admin','Slarke','admin','$2a$04$XdcZB7ygdp/nFcHx3FzKFeE3RUH0pTt7LDcTVQJva6EElFhRjO8SW','admin@cam.ac.uk',2),
('Bengt','Hugonin','hugonin8','$2a$04$78PLdDuy6nQB0ZMR..vcruQGsFX64gdorzHPDEZ453ZzDmGBZ.l8i','hugonin8@discuz.net',1),
('Deeanne','Parade','parade9','$2a$04$6q6nJYv8cBzunRufEnOxBe/6oGKMnA0MAbKT2KXwWINUJHTOker7G','parade9@geocities.com',1);
        
	insert into recipes 
    (recipe_name, difficulty, spiciness, prep_time, image_link, recipe_desc, recipe_text, user_id, time_posted, time_updated)
    values 
    ('Spaghetti with Turkey Meatballs', 2, 1, 20, null, 'This spaghetti and meatballs recipe is sure to be a winner with your family. Because it’s made healthier with whole wheat spaghetti and turkey meatballs, 
    you’ll feel good knowing it’s good for them too. Making it all in the Instant Pot is certainly a dinner game changer. The Instant Pot makes spaghetti and meatballs super easy!', 
    'I initially tried sautéing the meatballs first, but this resulted in extra time and work. Plus, the meatballs tend to stick to the pot. Also, I was trying to avoid the dreaded IP “burn” error on newer models. To prevent the burn message, I skipped the browning step and plopped them right into my sauce, which helped with cooking times and ultimately made for juicer meatballs.
    Building a sauce with garlic and canned tomatoes (rather than starting with a jarred sauce) gave me more control of the amount of oil used in the recipe. Here are some helpful tips for success:
    Make the Meatballs. Don’t overwork the meatballs because it will make them tough. Mix all the ingredients well, adding the turkey last.
    Make the Marinara Sauce. This pasta is saucy and delicious. I start with garlic and olive oil and then add crushed tomatoes and basil. Super simple.
    Add the Meatballs. Just nestle the meatballs in. If you have a 6-quart pot, they should fit in one layer. If not, just add more on top, but be sure not to smash them.
    Add Pasta. Add your spaghetti, which you will need to break in half so the noodles fit in the pot. Do not stir. You do not want the noodles to end up on the bottom of the pot – they could stick to the pot and burn. Then, pour in your broth or water.
    Pressure Cook. Cook on high pressure for 8 minutes (it takes about 10 minutes to come up to pressure). Then, do a quick release by turning the valve and uncover. You don’t want to do a natural 
    release because your noodles could end up being overcooked. Stir everything at this point. If it looks too watery, don’t worry – it will thicken as you stir.', 2, '2023-10-11 10:07:55', '2023-10-11 10:07:55'),
    ('No Waste Carnitas', 3, 2, 40, 'https://www.seriouseats.com/thmb/NGEENXeiy0of0H5q5ufL-3Gl0rw=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/20220209-no-waste-tacos-de-carnitas-with-salsa-verde-Hero-6109a35fdb2d4dd3a4c27fb32193807d.jpg',
    'Carnitas. The undisputed king of the taco cart. The Mexican answer to American pulled pork, they should be, at their best, moist, juicy, and ultra-porky with the rich, 
    tender texture of a French confit, with plenty of well-browned crisp edges. The most famous version of the dish comes from Michoacán, in central Mexico. Delicately flavored with a hint of orange, 
    onion, and occasionally some warm herbs or spices like cinnamon, cloves, bay leaf, or oregano, 
    all it needs is a squeeze of lime, some chopped onion and cilantro, and simple hot salsa to form a snack of unrivaled deliciousness.', ' Adjust oven rack to middle position and preheat oven to 275°F (135°C). Cut one onion into fine dice and combine with cilantro. Refrigerate until needed. Split remaining onion into quarters. Set aside. Season pork with 1 tablespoon salt and place in a 9- by 13-inch glass baking dish. The pork should fill the dish with no extra space. Split orange into quarters and squeeze juice over pork. Nestle squeezed orange pieces into dish. Add 2 onion quarters, 4 cloves garlic, bay leaves, and cinnamon stick to dish. Nestle everything into an even layer. Pour vegetable oil over surface. Cover dish tightly with aluminum foil and place in oven. Cook until pork is fork tender, about 3 1/2 hours.
    Cubes of pork in a dish with onions, oranges, and seasonings
    Set large fine-mesh strainer over a 1-quart liquid measuring cup or bowl. Using tongs, remove and discard orange peel, onion, garlic, cinnamon stick, and bay leaves. Transfer pork, rendered fat, and cooking liquid to strainer. Let drain for 10 minutes. Transfer pork back to baking dish. You should end up with about 1/2 cup cooking liquid and 1/2 cup fat. Using a flat spoon or fat separator, skim fat from surface and add back to pork. Shred pork into large chunks with fingers or two forks. Season to taste with salt. Refrigerate until ready to serve. Transfer remaining cooking liquid to medium saucepan.
    Cooked pork in a strainer, with cooking liquid and fat separated. Two forks shredding pork.
    Add tomatillos, remaining 2 onion quarters, remaining 2 garlic cloves, and jalapeños to saucepan with strained pork liquid. Add water until it is about 1 inch below the top of the vegetables. Bring to a boil over high heat, reduce to a simmer, and cook until all vegetables are completely tender, about 10 minutes. Blend salsa with immersion blender or in a countertop blender until smooth. Season to taste with salt. Allow to cool and refrigerate until ready to use.
    Vegetables cooking in pork liquid, then blended with an immersion blender.

    To serve: Preheat broiler to high with oven rack 4 inches below heating element. Broil pork until brown and crisp on surface, about 6 minutes. Remove pork, stir with a spoon to expose new bits to heat, and broil again until crisp, 6 more minutes. Tent with foil to keep warm.
    Broiled pork pieces on a baking sheet

    Meanwhile, heat tortillas. Preheat an 8-inch cast iron skillet over medium-high heat until hot. Working one tortilla at a time, dip tortilla in bowl filled with water. Transfer to hot skillet and cook until water evaporates from first side and tortilla is browned in spots, about 30 seconds. Flip and cook until dry, about 15 seconds longer. Transfer tortilla to a tortilla warmer, or wrap in a clean dish towel. Repeat with remaining tortillas.
    Dipping tortillas in water, then heating them up in a cast iron skillet.', 3, '2024-06-23 18:40:22', '2024-07-25 22:16:10'),
    ('BBQ Chicken Sammies', 1, 4, 200, null, 'Ingredients
1 cup chicken stock

1 bottle Mexican beer

4 pieces, 6 ounces each boneless, skinless chicken breast

2 tablespoons extra-virgin olive oil, 2 turns of the pan

2 cloves garlic, chopped

1 medium onion, peeled and finely chopped

2 tablespoons Worcestershire sauce, eyeball it

1 tablespoon hot sauce (recommended: Tabasco)
tap here

2 tablespoons grill seasoning blend (recommended: Montreal Steak Seasoning, by McCormick)

3 tablespoons dark brown sugar

4 tablespoons tomato paste

1 large sour deli pickle, chopped

6 to 8 slices sweet bread and butter pickles, chopped 

6 to 8 slices sweet bread and butter pickles, chopped

6 soft sammy buns, such as soft burger rolls, split', 'Bring liquids to a simmer in a small to medium skillet and slide in the chicken breast meat. Gently poach the chicken 10 minutes, turning once about after 5 minutes.
    While chicken poaches, heat a second medium skillet over medium low heat. To hot skillet, add extra-virgin olive oil and garlic and onion and gently saute until chicken is ready to come out of poaching liquids. 
    Combine the next 5 ingredients in a medium bowl and reserve. 
    When the chicken has cooked through, add 2 ladles of the cooking liquid to the bowl, combining with the sauces, spices, brown sugar and tomato paste. 
    Once the liquids and seasonings are combined, remove chicken, slice it, and transfer to the medium bowl. Using 2 forks, shred the chicken and combine with the liquids. 
    Add the shredded chicken to the onions and garlic and combine well. Simmer together 5 to 10 minutes, using extra cooking liquids to make your chicken as saucy as you like. 
    Combine sour and sweet pickles in a small bowl. Split rolls and fill with scoops of shredded chicken. Top with pickle relish and serve.',5, '2024-04-06 00:59:02', '2024-04-06 00:59:02');
	
    insert into recipe_tags 
		(recipe_id, tag_id) 
        values (1,3), (1,4), (2,1), (2,2), (2,5), (2,3);
    insert into reviews
		(user_id, recipe_id, review_title, review_desc, rating)
	values
		('1', '2', null, null, 3),
		('3', '1', 'i ate it, it was good', null, 4),
		('5', '2', 'This recipe needs a lot of work', 'I swear people never know how to cook these days. blah blah blah I am salty af because I used salt instead of sugar and it sucked.
        I followed all the recipe instructions but i replaced all the spices with salt and it was too salty', 1),
		('8', '2', 'This was great for the witch\'s gathering!', 'Ohohohohhohoho, we added some good ol eye of newt and dragon\'s blood for added effect! it really was quite delicious hehehehe!', 5);
        

end //
-- 4. Change the statement terminator back to the original.
delimiter ;


