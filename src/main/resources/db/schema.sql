DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `hibernate_sequence`;
DROP TABLE IF EXISTS `ordermain`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `productinorder`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `tintuc`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `roles`  (
                          `id` bigint NOT NULL,
                          `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `UK_nb4h0p6txrmfc0xbrd1kglp9t`(`name`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `user`  (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `avatar` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL,
                         `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         `verification_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `UKsb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE,
                         UNIQUE INDEX `UKob8kqyqqgmefl0aco34akdtpe`(`email`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `user_role`  (
                              `user_id` bigint NOT NULL,
                              `role_id` bigint NOT NULL,
                              PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
                              INDEX `FKt7e7djp752sqn6w22i6ocqy6q`(`role_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Fixed;

CREATE TABLE `category`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `product`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL,
                            `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                            `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                            `price` double NOT NULL,
                            `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                            `category_id` bigint NULL DEFAULT NULL,
                            `user_id` bigint NULL DEFAULT NULL,
                            `soluong` int NOT NULL,
                            `discount` int NOT NULL DEFAULT 0,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `FK1mtsbur82frn64de7balymq9s`(`category_id`) USING BTREE,
                            INDEX `FK979liw4xk18ncpl87u4tygx2u`(`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 48 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `productinorder`  (
                                   `p_id` bigint NOT NULL,
                                   `category_id` bigint NOT NULL,
                                   `count` int NULL DEFAULT NULL,
                                   `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
                                   `id` bigint NOT NULL,
                                   `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                                   `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                                   `price` decimal(19, 2) NOT NULL,
                                   `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
                                   `cart_user_id` bigint NULL DEFAULT NULL,
                                   `order_id` bigint NULL DEFAULT NULL,
                                   PRIMARY KEY (`p_id`) USING BTREE,
                                   INDEX `FKgjvj092i650s3rg9gx69po0ql`(`cart_user_id`) USING BTREE,
                                   INDEX `FKedt4527x3pqgh4xnwdxbxd8gl`(`order_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `hibernate_sequence`  (
    `next_val` bigint NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Fixed;

CREATE TABLE `cart`  (
                         `user_id` bigint NOT NULL,
                         PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Fixed;

CREATE TABLE `ordermain`  (
                              `id` bigint NOT NULL,
                              `buyer_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                              `buyer_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                              `buyer_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                              `buyer_phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                              `create_time` datetime NULL DEFAULT NULL,
                              `order_amount` decimal(19, 2) NOT NULL,
                              `order_status` int NOT NULL DEFAULT 0,
                              `update_time` datetime NULL DEFAULT NULL,
                              `paypal` int NULL DEFAULT 0,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;

CREATE TABLE `tintuc`  (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL,
                           `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL,
                           `image` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL,
                           `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_vietnamese_ci ROW_FORMAT = Dynamic;