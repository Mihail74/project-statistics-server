--PLAYER
CREATE SEQUENCE player_id_seq START 1 INCREMENT 50;
CREATE TABLE player (
  id       BIGINT PRIMARY KEY DEFAULT nextval('player_id_seq'),
  name     TEXT,
  password TEXT NOT NULL,
  email    TEXT,

  CONSTRAINT uq_player_email UNIQUE (email)
);

ALTER SEQUENCE player_id_seq OWNED BY player.id;

--TEAM
CREATE SEQUENCE team_id_seq START 1 INCREMENT 50;
CREATE TABLE team (
  id   BIGINT PRIMARY KEY DEFAULT nextval('team_id_seq'),
  name TEXT NOT NULL,

  CONSTRAINT uq_team_name UNIQUE (name)
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

--GAME
CREATE SEQUENCE game_id_seq START 1 INCREMENT 50;
CREATE TABLE game (
  id           BIGINT PRIMARY KEY DEFAULT nextval('game_id_seq'),
  name         TEXT NOT NULL,
  score_to_win INTEGER,

  CONSTRAINT uq_game_name UNIQUE (name)
);

ALTER SEQUENCE game_id_seq OWNED BY game.id;

--TEAM + GAME
CREATE TABLE team_game (
  team_id BIGINT NOT NULL,
  game_id BIGINT NOT NULL,

  CONSTRAINT team_game_pkey PRIMARY KEY (team_id, game_id),
  CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES team (id),
  CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game (id)
);

--MATCH
CREATE TABLE match (
  "timestamp" BIGINT NOT NULL,
  team2_id    BIGINT NOT NULL,
  team1_id    BIGINT NOT NULL,
  game_id     BIGINT NOT NULL,
  team1score INTEGER NOT NULL,
  team2score INTEGER NOT NULL,

  CONSTRAINT pk_match PRIMARY KEY (game_id, team1_id, team2_id, "timestamp"),
  CONSTRAINT fk_match_game FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT fk_match_team1 FOREIGN KEY (team1_id) REFERENCES team (id),
  CONSTRAINT fk_match_team2 FOREIGN KEY (team2_id) REFERENCES team (id)
)