USE pombodb;

-- password = user1
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('ed8f56a4-12cd-44b5-8f99-4b1d8761d3b9', '48750168088', NOW(), 'user1@user.com', 'UmpjuuqncD2ACZaUvj3NyPTHmEk+H9Np37ql/G8ICQDxzT4wks2DKwtrlbzUVsg+9RJFE6RoRUTU3lRB50tPbMKX4g1sqkHt3ir5DIWgArIUb3wRHJkI+0ygYid5IraJU1D9XLQwh4gN7OTqpZSKKS6H90mjFqI+CG13IUAx0jN43hxsAaBX3N1X7u77eVUE+xvQMQrgNg20iHAMAg33Cbo4g0qu/2Pl/ZaLpgJ5MGWhwfAl97gHB4jeArc6OIbDB6+WCfhxzTKbd110JgI+vBHtVJakvgfzMy73TccCc0P10X3D9xH7RpY0UlW/aYLcG5RzuSlcsJ3UNtORw74Y/g==', 'John Doe', 'USER');

-- password = user2
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('1fbb4516-b8b9-4d5a-929b-761a2c32e981', '82416611003', NOW(), 'user2@user.com', 'EdZml1IXoX3LTJ22772oWiD7HJAVXDOkKAYfILYBJyb4X5erUvuSfBp3dlvHrznAzeOWCSWaUtahK34wg1UbERNMdmAFvaEYHYTovR1iDZdr06XptqIzjD6zIMUkOzGAuOZcYHDuNrJ4ANY9n82uV0JIMS9OnS3WOidmAkahhSKe/WtBefNzfG2exBvI2VmbiUm/WQZKD28SZJojqdLkO5dTqFXoe9G7wn6hEExvx2i8Rb3v5yr58Mu/4lOXhpF4SC1OLTYdQepFUEt2t1yHJ71TV4/Ce0+ljPn1nqsHvwi02Psi34TPKQrKFUiEWXmBIkFegWUqBg7nUbq3V7IZ0w==', 'Robert Jones', 'USER');

-- password = user3
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('d1e4edaf-bc63-4d49-822d-d58ec6d8d05a', '87024830093', NOW(), 'user3@user.com', 'W6tMPG827P2hFcD1kZy05V3Lhgl8CVcR+th6iHPXsTlk1lPO4BCwUtIwWe8PQd64n/YMBxis0RfNycOxCpT/4zD/ns8kP6xlLvMUy5u2DiPn/SjQA9Uw6KWWmEDcwiQ8vSao4WPO0K1EKbZ6vEWfek2v9KRNW2t1zIdfGJR/9clEvus3SdfS9Qih3yXb/ZVF8vCGiOBb04IRj++JvVRzLfr+wd6Bf+wFZpFbELNEYLWMj1VyGGtCBH+l1cuNBbGDjbdgvWLyOFviuSKUpVMXuWuu4amF2TX/ro4L5/KaCUkV64NAFeO53BwMzu22oRImxyoqpB+Chri36GEcFUqPcA==', 'Lisa White', 'USER');

-- password = admin1
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`, `password`, `name`, `role`)
VALUES ('9b24b252-80ba-4e13-bafa-47a970b9e7cd', '94448025071', NOW(), 'admin1@admin.com', 'DtGin71xWG+npZrTLmVgF3poWtT3S7Nf8wLNFxnvlQsVGAdY7Sfm3IQ51eHj7iohQVdz/k1Ydt/RvtxOXbnhFl7t4ZMocBaTiARUpT9OG43EdyUY/Zc9Gkuic2Keqfii88f9FtFbrSYSIdYQTkHn6F5yG7pdVN1osVzUattrYbHPdpesgN4cKYHD1obH+Szew1LYs10pFsQsMsOYwXx1Llv0j/JS4QgQa/ObpGwTNwkLUvwwM08vvN7CTrPLRCeoZnfbJQm5pysuPj0zsCFxQ826Ku8kEif4BatngrED7V8RvbuXvnMMRHIJZ9yERPemXW5hGrY4YrA0Wu1nUvH2mw==', 'John Admin', 'ADMIN');

-- password = admin2
INSERT INTO `user` (`id`, `cpf`, `created_at`, `email`,`password`, `name`, `role`)
VALUES ('7f67d21e-2e4f-4b6d-a0cc-54b3036c8f45', '09394763040', NOW(), 'admin2@admin.com', 'SEjwsj+zrX7vLlcVzDdbihFn26wKtqWkmr7YfXT+AgnvjJ1RMlIuwDANwiE0hTclgff0JSFIJqq7BM9cG0iphmfCal90DqgvgbZqBuBwiLZZaI2jEThpsBzzd5WmcNikVogsyajS1AfahH3eT8vLxA3/PignHHbVr8KewFTkCLt/p6DCwrVOKk7AuRVtooNTV2ixrrA3UqoLgRN8fIL7J2PodqOo//CGQ6wK8nZhgGIUOh4psDNDzodTFQ9lXFfjM626AaIG/BYwlBXJuzwKCnZ2viwqtGwBGKZymjXUway6yE4yOOGbB26aE8qM7HyeipfktiAZ0HL4r3YR/udeJg==', 'Jane Admin', 'ADMIN');

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