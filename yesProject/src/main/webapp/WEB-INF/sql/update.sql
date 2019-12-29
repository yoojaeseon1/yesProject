UPDATE branch_info SET acceptState=true;

SELECT * FROM branch_info;

SELECT * FROM branch_menu;

select * from branch_info a inner join branch_address b on a.id = b.id inner join branch_image c on a.id = c.id where a.acceptState = true;


 select * from branch_info a inner join branch_address b on a.id = b.id inner join branch_image c on a.id = c.id WHERE roadAddress LIKE "%강남%";