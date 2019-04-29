create or replace trigger trig_a1000 
before update
on trig_a1000
begin
  insert into b1000 values (:old.c1,:old.c2,:old.c3);
end;