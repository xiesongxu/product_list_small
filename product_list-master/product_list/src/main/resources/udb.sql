create table product_all_list (
                                  serialno varchar(50)  comment '流水号',
                                  productID varchar(50) COMMENT '商品ID',
                                  productSum varchar(20) COMMENT '商品总数',
                                  primary key (serialno)
) comment ='商品库存';

create table product_list (
                              serialno varchar(50) COMMENT '流水号',
                              productID varchar(50) COMMENT '商品ID',
                              productName varchar(200) COMMENT '商品名字',
                              productValue varchar(20) COMMENT '商品销售额',
                              productSum varchar(20) COMMENT '商品数量',
                              remark varchar(100) COMMENT '备注',
                              flag varchar(10) COMMENT '标签（1：入库商品，2：出库商品）',
                              expireDate varchar(10) COMMENT '过期时间（几天）',
                              inputDate varchar(20) COMMENT '输入时间',
                              updateDate varchar(20) COMMENT '修改时间',
                              userid varchar(10) COMMENT '用户',
                              primary key (serialno)
) comment ='商品导入信息';

create table expire_list (
                             serialno varchar(50)  COMMENT '流水号',
                             relativeSerialno varchar(50) COMMENT 'product_list关联流水号',
                             productID varchar(50) COMMENT '商品ID',
                             productSum varchar(20) COMMENT '商品数量',
                             inputDate varchar(20) COMMENT '输入时间',
                             expireDate varchar(10) COMMENT '过期时间（几天）',
                             primary key (serialno)
) COMMENT ='过期商品或在保质期商品查询';