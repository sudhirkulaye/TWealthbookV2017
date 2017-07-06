SET SQL_SAFE_UPDATES = 0;
Commit;

drop table if exists portfolio_historical_holdings;
drop table if exists portfolio_holdings;
drop table if exists portfolio_cashflow;
drop table if exists portfolio;
drop table if exists family_member;
drop table if exists client;
drop table if exists user_role;
drop table if exists role;
drop table if exists user;
drop table if exists sequence_next_hi_value;
drop table if exists setup_dates;

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

create table sequence_next_hi_value (id varchar(50), sequence_next_hi_value int, primary key (id));

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

delete from sequence_next_hi_value where id = 'client_id';
insert into sequence_next_hi_value select 'client_id', (max(client_id)+1) from client;
select * from sequence_next_hi_value;

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
  portfolio_value float COMMENT 'Market value of the portfolio',
  PRIMARY KEY (client_id, portfolio_id),
  CONSTRAINT c_portfolio_client_id FOREIGN KEY (client_id) REFERENCES client (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Portfolio';

insert into portfolio 
(select customer_code, portfolio_no, 1, portfolio_goal, portfolio_start_date, portfolio_end_date, '', 1, 'NSE:NIFTY' from equityanalysis.portfolio_master a
where a.customer_code in (1006,1015,1018,1,1001,1002,1003,1004,1005,1007,1008,1009,1010,1014,1016,1017) order by a.customer_code);

select * from portfolio;

CREATE TABLE portfolio_cashflow (
  client_id int NOT NULL COMMENT 'Client ID unique Auto Generated',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  date date NOT NULL  COMMENT 'Date on which major cash inflow or outflow happens',
  amount float NOT NULL COMMENT 'Amount of cash inflow (negative) or outflow (outflow) happens',
  PRIMARY KEY (client_id,portfolio_id,date),
  CONSTRAINT c_portfolio_cashflow_client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
  CONSTRAINT c_portfolio_cashflow_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolio (portfolio_id)
) COMMENT='Portfolio Cashflow';

insert into portfolio_cashflow select * from equityanalysis.portfolio_inflow_outflow_history;

select * from portfolio_cashflow;

CREATE TABLE portfolio_holdings (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  security_id varchar(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Id',
  security_buy_date date NOT NULL COMMENT 'Security Buy Date',
  security_name varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Name',
  security_asset_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Class',
  security_asset_sub_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Sub Class',
  security_sector_name varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Sector Name',
  security_industry_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Industry Name',
  security_quantity float COMMENT 'Security Quantity',
  security_buy_rate float COMMENT 'Security Buy Rate per Quantity',
  security_brokerage float COMMENT 'Security Total Brokerage',
  security_tax float COMMENT 'Security Total Tax',
  security_total_cost float COMMENT 'Security Total Cost (Buy Rate*Quantity) + Brokerage + Tax',
  security_cost_rate float COMMENT 'Security effective cost per quantity i.e. Total Cost/Quantity',
  security_cmp float COMMENT 'Security Current Market Price',
  security_market_value float COMMENT 'Security market value (CMP*Quantity)',
  security_holding_period float  COMMENT 'Security holding period in years i.e. Buy date to till date ',
  security_unrealized_net_profit float COMMENT 'Unrealized Net Profit = Market Value - Total Cost',
  security_absolute_return float COMMENT 'Unrealized absolute return',
  security_annualized_return float COMMENT 'Unrealized annualized return',
  security_maturity_value float COMMENT 'Security Maturity Value especially for FDs',
  security_maturity_date date default '1900-01-01' COMMENT 'Security Maturity Value especially for FDs',
  PRIMARY KEY (client_id,portfolio_id,security_id,security_buy_date),
  CONSTRAINT c_portfolio_holdings_client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
  CONSTRAINT c_portfolio_holdings_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolio (portfolio_id)
) COMMENT='Portfolio Current Holdings';

delete from portfolio_holdings;

insert into portfolio_holdings 
select a.customer_code, a.portfolio_no, a.script_code, a.buy_date, a.script_name, a.asset_class, a.sub_class, a.sector_name, 
a.industry_name, a.quantity, a.buy_rate, a.brokerage, a.tax, a.total_cost, a.price_per_unit, a.cmp, a.market_value, a.holding_period, 
a.net_profit, a.absolute_return, a.cagr_return, a.maturity_value, a.maturity_date from equityanalysis.portfolio_current_status a;

update portfolio_holdings a set a.security_maturity_date = '1900-01-01' where security_maturity_date is null or security_maturity_date = '0000-00-00';

select * from portfolio_holdings a where a.client_id not in (select client_id from client); 

CREATE TABLE portfolio_historical_holdings (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  security_id varchar(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Id',
  security_buy_date date NOT NULL COMMENT 'Security Buy Date',
  security_name varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Name',
  security_asset_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Class',
  security_asset_sub_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Sub Class',
  security_sector_name varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Sector Name',
  security_industry_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Security Industry Name',
  security_quantity float COMMENT 'Security Quantity',
  security_buy_rate float COMMENT 'Security Buy Rate per Quantity',
  security_brokerage float COMMENT 'Brokerage for buy',
  security_tax float COMMENT 'Tax for buy',
  security_total_cost float COMMENT 'Security Total Cost (Buy Rate*Quantity) + Brokerage + Tax',
  security_cost_rate float COMMENT 'Security effective cost per quantity i.e. Total Cost/Quantity',
  security_sell_date date NOT NULL COMMENT 'Security Sell Date',
  security_sell_rate float COMMENT 'Security Sell Rate per Quantity',
  security_brokerage_sell float COMMENT 'Brokerage for sell',
  security_tax_sell float COMMENT 'Tax for sell',
  security_net_sell float COMMENT 'Security Total Sell (Sell Rate*Quantity) - Brokerage - Tax',
  security_net_sell_rate float COMMENT 'Security effective sell per quantity i.e. Net Sell/Quantity',
  security_holding_period float  COMMENT 'Security holding period in years i.e. Buy date to till date ',
  security_realized_net_profit float COMMENT 'Realized Net Profit = Market Value - Total Cost',
  security_absolute_return float COMMENT 'Realized absolute return',
  security_annualized_return float COMMENT 'Realized annualized return',
  fin_year varchar(9) COLLATE utf8_unicode_ci NOT NULL COMMENT 'FIN Year when security was sold',
  PRIMARY KEY (client_id, portfolio_id, security_id, security_buy_date, security_sell_date)
) COMMENT='Portfolio Historical Holdings';

insert into portfolio_historical_holdings 
select a.customer_code, a.portfolio_no, a.script_code, a.buy_date, a.script_name, a.asset_class, a.sub_class, a.sector_name, 
a.industry_name, a.quantity, a.buy_rate, a.brokerage, a.tax, a.total_cost, a.price_per_unit, a.sell_date, a.sell_rate, a.brokerage_sell,
a.tax_sell, a.total_sell, a.sell_per_unit, a.holding_period, a.net_profit, a.absolute_return, a.cagr_return, a.fin_year from equityanalysis.portfolio_realized_gain a;

CREATE TABLE portfolio_asset_allocation (
  client_id int NOT NULL COMMENT 'Client ID for reference unique',
  portfolio_id int(3) NOT NULL COMMENT 'Portfolio No unique',
  allocation_date date NOT NULL COMMENT 'Date of asset allocation',
  allocation_asset_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Class',
  allocation_asset_sub_class varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Security Asset Sub Class',
  allocation_market_value float COMMENT 'Market Value of Asset sub class',
PRIMARY KEY (client_id, portfolio_id, security_id, security_buy_date, security_sell_date)
) COMMENT='Portfolio Asset Allocation';

