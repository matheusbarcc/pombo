USE pombodb;

-- password = pass1
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '12345678901', NOW(), 'john.doe@example.com', '$2a$12$3uhbH4AtQC.LWJo8EnKS5.ivlWS/jiSElamaT4R6Q24Snjlldqo96', 'John Doe', 'USER');

-- password = pass2
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('9b24b252-80ba-4e13-bafa-47a970b9e7cd', '23456789012', NOW(), 'jane.smith@example.com', '$2a$12$PqkSVkQKdGyrxCaxCllvMOwjdPFfpnWEvphfIYq/9ASOpJYNu6KZ2', 'Jane Smith', 'ADMIN');

-- password = pass3
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('1fbb4516-b8b9-4d5a-929b-761a2c32e981', '34567890123', NOW(), 'robert.jones@example.com', '$2a$12$MVSgVGNPnBo7vtb8exM8Buctlr4kBrliXpJYNy4eMMEiF7Vh13WDq', 'Robert Jones', 'USER');

-- password = pass4
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('d1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '45678901234', NOW(), 'lisa.white@example.com', '$2a$12$kU5ftwFejuUaiWJCDWYs0O0ILJbOGOkYtgmp0j2uDjCteKhwwTYwy', 'Lisa White', 'USER');

-- password = pass5
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', '56789012345', NOW(), 'michael.brown@example.com', '$2a$12$hiYDBvtIuWoxfrYI6635ZeV9F.tOfflKzawFg8w89988F/nFhonG6', 'Michael Brown', 'ADMIN');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', b'0', 'First publication content', NOW(), 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', b'0', 'Second publication content', NOW(), '9b24b252-80ba-4e13-bafa-47a970b9e7cd');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', b'0', 'Third publication content', NOW(), '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('4f9a5297-6c37-489e-a304-9b1d8de0e25f', b'1', 'Fourth publication content', NOW(), 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('a0e5f739-5e10-403d-a529-29bb7f5fa1c7', b'0', 'Fifth publication content', NOW(), '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', '9b24b252-80ba-4e13-bafa-47a970b9e7cd');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('4f9a5297-6c37-489e-a304-9b1d8de0e25f', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication_like` (`publication_id`, `user_id`)
VALUES ('a0e5f739-5e10-403d-a529-29bb7f5fa1c7', '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('6f78a31d-c76b-43df-b3d8-3c5c8b3c72f9', 'SCAM', 'ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', NOW(), 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('2d8b29c9-4458-4a6a-9ac4-845876a948e3', 'HATE_SPEECH', '1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', '9b24b252-80ba-4e13-bafa-47a970b9e7cd', NOW(), 'ANALYSED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('ae239b22-728a-4f43-9cfc-8309b31892d4', 'SPAM', 'ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', '1fbb4516-b8b9-4d5a-929b-761a2c32e981', NOW(), 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('f0adfa82-5b1b-497f-929b-1b594f268ed7', 'FALSE_INFORMATION', '4f9a5297-6c37-489e-a304-9b1d8de0e25f', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a', NOW(), 'ANALYSED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('3ae26f83-79ac-4c2b-b7fe-903c23452908', 'BULLYING_OR_HARASSMENT', 'a0e5f739-5e10-403d-a529-29bb7f5fa1c7', '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', NOW(), 'PENDING');