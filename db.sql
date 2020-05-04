SELECT count(*) from player where league_id = 2;

-- find league and their owner contact info:

select * from league l, player p, acct a where p.is_owner = 1 and l.id = p.league_id
                                          and p.acct_id = a.id and password not in ( 'sandy2525', '252525') order by l.id;

