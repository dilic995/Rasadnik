use rasadnik_db;

-- TRIGERI

drop trigger if exists update_transaction_after_insert_purchase;
delimiter $$
create trigger update_transaction_after_insert_purchase after insert on purchase for each row
begin
	if (new.paid_off=true) then
		insert into transaction values (null, new.price, true, new.purchase_id); -- false je in, a true je out
	end if;
end$$
delimiter ;

drop trigger if exists update_transaction_after_insert_sale;
delimiter $$
create trigger update_transaction_after_insert_sale after insert on sale for each row
begin
	if (new.paid_off=true) then
		insert into transaction values (null, new.price, false, new.sale_id); -- false je in, a true je out
	end if;
end$$
delimiter ;

drop trigger if exists update_transaction_after_update_purchase;
delimiter $$
create trigger update_transaction_after_update_purchase after update on purchase for each row
begin
	if (old.paid_off=false AND new.paid_off=true) then
		insert into transaction values (null, new.price, true, new.purchase_id); -- false je in, a true je out
	end if;
end$$
delimiter ;

drop trigger if exists update_transaction_after_update_sale;
delimiter $$
create trigger update_transaction_after_update_sale after update on sale for each row
begin
	if (old.paid_off=false AND new.paid_off=true) then
		insert into transaction values (null, new.price, false, new.sale_id); -- false je in, a true je out
	end if;
end$$
delimiter ;

drop trigger if exists update_tool_after_insert_tool_item;
delimiter $$
create trigger update_tool_after_insert_tool_item after insert on tool_item for each row
begin
	update tool t
	set count = count + 1 
	where t.tool_id = new.tool_id;
end$$
delimiter ;

drop trigger if exists update_tool_after_insert_tool_maintance_activity;
delimiter $$
create trigger update_tool_after_insert_tool_maintance_activity after insert on tool_maintance_activity for each row
begin
	if (new.up_to_date_service=true) then
		update tool_item ti
		set next_service_date = date_add(next_service_date, interval 1 year)
		where ti.tool_item_id = new.tool_item_id;
	end if;
end$$
delimiter ;