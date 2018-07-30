create table entity (
  entity_id                 integer auto_increment not null,
  entity_name               varchar(255),
  user_id                   bigint,
  constraint pk_entity primary key (entity_id))
;

create table handle (
  id                        bigint auto_increment not null,
  handle                    varchar(255),
  entity_id                 integer,
  constraint pk_handle primary key (id))
;

create table hashtag_counter (
  id                        bigint auto_increment not null,
  hashtag                   varchar(255),
  entity_id                bigint,
  created_at                datetime,
  count                     bigint,
  constraint pk_hashtag_counter primary key (id))
;

create table keyword (
  id                        bigint auto_increment not null,
  keyword                   varchar(255),
  entity_id                 integer,
  constraint pk_keyword primary key (id))
;

create table talkers (
  id                        bigint auto_increment not null,
  entity_id                 integer,
  mention_time              datetime,
  count                     bigint,
  constraint pk_talkers primary key (id))
;

create table token (
  user_id                   bigint auto_increment not null,
  access_token              varchar(255),
  access_token_secret       varchar(255),
  constraint pk_token primary key (user_id))
;

create table tweets (
  id                  bigint auto_increment not null,
  tweet_id                bigint,
  entity_id               bigint,
  text                      varchar(255),
  retweet_count             bigint,
  created_at                datetime,
  screen_name               varchar(255),
  constraint pk_tweets primary key (id))
;

create table followers (
id bigint auto_increment not null,
screen_name varchar(255),
time datetime,
count integer,
constraint pk_followers primary key(id))
;

create table geography (
id bigint auto_increment not null,
entity_id integer,
created_at datetime,
latitude double,
longitude double,
constraint pk_geography primary key(id))
;

create table sentiment (
id bigint auto_increment not null,
time datetime,
entity_id integer,
score integer,
count bigint,
constraint pk_sentiment primary key(id))
;

create table topics (
id bigint auto_increment not null,
hashtag varchar(255),
entity_id integer,
created_at datetime,
count bigint,
constraint pk_topics primary key(id))
;

create table tweets (
id bigint auto_increment not null,
tweet_id varchar(200),
entity_id integer,
text varchar(255),
retweet_count bigint,
created_at datetime,
screen_name varchar(255),
constraint pk_topics primary key(id))
;

alter table entity add constraint fk_entity_token_1 foreign key (user_id) references token (user_id) on delete restrict on update restrict;
create index ix_entity_token_1 on entity (user_id);
alter table handle add constraint fk_handle_entity_2 foreign key (entity_id) references entity (entity_id) on delete restrict on update restrict;
create index ix_handle_entity_2 on handle (entity_id);
alter table keyword add constraint fk_keyword_entity_3 foreign key (entity_id) references entity (entity_id) on delete restrict on update restrict;
create index ix_keyword_entity_3 on keyword (entity_id);


