
drop view match_result_view;

ALTER TABLE match_result ADD match_memo VARCHAR(200);

CREATE VIEW match_result_view AS SELECT m.id, m.league_id, m.season_id, m.winner_id, m.loser_id, m.set1_score, m.set2_score, m.set3_score,
                                   m.match_score, m.enter_by_winner, m.match_date, m.record_time, m.match_memo,
                                   u1.first_name as winner_first_name, u1.last_name as winner_last_name,
                                   u2.first_name as loser_first_name, u2.last_name as loser_last_name
                            FROM match_result m join player u1 join player u2 where m.winner_id = u1.id and m.loser_id = u2.id;
