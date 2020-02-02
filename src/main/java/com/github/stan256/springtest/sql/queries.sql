-- select users with sum of goods >
-- select u.first_name, sum(p.price) from users u
--     left join user_product up on u.user_id = up.user_id
--     left join product p on up.product_id = p.product_id
-- group by u.first_name
-- having sum(p.price) > 800;


select u.first_name, up.product_id from users u inner join user_product up on u.user_id = up.user_id;
