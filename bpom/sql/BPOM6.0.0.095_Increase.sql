--创建订单表--
-- Create table
create table T_ORDER
(
  order_id         VARCHAR2(40) not null,
  order_time       DATE default sysdate not null,
  request_id       VARCHAR2(32),
  system_code      VARCHAR2(16) not null,
  servpltfm_code   VARCHAR2(16) not null,
  pay_way          VARCHAR2(16) not null,
  pay_organization VARCHAR2(16) not null,
  pay_type         NUMBER(4) not null,
  ip_address       VARCHAR2(16) not null,
  callback_url     VARCHAR2(256),
  out_order_id     VARCHAR2(32) not null,
  trade_id         VARCHAR2(32) not null,
  user_id          VARCHAR2(32) not null,
  merchant_name    VARCHAR2(50),
  product_id       VARCHAR2(32) not null,
  product_name     VARCHAR2(50) not null,
  product_desc     VARCHAR2(256),
  channel_id       VARCHAR2(50),
  amount           NUMBER(16) not null,
  reserved1        VARCHAR2(256),
  product_url      VARCHAR2(256),
  contract_code    VARCHAR2(256),
  wxopen_id        VARCHAR2(128),
  pay_time         DATE,
  pay_status       VARCHAR2(8),
  operate_time     DATE,
  refund_desc      VARCHAR2(100),
  state            NUMBER(4) not null
);
-- Add comments to the table 
comment on table T_ORDER
  is '订单历史表';
-- Add comments to the columns 
comment on column T_ORDER.order_id
  is '计费平台订单号ID';
comment on column T_ORDER.order_time
  is '订单时间';
comment on column T_ORDER.request_id
  is '支付网关记录的交易流水号';
comment on column T_ORDER.system_code
  is '内部系统代码';
comment on column T_ORDER.servpltfm_code
  is '内部业务平台代码';
comment on column T_ORDER.pay_way
  is '支付通道';
comment on column T_ORDER.pay_organization
  is '支付平台代码';
comment on column T_ORDER.pay_type
  is '支付方式。1.基本账户支付，默认。2.快捷支付/一键支付。3.网银支付';
comment on column T_ORDER.ip_address
  is '用户IP';
comment on column T_ORDER.callback_url
  is '返回跳转URL';
comment on column T_ORDER.out_order_id
  is '外部订单号';
comment on column T_ORDER.trade_id
  is '外部交易ID';
comment on column T_ORDER.user_id
  is '用户ID';
comment on column T_ORDER.merchant_name
  is '商户展示名称';
comment on column T_ORDER.product_id
  is '产品代码';
comment on column T_ORDER.product_name
  is '产品名';
comment on column T_ORDER.product_desc
  is '商品描述';
comment on column T_ORDER.channel_id
  is '渠道代码';
comment on column T_ORDER.amount
  is '订单金额，单位：分';
comment on column T_ORDER.reserved1
  is '附加数据，交易返回时原样返回给商家网站，给商户备用';
comment on column T_ORDER.product_url
  is '商品展示URL';
comment on column T_ORDER.contract_code
  is '签约协议号';
comment on column T_ORDER.wxopen_id
  is '用户标识';
comment on column T_ORDER.pay_time
  is '用户完成支付的时间';
comment on column T_ORDER.pay_status
  is '支付状态。1  订单未提交支付请求，10  .待支付，已提交支付请求，20.支付成功，30.支付平台反馈失败，40.支付单超时，50.对账补单成功(支付补单)，60.退款中，70.退款成功，80.退款失败，90.对账补单成功(退款补单)';
comment on column T_ORDER.operate_time
  is '操作时间';
comment on column T_ORDER.refund_desc
  is '退款原因描述';
comment on column T_ORDER.state
  is '订单状态。0：预备，1：成功，2订购失败,3退订,4.关闭';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ORDER
  add constraint PK_ORDER primary key (ORDER_ID)
  using index ;
alter table T_ORDER
  add constraint UNIQUE_REQUEST unique (REQUEST_ID)
  using index;
alter table T_ORDER
  add constraint UNIQUE_TRADE unique (TRADE_ID)
  using index;

--创建订单流水表--
-- Create table
create table T_ORDER_HIS
(
  order_id         VARCHAR2(40) not null,
  order_time       DATE default sysdate not null,
  request_id       VARCHAR2(32),
  system_code      VARCHAR2(16) not null,
  servpltfm_code   VARCHAR2(16) not null,
  pay_way          VARCHAR2(16) not null,
  pay_organization VARCHAR2(16) not null,
  pay_type         NUMBER(4) not null,
  ip_address       VARCHAR2(16) not null,
  callback_url     VARCHAR2(256),
  out_order_id     VARCHAR2(32) not null,
  trade_id         VARCHAR2(32) not null,
  user_id          VARCHAR2(32) not null,
  merchant_name    VARCHAR2(50),
  product_id       VARCHAR2(32) not null,
  product_name     VARCHAR2(50) not null,
  product_desc     VARCHAR2(256),
  channel_id       VARCHAR2(50),
  amount           NUMBER(16) not null,
  reserved1        VARCHAR2(256),
  product_url      VARCHAR2(256),
  contract_code    VARCHAR2(256),
  wxopen_id        VARCHAR2(128),
  pay_time         DATE,
  pay_status       VARCHAR2(8),
  operate_time     DATE,
  refund_desc      VARCHAR2(100),
  state            NUMBER(4) not null
);
-- Add comments to the table 
comment on table T_ORDER_HIS
  is '订单历史表';
-- Add comments to the columns 
comment on column T_ORDER_HIS.order_id
  is '计费平台订单号ID';
comment on column T_ORDER_HIS.order_time
  is '订单时间';
comment on column T_ORDER_HIS.request_id
  is '支付网关记录的交易流水号';
comment on column T_ORDER_HIS.system_code
  is '内部系统代码';
comment on column T_ORDER_HIS.servpltfm_code
  is '内部业务平台代码';
comment on column T_ORDER_HIS.pay_way
  is '支付通道';
comment on column T_ORDER_HIS.pay_organization
  is '支付平台代码';
comment on column T_ORDER_HIS.pay_type
  is '支付方式。1.基本账户支付，默认。2.快捷支付/一键支付。3.网银支付';
comment on column T_ORDER_HIS.ip_address
  is '用户IP';
comment on column T_ORDER_HIS.callback_url
  is '返回跳转URL';
comment on column T_ORDER_HIS.out_order_id
  is '外部订单号';
comment on column T_ORDER_HIS.trade_id
  is '外部交易ID';
comment on column T_ORDER_HIS.user_id
  is '用户ID';
comment on column T_ORDER_HIS.merchant_name
  is '商户展示名称';
comment on column T_ORDER_HIS.product_id
  is '产品代码';
comment on column T_ORDER_HIS.product_name
  is '产品名';
comment on column T_ORDER_HIS.product_desc
  is '商品描述';
comment on column T_ORDER_HIS.channel_id
  is '渠道代码';
comment on column T_ORDER_HIS.amount
  is '订单金额，单位：分';
comment on column T_ORDER_HIS.reserved1
  is '附加数据，交易返回时原样返回给商家网站，给商户备用';
comment on column T_ORDER_HIS.product_url
  is '商品展示URL';
comment on column T_ORDER_HIS.contract_code
  is '签约协议号';
comment on column T_ORDER_HIS.wxopen_id
  is '用户标识';
comment on column T_ORDER_HIS.pay_time
  is '用户完成支付的时间';
comment on column T_ORDER_HIS.pay_status
  is '支付状态。1  订单未提交支付请求，10  .待支付，已提交支付请求，20.支付成功，30.支付平台反馈失败，40.支付单超时，50.对账补单成功(支付补单)，60.退款中，70.退款成功，80.退款失败，90.对账补单成功(退款补单)';
comment on column T_ORDER_HIS.operate_time
  is '操作时间';
comment on column T_ORDER_HIS.refund_desc
  is '退款原因描述';
comment on column T_ORDER_HIS.state
  is '订单状态。0：预备，1：成功，2订购失败,3退订,4.关闭';

--创建签约表--
-- Create table
create table T_WX_CONTRACT
(
  contract_code  VARCHAR2(32) not null,
  contract_id    VARCHAR2(32),
  signed_time    DATE,
  plan_id        VARCHAR2(28) not null,
  display_name   VARCHAR2(32) not null,
  expired_time   DATE,
  system_code    VARCHAR2(16) not null,
  servpltfm_code VARCHAR2(16) not null,
  user_id        VARCHAR2(32) not null,
  open_id        VARCHAR2(32),
  product_id     VARCHAR2(32) not null,
  product_name   VARCHAR2(50) not null,
  product_desc   VARCHAR2(256),
  channel_id     VARCHAR2(50),
  reserved1      VARCHAR2(256),
  product_url    VARCHAR2(256),
  unsign_time    DATE,
  unsign_mode    NUMBER(4),
  unsign_memo    VARCHAR2(256),
  state          NUMBER(4) not null
);
-- Add comments to the table 
comment on table T_WX_CONTRACT
  is '微信签约表';
-- Add comments to the columns 
comment on column T_WX_CONTRACT.contract_code
  is '签约协议号';
comment on column T_WX_CONTRACT.contract_id
  is '委托代扣协议ID';
comment on column T_WX_CONTRACT.signed_time
  is '签约时间';
comment on column T_WX_CONTRACT.plan_id
  is '模板ID';
comment on column T_WX_CONTRACT.display_name
  is '展示名称';
comment on column T_WX_CONTRACT.expired_time
  is '协议到期时间';
comment on column T_WX_CONTRACT.system_code
  is '内部系统代码';
comment on column T_WX_CONTRACT.servpltfm_code
  is '内部业务平台代码';
comment on column T_WX_CONTRACT.user_id
  is '用户ID';
comment on column T_WX_CONTRACT.open_id
  is '微信用户标识';
comment on column T_WX_CONTRACT.product_id
  is '产品代码';
comment on column T_WX_CONTRACT.product_name
  is '产品名';
comment on column T_WX_CONTRACT.product_desc
  is '对商品的描述';
comment on column T_WX_CONTRACT.channel_id
  is '渠道代码';
comment on column T_WX_CONTRACT.reserved1
  is '交易返回时原样返回给商家网站';
comment on column T_WX_CONTRACT.product_url
  is '商品展示链接地址';
comment on column T_WX_CONTRACT.unsign_time
  is '解约时间';
comment on column T_WX_CONTRACT.unsign_mode
  is '协议解约方式。当STATE为1时该值有效。0-未解约，1-有效期过自动解约，2-用户主动解约 ，3-商户API解约，4-商户平台解约，5-注销';
comment on column T_WX_CONTRACT.unsign_memo
  is '解约备注';
comment on column T_WX_CONTRACT.state
  is '状态 0：未签约，1：签约成功，2：解约/过期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WX_CONTRACT
  add constraint PK_WX_CONTRACT primary key (CONTRACT_CODE)
  using index;
