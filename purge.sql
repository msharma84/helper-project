
-- It's a purging trigger which clears data based on status, here in out case queue is a table
-- where transactional data is added and creating large chunk of data in the table

-- We created a replica of the original table - queue_history and contains only last 10 transactions
CREATE OR REPLACE FUNCTION queue_update_trigger()
   RETURNS TRIGGER LANGUAGE plpgsql
AS $$
declare 
   totalcount integer;
BEGIN
if(new.status='processed'  OR new.status='failed')
then
   INSERT INTO queue_history 
    (column_1, column_2, column_3)
    VALUES(new.column_1_data,
         new.column_2_data,
         new.column_3_data); 
End If;
totalcount:= (select count(*) from queue_history where column_1=new.column_1);
if(totalcount>10) 
then 
   delete from queue_history where id =
    (select id from queue_history where column_1=new.column_1 order by id limit 1);
    delete from queue where id in (select id from queue where status in ('processed','failed'));
end if;
   
return null;
END;
   $$;