  SET SQL_SAFE_UPDATES = 0;
  
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
    ('Fresh'),
    ('Vegan'),
    ('Gluten-Free'),
    ('Dairy-Free'),
    ('Quick'),
    ('Comfort Food'),
    ('Low Carb'),
    ('Paleo'),
    ('Nut-Free'),
    ('Low Sodium'),
    ('Kid-Friendly');

        
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
    ('Spaghetti with Turkey Meatballs', 2, 1, 20, 'https://static01.nyt.com/images/2017/04/05/dining/05COOKING-NIGMEATBALLS2/05COOKING-NIGMEATBALLS2-superJumbo.jpg', 'This spaghetti and meatballs recipe is sure to be a winner with your family. Because it’s made healthier with whole wheat spaghetti and turkey meatballs, 
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
    ('BBQ Chicken Sammies', 1, 4, 200, 'https://www.theanthonykitchen.com/wp-content/uploads/2017/03/Barbecue-Chicken-Sandwich-5.jpg',"A chicken sandwich that the whole family will love!", 'Ingredients
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

6 soft sammy buns, such as soft burger rolls, split Bring liquids to a simmer in a small to medium skillet and slide in the chicken breast meat. Gently poach the chicken 10 minutes, turning once about after 5 minutes.
    While chicken poaches, heat a second medium skillet over medium low heat. To hot skillet, add extra-virgin olive oil and garlic and onion and gently saute until chicken is ready to come out of poaching liquids. 
    Combine the next 5 ingredients in a medium bowl and reserve. 
    When the chicken has cooked through, add 2 ladles of the cooking liquid to the bowl, combining with the sauces, spices, brown sugar and tomato paste. 
    Once the liquids and seasonings are combined, remove chicken, slice it, and transfer to the medium bowl. Using 2 forks, shred the chicken and combine with the liquids. 
    Add the shredded chicken to the onions and garlic and combine well. Simmer together 5 to 10 minutes, using extra cooking liquids to make your chicken as saucy as you like. 
    Combine sour and sweet pickles in a small bowl. Split rolls and fill with scoops of shredded chicken. Top with pickle relish and serve.',5, '2024-04-06 00:59:02', '2024-04-06 00:59:02'),
      ('Classic Beef Stroganoff', 2, 1, 30, 'https://natashaskitchen.com/wp-content/uploads/2019/04/Beef-Stroganoff-6.jpg', 
    'A classic Russian dish with tender beef in a creamy mushroom sauce, served over egg noodles or rice.', 
    '1. In a large skillet, cook beef strips in butter until browned. 2. Remove beef and set aside. 3. In the same skillet, sauté onions and mushrooms until tender. 4. Stir in flour, then gradually add beef broth and sour cream. 5. Return beef to skillet, simmer until thickened. 6. Serve over noodles or rice.', 
    1, '2024-08-01 12:00:00', '2024-08-01 12:00:00'),

    ('Vegetarian Chili', 2, 2, 45, 'https://www.allrecipes.com/thmb/IBOMtUsQutvqdCWXrXjEZNZsNFk=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/215311-hearty-vegan-slow-cooker-chili-ddmfs-3X4-0131-245669e46ac7445497b40814197724a8.jpg', 
    'A hearty and flavorful chili packed with beans and vegetables, perfect for a satisfying meal.', 
    '1. Heat olive oil in a large pot. 2. Add onions, garlic, and bell peppers, cook until softened. 3. Stir in spices, tomatoes, beans, and vegetable broth. 4. Simmer for 30 minutes, stirring occasionally. 5. Serve with your favorite chili toppings.', 
    1, '2024-08-02 14:00:00', '2024-08-02 14:00:00'),

    ('Lemon Garlic Shrimp', 1, 0, 20, 'https://www.wholesomeyum.com/wp-content/uploads/2022/01/wholesomeyum-Lemon-Garlic-Butter-Shrimp-Recipe-19.jpg', 
    'Quick and easy shrimp sautéed in a tangy lemon garlic sauce, perfect for a light dinner.', 
    '1. Heat olive oil in a skillet over medium heat. 2. Add garlic and cook until fragrant. 3. Add shrimp, lemon juice, and zest. 4. Cook until shrimp are pink and opaque. 5. Garnish with parsley and serve with rice or pasta.', 
    2, '2024-08-03 16:00:00', '2024-08-03 16:00:00'),

    ('Chicken Alfredo', 2, 0, 30, 'https://www.budgetbytes.com/wp-content/uploads/2022/07/Chicken-Alfredo-bowl.jpg', 
    'Creamy Alfredo sauce with tender chicken served over fettuccine pasta.', 
    '1. Cook fettuccine according to package instructions. 2. In a skillet, cook chicken until golden and cooked through. 3. Remove chicken and set aside. 4. In the same skillet, make Alfredo sauce by combining butter, cream, and Parmesan cheese. 5. Toss pasta and chicken in the sauce. 6. Serve with additional Parmesan and parsley.', 
    2, '2024-08-04 18:00:00', '2024-08-04 18:00:00'),

    ('Beef Tacos', 1, 1, 25, 'https://iamhomesteader.com/wp-content/uploads/2022/04/birria-taco-2.jpg', 
    'Flavorful beef tacos with all the traditional toppings.', 
    '1. Cook ground beef with taco seasoning until browned. 2. Warm taco shells. 3. Fill shells with beef and top with lettuce, cheese, salsa, and sour cream.', 
    1, '2024-08-05 20:00:00', '2024-08-05 20:00:00'),

    ('Greek Salad', 1, 0, 15, 'https://hips.hearstapps.com/hmg-prod/images/greek-salad-index-642f292397bbf.jpg?crop=0.888888888888889xw:1xh;center,top&resize=1200:*', 
    'A refreshing salad with tomatoes, cucumbers, olives, and feta cheese, dressed with a lemon-oregano vinaigrette.', 
    '1. Combine tomatoes, cucumbers, red onion, olives, and feta in a bowl. 2. Whisk together olive oil, lemon juice, oregano, salt, and pepper. 3. Toss salad with dressing and serve.', 
    1, '2024-08-06 22:00:00', '2024-08-06 22:00:00'),

    ('Pancakes', 1, 0, 20, 'https://www.allrecipes.com/thmb/WqWggh6NwG-r8PoeA3OfW908FUY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/21014-Good-old-Fashioned-Pancakes-mfs_001-1fa26bcdedc345f182537d95b6cf92d8.jpg', 
    'Fluffy pancakes that are perfect for breakfast or brunch.', 
    '1. Mix flour, sugar, baking powder, and salt in a bowl. 2. In another bowl, combine milk, eggs, and melted butter. 3. Stir wet ingredients into dry ingredients until just combined. 4. Cook on a hot griddle until bubbles form, then flip and cook until golden.', 
    2, '2024-08-07 10:00:00', '2024-08-07 10:00:00'),

    ('Caprese Salad', 1, 0, 10, 'https://whatsgabycooking.com/wp-content/uploads/2023/06/Dinner-Party-__-Traditional-Caprese-1-1200x800-1.jpg', 
    'A simple salad of fresh tomatoes, mozzarella, and basil, drizzled with balsamic glaze.', 
    '1. Slice tomatoes and mozzarella. 2. Arrange on a plate, alternating slices. 3. Top with fresh basil leaves. 4. Drizzle with olive oil and balsamic glaze.', 
    2, '2024-08-05 12:00:00', '2024-08-05 12:00:00'),

    ('Vegetable Stir-Fry', 2, 1, 25, 'https://www.onceuponachef.com/images/2017/02/Asian-Vegetable-Stir-Fry-3.jpg', 
    'A colorful and healthy vegetable stir-fry served with rice or noodles.', 
    '1. Heat oil in a wok or large skillet. 2. Add sliced vegetables and cook until tender-crisp. 3. Stir in sauce (soy sauce, garlic, ginger, etc.). 4. Serve over rice or noodles.', 
    1, '2024-08-06 14:00:00', '2024-08-06 14:00:00'),

    ('Apple Pie', 3, 0, 90, 'https://www.inspiredtaste.net/wp-content/uploads/2022/11/Apple-Pie-Recipe-Video.jpg', 
    'Classic apple pie with a flaky crust and a sweet, spiced apple filling.', 
    '1. Prepare pie crust and place in a pie dish. 2. Toss sliced apples with sugar, cinnamon, and flour. 3. Fill pie crust with apple mixture. 4. Top with another crust or lattice pattern. 5. Bake at 375°F (190°C) for 50-60 minutes.', 
    2, '2024-08-07 16:00:00', '2024-08-07 16:00:00'),

    ('Chocolate Chip Cookies', 1, 0, 30, 'https://sallysbakingaddiction.com/wp-content/uploads/2013/05/classic-chocolate-chip-cookies.jpg', 
    'Soft and chewy chocolate chip cookies that are always a hit.', 
    '1. Cream together butter and sugars. 2. Add eggs and vanilla, then mix in flour, baking soda, and salt. 3. Stir in chocolate chips. 4. Drop spoonfuls of dough onto a baking sheet and bake at 350°F (175°C) for 10-12 minutes.', 
    1, '2024-08-8 18:00:00', '2024-08-8 18:00:00');
	
    insert into recipe_tags 
		(recipe_id, tag_id) 
        values (1,1),(2,15),(3,1),(4,1),(5,6),(6,15),(7,10),(8,10),(9,9),(10,1),(11,1),(12,15),(13,15),(14,1);
    insert into reviews
		(user_id, recipe_id, review_title, review_desc, rating)
	values
		('1', '2', null, null, 3),
		('3', '1', 'i ate it, it was good', null, 4),
		('5', '2', 'This recipe needs a lot of work', 'I swear people never know how to cook these days. blah blah blah I am salty af because I used salt instead of sugar and it sucked.
        I followed all the recipe instructions but i replaced all the spices with salt and it was too salty', 1),
		('8', '2', 'This was great for the witch\'s gathering!', 'Ohohohohhohoho, we added some good ol eye of newt and dragon\'s blood for added effect! it really was quite delicious hehehehe!', 5);
        
SET SQL_SAFE_UPDATES = 1;
