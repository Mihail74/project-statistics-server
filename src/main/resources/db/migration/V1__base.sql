CREATE SEQUENCE player_id_seq START 1 INCREMENT 50;
CREATE TABLE player (
  id          BIGINT PRIMARY KEY DEFAULT nextval('player_id_seq'),
  name TEXT
);

ALTER SEQUENCE player_id_seq OWNED BY player.id;