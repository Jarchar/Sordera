# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table palabra (
  id                            bigint not null,
  url                           varchar(255),
  nombre                        varchar(255),
  submitted_on                  timestamp,
  descripcion                   varchar(255),
  categoria                     varchar(255),
  constraint pk_palabra primary key (id)
);
create sequence palabra_seq;


# --- !Downs

drop table if exists palabra;
drop sequence if exists palabra_seq;

