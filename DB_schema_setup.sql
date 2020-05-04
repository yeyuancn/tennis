create DATABASE tennis;

create user 'yye'@'localhost' IDENTIFIED BY '252525';

GRANT ALL on tennis.* to 'yye'@'localhost';