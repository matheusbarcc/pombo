USE pombodb;

-- password = user1
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '48750168088', '2024-08-10 10:15:30', 'user1@user.com', '$2y$10$dqZZ4n8ML8YttY6f56r/q.e7ofDJpqlpsUcTNP8a/KkvGGm.00YRi', 'John Doe', 'USER');

-- password = user2
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('1fbb4516-b8b9-4d5a-929b-761a2c32e981', '82416611003', '2024-08-10 10:15:30', 'user2@user.com', '$2y$10$IDSoEyCZLkqQJOsVuX7lveKb0w3Lg7eLcDx0TrqqykFvBXsETtwIu', 'Robert Jones', 'USER');

-- password = user3
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('d1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '87024830093', '2024-08-10 10:15:30', 'user3@user.com', '$2y$10$1mN6jRM5Df0AqDhRtdiXmuuHAv9YX/v/RT9ZbMhlXjVbZojBxkvpq', 'Lisa White', 'USER');

-- password = admin1
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('9b24b252-80ba-4e13-bafa-47a970b9e7cd', '94448025071', '2024-08-10 10:15:30', 'admin1@admin.com', '$2y$10$FPEqU5lypx5Bys6kX1RR4u5q0qav3147Z9Lqbbpl0XP1pRNi.7wMq', 'John Admin', 'ADMIN');

-- password = admin2
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', '09394763040', '2024-08-10 10:15:30', 'admin2@admin.com', '$2y$10$C6.z2sAfc5fIt8xnOYOaVeXFpgNS3ISgm2wk0UWG/jQY6tRRcb7mG', 'Jane Admin', 'ADMIN');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', b'0', 'G+GKacmD//FZjhV8NCUEeL71ZDlDyMlC6yutB382OGldLt+ieCsOUp4vdr9bn4d454ma6SgrOGSO1TbZmiFnqn35FjLHbbbGe1H2aD8mrkRCaslqPF7WCy466ZIaqE1eyayr4kkorzcpRz99HSv0OgaWVjBd3kpROheaA77fUbTbGpdWn0zYsb7YAfyWSp1sXTOlqTzh4XCHBUuU2eCI0H3sNBXRrkmbGKrJjS/mzUdBfzmh17y/37v8Wh0kXMYUC7wpH+fS4GdsdxcwlxWrHupcW+qSqmoYi3suG4SD0gUDd5dT3T0j27tDsAcCM/jDY1Mv7GaOdH6696iWLWo+jA==', '2024-08-12 10:15:30', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', b'0', 'AeMp0f5oCV08ZwBQYVWD+rDHOvQpRX6Ehbz9N6SpHN1/X+xSQtO0ir+m0WyYawl6Rgv8wrh0nHNM7OSL8cvN2W5HSgWLru0FADxt75nTltivCkVz1fAmMpEgzzGwNYGQX7rAKwiaiGmtxyRO6kRxKncWtl7koYwTN2RH7OGtwGU8S52uc48PXjieU4t76LTDgOD0mKuNraF2lXBq4TuvnnSJgi00YBjELBnOjcaCryE/nynA+wigaGHroNjdPpZz/ppi9Uo0ATOW+aNQ4UZcRRHTQTSyqLMDczPxxcQ/WLC7tgO5F4w6n235sKCEBHUyDf8ZumeP0PlMRGl3V/3z3w==', '2024-09-05 14:45:10', '1fbb4516-b8b9-4d5a-929b-761a2c32e981');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', b'0', 'hJV/rQva2SzgL8fEVwgRHj1b0ujMXQFwvvJmH3VPNADi/OKSy43vx00DdJjN5DVqVFmHo/0NcWy++q8+Av5BgX1W4ZFKXKxrwDkyNX5V7ndVay6R9uqzEQHwX0UikaPgUW91Fo/mrNp8+p8d1zzZh31g8xEEUktS2EwAqcwDzB8ydroSB13G1/GVmnk1XVymU/KPP1V4DSLo/o9YOl5LiquEW4qInp0OQnXBnqNvDjAJUu9naaPj+eJvKdT0PyaTy244GttVeHrW/xrhZgTQWVj3yOO2PjEGgoSBxCI/ZNDLNLn3GDWbt6i/b5IkGJT0GEPNHpxLNbXbQHQ8FI5MFg==', '2024-09-18 19:30:25', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('4f9a5297-6c37-489e-a304-9b1d8de0e25f', b'1', 'JxIyAVUAcig6Sxzhi6g45H/SROme8cr6oYjfm2PDeQzRegfREgxRiM00R0WR2GcrkJKOfLTR9Ng8StLIWOhpjvSddHO3XEjNHhi9pCHiX0BCQmYsX6Qwhgm6nM96YA8YFXk8g8+eyHm2opcxmuWiLqjzzbAYZVCOv7KbjDVv91P/Wxb8JalVK4/LhiRnJuhnC67RVqzemJbcnIySCfiqCB+TZlO9ddQ6qEDb2T+FEvBUdU5TpGfcMDSxl2t2XBbE7fTg/fJ1ZbgmEQbLtSrzQeAuATx++rcxvZiD2/KfLWidHoTubTXvMxa59XS1tgsjV0LQQpzxBcS5et2b7aluNw==', '2024-10-02 08:10:50', '9b24b252-80ba-4e13-bafa-47a970b9e7cd');

INSERT INTO `publication` (`id`, `blocked`, `content`, `created_at`, `user_id`)
VALUES ('a0e5f739-5e10-403d-a529-29bb7f5fa1c7', b'0', 'jFYlAKaoMvDvLz18GEf94HF20YPCDfMaafwmt6FSZOh/x5zMiBmoE7MN1aKzX/0SeUEyxvXAZabi/dMFfOv3tZsFyT3mCafJBUy70vQL+VM23ymcDQ19uQluAwzMy8yxTZ7O1pxP/Wy9IN7tNWYwW3ZrHH+VGoGMvMCRt5OZALfK79vMYNJN6ti3B7ugs4HkS4r/C/+pAZsfZf4F9qiQpVYTfC8RjAxJMLiTTLg5lOxdrMOY5k80d+K1fNWZyhUnkMliesNGe+o8oHqTQN+AAb7bFdo2+KyHlspH8Y4bsjdvyNRcOT8JISfKFxB2V6lJfuAcxy7vUIjX+KytQXdHxQ==', '2024-10-29 23:55:05', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9');

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
VALUES ('6f78a31d-c76b-43df-b3d8-3c5c8b3c72f9', 'SCAM', 'ffb0c3d7-0e44-4c8b-87b6-9580e6b63fa4', 'ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '2024-08-12 10:15:30', 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('2d8b29c9-4458-4a6a-9ac4-845876a948e3', 'HATE_SPEECH', '1f0cc3b1-cb87-49cf-bb5d-5fe646ad6cf0', '9b24b252-80ba-4e13-bafa-47a970b9e7cd', '2024-09-05 14:45:10', 'REJECTED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('ae239b22-728a-4f43-9cfc-8309b31892d4', 'SPAM', 'ab12d5c8-b3b4-4b1e-932f-fc6e47c59aa3', '1fbb4516-b8b9-4d5a-929b-761a2c32e981', '2024-09-18 19:30:25', 'PENDING');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('f0adfa82-5b1b-497f-929b-1b594f268ed7', 'FALSE_INFORMATION', '4f9a5297-6c37-489e-a304-9b1d8de0e25f', 'd1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '2024-10-02 08:10:50', 'ACCEPTED');

INSERT INTO `complaint` (`id`, `reason`, `publication_id`, `user_id`, `created_at`, `status`)
VALUES ('3ae26f83-79ac-4c2b-b7fe-903c23452908', 'BULLYING_OR_HARASSMENT', 'a0e5f739-5e10-403d-a529-29bb7f5fa1c7', '7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', '2024-10-29 23:55:05', 'PENDING');