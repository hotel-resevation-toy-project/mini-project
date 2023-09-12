drop table "reserve";

drop table "room";

drop table "user";

drop table "hotel";

create table "hotel"
(
    hotel_id           bigint auto_increment
        primary key,
    address            varchar(50) not null,
    hotel_name         varchar(50) not null,
    hotel_phone_number varchar(50) not null,
    discount_policy    varchar(50) not null,
    start_peak_date    date        not null,
    end_peak_date      date        not null,
    check_in_time      datetime    not null,
    check_out_time     datetime    not null,
    create_at          datetime    not null,
    update_at          datetime    not null
);

create table "user"
(
    user_id      bigint auto_increment
        primary key,
    name         varchar(50) not null,
    email        varchar(50) not null,
    pswd         varchar(50) not null,
    phone_number varchar(50) not null,
    join_status  varchar(50) not null,
    user_role    varchar(50) not null,
    create_at    datetime    not null,
    update_at    datetime    not null,
    hotel_id     bigint      null,
    constraint user_pk2
        unique (email),
    constraint user_hotel_fk
        foreign key (hotel_id) references "hotel" (hotel_id)
);

create table "room"
(
    room_id    bigint auto_increment
        primary key,
    room_type  varchar(50) not null,
    room_stock int         not null,
    room_price int         not null,
    create_at  datetime    not null,
    update_at  datetime    not null,
    hotel_id   bigint      not null,
    constraint room_hotel_fk
        foreign key (hotel_id) references "hotel" (hotel_id)
);

create table "reserve"
(
    reserve_id     varchar(50) not null
        primary key,
    check_in_date  datetime    not null,
    check_out_date datetime    not null,
    total_price    int         not null,
    create_at      datetime    not null,
    update_at      datetime    not null,
    room_id        bigint      not null,
    user_id        bigint      not null,
    constraint reserve_room_fk
        foreign key (room_id) references "room" (room_id),
    constraint reserve_user_fk
        foreign key (user_id) references "user" (user_id)
);