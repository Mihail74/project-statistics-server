--PLAYER
CREATE SEQUENCE player_id_seq START 1 INCREMENT 50;
CREATE TABLE player (
  id   BIGINT PRIMARY KEY DEFAULT nextval('player_id_seq'),
  name TEXT
);

ALTER SEQUENCE player_id_seq OWNED BY player.id;

--TEAM
CREATE SEQUENCE team_id_seq START 1 INCREMENT 50;
CREATE TABLE team (
  id   BIGINT PRIMARY KEY DEFAULT nextval('team_id_seq'),
  name TEXT
);

ALTER SEQUENCE team_id_seq OWNED BY team.id;

--TEAM + PLAYER
CREATE TABLE team_player (
  player_id BIGINT NOT NULL,
  team_id   BIGINT NOT NULL,
  
  CONSTRAINT team_player_pkey PRIMARY KEY (player_id, team_id),
  CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES player (id),
  CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES team (id)
);