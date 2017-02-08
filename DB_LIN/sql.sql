

use 	db2_1;
set @num='2_1';

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(20) NOT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `money` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试用表';

insert into t_user (id,userName,money) values
(concat(@num,'u001'),concat(@num,'userName01'),'800' )
,(concat(@num,'u002'),concat(@num,'userName02'),'1000')
,(concat(@num,'u003'),concat(@num,'userName03'),'1200')
;
DROP TABLE IF EXISTS `t_userProduct`;
CREATE TABLE `t_userProduct` (
  `id` varchar(20) NOT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `productName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试用表';

insert into t_userProduct(`id`,`userId`,`productName`) values
(concat(@num,'p001'),concat(@num,'u001'),concat(@num,'productName01'))
,(concat(@num,'p002'),concat(@num,'u002'),concat(@num,'productName02'))
,(concat(@num,'p003'),concat(@num,'u003'),concat(@num,'productName03'));

SET FOREIGN_KEY_CHECKS = 1;

