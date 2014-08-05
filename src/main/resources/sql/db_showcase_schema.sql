CREATE TABLE IF NOT EXISTS tb_article (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  articleName varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE IF NOT EXISTS tb_article_tag (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  tagName varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;
