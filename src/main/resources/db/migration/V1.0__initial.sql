CREATE SEQUENCE game_id_seq INCREMENT 50;
CREATE SEQUENCE match_id_seq INCREMENT 50;
CREATE SEQUENCE team_id_seq INCREMENT 50;
CREATE SEQUENCE team_invite_id_seq INCREMENT 50;
CREATE SEQUENCE users_id_seq INCREMENT 50;


CREATE TABLE games
(
  id bigint NOT NULL,
  description varchar(255),
  name varchar(255) NOT NULL,
  score_to_win bigint,
  team_count_in_match bigint,
  CONSTRAINT games_pkey PRIMARY KEY (id),
  CONSTRAINT uq_game_name UNIQUE (name)
);

CREATE TABLE users
(
  id bigint NOT NULL,
  login varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT uq_users_login UNIQUE (login)
);


CREATE TABLE user_roles
(
  user_id bigint NOT NULL,
  roles varchar(255),
  CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE tokens
(
  id varchar(255) NOT NULL,
  expired_time bigint,
  raw_token character varying(4096),
  type varchar(255),
  user_id bigint,
  CONSTRAINT tokens_pkey PRIMARY KEY (id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE team
(
  id bigint NOT NULL,
  forming_status varchar(255),
  name varchar(255) NOT NULL,
  number_of_matches bigint,
  number_of_win_matches bigint,
  game_id bigint,
  leader_id bigint,
  CONSTRAINT team_pkey PRIMARY KEY (id),
  CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games (id),
  CONSTRAINT fk_leader_user FOREIGN KEY (leader_id) REFERENCES users (id),
  CONSTRAINT uq_team_name UNIQUE (name)
);

CREATE TABLE invites
(
  id bigint NOT NULL,
  status varchar(255),
  team_id bigint,
  user_id bigint,
  CONSTRAINT invites_pkey PRIMARY KEY (id),
  CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team (id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE match
(
  id bigint NOT NULL,
  "timestamp" bigint NOT NULL,
  game_id bigint,
  team_id bigint,
  CONSTRAINT match_pkey PRIMARY KEY (id),
  CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games (id),
  CONSTRAINT fk_team_winner FOREIGN KEY (team_id) REFERENCES team (id)
);


CREATE TABLE team_match_score
(
  match_id bigint NOT NULL,
  team_id bigint NOT NULL,
  score bigint,
  CONSTRAINT team_match_score_pkey PRIMARY KEY (match_id, team_id),
  CONSTRAINT fk_match FOREIGN KEY (match_id) REFERENCES match (id),
  CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE team_users
(
  users_id bigint NOT NULL,
  team_id bigint NOT NULL,
  CONSTRAINT team_users_pkey PRIMARY KEY (team_id, users_id),
  CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team (id),
  CONSTRAINT fk_users FOREIGN KEY (users_id) REFERENCES users (id)
);

