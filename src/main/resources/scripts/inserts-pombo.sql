USE pombodb;

-- password = 123
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '48750168088', '2024-08-10 10:15:30', 'bronx@user.com', '$2a$10$m156.6HhJIh/Kc04R6yVrOXYcHu/rnJIbgX9joUHwaBI9nRjqIlDS', 'Charles do Bronxs', 'USER');

-- password = 123
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('1fbb4516-b8b9-4d5a-929b-761a2c32e981', '82416611003', '2024-08-10 10:15:30', 'poatan@user.com', '$2a$10$m156.6HhJIh/Kc04R6yVrOXYcHu/rnJIbgX9joUHwaBI9nRjqIlDS', 'Alex Poatan', 'USER');

-- password = 123
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('d1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '87024830093', '2024-08-10 10:15:30', 'khabib@user.com', '$2a$10$m156.6HhJIh/Kc04R6yVrOXYcHu/rnJIbgX9joUHwaBI9nRjqIlDS', 'Khabib Nurmagomedov', 'USER');

-- password = 123
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('9b24b252-80ba-4e13-bafa-47a970b9e7cd', '94448025071', '2024-08-10 10:15:30', 'mcgregor@user.com', '$2a$10$m156.6HhJIh/Kc04R6yVrOXYcHu/rnJIbgX9joUHwaBI9nRjqIlDS', 'Connor Mc Gregor', 'USER');

-- password = 123
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', '09394763040', '2024-08-10 10:15:30', 'dana@admin.com', '$2a$10$m156.6HhJIh/Kc04R6yVrOXYcHu/rnJIbgX9joUHwaBI9nRjqIlDS', 'Dana White', 'ADMIN');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('1ad1fccc-d279-46a0-8980-1d91afd6ba67', b'0', b'0', 'Tamo junto professsooooooooooor!', '2024-11-20 23:55:05', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', b'0', b'0', 'Pombo Richarlison', '2024-10-29 23:55:05', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', b'0', b'0', 'Segunda publicacaoooooooooo', '2024-09-05 14:45:10', '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', b'0', b'0', 'Terceira publicacaoooooooooo', '2024-09-18 19:30:25', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('4f9a5297-6c37-489e-a304-9b1d8de0e25f', b'0', b'0', 'Quarta publicacaooooooooooQuarta publicacaooooooooooQuarta publicacaooooooooooQuarta publicacaooooooooooQuarta publicacaooooooooooQuarta publicacaoooooooooo', '2024-10-02 08:10:50', '9b24b252-80ba-4e13-bafa-47a970b9e7cd');

INSERT INTO `publication` (`id`, `blocked`, `deleted`, `content`, `created_at`, `user_id`)
VALUES ('a0e5f739-5e10-403d-a529-29bb7f5fa1c7', b'0', b'0', 'Quinta publicacaoooooooooo', '2024-08-12 10:15:30', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('4f9a5297-6c37-489e-a304-9b1d8de0e25f', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('a0e5f739-5e10-403d-a529-29bb7f5fa1c7', '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('6f78a31d-c76b-43df-b3d8-3c5c8b3c72f9', 'SCAM', 'ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', '1fbb4516-b8b9-4d5a-929b-761a2c32e981', '2024-08-12 10:15:30', 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('2d8b29c9-4458-4a6a-9ac4-845876a948e3', 'HATE_SPEECH', '1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '2024-09-05 14:45:10', 'REJECTED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('ae239b22-728a-4f43-9cfc-8309b31892d4', 'HATE_SPEECH', '4f9a5297-6c37-489e-a304-9b1d8de0e25f', '1fbb4516-b8b9-4d5a-929b-761a2c32e981', '2024-09-18 19:30:25', 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('f0adfa82-5b1b-497f-929b-1b594f268ed7', 'FALSE_INFORMATION', '4f9a5297-6c37-489e-a304-9b1d8de0e25f', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '2024-10-02 08:10:50', 'REJECTED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('3ae26f83-79ac-4c2b-b7fe-903c23452908', 'BULLYING_OR_HARASSMENT', '4f9a5297-6c37-489e-a304-9b1d8de0e25f', '1fbb4516-b8b9-4d5a-929b-761a2c32e981', '2024-10-29 23:55:05', 'PENDING');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('a92fa8b4-6db2-463f-8fe7-76e87e276a18', '9354861e-8562-4dcc-a950-9ebc8da1f01f-bronx.jpg', null, 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('df265bbd-9c6d-4f8a-ba20-eb317f79adfc', '11f4191f-e666-4f6d-bbc0-c7cb61c1ebce-poatan.jpeg', null, '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('04f75e1a-8124-48d4-898d-15537aaec698', 'c61a4d2a-214d-465b-a2fc-82dae64c1297-khabib.jpeg', null, 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('4404fec4-845c-4747-9528-753316741297', '62534b92-54ff-4131-a45c-84bfca5ca695-mcgregor.jpg', null, '9b24b252-80ba-4e13-bafa-47a970b9e7cd');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('5d72e8eb-f6d7-4b24-ba20-f66a08f02764', 'e66a41fe-6c2b-4934-aa24-4407466ce6b1-dana.jpeg', null, '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45');

INSERT INTO `attachment` (`id`, `url`, `publication_id`, `user_id`)
VALUES ('93c209b1-eb44-4b55-b943-898feb6cfb4c', 'bfb9744a-bb19-450e-a7cc-cdc0096117dc-pombo.png', 'ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', null);