/** table and data from micromacro numbers */
SET SQL_SAFE_UPDATES = 0;
Commit;

CREATE TABLE log_table (
  timestamp datetime DEFAULT NULL COMMENT 'timestamp',
  log_query varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Log Query'
) COMMENT='Debug - Log table';


drop table if exists asset_classification;
CREATE TABLE asset_classification (
  asset_class_id int(11) NOT NULL COMMENT 'PK Asset Class',
  asset_subclass_id int(11) NOT NULL COMMENT 'PK Asset Sub Class',
  asset_class_name_display varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Asset Class Display Name',
  asset_sub_class_name_display varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Asset Sub Class name Display',
  asset_subclass_description varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Description of Asset - Asset Subclass',
  PRIMARY KEY (asset_class_id,asset_subclass_id)
) COMMENT='Securities - Setup - Asset Classification';

select * from asset_classification order by asset_class_id, asset_subclass_id;
-- @C:\MyDocuments\16TWealthbook\ProductinDatabase\asset_classification.sql;

drop table if exists sector;
CREATE TABLE sector (
  sector_id int(2) NOT NULL COMMENT 'PK Sector ID',
  sector_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Sector Name ',
  sector_name_display varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Short Name for display purpose',
  PRIMARY KEY (sector_id)
) COMMENT='Securities - Setup - Sector Master as per MSCI Sector-Industry Classification 2015';

SELECT * from sector order by sector_id;
-- @C:\MyDocuments\16TWealthbook\ProductinDatabase\sector.sql;

drop table if exists industry;
CREATE TABLE industry (
  sector_id int(2) NOT NULL COMMENT 'FK Sector ID',
  industry_id int(6) NOT NULL COMMENT 'PK Industry ID',
  sector_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Sector Name',
  sector_name_display varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Short Name for display purpose',
  industry_name varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Industry Name',
  industry_name_display varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for display purpose',
  PRIMARY KEY (sector_id,industry_id)
) COMMENT='Securities - Setup - Sector-Industry Mapping as per MSCI Sector-Industry Classification 2015';

-- insert into industry select * from micromacronumbers.sector_industry_mapping a order by sector_id, industry_id;
select * from industry order by sector_id, industry_id;

CREATE TABLE sub_industry (
  sector_id int(2) NOT NULL COMMENT 'PK Sector ID',
  industry_id int(6) NOT NULL COMMENT 'PK Industry ID',
  sub_industry_id int(8) NOT NULL COMMENT 'PK Sub Industry ID',
  sector_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Sector Name',
  sector_name_display varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Short Name for display purpose',
  industry_name varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Industry Name',
  industry_name_display varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for display purpose',
  sub_industry_name varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Sub Industry Name',
  sub_industry_name_display varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for display purpose',
  PRIMARY KEY (sector_id,industry_id,sub_industry_id),
  KEY c_sub_industry_industry_id (industry_id)
) COMMENT='Securities - Setup - Sector-Industry-Subindustry Mapping as per MSCI Sector-Industry Classification 2015';
-- insert into sub_industry select * from micromacronumbers.sector_sub_industry_mapping a order by a.sector_id, a.industry_id, a.sub_industry_id;
select * from sub_industry a order by sector_id, industry_id, sub_industry_id; 

CREATE TABLE company_universe (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  company_ticker_mc varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Money Control Code for the stock',
  company_ticker_b varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bloomberg code for the stock',
  company_nse_code varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'NSE Code',
  company_bse_code varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'BSE Code',
  company_isin_code varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'ISIN Code ',
  company_short_name varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for Display Purpose',
  company_name_mc varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Full Name on Money Control',
  company_name_b varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Full Name on Bloomberg',
  company_sub_industry_name_mc varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Sub Industry Name taken from Moneycontrol',
  company_sector_name_b varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sector Name taken from Bloomberg',
  company_industry_name_b varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Industry Name taken from Bloomberg',
  company_sub_industry_name_b varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sub Industry Name taken from Bloomberg',
  company_sector_name_display varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sector Name for Display as per Sector_Master',
  company_industry_name_display varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Industry Name for Display as per Sector-Industry Mapping',
  company_sub_industry_name_display varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sub Industry Name for Display as per Sector-Industry-SubIndustry Mapping',
  is_sensex int(1) DEFAULT '0' COMMENT '1 = If Stock is in SENSEX ',
  is_nifty50 int(1) DEFAULT '0' COMMENT '1 = If Stock is in NIFTY50',
  is_niftyjr int(1) DEFAULT '0' COMMENT '1 = If Stock is in NIFTY JR',
  is_bse100 int(1) DEFAULT '0' COMMENT '1 = If Stock is in BSE100',
  is_nse100 int(1) DEFAULT '0' COMMENT '1 = If Stock is in NSE100',
  is_bse200 int(1) DEFAULT '0' COMMENT '1 = If Stock is in NSE200',
  is_nse200 int(1) DEFAULT '0' COMMENT '1 = If Stock is in NSE200',
  is_bse500 int(1) DEFAULT '0' COMMENT '1 = If Stock is in BSE500',
  is_nse500 int(1) DEFAULT '0' COMMENT '1 = If Stock is in NSE500',
  company_asset_class_id int(11) DEFAULT '0' COMMENT 'Asset Class as per Asset Classification',
  company_asset_subclass_id int(11) DEFAULT '0' COMMENT 'Asset Sub Class as per Asset Classification ',
  company_market_cap_g decimal(20,0) DEFAULT NULL COMMENT 'Company Latest Market Cap',
  company_market_cap_rank_g int(5) DEFAULT NULL COMMENT 'Company Latest Market Cap Rank',
  company_market_cap_b decimal(20,0) DEFAULT NULL,
  company_market_cap_rank_b int(5) DEFAULT NULL,
  company_pe decimal(10,2) DEFAULT NULL COMMENT 'Company Latest PE ratio',
  company_delisted int(1) DEFAULT '0' COMMENT 'Flag for delisting due to merger or acquisition. 0 Not Delisted 1 Delisted',
  company_ticker_new varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (company_ticker)
) COMMENT='Securities - Company universe composed of NIFTY50, NIFTY JR, NIFTY100/BSE100, NIFTY200/BSE200, NIFTY500/BSE500 and other small stocks tracked by brokerage houses';

select * from company_universe a where a.company_delisted = 0 and a.company_sub_industry_name_mc in ('INDEX', 'ETF')
order by company_asset_subclass_id, is_sensex desc, is_nifty50 desc, is_nse100 desc, is_bse100 desc, is_nse200 desc, is_bse200 desc, is_nse500 desc, company_ticker; 

select * from company_universe a where company_asset_subclass_id in (406040);
select from_unixtime(1500375953);
select company_bse_code, count(1) from company_universe a group by company_bse_code having count(1) > 1;
select company_asset_subclass_id, count(1) from company_universe a group by company_asset_subclass_id;

CREATE TABLE company_profile (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  company_ticker_mc varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Money Control Code for the stock',
  company_ticker_b varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bloomberg code for the stock',
  company_nse_code varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'NSE Code',
  company_bse_code varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'BSE Code',
  company_short_name varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for Display Purpose',
  company_profile varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (company_ticker)
) COMMENT='Securities - Company profile taken from Bloomberg';

insert into company_profile select nse_code, moneycontrol_ticker, bloomberg_ticker, nse_code, bse_code, company_name, company_profile from equityanalysis.company_universe_profile a where a.nse_code <>'';
update company_profile a, company_universe b set a.company_ticker = b.company_ticker, a.company_nse_code = b.company_nse_code, a.company_bse_code = b.company_bse_code where a.company_ticker_b = b.company_ticker_b;
select * from company_profile; 

CREATE TABLE company_corp_action (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  corp_action_date date NOT NULL COMMENT 'PK Date associated with Corporate Action',
  corp_action_type varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Corporate Actions like STOCK_SPLIT, BONUS',
  corp_action_multiplier decimal(6,3) DEFAULT NULL COMMENT 'Action Information',
  corp_action_desc varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Action Description',
  PRIMARY KEY (company_ticker,corp_action_date,corp_action_type)
) COMMENT='Securities - Corporate Action History';

select * from company_corp_action;

drop table if exists index_valuation;
CREATE TABLE index_valuation (
  index_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Index code form google',
  index_valuation_date date NOT NULL COMMENT 'PK Index valuation date',
  index_PE_ratio decimal(10,3) DEFAULT NULL COMMENT 'Index PE Ratio',
  index_PB_ratio decimal(10,3) DEFAULT NULL COMMENT 'Index PB Ratio',
  index_div_yield decimal(10,2) DEFAULT NULL COMMENT 'Index Div Yield Ratio',
  index_value decimal(10,2) DEFAULT NULL COMMENT 'Index value',
  index_turnover decimal(20,2) DEFAULT NULL COMMENT 'Index turnover in Rs',
  index_implied_earnings decimal(10,3) DEFAULT NULL COMMENT 'Index implied earnings Index Value/index PE',
  PRIMARY KEY (index_ticker,index_valuation_date)
) COMMENT='Valutation - Index Valuation Data';
select * from index_valuation a where a.index_valuation_date >= '2017-07-26'; (select max(index_valuation_date) from index_valuation); 

create table fixed_income_index (
  index_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Index code',
  index_date date NOT NULL COMMENT 'PK Index date',
  index_value decimal(10,2) DEFAULT NULL COMMENT 'Index value',
  PRIMARY KEY (index_ticker,index_date)
) COMMENT='Fixed Income - Index Data';

select * from fixed_income_index order by index_date desc; 


drop table company_daily_data_b;
CREATE TABLE company_daily_data_b (
  company_ticker_b varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bloomberg code for the stock; mapped to ticker',
  company_daily_date date NOT NULL COMMENT 'PK Date; mapped to UTIME',
  company_name_b varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Full Name on Bloomberg; mapped to disp_name',
  company_daily_closing_price decimal(20,3) DEFAULT '0.000' COMMENT 'Last Close Price of the day; mapped to last_price',
  company_daily_previous_day_closing_price decimal(20,3) DEFAULT '0.000' COMMENT 'Previous trading day closing price; mapped to ',
  company_daily_volume decimal(20,2) DEFAULT '0.00' COMMENT 'Day Volume; mapped to volume',
  company_daily_volume_30d decimal(20,2) DEFAULT '0.00' COMMENT '30 Day Avg Volume; mapped to volume_30d',
  company_daily_market_cap decimal(20,0) DEFAULT '0' COMMENT 'Market Cap in Rs; mapped to market_cap',
  company_daily_shares_outstanding decimal(20,0) DEFAULT '0' COMMENT 'Shares Outstanding; mapped to (company_daily_market_cap/company_daily_closing_price)',
  company_daily_eps decimal(10,3) DEFAULT '0.000' COMMENT 'EPS; mapped to eps',
  company_daily_best_eps_lst_qtr decimal(10,3) DEFAULT '0.000' COMMENT 'Best EPS last Quarter; mapped to best_eps_lst_qtr',
  company_daily_est_eps_last_qtr decimal(10,3) DEFAULT '0.000' COMMENT 'Estimated EPS last Quarter; mapped to est_eps_last_qtr',
  company_daily_eps_surprise_last_qtr decimal(10,3) DEFAULT '0.000' COMMENT 'EPS surprise last Quarter; mapped to eps_surprise_last_qtr',
  company_daily_estimated_eps_yr decimal(10,3) DEFAULT '0.000' COMMENT 'Estimated EPS for Year; mapped to estimated_eps_yr',
  company_daily_estimated_eps_nxt_qtr decimal(10,3) DEFAULT '0.000' COMMENT 'Estimated EPS next Quarter; mapped to estimated_eps_nxt_qtr',
  company_daily_current_pe decimal(10,3) DEFAULT '0.000' COMMENT 'PE Ratio; mapped to current_pe',
  company_daily_estimated_pe_cur_yr decimal(10,3) DEFAULT '0.000' COMMENT 'PE Ratio; mapped to estimated_pe_cur_yr',
  company_daily_price_book decimal(10,3) DEFAULT '0.000' COMMENT 'PE Ratio; mapped to price_book',
  company_daily_price_to_sales decimal(10,3) DEFAULT '0.000' COMMENT 'Price to Sales; mapped to ',
  company_daily_dividend_yield decimal(10,3) DEFAULT '0.000' COMMENT 'Dividend Yield; mapped to dividend_indicated_gross_yield',
  company_sector_name_b varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sector; mapped to company_sector',
  company_industry_name_b varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Industry; mapped to company_industry',
  company_sub_industry_name_b varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sub Industry Name taken from Bloomberg; mapped to ',
  company_daily_DS199 varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Sector; mapped to DS199',
  company_daily_DS201 varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Industry; mapped to DS201',
  company_daily_52week_low decimal(10,3) DEFAULT '0.000' COMMENT '52 week low; mapped to range_52wk_low',
  company_daily_52week_high decimal(10,3) DEFAULT '0.000' COMMENT '52 week High; mapped to range_52wk_high',
  company_daily_price_chge_1D decimal(10,2) DEFAULT '0.00' COMMENT '52 week High; mapped to price_chge_1D',
  company_daily_pct_chge_1D decimal(10,3) DEFAULT '0.000' COMMENT '52 week High; mapped to pct_chge_1D',
  company_daily_total_return_1year decimal(10,3) DEFAULT '0.000' COMMENT 'Total Return 1 Year; mapped to pct_return_52wk',
  company_daily_total_return_YTD decimal(10,3) DEFAULT '0.000' COMMENT 'Total Return YTD; mapped to ',
  company_daily_market_cap_rank int(5) DEFAULT '0' COMMENT 'Rank by Market Cap; mapped to ',
  company_daily_last_earning_date date DEFAULT '2000-01-01' COMMENT 'Last Earnings Date; mapped to last_earning_date',
  company_daily_next_earning_date date DEFAULT '2000-01-01' COMMENT 'Next Earnings Date; mapped to next_earning_date',
  company_daily_latest_anncmt_period varchar(10) COLLATE utf8_unicode_ci DEFAULT '2000:Q1' COMMENT 'Latest announcement period; mapped to latest_anncmt_period',
  company_daily_shares int(3) DEFAULT '0' COMMENT 'If not 10 then it could be split or bonus event; mapped to shares',
  PRIMARY KEY (company_ticker_b,company_daily_date),
  KEY idx_company_daily_data_b_company_ticker_b (company_ticker_b),
  KEY idx_company_daily_data_b_company_daily_date (company_daily_date)
) COMMENT='Securities - Daily Data obtained from Bloomberg Portfolio';

insert into company_daily_data_b
select a.bloomberg_ticker, a.date, '', a.last_price, a.last_day_closing_price, a.volume, 0, a.market_cap, a.shares_outstanding, a.EPS, 0,0,0,0,0, a.PE, 0, 0, a.price_to_sales, a.dividend_yield, a.sector_name_Bloomberg, a.industry_name_Bloomberg, a.sub_industry_name_Bloomberg, '','', a.52week_low, a.52week_high, 0,0, a.total_return_1year, a.total_return_YTD, a.market_cap_rank, '2000-01-01', '2000-01-01', '', 0 from micromacronumbers.daily_upload_bloomberg a where a.date <= '2017-07-18'; 

drop table company_daily_data_g;
CREATE TABLE company_daily_data_g (
  company_daily_date date NOT NULL COMMENT 'PK Date',
  company_short_name varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Short Name for Display Purpose',
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_daily_last_price decimal(20,3) DEFAULT NULL COMMENT 'Last Price',
  company_daily_change decimal(10,3) DEFAULT NULL COMMENT 'Day Change in percent',
  company_daily_market_cap varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Market Cap',
  company_daily_volume varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Volume',
  company_daily_open_price decimal(10,3) DEFAULT NULL COMMENT 'Open Price',
  company_daily_high_price decimal(10,3) DEFAULT NULL COMMENT 'High Price',
  company_daily_low_price decimal(10,3) DEFAULT NULL COMMENT 'Low Price',
  company_daily_volume_no decimal(20,2) DEFAULT '0.00' COMMENT 'Volume in numbers',
  company_daily_rupee_volume decimal(20,2) DEFAULT '0.00',
  company_daily_market_cap_no decimal(20,0) DEFAULT '0' COMMENT 'Market Cap in Numbers',
  company_daily_market_cap_rank int(5) DEFAULT '0' COMMENT 'Rank by Market Cap',
  PRIMARY KEY (company_daily_date,company_ticker),
  KEY idx_company_daily_data_g_company_ticker (company_ticker),
  KEY idx_company_daily_data_g_company_daily_date (company_daily_date)
) COMMENT='Securities - Daily data obtained from google finance';

insert into company_daily_data_g 
select * from micromacronumbers.daily_upload_google a where a.company_ticker in (select company_ticker from company_universe);

drop table mutual_fund;
CREATE TABLE mutual_fund (
  mf_date date DEFAULT '2000-01-01' COMMENT 'PK Mutual Fund Date',
  mf_scheme_code INT(10) NOT NULL COMMENT 'PK Mutual Fund Scheme Code Unique',
  mf_isin_div_payout_or_isin_growth varchar(50) COLLATE utf8_unicode_ci DEFAULT 'XX' COMMENT 'ISIN Dividend Payout or Growth Code',
  mf_isin_div_reinvestment varchar(50) COLLATE utf8_unicode_ci DEFAULT 'XX' COMMENT 'ISIN Dividend Reinvestment Code',
  mf_scheme_name varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Mutual Fund Scheme Name',
  mf_nav DECIMAL(20,4) NOT NULL COMMENT 'Mutual Fund NAV',
  mf_repurchase_price DECIMAL(20,4) DEFAULT '0' COMMENT 'Mutual Fund repurchase price',
  mf_sale_price DECIMAL(20,4) DEFAULT '0' COMMENT 'Mutual Fund repurchase price',
  PRIMARY KEY (mf_date,mf_scheme_code,mf_isin_div_payout_or_isin_growth,mf_isin_div_reinvestment),
  KEY idx_mutual_fund_mf_scheme_code (mf_scheme_code),
  KEY idx_mutual_fund_mf_isin_div_payout_or_isin_growth (mf_isin_div_payout_or_isin_growth)
) COMMENT='Securities - Daily data obtained from AMFI India';
select * from mutual_fund a where a.mf_date = '2017-07-31';


select * from company_daily_data_g where company_daily_date = '2017-07-27' order by company_daily_market_cap_rank;
drop table company_price_movement;
CREATE TABLE company_price_movement (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  company_ticker_b varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Bloomberg code for the stock; mapped to ticker',
  price_move_last_price decimal(20,3) DEFAULT '0.000' COMMENT 'Closing Market Price',
  price_move_daily decimal(20,3) DEFAULT '0.000' COMMENT 'Daily Change',
  price_move_weekly decimal(20,3) DEFAULT '0.000' COMMENT 'Weekly Change',
  price_move_biweekly decimal(20,3) DEFAULT '0.000' COMMENT 'Biweekly Change',
  price_move_monthly decimal(20,3) DEFAULT '0.000' COMMENT 'Monthly change',
  price_move_quarterly decimal(20,3) DEFAULT '0.000' COMMENT 'Quarterly change',
  price_move_semianual decimal(20,3) DEFAULT '0.000' COMMENT 'Semianual change',
  price_move_annual decimal(20,3) DEFAULT '0.000' COMMENT 'Annual change',
  price_move_current_fin_year decimal(20,3) DEFAULT '0.000' COMMENT 'Change from begining of FIN Year',
  price_move_3Year decimal(20,3) DEFAULT '0.000' COMMENT '3 Year Change',
  price_move_since_3yr_low decimal(20,3) DEFAULT '0.000' COMMENT 'Change since 3yr low',
  price_move_since_1yr_low decimal(20,3) DEFAULT NULL COMMENT 'Change since 1yr low',
  price_move_since_3yr_high decimal(20,3) DEFAULT NULL COMMENT 'Change since 3yr hing',
  price_move_since_1yr_high decimal(20,3) DEFAULT NULL COMMENT 'Change since 1yr high',
  avg_daily_traded_turnover decimal(20,2) DEFAULT NULL COMMENT 'Avg. daily traded turnover in Cr',
  avg_daily_volume decimal(20,2) DEFAULT NULL COMMENT 'Avg. daily volume',
  PRIMARY KEY (company_ticker)
) COMMENT='Security Analysis - Price Movement';

CREATE TABLE company_marketcap_rank_movement (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  company_ticker_b varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Bloomberg code for the stock; mapped to ticker',
  market_cap_g decimal(20,0) DEFAULT NULL COMMENT 'Market Cap as per google',
  rank_today_g int(4) DEFAULT NULL COMMENT 'Market Cap Rank Today as per google',
  rank_move_monthly_g int(4) DEFAULT NULL COMMENT 'Monthly Change in Market Cap Rank as per google',
  rank_move_quarterly_g int(4) DEFAULT NULL COMMENT 'Quarterly Change in Market Cap Rank as per google',
  rank_move_fin_year_g int(4) DEFAULT NULL COMMENT 'Change in Market Cap Rank Begining of FIN Year as per google',
  rank_move_semiannual_g int(4) DEFAULT NULL COMMENT 'Semiannual Change in Market Cap Rank as per google',
  rank_move_annual_g int(4) DEFAULT NULL COMMENT 'Annual Change in Market Cap Rank as per google',
  rank_move_3Year_g int(4) DEFAULT NULL COMMENT '3 Year Change in Market Cap Rank as per google',
  market_cap_b decimal(20,0) DEFAULT NULL COMMENT 'Market Cap as per Bloomberg',
  rank_today_b int(4) DEFAULT NULL COMMENT 'As per Bloomberg, Market Cap Rank Today as per Bloomberg',
  rank_move_monthly_b int(4) DEFAULT NULL COMMENT 'Monthly Change in Market Cap Rank as per Bloomberg',
  rank_move_quarterly_b int(4) DEFAULT NULL COMMENT 'Quarterly Change in Market Cap Rank as per Bloomberg',
  rank_move_fin_year_b int(4) DEFAULT NULL COMMENT 'Change in Market Cap Rank Begining of FIN Year as per Bloomberg',
  rank_move_semiannual_b int(4) DEFAULT NULL COMMENT 'Semiannual Change in Market Cap Rank as per Bloomberg',
  rank_move_annual_b int(4) DEFAULT NULL COMMENT 'Annual Change in Market Cap Rank as per Bloomberg',
  rank_move_3Year_b int(4) DEFAULT NULL COMMENT '3 Year Change in Market Cap Rank as per Bloomberg',
  PRIMARY KEY (company_ticker)
) COMMENT='Security Analysis - Market Cap Rank Movement';

CREATE TABLE company_results_analysis (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK, Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  last_quarter_Consol date DEFAULT NULL COMMENT 'Last Consolidated Quarter',
  last_quarter_Stand date DEFAULT NULL COMMENT 'Last Standalone Quarter',
  last_FIN_Year_Consol date DEFAULT NULL COMMENT 'Last Consolidated FIN Year',
  last_FIN_Year_Stand date DEFAULT NULL COMMENT 'Last Standalone FIN Year',
  TTM_Net_Sales_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Total Income',
  TTM_Net_Sales_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Total Income',
  TTM_Total_Inc_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Net Sales',
  TTM_Total_Inc_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Net Sales',
  TTM_Total_Expenses_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Total Expense',
  TTM_Total_Expenses_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Total Exp',
  TTM_PBITDA_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated PBITDA',
  TTM_PBITDA_Stand double DEFAULT '0' COMMENT '12 trailing months standalone PBITDA',
  TTM_Other_Inc_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Other Inc',
  TTM_Other_Inc_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Other Inc',
  TTM_Depreciation_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Depreciation',
  TTM_Depreciation_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Depreciation',
  TTM_PBIT_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated PBIT',
  TTM_PBIT_Stand double DEFAULT '0' COMMENT '12 trailing months standalone PBIT',
  TTM_Interest_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Interest',
  TTM_Interest_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Interest',
  TTM_PBT_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated PBT',
  TTM_PBT_Stand double DEFAULT '0' COMMENT '12 trailing months standalone PBT',
  TTM_Exceptional_Items_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Exceptional Iteam',
  TTM_Exceptional_Items_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Exceptional Iteam',
  TTM_Tax_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Tax',
  TTM_Tax_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Tax',
  TTM_PAT_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated PAT',
  TTM_PAT_Stand double DEFAULT '0' COMMENT '12 trailing months standalone PAT',
  TTM_Net_Profit_Consol double DEFAULT '0' COMMENT '12 trailing months cosolidated Net Profit',
  TTM_Net_Profit_Stand double DEFAULT '0' COMMENT '12 trailing months standalone Net Profit',
  TTM_EPS_Consol float DEFAULT '0' COMMENT '12 trailing months cosolidated EPS',
  TTM_EPS_Stand float DEFAULT '0' COMMENT '12 trailing months standalone EPS',
  Y0_BookValue_Consol float DEFAULT '0' COMMENT '12 trailing months cosolidated BookValue',
  Y0_BookValue_Stand float DEFAULT '0' COMMENT '12 trailing months standalone BookValue',
  Y0_Dividend_per_share float DEFAULT '0' COMMENT '12 trailing months Dividend Paid',
  TTM_PBIT_margin_Consol float DEFAULT '0' COMMENT '12 trailing months PBIT Margin',
  TTM_PBIT_margin_Stand float DEFAULT '0' COMMENT '12 trailing months PBIT Margin',
  TTM_Net_Profit_margin_Consol float DEFAULT '0' COMMENT '12 trailing months Net Profit Margin',
  TTM_Net_Profit_margin_Stand float DEFAULT '0' COMMENT '12 trailing months Net Profit Margin',
  Q2Q_Sales_growth_Consol float DEFAULT '0' COMMENT 'Quarter-to-Quarter Consolidated Sales Growth',
  Q2Q_Sales_growth_Stand float DEFAULT '0' COMMENT 'Quarter-to-Quarter Standlaone Sales Growth',
  YoY_Sales_growth_Consol float DEFAULT '0' COMMENT 'Year-on-Year Consolidated Sales Growth',
  YoY_Sales_growth_Stand float DEFAULT '0' COMMENT 'Year-on-Year Standlaone Sales Growth',
  Q2Q_PBIT_growth_Consol float DEFAULT '0' COMMENT 'Quarter-to-Quarter Consolidated PBIT Growth',
  Q2Q_PBIT_growth_Stand float DEFAULT '0' COMMENT 'Quarter-to-Quarter Standlaone PBIT Growth',
  YoY_PBIT_growth_Consol float DEFAULT '0' COMMENT 'Year-on-Year Consolidated PBIT Growth',
  YoY_PBIT_growth_Stand float DEFAULT '0' COMMENT 'Year-on-Year Standlaone PBIT Growth',
  Q2Q_Net_Profit_growth_Consol float DEFAULT '0' COMMENT 'Quarter-to-Quarter Consolidated Net Profit Growth',
  Q2Q_Net_Profit_growth_Stand float DEFAULT '0' COMMENT 'Quarter-to-Quarter Standlaone Net Profit Growth',
  YoY_Net_Profit_growth_Consol float DEFAULT '0' COMMENT 'Year-on-Year Consolidated Net Profit Growth',
  YoY_Net_Profit_growth_Stand float DEFAULT '0' COMMENT 'Year-on-Year Standlaone Net Profit Growth',
  1Yr_Sales_growth_Consol float DEFAULT '0' COMMENT '1Yr Consolidated Sales Growth',
  1Yr_Sales_growth_Stand float DEFAULT '0' COMMENT '1Yr Standlaone Sales Growth',
  1Yr_Net_Profit_growth_Consol float DEFAULT '0' COMMENT '1Yr Consolidated Net Profit Growth',
  1Yr_Net_Profit_growth_Stand float DEFAULT '0' COMMENT '1Yr Standlaone Net Profit Growth',
  3Yr_Sales_growth_Consol float DEFAULT '0' COMMENT '3Yr Consolidated Sales Growth',
  3Yr_Sales_growth_Stand float DEFAULT '0' COMMENT '3Yr Standlaone Sales Growth',
  3Yr_Net_Profit_growth_Consol float DEFAULT '0' COMMENT '3Yr Consolidated Net Profit Growth',
  3Yr_Net_Profit_growth_Stand float DEFAULT '0' COMMENT '3Yr Standlaone Net Profit Growth',
  5Yr_Sales_growth_Consol float DEFAULT '0' COMMENT '5Yr Consolidated Sales Growth',
  5Yr_Sales_growth_Stand float DEFAULT '0' COMMENT '5Yr Standlaone Sales Growth',
  5Yr_Net_Profit_growth_Consol float DEFAULT '0' COMMENT '5Yr Consolidated Net Profit Growth',
  5Yr_Net_Profit_growth_Stand float DEFAULT '0' COMMENT '5Yr Standlaone Net Profit Growth',
  7Yr_Sales_growth_Consol float DEFAULT '0' COMMENT '7Yr Consolidated Sales Growth',
  7Yr_Sales_growth_Stand float DEFAULT '0' COMMENT '7Yr Standlaone Sales Growth',
  7Yr_Net_Profit_growth_Consol float DEFAULT '0' COMMENT '7Yr Consolidated Net Profit Growth',
  7Yr_Net_Profit_growth_Stand float DEFAULT '0' COMMENT '7Yr Standlaone Net Profit Growth',
  10Yr_Sales_growth_Consol float DEFAULT '0' COMMENT '10Yr Consolidated Sales Growth',
  10Yr_Sales_growth_Stand float DEFAULT '0' COMMENT '10Yr Standlaone Sales Growth',
  10Yr_Net_Profit_growth_Consol float DEFAULT '0' COMMENT '10Yr Consolidated Net Profit Growth',
  10Yr_Net_Profit_growth_Stand float DEFAULT '0' COMMENT '10Yr Standlaone Net Profit Growth',
  PBIT_margin_Q0_Consol float DEFAULT NULL,
  PBIT_margin_Q0_Stand float DEFAULT NULL,
  PBIT_margin_Q1_Consol float DEFAULT NULL,
  PBIT_margin_Q1_Stand float DEFAULT NULL,
  PBIT_margin_Q4_Consol float DEFAULT NULL,
  PBIT_margin_Q4_Stand float DEFAULT NULL,
  PBIT_margin_Y3_Consol float DEFAULT NULL,
  PBIT_margin_Y3_Stand float DEFAULT NULL,
  PBIT_margin_Y5_Consol float DEFAULT NULL,
  PBIT_margin_Y5_Stand float DEFAULT NULL,
  Net_Profit_margin_Q0_Consol float DEFAULT NULL,
  Net_Profit_margin_Q0_Stand float DEFAULT NULL,
  Net_Profit_margin_Q1_Consol float DEFAULT NULL,
  Net_Profit_margin_Q1_Stand float DEFAULT NULL,
  Net_Profit_margin_Q4_Consol float DEFAULT NULL,
  Net_Profit_margin_Q4_Stand float DEFAULT NULL,
  Net_Profit_margin_Y3_Consol float DEFAULT NULL,
  Net_Profit_margin_Y3_Stand float DEFAULT NULL,
  Net_Profit_margin_Y5_Consol float DEFAULT NULL,
  Net_Profit_margin_Y5_Stand float DEFAULT NULL,
  PRIMARY KEY (company_ticker)
) COMMENT='Company Result Processing  	Processing	Quarterly';


CREATE TABLE company_valuation (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK, Usually NSE Code, BSE Code in case there is ''&'' in NSE Code or stock is only listed on BSE',
  TTM_PE_Consol float DEFAULT '0' COMMENT '12 trailing months cosolidated PE',
  TTM_PE_Stand float DEFAULT '0' COMMENT '12 trailing months standalone PE',
  TTM_PB_Consol float DEFAULT '0' COMMENT '12 trailing months cosolidated PB',
  TTM_PB_Stand float DEFAULT '0' COMMENT '12 trailing months standalone PB',
  dividend_yield float DEFAULT '0' COMMENT 'Dividend Yield',
  market_cap_to_sales float DEFAULT '0' COMMENT 'Market Cap to TTM consolidated sales ratio, if not consolidated then TTM standalone sales',
  EV_to_sales float DEFAULT '0' COMMENT 'Enterprise Value to TTM consolidated sales ratio, if not consolidated then TTM standalone sales',
  EV_to_PBITDA float DEFAULT '0' COMMENT 'Enterprise Value to TTM consolidated PBITDA ratio, if not consolidated then TTM standalone EBITDA',
  dividend_payout_ratio float DEFAULT '0' COMMENT 'Consolidated Dividend Payout Ratio, if not consolidated then standalone',
  PEG_ratio float DEFAULT '0' COMMENT 'PEG Ratio',
  1yr_high_PE_Consol float DEFAULT '0' COMMENT '1 Yr high TTM consolidated PE',
  1yr_high_PE_Stand float DEFAULT '0' COMMENT '1 Yr high TTM standalone PE',
  1yr_low_PE_Consol float DEFAULT '0' COMMENT '1 Yr low TTM Consolidated PE',
  1yr_low_PE_Stand float DEFAULT '0' COMMENT '1 Yr low TTM standalone PE',
  1yr_high_PB_Consol float DEFAULT '0' COMMENT '1 Yr high TTM consolidated PB',
  1yr_high_PB_Stand float DEFAULT '0' COMMENT '1 Yr high TTM standalone PB',
  1yr_low_PB_Consol float DEFAULT '0' COMMENT '1 Yr low TTM consolidated PB',
  1yr_low_PB_Stand float DEFAULT '0' COMMENT '1 Yr low TTM Standalone PB',
  3yr_high_PE_Consol float DEFAULT '0' COMMENT '3 Yr high TTM consolidated PE',
  3yr_high_PE_Stand float DEFAULT '0' COMMENT '3 Yr high TTM standalone PE',
  3yr_low_PE_Consol float DEFAULT '0' COMMENT '3 Yr low TTM consolidated PE',
  3yr_low_PE_Stand float DEFAULT '0' COMMENT '3 Yr low TTM standalone PE',
  3yr_high_PB_Consol float DEFAULT '0' COMMENT '3 Yr high TTM consolidated PB',
  3yr_high_PB_Stand float DEFAULT '0' COMMENT '3 Yr high TTM standalone PB',
  3yr_low_PB_Consol float DEFAULT '0' COMMENT '3 Yr low TTM consolidated PB',
  3yr_low_PB_Stand float DEFAULT '0' COMMENT '3 Yr low TTM standalone PB',
  PRIMARY KEY (company_ticker)
) COMMENT='Valuation Movement  	Processing	Dialy';


CREATE TABLE standardized_annual_resluts (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL COMMENT 'PK Financial Year Date',
  Net_Sales double DEFAULT NULL COMMENT 'Net Sales',
  Other_Op_Inc double DEFAULT NULL COMMENT 'Other Operating Income',
  Total_Inc double DEFAULT NULL COMMENT 'Total Income',
  Row_Material double DEFAULT NULL COMMENT 'Row Material',
  Traded_Goods double DEFAULT NULL COMMENT 'Traded Goods',
  Change_in_Stocks double DEFAULT NULL COMMENT 'Change in Stocks',
  Power_n_Fuel double DEFAULT NULL COMMENT 'Power & Fuel',
  Employees_Cost double DEFAULT NULL COMMENT 'Employees Cost',
  Excise_Duty double DEFAULT NULL COMMENT 'Excise Duty',
  Admin_And_Selling_Expenses double DEFAULT NULL COMMENT 'Admin & Selling Expenses',
  R_n_D_Expenses double DEFAULT NULL COMMENT 'R&D Expenses',
  Provisions_And_Contingencies double DEFAULT NULL COMMENT 'Provisions & Contingencies',
  Exp_Capitalized double DEFAULT NULL COMMENT 'Expense Capitalized',
  Other_Expenses double DEFAULT NULL COMMENT 'Other Expenses',
  Total_Expenses double DEFAULT NULL COMMENT 'Total Expenses',
  PBITDA double DEFAULT NULL COMMENT 'Profit before Interest, Tax, Depreciation & Other Income',
  Other_Inc double DEFAULT NULL COMMENT 'Other Non-Operating Income',
  Depreciation double DEFAULT NULL COMMENT 'Depreciation',
  PBIT double DEFAULT NULL COMMENT 'Profit before Interest and Taxes',
  Interest double DEFAULT NULL COMMENT 'Interest',
  PBT double DEFAULT NULL COMMENT 'Profit before Taxes',
  Exceptional_Items double DEFAULT NULL COMMENT 'Exceptional Items',
  Tax double DEFAULT NULL COMMENT 'Tax',
  PAT double DEFAULT NULL COMMENT 'Profit after Tax',
  Adjustments double DEFAULT NULL COMMENT 'Adjustments',
  Extra_Ordinary_Items double DEFAULT NULL COMMENT 'Extra Ordinary Items',
  Net_Profit_before_MI double DEFAULT NULL COMMENT 'Net Profit before Minority Interest & Profit from Associate',
  Minority_Interest double DEFAULT NULL COMMENT 'Minority Interest',
  Profit_from_Associate double DEFAULT NULL COMMENT 'Profit from Associate',
  Net_Profit double DEFAULT NULL COMMENT 'Net Profit',
  EPS double DEFAULT NULL COMMENT 'EPS',
  Dividend_Amt float DEFAULT NULL COMMENT 'Dividend Amount',
  Dividend_per_share float DEFAULT NULL COMMENT 'Dividend Paid Per Share',
  PRIMARY KEY (company_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT='Standardized Annualized Profit & Loss 	Processing	Annually';

CREATE TABLE standardized_annual_resluts_from_mc (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL COMMENT 'PK Financial Year Date',
  Net_Sales double DEFAULT NULL COMMENT 'Net Sales',
  Other_Op_Inc double DEFAULT NULL COMMENT 'Other Operating Income',
  Total_Inc double DEFAULT NULL COMMENT 'Total Income',
  Row_Material double DEFAULT NULL COMMENT 'Row Material',
  Traded_Goods double DEFAULT NULL COMMENT 'Traded Goods',
  Change_in_Stocks double DEFAULT NULL COMMENT 'Change in Stocks',
  Power_n_Fuel double DEFAULT NULL COMMENT 'Power & Fuel',
  Employees_Cost double DEFAULT NULL COMMENT 'Employees Cost',
  Excise_Duty double DEFAULT NULL COMMENT 'Excise Duty',
  Admin_And_Selling_Expenses double DEFAULT NULL COMMENT 'Admin & Selling Expenses',
  R_n_D_Expenses double DEFAULT NULL COMMENT 'R&D Expenses',
  Provisions_And_Contingencies double DEFAULT NULL COMMENT 'Provisions & Contingencies',
  Exp_Capitalized double DEFAULT NULL COMMENT 'Expense Capitalized',
  Other_Expenses double DEFAULT NULL COMMENT 'Other Expenses',
  Total_Expenses double DEFAULT NULL COMMENT 'Total Expenses',
  PBITDA double DEFAULT NULL COMMENT 'Profit before Interest, Tax, Depreciation & Other Income',
  Other_Inc double DEFAULT NULL COMMENT 'Other Non-Operating Income',
  Depreciation double DEFAULT NULL COMMENT 'Depreciation',
  PBIT double DEFAULT NULL COMMENT 'Profit before Interest and Taxes',
  Interest double DEFAULT NULL COMMENT 'Interest',
  PBT double DEFAULT NULL COMMENT 'Profit before Taxes',
  Exceptional_Items double DEFAULT NULL COMMENT 'Exceptional Items',
  Tax double DEFAULT NULL COMMENT 'Tax',
  PAT double DEFAULT NULL COMMENT 'Profit after Tax',
  Adjustments double DEFAULT NULL COMMENT 'Adjustments',
  Extra_Ordinary_Items double DEFAULT NULL COMMENT 'Extra Ordinary Items',
  Net_Profit_before_MI double DEFAULT NULL COMMENT 'Net Profit before Minority Interest & Profit from Associate',
  Minority_Interest double DEFAULT NULL COMMENT 'Minority Interest',
  Profit_from_Associate double DEFAULT NULL COMMENT 'Profit from Associate',
  Net_Profit double DEFAULT NULL COMMENT 'Net Profit',
  EPS double DEFAULT NULL COMMENT 'EPS',
  Dividend_Amt float DEFAULT NULL COMMENT 'Dividend Amount',
  Dividend_per_share float DEFAULT NULL COMMENT 'Dividend Paid Per Share',
  PRIMARY KEY (company_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT='Standardized Annualized Profit & Loss 	Processing	Annually';

CREATE TABLE standardized_balancesheet (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL COMMENT 'PK Financial Year Date',
  total_share_capital double DEFAULT '0' COMMENT 'Total Share Capital',
  total_reserves_and_surplus double DEFAULT '0' COMMENT 'Total Reserves and Surplus',
  total_shareholders_funds double DEFAULT '0' COMMENT 'Total Shareholders Funds',
  equity_share_application_money double DEFAULT '0' COMMENT 'Equity Share Applicaiton Money',
  minority_interest double DEFAULT '0' COMMENT 'Minority Interest',
  long_term_borrowings double DEFAULT '0' COMMENT 'Long Term Borrowings',
  deferred_tax_liabilities double DEFAULT '0' COMMENT 'Deferred Tax Liabilities',
  other_long_term_liabilities double DEFAULT '0' COMMENT 'Other Long Term Liabilities',
  long_term_provisions double DEFAULT '0' COMMENT 'Long Term Provisions',
  deposits double DEFAULT '0' COMMENT 'Deposits',
  borrowings double DEFAULT '0' COMMENT 'Borrowings',
  total_non_current_liabilities double DEFAULT '0' COMMENT 'Total Non-Current Liabilities',
  short_term_borrowings double DEFAULT '0' COMMENT 'Short Term Borrowings',
  trade_payables double DEFAULT '0' COMMENT 'Trade Payables',
  other_current_liabilities double DEFAULT '0' COMMENT 'Other Current Liabilities',
  short_term_provisions double DEFAULT '0' COMMENT 'Short Term Provisions',
  total_current_liabilities double DEFAULT '0' COMMENT 'Total Current Liabilities',
  total_liabilities double DEFAULT '0' COMMENT 'Total Liabilities',
  total_capital_and_liabilities double DEFAULT '0' COMMENT 'Total Capital And Liabilities',
  tangible_assets double DEFAULT '0' COMMENT 'Tangible Assets',
  intangible_assets double DEFAULT '0' COMMENT 'Intangible Assets',
  intangible_assets_under_development double DEFAULT '0',
  capital_work_in_progress double DEFAULT '0' COMMENT 'Capital Work-In-Progress',
  fixed_assets double DEFAULT '0' COMMENT 'Fixed Assets',
  non_current_investments double DEFAULT '0' COMMENT 'Non-Current Investments',
  deferred_tax_assets double DEFAULT '0' COMMENT 'Deferred Tax Assets',
  long_term_loans_and_advances double DEFAULT '0' COMMENT 'Long Term Loans And Advances',
  other_non_current_assets double DEFAULT '0' COMMENT 'Other Non-Current Assets',
  goodwill double DEFAULT '0' COMMENT 'Goodwill',
  total_non_current_assets double DEFAULT '0' COMMENT 'Total Non-Current Assets',
  current_investments double DEFAULT '0' COMMENT 'Current Investments',
  inventories double DEFAULT '0' COMMENT 'Inventories',
  trade_receivables double DEFAULT '0' COMMENT 'Trade Receivables',
  cash_and_cash_equivalents double DEFAULT '0' COMMENT 'Cash And Cash Equivalents',
  cash_and_blance_with_RBI double DEFAULT '0',
  money_at_call_and_short_notice double DEFAULT '0',
  short_term_loans_and_advances double DEFAULT '0' COMMENT 'Short Term Loans And Advances',
  other_current_assets double DEFAULT '0' COMMENT 'Other Current Assets',
  total_current_assets double DEFAULT '0' COMMENT 'Total Current Assets',
  total_assets double DEFAULT '0' COMMENT 'Total Assets',
  Face_value float DEFAULT '0' COMMENT 'Face Value',
  Adj_Equity_Shares_in_Cr double DEFAULT '0' COMMENT 'Adjusted Equity Shares in Crs at Yn = (Face Value Yn * Shares Outstanding Yn)/Face Value Y0 + Bonus Shares added from Yn to Y0',
  Price float DEFAULT '0' COMMENT 'Stock Price at Yn',
  market_cap_in_Cr double DEFAULT '0' COMMENT 'Market Capital in Cr at Yn = Adjusted Equity Shares Yn * Price Yn',
  PRIMARY KEY (company_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT='Standardized Annualized Balancesheet 	Processing	Annually';

CREATE TABLE standardized_balancesheet_from_mc (
  moneycontrol_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'PK MoneyControl Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL DEFAULT '0000-00-00' COMMENT 'PK Financial Year Date',
  total_share_capital double DEFAULT '0' COMMENT 'Total Share Capital',
  total_reserves_and_surplus double DEFAULT '0' COMMENT 'Total Reserves and Surplus',
  total_shareholders_funds double DEFAULT '0' COMMENT 'Total Shareholders Funds',
  equity_share_application_money double DEFAULT '0',
  minority_interest double DEFAULT '0' COMMENT 'Minority Interest',
  long_term_borrowings double DEFAULT '0' COMMENT 'Long Term Borrowings',
  deferred_tax_liabilities double DEFAULT '0' COMMENT 'Deferred Tax Liabilities',
  other_long_term_liabilities double DEFAULT '0' COMMENT 'Other Long Term Liabilities',
  long_term_provisions double DEFAULT '0' COMMENT 'Long Term Provisions',
  deposits double DEFAULT '0',
  borrowings double DEFAULT '0',
  total_non_current_liabilities double DEFAULT '0' COMMENT 'Total Non-Current Liabilities',
  short_term_borrowings double DEFAULT '0' COMMENT 'Short Term Borrowings',
  trade_payables double DEFAULT '0' COMMENT 'Trade Payables',
  other_current_liabilities double DEFAULT '0' COMMENT 'Other Current Liabilities',
  short_term_provisions double DEFAULT '0' COMMENT 'Short Term Provisions',
  total_current_liabilities double DEFAULT '0' COMMENT 'Total Current Liabilities',
  total_liabilities double DEFAULT '0' COMMENT 'Total Liabilities',
  total_capital_and_liabilities double DEFAULT '0' COMMENT 'Total Capital And Liabilities',
  tangible_assets double DEFAULT '0' COMMENT 'Tangible Assets',
  intangible_assets double DEFAULT '0' COMMENT 'Intangible Assets',
  intangible_assets_under_development double DEFAULT '0' COMMENT 'Intangible Assets Under Development',
  capital_work_in_progress double DEFAULT '0' COMMENT 'Capital Work-In-Progress',
  fixed_assets double DEFAULT '0' COMMENT 'Fixed Assets',
  non_current_investments double DEFAULT '0' COMMENT 'Non-Current Investments',
  deferred_tax_assets double DEFAULT '0' COMMENT 'Deferred Tax Assets',
  long_term_loans_and_advances double DEFAULT '0' COMMENT 'Long Term Loans And Advances',
  other_non_current_assets double DEFAULT '0' COMMENT 'Other Non-Current Assets',
  goodwill double DEFAULT '0' COMMENT 'Goodwill',
  total_non_current_assets double DEFAULT '0' COMMENT 'Total Non-Current Assets',
  current_investments double DEFAULT '0' COMMENT 'Current Investments',
  inventories double DEFAULT '0' COMMENT 'Inventories',
  trade_receivables double DEFAULT '0' COMMENT 'Trade Receivables',
  cash_and_cash_equivalents double DEFAULT '0' COMMENT 'Cash And Cash Equivalents',
  cash_and_blance_with_RBI double DEFAULT '0',
  money_at_call_and_short_notice double DEFAULT '0',
  short_term_loans_and_advances double DEFAULT '0' COMMENT 'Short Term Loans And Advances',
  other_current_assets double DEFAULT '0' COMMENT 'Other Current Assets',
  total_current_assets double DEFAULT '0' COMMENT 'Total Current Assets',
  total_assets double DEFAULT '0' COMMENT 'Total Assets',
  Face_value float DEFAULT '0' COMMENT 'Face Value',
  Adj_Equity_Shares_in_Cr double DEFAULT '0' COMMENT 'Adjusted Equity Shares in Crs at Yn = (Face Value Yn * Shares Outstanding Yn)/Face Value Y0 + Bonus Shares added from Yn to Y0',
  Price float DEFAULT '0' COMMENT 'Stock Price at Yn',
  market_cap_in_Cr double DEFAULT '0' COMMENT 'Market Capital in Cr at Yn = Adjusted Equity Shares Yn * Price Yn',
  PRIMARY KEY (moneycontrol_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT=' ';

CREATE TABLE standardized_cashflow (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL COMMENT 'PK Financial Year Date',
  cash_from_operating_activity double DEFAULT NULL COMMENT 'Cash from Operating Activity',
  cash_from_investing_activity double DEFAULT NULL COMMENT 'Cash from Investing Activity',
  cash_from_financing_activity double DEFAULT NULL COMMENT 'Cash from Financing Activity',
  net_cash_flow double DEFAULT NULL COMMENT 'Net Cash Flow',
  PRIMARY KEY (company_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT='Standardized Annualized Balancesheet 	Processing	Annually';

CREATE TABLE standardized_cashflow_from_mc (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  FIN_YEAR date NOT NULL COMMENT 'PK Financial Year Date',
  cash_from_operating_activity double DEFAULT NULL COMMENT 'Cash from Operating Activity',
  cash_from_investing_activity double DEFAULT NULL COMMENT 'Cash from Investing Activity',
  cash_from_financing_activity double DEFAULT NULL COMMENT 'Cash from Financing Activity',
  net_cash_flow double DEFAULT NULL COMMENT 'Net Cash Flow',
  PRIMARY KEY (company_ticker,consolidated_standalone,FIN_YEAR)
) COMMENT='Standardized Annualized Balancesheet 	Processing	Annually';

CREATE TABLE standardized_quarter_resluts (
  company_ticker varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK Company Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  Quarter date NOT NULL COMMENT 'PK Quarter Date',
  Net_Sales double DEFAULT NULL COMMENT 'Net Sales',
  Other_Op_Inc double DEFAULT NULL COMMENT 'Other Operating Income',
  Total_Inc double DEFAULT NULL COMMENT 'Total Income',
  Row_Material double DEFAULT NULL COMMENT 'Row Material',
  Traded_Goods double DEFAULT NULL COMMENT 'Traded Goods',
  Change_in_Stocks double DEFAULT NULL COMMENT 'Change in Stocks',
  Power_n_Fuel double DEFAULT NULL COMMENT 'Power & Fuel',
  Employees_Cost double DEFAULT NULL COMMENT 'Employees Cost',
  Excise_Duty double DEFAULT NULL COMMENT 'Excise Duty',
  Admin_And_Selling_Expenses double DEFAULT NULL COMMENT 'Admin & Selling Expenses',
  R_n_D_Expenses double DEFAULT NULL COMMENT 'R&D Expenses',
  Provisions_And_Contingencies double DEFAULT NULL COMMENT 'Provisions & Contingencies',
  Exp_Capitalized double DEFAULT NULL COMMENT 'Expense Capitalized',
  Other_Expenses double DEFAULT NULL COMMENT 'Other Expenses',
  Total_Expenses double DEFAULT NULL COMMENT 'Total Expenses',
  PBITDA double DEFAULT NULL COMMENT 'Profit before Interest, Tax, Depreciation & Other Income',
  Other_Inc double DEFAULT NULL COMMENT 'Other Non-Operating Income',
  Depreciation double DEFAULT NULL COMMENT 'Depreciation',
  PBIT double DEFAULT NULL COMMENT 'Profit before Interest and Taxes',
  Interest double DEFAULT NULL COMMENT 'Interest',
  PBT double DEFAULT NULL COMMENT 'Profit before Taxes',
  Exceptional_Items double DEFAULT NULL COMMENT 'Exceptional Items',
  Tax double DEFAULT NULL COMMENT 'Tax',
  PAT double DEFAULT NULL COMMENT 'Profit after Tax',
  Adjustments double DEFAULT NULL COMMENT 'Adjustments',
  Extra_Ordinary_Items double DEFAULT NULL COMMENT 'Extra Ordinary Items',
  Net_Profit_before_MI double DEFAULT NULL COMMENT 'Net Profit before Minority Interest & Profit from Associate',
  Minority_Interest double DEFAULT NULL COMMENT 'Minority Interest',
  Profit_from_Associate double DEFAULT NULL COMMENT 'Profit from Associate',
  Net_Profit double DEFAULT NULL COMMENT 'Net Profit',
  Quarterly_EPS float DEFAULT NULL COMMENT 'EPS',
  PRIMARY KEY (company_ticker,consolidated_standalone,Quarter)
) COMMENT='Standardized Quarterly Profit & Loss 	Processing	Quarterly';

CREATE TABLE standardized_quarter_resluts_from_mc (
  moneycontrol_ticker varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PK MoneyControl Ticker',
  company_short_name varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Company Name',
  consolidated_standalone varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'C' COMMENT 'PK C for consolidated, S for standalone',
  Quarter date NOT NULL COMMENT 'PK Quarter Date',
  Net_Sales double DEFAULT NULL COMMENT 'Net Sales',
  Other_Op_Inc double DEFAULT NULL COMMENT 'Other Operating Income',
  Total_Inc double DEFAULT NULL COMMENT 'Total Income',
  Row_Material double DEFAULT NULL COMMENT 'Row Material',
  Traded_Goods double DEFAULT NULL COMMENT 'Traded Goods',
  Change_in_Stocks double DEFAULT NULL COMMENT 'Change in Stocks',
  Power_n_Fuel double DEFAULT NULL COMMENT 'Power & Fuel',
  Employees_Cost double DEFAULT NULL COMMENT 'Employees Cost',
  Excise_Duty double DEFAULT NULL COMMENT 'Excise Duty',
  Admin_And_Selling_Expenses double DEFAULT NULL COMMENT 'Admin & Selling Expenses',
  R_n_D_Expenses double DEFAULT NULL COMMENT 'R&D Expenses',
  Provisions_And_Contingencies double DEFAULT NULL COMMENT 'Provisions & Contingencies',
  Exp_Capitalized double DEFAULT NULL COMMENT 'Expense Capitalized',
  Other_Expenses double DEFAULT NULL COMMENT 'Other Expenses',
  Total_Expenses double DEFAULT NULL COMMENT 'Total Expenses',
  PBITDA double DEFAULT NULL COMMENT 'Profit before Interest, Tax, Depreciation & Other Income',
  Other_Inc double DEFAULT NULL COMMENT 'Other Non-Operating Income',
  Depreciation double DEFAULT NULL COMMENT 'Depreciation',
  PBIT double DEFAULT NULL COMMENT 'Profit before Interest and Taxes',
  Interest double DEFAULT NULL COMMENT 'Interest',
  PBT double DEFAULT NULL COMMENT 'Profit before Taxes',
  Exceptional_Items double DEFAULT NULL COMMENT 'Exceptional Items',
  Tax double DEFAULT NULL COMMENT 'Tax',
  PAT double DEFAULT NULL COMMENT 'Profit after Tax',
  Adjustments double DEFAULT NULL COMMENT 'Adjustments',
  Extra_Ordinary_Items double DEFAULT NULL COMMENT 'Extra Ordinary Items',
  Net_Profit_before_MI double DEFAULT NULL COMMENT 'Net Profit before Minority Interest & Profit from Associate',
  Minority_Interest double DEFAULT NULL COMMENT 'Minority Interest',
  Profit_from_Associate double DEFAULT NULL COMMENT 'Profit from Associate',
  Net_Profit double DEFAULT NULL COMMENT 'Net Profit',
  Quarterly_EPS double DEFAULT NULL COMMENT 'EPS',
  PRIMARY KEY (moneycontrol_ticker,consolidated_standalone,Quarter)
) COMMENT='Standardized Quarterly Profit & Loss from Money Control 	Processing	Quarterly';
