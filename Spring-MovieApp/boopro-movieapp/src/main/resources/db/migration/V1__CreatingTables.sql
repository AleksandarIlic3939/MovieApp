CREATE TABLE `users`
(
    `id`       integer PRIMARY KEY AUTO_INCREMENT,
    `email`    varchar(255),
    `password` varchar(255),
    `role_id`  integer
);

CREATE TABLE `roles`
(
    `id`   integer PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255)
);

CREATE TABLE `categories`
(
    `id`   integer PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255)
);

CREATE TABLE `movies`
(
    `id`          integer PRIMARY KEY AUTO_INCREMENT,
    `name`        varchar(255),
    `image_src`   varchar(255),
    `category_id` integer
);

ALTER TABLE `movies`
    ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `users`
    ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);