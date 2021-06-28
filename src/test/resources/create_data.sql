delete from orders_storage_test.orders;
insert orders_storage_test.orders (id, item, quantity, price, creation_date )
values (1, 'Bread', 5, 4.25, now()),
    (2, 'Loaf', 3, 2.68, now()),
    (3, 'Bread', 3, 3.75, now()),
    (4, 'Bread', 8, 4.50, now()),
    (5, 'Bread', 2, 4.25, '2021-06-25 22:30:00');