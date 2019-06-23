insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 21, 1, 'XXGK', '表1-1 学校概况', 'TBL_XXGK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 21, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 22, 1, 'XQDZ', '表1-2 校区及地址', 'TBL_XQDZ', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 22, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 23, 1, 'XXXGDZDW', '表1-3 学校相关党政单位', 'TBL_XXXGDZDW', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 23, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 24, 1, 'XXJXKYDW', '表1-4 学校教学科研单位', 'TBL_XXJXKYDW', 'P', 'G', 'P', 'N', '(23)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 24, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 25, 1, 'XYHYSHHZ', '表1-10 校友会与社会合作', 'TBL_XYHYSHHZ', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 25, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 26, 1, 'ZYJBQK', '表1-5-1 专业基本情况', 'TBL_ZYJBQK', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 26, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 27, 1, 'ZYDLQKB', '表1-5-2 专业大类情况表', 'TBL_ZYDLQKB', 'P', 'G', 'P', 'Y', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 27, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 28, 1, 'JZGJBXX', '表1-6-1 教职工基本信息', 'TBL_JZGJBXX', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 28, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 29, 1, 'JZGQTXX', '表1-6-2 教职工其他信息', 'TBL_JZGQTXX', 'P', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 29, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 30, 1, 'WPJSJBXX', '表1-6-3 外聘教师基本信息', 'TBL_WPJSJBXX', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 30, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 31, 1, 'FSYYSZQK', '表1-6-4 附属医院师资情况', 'TBL_FSYYSZQK', 'P', 'G', 'P', 'Y', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 31, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 32, 1, 'BKSJBQK', '表1-7 本科生基本情况', 'TBL_BKSJBQK', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 32, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 33, 1, 'BKSYCS', '表1-8-1 本科实验场所', 'TBL_BKSYCS', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 33, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 34, 1, 'KYJD', '表1-8-2 科研基地', 'TBL_KYJD', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 34, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 35, 1, 'BXZDSX', '表1-9 办学指导思想', 'TBL_BXZDSX', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 35, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 36, 1, 'ZDYJZMJ', '表2-1 占地与建筑面积', 'TBL_ZDYJZMJ', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 36, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 37, 1, 'JXXZYFMJ', '表2-2 教学行政用房面积', 'TBL_JXXZYFMJ', 'P', 'G', 'P', 'N', '(36)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 37, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 38, 1, 'TSG', '表2-3-1 图书馆', 'TBL_TSG', 'N', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 38, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 39, 1, 'TSDNXZQK', '表2-3-2 图书当年新增情况', 'TBL_TSDNXZQK', 'N', 'G', 'P', 'N', '(38)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 39, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 40, 1, 'BKSYSBQK', '表2-7 本科实验设备情况', 'TBL_BKSYSBQK', 'P', 'G', 'P', 'N', '(33)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 40, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 41, 1, 'XWSXSXJD', '表2-4 校外实习、实训基地', 'TBL_XWSXSXJD', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 41, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 42, 1, 'XYW', '表2-5 校园网', 'TBL_XYW', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 42, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 43, 1, 'SYJXSFZX', '表2-8 实验教学示范中心（虚拟仿真实验教学中心）', 'TBL_SYJXSFZX', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 43, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 44, 1, 'GDZC', '表2-6 固定资产', 'TBL_GDZC', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 44, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 45, 1, 'JYJFGK', '表2-9-1 教育经费概况', 'TBL_JYJFGK', 'N', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 45, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 46, 1, 'JYJFSZQK', '表2-9-2 教育经费收支情况', 'TBL_JYJFSZQK', 'N', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 46, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 47, 1, 'XSSHYDTJ', '表2-10 学生生活、运动条件', 'TBL_XSSHYDTJ', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 47, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 48, 1, 'XLDJBXX', '表3-1 校领导基本信息', 'TBL_XLDJBXX', 'P', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 48, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 49, 1, 'GCCYJTD', '表3-3-2 高层次教学、研究团队', 'TBL_GCCYJTD', 'P', 'G', 'P', 'Y', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 49, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 50, 1, 'JSJXFZJG', '表3-4-1 教师教学发展机构', 'TBL_JSJXFZJG', 'S', 'G', 'P', 'Y', '(23)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 50, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 51, 1, 'JSPXJXJLQK', '表3-4-2 教师培训进修、交流情况', 'TBL_JSPXJXJLQK', 'S', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 51, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 52, 1, 'JSHDKYJLQK', '表3-5-2 教师获得科研奖励情况', 'TBL_JSHDKYJLQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 52, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 53, 1, 'JSFBLWS', '表3-5-3 教师发表的论文情况', 'TBL_JSFBLWS', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 53, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 54, 1, 'JSCBZZQK', '表3-5-4 教师出版著作情况', 'TBL_JSCBZZQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 54, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 55, 1, 'JSHZLSHQQK', '表3-5-5 教师专利（著作权）授权情况', 'TBL_JSHZLSHQQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 55, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 56, 1, 'XGGLRYJBXX', '表3-2 相关管理人员基本信息', 'TBL_XGGLRYJBXX', 'P', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 56, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 57, 1, 'JSZCKYXMQK', '表3-5-1 教师主持科研项目情况', 'TBL_JSZCKYXMQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 57, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 58, 1, 'JSZBBZYJCQK', '表3-5-6 教师主编本专业教材情况', 'TBL_JSZBBZYJCQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 58, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 59, 1, 'JSKYCGZHQK', '表3-5-7 教师科研成果转化情况', 'TBL_JSKYCGZHQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 59, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 60, 1, 'CXCYJSQK', '表3-6 创新创业教师情况', 'TBL_CXCYJSQK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 60, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 61, 1, 'XKJS', '表4-1-1 学科建设', 'TBL_XKJS', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 61, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 62, 1, 'BSHLDZ', '表4-1-2 博士后流动站', 'TBL_BSHLDZ', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 62, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 63, 1, 'BSDSSD', '表4-1-3 博士点硕士点', 'TBL_BSDSSD', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 63, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 64, 1, 'ZDXK', '表4-1-4 重点学科', 'TBL_ZDXK', 'P', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 64, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 65, 1, 'ZYPYJHB', '表4-2 专业培养计划表', 'TBL_ZYPYJHB', 'P', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 65, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 66, 1, 'YSZYQK', '表4-3 优势专业情况', 'TBL_YSZYQK', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 66, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 67, 1, 'KKQK', '表5-1-1 开课情况', 'TBL_KKQK', 'S', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 67, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 68, 1, 'ZYJXSSQKB', '表5-1-2 专业课教学实施情况', 'TBL_ZYJXSSQKB', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 68, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 69, 1, 'RCPYMSCXSYXM', '表5-3-1 人才培养模式创新实验项目', 'TBL_RCPYMSCXSYXM', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 69, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 70, 1, 'ZYHXKCQK', '表5-1-3 专业核心课程情况', 'TBL_ZYHXKCQK', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 70, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 71, 1, 'FZYDLZYSYKQK', '表5-1-4 分专业（大类） 专业实验课情况', 'TBL_FZYDLZYSYKQK', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 71, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 72, 1, 'YGKCQKB', '表5-1-5 有关课程情况表', 'TBL_YGKCQKB', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 72, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 73, 1, 'FZYBYZHXLQK', '表5-2-1 分专业毕业综合训练情况', 'TBL_FZYBYZHXLQK', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 73, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 74, 1, 'FZYJSZDXSBYZHXLQK', '表5-2-2 分专业教师指导学生毕业综合训练情况（非医学类专业填报）', 'TBL_FZYJSZDXSBYZHXLQK', 'S', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 74, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 75, 1, 'LCYXZYXSBYZHXLQK', '表5-2-3 医学专业学生毕业综合训练情况', 'TBL_LCYXZYXSBYZHXLQK', 'S', 'G', 'P', 'N', '(24)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 75, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 76, 1, 'BKJXXXHH', '表5-3-2 本科教学信息化', 'TBL_BKJXXXHH', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 76, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 77, 1, 'CXCYJYQK', '表5-4-1 创新创业教育情况', 'TBL_CXCYJYQK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 77, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 78, 1, 'GXSJYRCXCYJD', '表5-4-2 高校创新创业教育实践基地', 'TBL_GXSJYRCXCYJD', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 78, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 79, 1, 'CXCYZDJS', '表5-4-3 创新创业制度建设', 'TBL_CXCYZDJS', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 79, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 80, 1, 'KWHDJZ', '表5-5 课外活动、讲座', 'TBL_KWHDJZ', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 80, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 81, 1, 'XSSLJBQK', '表6-1 学生数量基本情况', 'TBL_XSSLJBQK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 81, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 82, 1, 'BKSJDB', '表6-4 本科生奖贷补', 'TBL_BKSJDB', 'N', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 82, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 83, 1, 'YJBKBYSJYQK', '表6-5-1 应届本科毕业生就业情况', 'TBL_YJBKBYSJYQK', 'S', 'G', 'P', 'N', '(84)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 83, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 84, 1, 'YJBKBYSFZYBYJY', '表6-5-2 应届本科毕业生分专业毕业就业情况', 'TBL_YJBKBYSFZYBYJY', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 84, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 85, 1, 'BKSZZYQK', '表6-2-1 本科生转专业情况', 'TBL_BKSZZYQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 85, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 86, 1, 'BKSFXSXWQK', '表6-2-2 本科生辅修、双学位情况', 'TBL_BKSFXSXWQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 86, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 87, 1, 'XSST', '表6-8 学生社团', 'TBL_XSST', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 87, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 88, 1, 'JYJBKZSLBQK', '表6-3-1 近一级本科生招生类别情况', 'TBL_JYJBKZSLBQK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 88, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 89, 1, 'BKSJWQK', '表6-3-2 本科生（境外） 情况', 'TBL_BKSJWQK', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 89, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 90, 1, 'YIJBKSLQBZRS', '表6-3-3 近一级本科生录取标准及人数', 'TBL_YIJBKSLQBZRS', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 90, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 91, 1, 'JYJGZYDLZSBDQK', '表6-3-4 近一级各专业（大类）招生报到情况', 'TBL_JYJGZYDLZSBDQK', 'P', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 91, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 92, 1, 'BKSXXCX', '表6-6 本科生学习成效', 'TBL_BKSXXCX', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 92, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 93, 1, 'XSCJDXSCXCYXLJHQK', '表6-6-1 学生参加大学生创新创业训练计划情况', 'TBL_XSCJDXSCXCYXLJHQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 93, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 94, 1, 'XSCYJSKYQK', '表6-6-2 学生参与教师科研项目情况', 'TBL_XSCYJSKYQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 94, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 95, 1, 'XSHDSJYSGLJSJLQK', '表6-6-3 学生获省级及以上各类竞赛奖励情况', 'TBL_XSHDSJYSGLJSJLQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 95, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 96, 1, 'XAHZYBSJLQKYSLZYY', '表6-6-4 学生获专业比赛奖励情况（艺术类专业用）', 'TBL_XAHZYBSJLQKYSLZYY', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 96, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 97, 1, 'XSHZYBSJLQK', '表6-6-5 学生获专业比赛奖励情况（体育类专业用）', 'TBL_XSHZYBSJLQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 97, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 98, 1, 'XSFBXSLWQK', '表6-6-6 学生发表学术论文情况', 'TBL_XSFBXSLWQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 98, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 99, 1, 'XSCZBYDDBXZP', '表6-6-7 学生创作、表演的代表性作品（除美术学类专业外的其他艺术类专业用）', 'TBL_XSCZBYDDBXZP', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 99, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 100, 1, 'XSZLZZQSQQK', '表6-6-8 学生专利（著作权） 授权情况', 'TBL_XSZLZZQSQQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 100, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 101, 1, 'BKSJLQK', '表6-7 本科生交流情况', 'TBL_BKSJLQK', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 101, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 102, 1, 'JXGLRYCG', '表7-1 教学管理人员成果', 'TBL_JXGLRYCG', 'P', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 102, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 103, 1, 'BKJXXXH', '表7-2 教学质量评估统计表', 'TBL_BKJXXXH', 'S', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 103, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 104, 1, 'JYJXYJYGGXM', '表7-3-1 教育教学研究与改革项目', 'TBL_JYJXYJYGGXM', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 104, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 105, 1, 'JXCGJ', '表7-3-2 教学成果奖（近一届）', 'TBL_JXCGJ', 'P', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 105, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 106, 1, 'BKJXZLNDBG', '表7-4 本科教学质量年度报告', 'TBL_BKJXZLNDBG', 'N', 'G', 'P', 'N', '', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 106, 'U', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 107, 1, 'SJJYSBKJXGCXMQK', '表7-3-3 省级及以上本科教学工程项目情况', 'TBL_SJJYSBKJXGCXMQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 107, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 108, 1, 'XSFZCZZDJSQK', '表SF-1：学生发展成长指导教师情况', 'TBL_XSFZCZZDJSQK', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 108, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 109, 1, 'SFJNLKC', '表SF-10：师范技能类课程', 'TBL_SFJNLKC', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 109, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 110, 1, 'JYSJQK', '表SF-11：教育实践情况', 'TBL_JYSJQK', 'S', 'G', 'P', 'N', '(26),(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 110, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 111, 1, 'SFLZYFBKXSSLJBQK', '表SF-12：师范类专业非本科学生数量基本情况', 'TBL_SFLZYFBKXSSLJBQK', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 111, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 112, 1, 'SFJNJSJLQK', '表SF-13：师范技能竞赛奖励情况', 'TBL_SFJNJSJLQK', 'S', 'G', 'P', 'N', '(32)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 112, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 113, 1, 'SFLZYYJBYSQK', '表SF-14：师范类专业应届毕业生情况', 'TBL_SFLZYYJBYSQK', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 113, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 114, 1, 'JSJYLYJYGGXMQK', '表SF-2：教师教育类研究与改革项目情况', 'TBL_JSJYLYJYGGXMQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 114, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 115, 1, 'JSZCJCJYLYHXYJXMQK', '表SF-3：教师主持基础教育领域横向研究项目情况', 'TBL_JSZCJCJYLYHXYJXMQK', 'N', 'G', 'P', 'N', '(26),(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 115, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 116, 1, 'JSZBJCJYKCJCQK', '表SF-4：教师主编基础教育课程教材情况', 'TBL_JSZBJCJYKCJCQK', 'N', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 116, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 117, 1, 'JSJWNJCJYFWJL', '表SF-5：教师近五年基础教育服务经历', 'TBL_JSJWNJCJYFWJL', 'S', 'G', 'P', 'N', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 117, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 118, 1, 'SFLZYBXJBTJ', '表SF-6：师范类专业办学基本条件', 'TBL_SFLZYBXJBTJ', 'N', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 118, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 119, 1, 'SFLZYJXSS', '表SF-7：师范类专业教学设施', 'TBL_SFLZYJXSS', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 119, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 120, 1, 'SFLZYPYQK', '表SF-8：师范类专业培养情况', 'TBL_SFLZYPYQK', 'P', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 120, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 121, 1, 'JSJYKCQKB', '表SF-9：教师教育课程情况表', 'TBL_JSJYKCQKB', 'S', 'G', 'P', 'N', '(26)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 121, 'C', NOW());

insert into education.tbl_form( id, user_id, tbl_name, bus_name, phsic_name, stats_time, display_method, form_type, is_null, dependency_form, create_time ) values ( 122, 1, 'GCCRC', '表3-3-1 高层次人才', 'TBL_GCCRC', 'N', 'G', 'P', 'Y', '(28)', NOW());
INSERT INTO education.tbl_datacol( user_id, form_id, status, update_time) VALUES ( 1, 122, 'C', NOW());