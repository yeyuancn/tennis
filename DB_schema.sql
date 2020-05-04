-- drop views
drop view if exists player_result_view;
drop view if exists match_result_view;
drop view if exists message_view;
drop view if EXISTS search_league_view;

drop table if exists season;
create table season
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
 name varchar(50),
 league_id MEDIUMINT,
 match_played MEDIUMINT,
 create_time DATETIME,
 summarize_time DATETIME,
  primary key(id),
  unique key(name, league_id)
) engine =myisam;

drop table if exists league;
create table league
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
 name varchar(50),
 city varchar(30),
 state char(5),
 mode char(10),
 create_time DATETIME,
 current_season_id mediumint,
 player_cnt mediumint,
  primary key(id),
  unique key(name)
) engine =myisam;

-- recreate tables
drop table if exists acct;
create table acct
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
 first_name varchar(20),
 last_name varchar(20),
 email varchar(40),
 level varchar(5),
 password varchar(20),
 last_login DATETIME,
 login_cnt mediumint,
 reset_key mediumint,
  primary key(id),
  unique key(email)
) engine =myisam;

drop table if exists player;
create table player
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
acct_id mediumint,
league_id mediumint,
first_name varchar(20),
last_name varchar(20),
level varchar(5),
is_active boolean,
is_owner boolean,
is_admin boolean,
primary key(id),
unique key acct_league_id (acct_id, league_id)
) engine =myisam;
alter table player add index(acct_id);
alter table player add index(league_id);

drop table if exists message;
create table message
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
from_player_id mediumint,
to_player_id mediumint,
content varchar(2000),
message_time DATETIME,
removed_by_from boolean,
removed_by_to boolean,
primary key(id)
) engine =myisam;
alter table message add index(from_player_id);
alter table message add index(to_player_id);

drop table if exists match_result;
create table match_result
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
league_id mediumint,
season_id mediumint,
winner_id mediumint,
loser_id mediumint,
set1_score char(5),
set2_score char(5),
set3_score char(5),
match_score char(5),
enter_by_winner smallint,
game_won smallint,
game_lost smallint,
match_date DATETIME,
record_time DATETIME,
match_memo VARCHAR(200),
primary key(id)
) engine =myisam;
alter table match_result add index(winner_id);
alter table match_result add index(loser_id);


drop table if exists player_result;
create table player_result
(
id MEDIUMINT NOT NULL AUTO_INCREMENT,
player_id mediumint,
season_id mediumint,
match_won int,
match_lost int,
match_won_percent decimal(5,2),
game_won int,
game_lost int,
game_won_percent decimal(5,2),
primary key(id)
) engine =myisam;
alter table player_result add index(player_id);
alter table player_result add index(season_id);

-- create views
CREATE VIEW message_view AS SELECT m.*, u1.first_name as from_first_name, u1.last_name as from_last_name,
                              u2.first_name as to_first_name, u2.last_name as to_last_name
                            FROM message m join player u1 join player u2 where m.from_player_id = u1.id and m.to_player_id = u2.id;

CREATE VIEW match_result_view AS SELECT m.id, m.league_id, m.season_id, m.winner_id, m.loser_id, m.set1_score, m.set2_score, m.set3_score,
                                   m.match_score, m.enter_by_winner, m.match_date, m.record_time, m.match_memo,
                                   u1.first_name as winner_first_name, u1.last_name as winner_last_name,
                                   u2.first_name as loser_first_name, u2.last_name as loser_last_name
                            FROM match_result m join player u1 join player u2 where m.winner_id = u1.id and m.loser_id = u2.id;

CREATE VIEW player_result_view AS SELECT p.league_id, pr.season_id, p.first_name, p.last_name, p.is_active, p.id as player_id, pr.match_won, pr.match_lost, pr.match_won_percent,
                                    pr.game_won, pr.game_lost, pr.game_won_percent
                            FROM player_result pr join player p where pr.player_id = p.id;

CREATE VIEW search_league_view AS SELECT l.id, l.name, l.city, l.state, l.create_time, l.player_cnt, p.first_name, p.last_name
                                  FROM league l join player p where l.id = p.league_id and p.is_owner = true and l.mode = 'searchable';

-- insert data
insert into league(name, city, state, mode, create_time, current_season_id, player_cnt) values
  ('Big Four(DEMO)', 'New York', 'NY', 'searchable', now(), 1, 4);


insert into season(name, league_id, create_time, match_played) values ('Current Season', 1, now(), 0);

insert into acct (first_name, last_name, email, level, password, last_login, login_cnt) values ('Roger', 'Federer', 'roger@test.com', 'PRO', '1234', now(), 1);
insert into acct (first_name, last_name, email, level, password, last_login, login_cnt) values ('Rafael', 'Nadal', 'rafael@test.com', 'PRO', '1234', now(), 1);
insert into acct (first_name, last_name, email, level, password, last_login, login_cnt) values ('Novak', 'Djokovic', 'novak@test.com', 'PRO', '1234', now(), 1);
insert into acct (first_name, last_name, email, level, password, last_login, login_cnt) values ('Andy', 'Murray', 'andy@test.com', 'PRO', '1234', now(), 1);

insert into player (acct_id, league_id, first_name, last_name, level, is_active, is_owner, is_admin) values (1, 1, 'Roger', 'Federer', 'PRO', true, true, true);
insert into player (acct_id, league_id, first_name, last_name, level, is_active, is_owner, is_admin) values (2, 1, 'Rafael', 'Nadal', 'PRO',  true, false, false);
insert into player (acct_id, league_id, first_name, last_name, level, is_active, is_owner, is_admin) values (3, 1, 'Novak', 'Djokovic', 'PRO', true, false, false);
insert into player (acct_id, league_id, first_name, last_name, level, is_active, is_owner, is_admin) values (4, 1, 'Andy', 'Murray', 'PRO', true, false, false);


insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (1, 0, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (2, 0, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (3, 0, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (4, 0, 0, 0, 0.0, 0, 0, 0.0);


insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (1, 1, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (2, 1, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (3, 1, 0, 0, 0.0, 0, 0, 0.0);
insert into player_result (player_id, season_id, match_won, match_lost, match_won_percent, game_won, game_lost, game_won_percent) values (4, 1, 0, 0, 0.0, 0, 0, 0.0);