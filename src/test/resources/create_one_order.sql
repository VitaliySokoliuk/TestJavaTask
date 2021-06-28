delete from orders_storage_test.orders;
insert orders_storage_test.orders (id, item, quantity, price, creation_date )
values (1, 'Bread', 5, 4.25, now())