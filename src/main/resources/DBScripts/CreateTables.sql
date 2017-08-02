SET SQL_SAFE_UPDATES = 0;
Commit;

SELECT table_name, table_rows, TABLE_COMMENT FROM information_schema.tables
WHERE TABLE_SCHEMA = 'twealthbookdev'  ORDER BY table_comment;

SELECT a.TABLE_NAME, a.COLUMN_NAME, a.COLUMN_TYPE, a.COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS a
WHERE TABLE_SCHEMA='twealthbookdev' and a.table_name = 'COMPANY_DAILY_DATA_B' order by table_name, a.ORDINAL_POSITION;

drop table if exists portfolio_returns;
drop table if exists portfolio_returns_calculation_support;
drop table if exists portfolio_asset_allocation;
drop table if exists portfolio_historical_holdings;
drop table if exists portfolio_holdings;
drop table if exists portfolio_cashflow;
drop table if exists portfolio;
drop table if exists family_member;
drop table if exists client;
drop table if exists user_role;
drop table if exists role;
drop table if exists user;
drop table if exists sequence_next_high_value;
drop table if exists setup_dates;
drop table if exists log_table;

CREATE TABLE log_table (
  timestamp datetime DEFAULT NULL COMMENT 'timestamp',
  log_query varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Log Query'
) COMMENT='Debug log table  	Debugging	Debugging';

CREATE TABLE setup_dates (
  date_today date NOT NULL COMMENT 'Today''s trading date',
  date_last_trading_day date NOT NULL COMMENT 'Last trading date',
  date_start_current_month date NOT NULL COMMENT 'Current month begining date',
  date_start_current_quarter date NOT NULL COMMENT 'Current quarter begining date',
  date_start_current_fin_year date NOT NULL COMMENT 'Current FIN year begining date',
  date_start_1_quarter date NOT NULL COMMENT 'Same as date_start_current_quarter i.e. begining date of current quarter',
  date_start_2_quarter date NOT NULL COMMENT 'Begining date of last quarter',
  date_start_3_quarter date NOT NULL COMMENT 'Begining date of last to last quarters before',
  date_start_4_quarter date NOT NULL COMMENT 'Begining date of quarter started 3 quarters before',
  date_start_next_fin_year date NOT NULL COMMENT 'Next Year date start',
  current_fin_year int(4) NOT NULL COMMENT 'Current FIN Year',
  current_quarter int(1) NOT NULL COMMENT 'Current Quarter'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

delete from setup_dates;
insert into setup_Dates (select * from equityanalysis.setup_dates); 
select * from setup_dates;

create table sequence_next_high_value (id varchar(50), sequence_next_high_value int, primary key (id));

CREATE TABLE user (
  user_id INT NOT NULL COMMENT 'User ID unique auto generated',
  user_login_id varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'User Login ID unique usually contact no.',
  user_password varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Encrypted Password',
  user_active_status int(1) DEFAULT '1' COMMENT '1-Active 2-Inactive Closed',
  user_first_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'User Name',
  user_last_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Family Name',
  user_joining_date date NULL COMMENT 'User Joining Date',
  user_emailid varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Email ID for communication',
  user_add_line1 varchar(100) COLLATE utf8_unicode_ci NULL COMMENT 'Address Line 1',
  user_add_line2 varchar(100) COLLATE utf8_unicode_ci NULL COMMENT 'Address Line 2',
  user_add_line3 varchar(100) COLLATE utf8_unicode_ci NULL COMMENT 'Address Line 3',
  user_city varchar(50) COLLATE utf8_unicode_ci NULL COMMENT 'User Address City',
  user_state varchar(50) COLLATE utf8_unicode_ci NULL COMMENT 'User Address State',
  user_pin int NULL COMMENT 'User Address PIN Code',
  user_contact_no BIGINT NOT NULL COMMENT 'User Contact No.',
  user_contact_no_alternate BIGINT NULL COMMENT 'User Alternate Contact No',
  user_last_login_time DATETIME NULL COMMENT 'User last login timestamp',
  PRIMARY KEY (user_id),
  UNIQUE INDEX ui_user_user_login_id (user_login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Users who can access the system';

delete from user; 
insert into user 
(select a.customer_code, a.customer_cell_no, 'QUALITY80', 1, a.customer_name, a.customer_last_name, '2017-04-01', a.customer_email_id, a.address_line1, a.address_line2, a.address_line3, a.address_city, a.address_state, a.address_pin, a.customer_cell_no, '', '2017-04-01 00:00:00' from equityanalysis.customer_master a where a.customer_code in (1,1001,1002,1003,1004,1005,1007,1008,1009,1010,1014,1016,1017) order by a.customer_code);
update user set user_password = '$2a$10$ESarR.eSIYmwip0JYr0d1.1S0K92vPiutH.kQH9lyaQGWw8OaXnhO';
select * from user;

CREATE TABLE role (
  role_id int(1) NOT NULL COMMENT 'Role ID',
  role_name varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Role Name',
  PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='User Role Master (1-ADMIN, 2-END_USER, 3-RM i.e Relationship Manager)';

insert into role VALUES (1, 'ADMIN');
insert into role values (2, 'END_USER');
insert into role VALUES (3, 'RELATIONSHIP_MANAGER');
select * from role;

CREATE TABLE user_role (
  user_id int NOT NULL COMMENT 'User ID - Foreign Key from User',
  role_id int(1) NOT NULL COMMENT 'Role ID - Foreign Key from Role',
  PRIMARY KEY (user_id),
  CONSTRAINT c_user_role_user_id FOREIGN KEY (user_id) REFERENCES user (user_id),
  CONSTRAINT c_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Role and User Mapping';

insert into user_role (select user_id, 2 from user);
select * from user_role;

CREATE TABLE client (
  client_id int NOT NULL COMMENT 'Client ID unique Auto Generated',
  client_active_status int(1) DEFAULT '2' COMMENT '1-Active Fee Paying 2-Active Non Fee Paying 4-Inactive Closed',
  client_first_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Client First Name',
  client_middle_name varchar(50) COLLATE utf8_unicode_ci NULL COMMENT 'Client Middle Name',
  client_last_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Client Last Name',
  client_birth_date date NULL COMMENT 'Client Birth Date',
  client_gender int(1) NULL COMMENT 'Client Gender 1: Male 2: Female 3: Other',
  client_marital_status int(1) DEFAULT '0' COMMENT '1-Single, 2-Married, 3-Divorcee, 4-Widow',
  client_joining_date date NULL COMMENT 'Client Joining Date',
  client_cell_no BIGINT NULL COMMENT 'Client Cell Phone NO',
  client_email_id varchar(50) COLLATE utf8_unicode_ci NULL COMMENT 'Client email id',
  client_pan_card_no char(10) COLLATE utf8_unicode_ci NULL COMMENT 'PAN No',
  client_aadhar_card_no char(12) COLLATE utf8_unicode_ci NULL COMMENT 'Aadhar No',
  PRIMARY KEY (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Clients';

delete from client;
insert into client 
select a.customer_code, 2, a.customer_name, a.customer_middle_name, a.customer_last_name, a.date_of_birth, if(a.marrital_status='S', 1, 2) marrital_status, '2017-04-01', a.customer_cell_no, a.customer_email_id, a.PAN_card_no, '' 
from equityanalysis.customer_master a where a.customer_code in (1006,1015,1018,1,1001,1002,1003,1004,1005,1007,1008,1009,1010,1014,1016,1017) order by a.customer_code;

update client set client_marital_status = 2 where client_id in (1004, 1016);
update client set client_marital_status = 4 where client_id in (1009);
SELECT * from client;

delete from sequence_next_high_value where id = 'client_id';
insert into sequence_next_high_value select 'client_id', (max(client_id)+1) from client;
select * from sequence_next_high_value;

CREATE TABLE family_member (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  user_id int NOT NULL COMMENT 'User ID for reference unique',
  user_login_id varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'User Login ID unique usually contact no.',
  family_relationship varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Relationship with main client. SELF, SPOUSE, MOTHER, FATHER, SON, DAUGHTER, GRANDPARENTS, GRANDCHILDREN',
  PRIMARY KEY (client_id),
  CONSTRAINT c_family_client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
  CONSTRAINT c_family_user_login_id FOREIGN KEY (user_id) REFERENCES user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='family';

delete from family_member;
insert into family_member select user_id, user_id, user_login_id, 'SELF' from user;
select * from client where client_id not in (select client_id from family_member);
-- insert into family_member values (1007, 1007, '9833539299', 'SELF');
insert into family_member values (1015, 1007, '9833539299', 'FATHER');
insert into family_member values (1018, 1002, '9987508953', 'SPOUSE');
select * from family_member order by user_login_id;
select * from user where user_login_id = '9833539299';

CREATE TABLE portfolio (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  portfolio_active_status int(1) DEFAULT '1' COMMENT '1-Active 2-Inactive 3-Inactive and Closed',
  portfolio_description varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Description of the portfolio',
  portfolio_start_date date DEFAULT NULL COMMENT 'Portfolio Start Date',
  portfolio_end_date date DEFAULT NULL COMMENT 'Portfolio Expected End Date',
  portfolio_current_strategy varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 
'Aggressive-1 : Speculative Daily Trading in Equity and F&O ,
Aggressive-2 : Short Term Undiversified Small Cap Equity,
Aggrassive-3 : Short Term Undiversified Small and Mid Cap Equity
Aggrassive-4 : Short Term Undiversified Mid and Large Cap Equity,
Moderate-1 : Diversified Small and Mid Cap Equity through Mutual Funds,
Moderate-2 : Diversified Large Cap and Midcap through Mutual Funds ,
Moderate-3 : Index Mutual Funds or ETFs
Hybrid : Combination of Moderate and Fixed Income Through Debt Mutual Funds,
Defensive-1 : Long Duration Debt Mutual Funds
Defensive-2 : Short Duration Debt Mutual Funds
Fixed Deposits : Only Fixed Deposits
Real Estate : Investment in Real Estate
Commodity : Gold, Silver etc.
Private Equity : Own Business or investment in private business',
  portfolio_benchmark_type int(1) DEFAULT 1 COMMENT 'Benchmark Type 1-Standard Index 2-Customized combination of Indices',
  portfolio_benchmark varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Benchmark Code for performance comparison',
  portfolio_value DECIMAL(20,3) COMMENT 'Market value of the portfolio',
  PRIMARY KEY (client_id, portfolio_id),
  CONSTRAINT c_portfolio_client_id FOREIGN KEY (client_id) REFERENCES client (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Portfolio';

delete from portfolio; 
insert into portfolio 
(select customer_code, portfolio_no, 1, portfolio_goal, portfolio_start_date, portfolio_end_date, '', 1, 'NSE:NIFTY', 0.00 from equityanalysis.portfolio_master a
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019) order by a.customer_code);

select * from portfolio a;

CREATE TABLE portfolio_cashflow (
  client_id int NOT NULL COMMENT 'Client ID unique Auto Generated',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  cashflow_date date NOT NULL  COMMENT 'Date on which major cash inflow or outflow happens',
  cashflow_amount DECIMAL(20,3) NOT NULL COMMENT 'Amount of cash inflow (negative) or outflow (outflow) happens',
  PRIMARY KEY (client_id,portfolio_id,cashflow_date)
--  CONSTRAINT c_portfolio_cashflow_client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
--  CONSTRAINT c_portfolio_cashflow_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolio (portfolio_id)
) COMMENT='Portfolio Cashflow';

delete from portfolio_cashflow;
insert into portfolio_cashflow select * from equityanalysis.portfolio_inflow_outflow_history a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019) ;

select * from portfolio_cashflow;

CREATE TABLE portfolio_holdings (
  client_id int(11) NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  security_id varchar(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Id',
  security_buy_date date NOT NULL COMMENT 'Security Buy Date',
  security_name varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Name',
  security_asset_class_id int(11) NOT NULL COMMENT 'Security Asset Class as per Asset Classification',
  security_asset_subclass_id int(11) NOT NULL COMMENT 'Security Asset Sub Class as per Asset Classification',
  security_sector_name varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Sector Name',
  security_industry_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Industry Name',
  security_quantity decimal(20,4) DEFAULT NULL COMMENT 'Security Quantity',
  security_buy_rate decimal(20,3) DEFAULT NULL COMMENT 'Security Buy Rate per Quantity',
  security_brokerage decimal(12,2) DEFAULT NULL COMMENT 'Security Total Brokerage',
  security_tax decimal(12,2) DEFAULT NULL COMMENT 'Security Total Tax',
  security_total_cost decimal(20,3) DEFAULT NULL COMMENT 'Security Total Cost (Buy Rate*Quantity) + Brokerage + Tax',
  security_cost_rate decimal(20,3) DEFAULT NULL COMMENT 'Security effective cost per quantity i.e. Total Cost/Quantity',
  security_cmp decimal(20,3) DEFAULT NULL COMMENT 'Security Current Market Price',
  security_market_value decimal(20,3) DEFAULT NULL COMMENT 'Security market value (CMP*Quanity)',
  security_holding_period decimal(7,3) DEFAULT NULL COMMENT 'Security holding period in years i.e. Buy date to till date ',
  security_unrealized_net_profit decimal(20,3) DEFAULT NULL COMMENT 'Unrealized Net Profit = Market Value - Total Cost',
  security_absolute_return decimal(20,3) DEFAULT NULL COMMENT 'Unrealized absolute return',
  security_annualized_return decimal(20,3) DEFAULT NULL COMMENT 'Unrealized annualized return',
  security_maturity_value decimal(20,3) DEFAULT NULL COMMENT 'Security Maturity Value especially for FDs',
  security_maturity_date date DEFAULT '1900-01-01' COMMENT 'Security Maturity Value especially for FDs',
  PRIMARY KEY (client_id,portfolio_id,security_id,security_buy_date),
  KEY c_portfolio_holdings_portfolio_id (portfolio_id)
) COMMENT='Portfolio Current Holdings';


delete from portfolio_holdings; -- where client_id = 1007;
insert into portfolio_holdings 
select a.customer_code, a.portfolio_no, a.script_code, a.buy_date, a.script_name, a.asset_class, a.sub_class, a.sector_name, 
a.industry_name, a.quantity, a.buy_rate, a.brokerage, a.tax, a.total_cost, a.price_per_unit, a.cmp, a.market_value, a.holding_period, 
a.net_profit, a.absolute_return, a.cagr_return, a.maturity_value, a.maturity_date 
from equityanalysis.portfolio_current_status a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019) ;

update portfolio_holdings a set a.security_maturity_date = '1900-01-01' where security_maturity_date is null or security_maturity_date = '0000-00-00';

select * from portfolio_holdings a where a.client_id = 1007; 

CREATE TABLE portfolio_historical_holdings (
  client_id int(11) NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  security_id varchar(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Id',
  security_buy_date date NOT NULL COMMENT 'Security Buy Date',
  security_name varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Name',
  security_asset_class_id int(11) NOT NULL COMMENT 'Security Asset Class',
  security_asset_subclass_id int(11) NOT NULL COMMENT 'Security Asset Sub Class',
  security_sector_name varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Sector Name',
  security_industry_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Industry Name',
  security_quantity decimal(20,4) DEFAULT NULL COMMENT 'Security Quantity',
  security_buy_rate decimal(20,3) DEFAULT NULL COMMENT 'Security Buy Rate per Quantity',
  security_brokerage decimal(12,2) DEFAULT NULL COMMENT 'Brokerage for buy',
  security_tax decimal(12,2) DEFAULT NULL COMMENT 'Tax for buy',
  security_total_cost decimal(20,3) DEFAULT NULL COMMENT 'Security Total Cost (Buy Rate*Quantity) + Brokerage + Tax',
  security_cost_rate decimal(20,3) DEFAULT NULL COMMENT 'Security effective cost per quantity i.e. Total Cost/Quantity',
  security_sell_date date NOT NULL COMMENT 'Security Sell Date',
  security_sell_rate decimal(20,3) DEFAULT NULL COMMENT 'Security Sell Rate per Quantity',
  security_brokerage_sell decimal(12,2) DEFAULT NULL COMMENT 'Brokerage for sell',
  security_tax_sell decimal(12,2) DEFAULT NULL COMMENT 'Tax for sell',
  security_net_sell decimal(20,3) DEFAULT NULL COMMENT 'Security Total Sell (Sell Rate*Quantity) - Brokerage - Tax',
  security_net_sell_rate decimal(20,3) DEFAULT NULL COMMENT 'Security effective sell per quantity i.e. Net Sell/Quantity',
  security_holding_period decimal(7,3) DEFAULT NULL COMMENT 'Security holding period in years i.e. Buy date to till date ',
  security_realized_net_profit decimal(20,3) DEFAULT NULL COMMENT 'Realized Net Profit = Market Value - Total Cost',
  security_absolute_return decimal(20,3) DEFAULT NULL COMMENT 'Realized absolute return',
  security_annualized_return decimal(20,3) DEFAULT NULL COMMENT 'Realized annualized return',
  fin_year varchar(9) COLLATE utf8_unicode_ci NOT NULL COMMENT 'FIN Year when security was sold',
  PRIMARY KEY (client_id,portfolio_id,security_id,security_buy_date,security_sell_date)
) COMMENT='Portfolio Historical Holdings';


delete from portfolio_historical_holdings;
insert into portfolio_historical_holdings 
select a.customer_code, a.portfolio_no, a.script_code, a.buy_date, a.script_name, a.asset_class, a.sub_class, a.sector_name, 
a.industry_name, a.quantity, a.buy_rate, a.brokerage, a.tax, a.total_cost, a.price_per_unit, a.sell_date, a.sell_rate, a.brokerage_sell,
a.tax_sell, a.total_sell, a.sell_per_unit, a.holding_period, a.net_profit, a.absolute_return, a.cagr_return, a.fin_year 
from equityanalysis.portfolio_realized_gain a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019);

select * from portfolio_historical_holdings;

CREATE TABLE portfolio_value_history (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  portfolio_value_date date NOT NULL  COMMENT 'Date ',
  portfolio_market_value DECIMAL(20,3) COMMENT 'Portfolio market value related to date',
  PRIMARY KEY (client_id,portfolio_id,portfolio_value_date)
) COMMENT='Portfolio Historical Value';

-- delete from portfolio_value_history;
insert into portfolio_value_history select * from equityanalysis.portfolio_value_history a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019) and date > '2017-07-13';
select * from portfolio_value_history a where a.portfolio_value_date > '2017-07-11';

CREATE TABLE portfolio_asset_allocation (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  allocation_date date NOT NULL COMMENT 'Date of asset allocation',
  allocation_asset_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Class',
  allocation_asset_sub_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Sub Class',
  allocation_market_value DECIMAL(20,3) COMMENT 'Market Value of Asset sub class',
  allocation_market_value_percent DECIMAL(7,3) COMMENT '%(Market Value) of Asset class',
PRIMARY KEY (client_id, portfolio_id, allocation_date, allocation_asset_class, allocation_asset_sub_class)
) COMMENT='Portfolio Asset Allocation';

delete from portfolio_asset_allocation;
insert into portfolio_asset_allocation 
select * from equityanalysis.portfolio_asset_allocation a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019) and date > '2017-07-10';
select * from portfolio_asset_allocation order by client_id, portfolio_id, allocation_date, allocation_asset_class, allocation_asset_sub_class;


CREATE TABLE portfolio_returns_calculation_support (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  calculation_date date NOT NULL COMMENT 'Date either cashflow date or end month',
  calculation_cashflow DECIMAL(20,3) COMMENT 'Cashflow amount Cash-in is negative, Cash-out is positive',
  calculation_market_value DECIMAL(20,3) COMMENT 'Market Value of the portfolio',
PRIMARY KEY (client_id, portfolio_id, calculation_date)
) COMMENT='Portfolio returns calculation support data';


delete from portfolio_returns_calculation_support;
insert into portfolio_returns_calculation_support
select a.customer_code, a.portfolio_no, a.date, a.cashflow, a.market_value from equityanalysis.portfolio_returns_calculation_support a
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019);

drop table portfolio_returns;
CREATE TABLE portfolio_returns (
  client_id int(11) NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  returns_year int(4) NOT NULL COMMENT 'returns for year',
  returns_calendar_year decimal(20,4) DEFAULT NULL COMMENT 'Returns for calendar year',
  returns_fin_year decimal(20,4) DEFAULT NULL COMMENT 'Returns for FIN year',
  returns_mar_ending_quarter decimal(20,4) DEFAULT NULL COMMENT 'Returns for Jan to Mar',
  returns_jun_ending_quarter decimal(20,4) DEFAULT NULL COMMENT 'Returns for Apr to Jun',
  returns_sep_ending_quarter decimal(20,4) DEFAULT NULL COMMENT 'Returns for Jul to Sep',
  returns_dec_ending_quarter decimal(20,4) DEFAULT NULL COMMENT 'Returns for Oct to Dec',
  returns_jan decimal(20,4) DEFAULT NULL COMMENT 'Returns for Jan',
  returns_feb decimal(20,4) DEFAULT NULL COMMENT 'Returns for Feb',
  returns_mar decimal(20,4) DEFAULT NULL COMMENT 'Returns for Mar',
  returns_apr decimal(20,4) DEFAULT NULL COMMENT 'Returns for Apr',
  returns_may decimal(20,4) DEFAULT NULL COMMENT 'Returns for May',
  returns_jun decimal(20,4) DEFAULT NULL COMMENT 'Returns for Jun',
  returns_jul decimal(20,4) DEFAULT NULL COMMENT 'Returns for Jul',
  returns_aug decimal(20,4) DEFAULT NULL COMMENT 'Returns for Aug',
  returns_sep decimal(20,4) DEFAULT NULL COMMENT 'Returns for Sep',
  returns_oct decimal(20,4) DEFAULT NULL COMMENT 'Returns for Oct',
  returns_nov decimal(20,4) DEFAULT NULL COMMENT 'Returns for Nov',
  returns_dec decimal(20,4) DEFAULT NULL COMMENT 'Returns for Dec',
  PRIMARY KEY (client_id,portfolio_id,year)
) COMMENT='Portfolio returns';

delete from portfolio_returns;
insert into portfolio_returns 
select * from equityanalysis.portfolio_returns a 
where a.customer_code in (1,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1014,1015,1016,1017,1018,1019);

CREATE TABLE portfolio_return_summary (
  client_id int(11) NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  returns_TWRR_since_inception decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns since inception',
  returns_TWRR_since_current_month decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns from current month',
  returns_TWRR_since_current_quarter decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns from current quarter',
  returns_TWRR_last_full_quarter decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns of last full quarter',
  returns_TWRR_last_full_two_quarter decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns of last full 2 quarters',
  returns_TWRR_last_full_three_quarter decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns of last full 3 quarters',
  returns_TWRR_last_full_four_quarter decimal(20,4) DEFAULT NULL COMMENT 'TWRR Returns of last full 4 quarters',
  returns_XIRR_since_inception decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns since inception',
  returns_XIRR_since_current_month decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns from current month',
  returns_XIRR_since_current_quarter decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns from current quarter',
  returns_XIRR_last_full_quarter decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns of last full quarter',
  returns_XIRR_last_full_two_quarter decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns of last full 2 quarters',
  returns_XIRR_last_full_three_quarter decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns of last full 3 quarters',
  returns_XIRR_last_full_four_quarter decimal(20,4) DEFAULT NULL COMMENT 'XIRR Returns of last full 4 quarters',
  PRIMARY KEY (client_id,portfolio_id)
) COMMENT='Portfolio returns summary';

