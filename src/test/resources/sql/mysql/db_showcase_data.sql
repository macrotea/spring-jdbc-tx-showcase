DELETE FROM `tb_article`;
INSERT INTO `tb_article` (`articleName`,`addTime`) VALUES ('Java开发大全',now());

DELETE FROM `tb_article_tag`;
INSERT INTO `tb_article_tag` (`tagName`,`addTime`) VALUES ('技术类',now());

DELETE FROM `tb_user`;
INSERT INTO `tb_user` (`username`,`age`,`addTime`) VALUES ('茶叶',23,now());

