create table songs
(
	id integer not null
		constraint songs_pk
			primary key,
	original_url varchar,
	name varchar,
	url varchar,
	updated integer
);

alter table songs owner to postgres;

create unique index songs_id_uindex
	on songs (id);

create table requests
(
	req_name varchar,
	time integer,
	id serial not null,
	length integer,
	songs jsonb
);

alter table requests owner to postgres;

create unique index requests_req_name_uindex
	on requests (req_name);

create unique index requests_id_uindex
	on requests (id);

create table persons
(
	id serial not null
		constraint persons_pk
			primary key,
	email varchar,
	password varchar,
	name varchar,
	song_count integer default 5
);

alter table persons owner to postgres;

create unique index person_id_uindex
	on persons (id);

create unique index persons_email_uindex
	on persons (email);

create table person_songs
(
	person_id integer
		constraint person_songs_person_id_fkey
			references persons,
	song_id integer
		constraint person_songs_song_id_fkey
			references songs
);

alter table person_songs owner to postgres;

create table request_info
(
	requested_times integer default 1,
	created date default now(),
	request_id integer not null
		constraint request_info_pk
			primary key
		constraint request_info_request_id_fkey
			references requests (id),
	last_request_time date default now()
);

alter table request_info owner to postgres;

create unique index request_info_request_id_uindex
	on request_info (request_id);






insert into persons (email, password, name, song_count) values ('tempemail@mail.ru', 'uQY8EpUhEg6OwCnZMJyTmv6bBQTFGOF3PllKP7AzGYM=$DDYrJ04FU+LDTdOnhXfR65muvgzUV2zhoSewyQFep4g=', 'Данияр', '17');


insert into songs (id, original_url, name, url, updated) values ('1219427', '/musicset/play/38e1f96a12a90bef58019fea1ea489d5/1219427.json', 'Сплин – Мое сердце остановилось', 'https://cdndl.zaycev.net/track/1219427/6VQrBEE5ykyiHzhUrja4BJZwusKv3jeyqg448wbXD9M98uA6pu97r3srcALj56vmiWygxxwmcCpkZy69xt6coYdquMRxHrzMFULkjd49EUjsKQiKq2wSy6JPXZ5jUKG3CXbkXFuAPGYknFxznMAd2uE8PSk9AuSD4Ps3amvMCoiKMgqwJpqCZXwrehx2gQfDB9NrbVYwpAWDLcMbHGRwwoUs2TyBsv9yWRw9ZKrE5ZNrV3KL5Cf86ck82YGM3bvaZ5iUvedohrBmdkrSL17gXXFKhZdxbpxfk4NnAVxo2W5W7cxjkj7AkVRi8yPP28Kf1vJTkRY3kAzC8LUBUfvHfMXsqzyV7VSz5N59Rtak8cys5smNPQxS', '1604305968'),
('21823755', '/musicset/play/cffefec166ed83656b4d40c0f6534cfb/21823755.json', 'Feduk – Краски', 'https://cdndl.zaycev.net/track/21823755/6Zhrjg2gMq8qcpMaNYvy627w8B7aezU65nDLHSkr4nUg75YUcyiWZBibUL7WCK1s1mwPYx2UXuV4V5qkNPKRApDADQ5qCND5r1jB3oYTr6TSn6yfzceSJj1EpmPRcf3d6n7W1dEhcoudtrQRu4dwna8hVeJevXWwCYKNL44L4XsEWWfugZkSZgsUPofghQ1GAPVPLMNprgvmshvpi7y7nueH6y6FzCTMnqLzrcY5crFGSzRoi7DgbS3ohXpUm3hK9nuBqkF6CzYumAL2Epf6E6TJYykoLJUkr33EmS2zWLUBtkZF9rgMSarLm1RRGH7VjA9tm6XNqQLF21LyGfw1ZxCapv5Xddg356qfyesiU7UPD5R8QVBA', '1604305968');


insert into person_songs (person_id, song_id) values ('1', '1219427'), ('1', '21823755');


insert into requests (req_name, time, id, length, songs) values ('сплин', '1604150473', '1', '1', '{"1219427": "T"}');


insert into request_info (requested_times, created, request_id, last_request_time) values ('3', '2020-10-31', '1', '2020-10-31');

