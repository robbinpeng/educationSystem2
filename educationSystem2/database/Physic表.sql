-- ��1-1 ѧУ�ſ�

  CREATE TABLE TBL_XXGK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	XXMC varchar(100), 
	XXDM varchar(100), 
	YWMC varchar(200), 
	BXLX varchar(20), 
	XXXZ varchar(20), 
	JBZ varchar(50), 
	ZGBM varchar(50), 
	XXWZ varchar(200), 
	ZSPC varchar(500), 
	KBBKJYNF varchar(4), 
	TJSJ varchar(100), 
	TBFZRXM varchar(50), 
	TBFZRLXDH varchar(50), 
	TBFZRLXDZYX varchar(100), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-10 У�ѻ���������

  CREATE TABLE TBL_XYHYSHHZ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XYHZS varchar(255), 
	XYHJNS varchar(255), 
	XYHJWS varchar(255), 
	QDHZXYJGZS varchar(255), 
	QDHZXYXSJGS varchar(255), 
	QDHZXYHYJGHQYS varchar(255), 
	QDHZXYDFZFS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-2 У������ַ

  CREATE TABLE TBL_XQDZ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XQMC varchar(100), 
	DZ varchar(200), 
	SSZZQ varchar(10), 
	SQZ varchar(200), 
	SFBDXQ varchar(20), 
	TASK_VER_ bigint,
	PRIMARY KEY (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-3 ѧУ��ص�����λ

  CREATE TABLE TBL_XXXGDZDW 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	DZDWMC varchar(200), 
	DWH varchar(20), 
	DWZN varchar(20), 
	DWFZR varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-4 ѧУ��ѧ���е�λ

  CREATE TABLE TBL_XXJXKYDW 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JXKYDWMC varchar(200), 
	DWH varchar(20), 
	DWFZR varchar(50), 
	SFKH varchar(100), 
	DWZN varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-5-1 רҵ�������

  CREATE TABLE TBL_ZYJBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYDM varchar(20), 
	XNZYMC varchar(100), 
	ZYMC varchar(50), 
	ZYDM varchar(20), 
	DMBB varchar(10), 
	SSDWMC varchar(200), 
	SSDWH varchar(10), 
	ZYSZNF varchar(4), 
	YSZYLX varchar(50), 
	XZ varchar(255), 
	YXXYNX varchar(255), 
	SYXWML varchar(20), 
	ZSZT varchar(20), 
	SFXZY varchar(20), 
	SPSFZY varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-5-2 רҵ���������

  CREATE TABLE TBL_ZYDLQKB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	DLMC varchar(50), 
	DLDM varchar(10), 
	FLSJ varchar(255), 
	SSDWH varchar(20), 
	BHXNZYDM varchar(20), 
	BHXNZYMC varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-6-1 ��ְ��������Ϣ

  CREATE TABLE TBL_JZGJBXX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	XM varchar(50), 
	XB varchar(10), 
	CSNY varchar(20), 
	RXSJ varchar(50), 
	RZZT varchar(20), 
	DWH varchar(20), 
	DWMC varchar(200), 
	XL varchar(20), 
	ZGXW varchar(20), 
	XY varchar(20), 
	ZYJSZC varchar(20), 
	XKLB varchar(100), 
	RJLX varchar(20), 
	RJZYMC varchar(50), 
	RJZYDM varchar(20), 
	ZYRJSJ varchar(30), 
	SFSYJSRY varchar(20), 
	SFSSX varchar(20), 
	SFGCBJ varchar(20), 
	SFHYBJ varchar(20), 
	DSLB varchar(20), 
	XNZDBSSS varchar(255), 
	XNZDSSSS varchar(255), 
	TASK_VER_ bigint, 
	ZZMM varchar(100), 
	MZ varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-6-2 ��ְ��������Ϣ

  CREATE TABLE TBL_JZGQTXX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	GH varchar(100), 
	TJSJ varchar(50), 
	XM varchar(100), 
	RJLX varchar(100), 
	RJZYMC varchar(100), 
	RJZYDM varchar(100), 
	ZYRJSJ varchar(100), 
	SFSYJSRY varchar(100), 
	SFSSSNX varchar(100), 
	SFGCBJ varchar(100), 
	SFHYBJ varchar(100), 
	DSLB varchar(100), 
	XNZDBSSS varchar(255), 
	XNZDSSSS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-6-3 ��Ƹ��ʦ������Ϣ

  CREATE TABLE TBL_WPJSJBXX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	XM varchar(50), 
	XB varchar(10), 
	CSNY varchar(50), 
	PRSJ varchar(50), 
	RZZT varchar(20), 
	PQ varchar(255), 
	DWH varchar(20), 
	DWMC varchar(200), 
	XL varchar(20), 
	ZGXW varchar(20), 
	ZYJSZC varchar(20), 
	GZDWLB varchar(50), 
	CDBKJXRW varchar(1000), 
	DSLB varchar(20), 
	DQ varchar(100), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-6-4 ����ҽԺʦ�����

  CREATE TABLE TBL_FSYYSZQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	GH varchar(20), 
	TJSJ varchar(20), 
	XM varchar(50), 
	XB varchar(10), 
	CSNY varchar(20), 
	RZSJ varchar(20), 
	RZZT varchar(20), 
	SWH varchar(20), 
	SWMC varchar(200), 
	XL varchar(20), 
	ZGXW varchar(20), 
	ZYJZZC varchar(20), 
	DSLB varchar(20), 
	XNZDBSSS varchar(255), 
	XNZDSSSS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-7 �������������

  CREATE TABLE TBL_BKSJBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(200), 
	XB varchar(10), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	SYLB varchar(20), 
	NJ varchar(10), 
	RXNF varchar(10), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-8-1 ����ʵ�鳡��

  CREATE TABLE TBL_BKSYCS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(20), 
	SYCSDM varchar(20), 
	SYCSMC varchar(500), 
	SZSWMC varchar(200), 
	SZSWH varchar(20), 
	XZ varchar(20), 
	GJQK varchar(20), 
	SYMJPFM bigint, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-8-2 ���л���

  CREATE TABLE TBL_KYJD 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(20), 
	KYJDMC varchar(500), 
	SZSWMC varchar(200), 
	SZSWH varchar(20), 
	KYJDLB varchar(50), 
	GJQK varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��1-9 ��ѧָ��˼��

  CREATE TABLE TBL_BXZDSX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(50), 
	XX varchar(200), 
	DWYFZMB varchar(2000), 
	FZZLGH varchar(500), 
	FZZLGH_URL varchar(500), 
	FZZLGHZDSJ varchar(10), 
	XKJSGH varchar(500), 
	XKJSGH_URL varchar(500), 
	XKJSGHZDSJ varchar(10), 
	ZYJSFZGH varchar(500), 
	ZYJSFZGH_URL varchar(500), 
	ZYJSFZGHZDSJ varchar(10), 
	SZDWJSGH varchar(500), 
	SZDWJSGH_URL varchar(500), 
	SZDWJSGHZDSJ varchar(10), 
	XXBXMB varchar(2000), 
	XXRCPYDW varchar(2000), 
	XXGH varchar(500), 
	XXGH_URL varchar(500), 
	XXGHZDSJ varchar(10), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-1 ռ���뽨�����

  CREATE TABLE TBL_ZDYJZMJ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZZDMJ bigint, 
	XXCQZDMJ bigint, 
	XXCQLHYDZDMJ bigint, 
	FXXCQZDMJ bigint, 
	FXXCQDLSYZDMJ bigint, 
	FXXCQGTSYZDMJ bigint, 
	FXXCQLHYDZDMJ bigint, 
	ZJZMJ bigint, 
	XXCQJZMJ bigint, 
	FXXCQJZMJ bigint, 
	FXXCQDLSYJZMJ bigint, 
	FXXCQGTSYJZMJ bigint, 
	XXCQYDCDZDMJ bigint, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-10 ѧ������˶�����

  CREATE TABLE TBL_XSSHYDTJ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XSSTMJ bigint, 
	XSSTSL varchar(255), 
	XSSSMJ bigint, 
	XSSSSL varchar(255), 
	YDCMJ bigint, 
	YDCSL varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-2 ��ѧ�����÷����

  CREATE TABLE TBL_JXXZYFMJ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JXKYJFZYF bigint, 
	JS bigint, 
	SYSSYCS bigint, 
	ZYKYYF bigint, 
	TYG bigint, 
	HT bigint, 
	XZYF bigint, 
	TSG varchar(10), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-3-1 ͼ���

  CREATE TABLE TBL_TSG 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	SL varchar(255), 
	YLSZWS varchar(255), 
	ZZTSZL varchar(255), 
	ZZQKSL varchar(255), 
	ZZQKZL varchar(255), 
	SZZYLDZTS varchar(255), 
	SJKSL varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-3-2 ͼ�鵱���������

  CREATE TABLE TBL_TSDNXZQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	DNXZZZTS varchar(255), 
	DNXZDZTS varchar(255), 
	DNWXGZF bigint, 
	DNTSLTL varchar(255), 
	DNDZZYFWL varchar(255), 
	DNXZSJK varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-4 У��ʵϰ��ʵѵ����

  CREATE TABLE TBL_XWSXSXJD 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JLSJ varchar(20), 
	JDMC varchar(200), 
	YXH varchar(20), 
	YXMC varchar(200), 
	MXXNZY varchar(500), 
	XNZYDM varchar(500), 
	MCKJNXXS varchar(255), 
	DNJNXXZS varchar(255), 
	DZ varchar(2000), 
	TASK_VER_ bigint, 
	SFSCYSXJD varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-5 У԰��

  CREATE TABLE TBL_XYW 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	WLJRXXDSL varchar(255), 
	DZYJXTYHS varchar(255), 
	XXHGZRYS varchar(255), 
	TASK_VER_ bigint, 
	GLXXXTSJZL bigint, 
	XYWCKDK bigint, 
	XYWZGDK bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-6 �̶��ʲ�

  CREATE TABLE TBL_GDZC 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GDZCZZ bigint, 
	JXKYYQSBZCZZ bigint, 
	JXKYYQSBZCDNXZZ bigint, 
	XXHSBZCZZ bigint, 
	XXHSBRJZC bigint, 
	TASK_VER_ BIGINT,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-7 ����ʵ���豸���

  CREATE TABLE TBL_BKSYSBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	SYCSDM varchar(50), 
	SYCSMC varchar(200), 
	ZYJXSYYQSBM varchar(200), 
	ZYJXSYYQSBBM varchar(2000), 
	TTS varchar(255), 
	DJ bigint, 
	CGND varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-8 ʵ���ѧʾ�����ģ��������ʵ���ѧ���ģ�

  CREATE TABLE TBL_SYJXSFZX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZXMC varchar(100), 
	XKMC varchar(1000), 
	XKDM varchar(1000), 
	JB varchar(20), 
	SLSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-9-1 �������Ѹſ�

  CREATE TABLE TBL_JYJFGK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XXJYJFZE bigint, 
	JXJFZE bigint, 
	JXGGYJSZXJFZE bigint, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��2-9-2 ����������֧���

  CREATE TABLE TBL_JYJFSZQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JXJFZCZJ bigint, 
	JXRCYXZC bigint, 
	JXGGZC bigint, 
	ZYJSZC bigint, 
	SJJXZC bigint, 
	SJJXSYJFZC bigint, 
	SJJXSXJFZC bigint, 
	QTJXZXZC bigint, 
	XSHDJFZC bigint, 
	JSPXJXZXJFZC bigint, 
	BKSSJGJBKZE bigint, 
	BKSSJDFBKZE bigint, 
	ZKSSJBKZE bigint, 
	BKSXFSR bigint, 
	GZGZXFSR bigint, 
	JGZXBK bigint, 
	SHJZJE bigint, 
	JCXYSNSYFSR bigint, 
	JGZXDFBK bigint, 
	TASK_VER_ bigint, 
	SZZZLLKCZXJSJFZC bigint, 
	XYJZJE bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-1 У�쵼������Ϣ

  CREATE TABLE TBL_XLDJBXX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XM varchar(50), 
	GH varchar(20), 
	ZW varchar(500), 
	RXSJ varchar(20), 
	XNFGGZ varchar(2000), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-2 ��ع�����Ա������Ϣ

  CREATE TABLE TBL_XGGLRYJBXX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	XM varchar(50), 
	GLRYLB varchar(20), 
	DWH varchar(20), 
	DWMC varchar(200), 
	ZW varchar(500), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-3-1 �߲���˲�

  CREATE TABLE TBL_GCCRC 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XM varchar(50), 
	GH varchar(20), 
	LX varchar(100), 
	YJFX varchar(2000), 
	HDSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-3-2 �߲�ν�ѧ���о��Ŷ�

  CREATE TABLE TBL_GCCYJTD 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	TDMC varchar(100), 
	FZR varchar(50), 
	FZRGH varchar(20), 
	LX varchar(100), 
	HDSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-4-1 ��ʦ��ѧ��չ����

  CREATE TABLE TBL_JSJXFZJG 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JGMC varchar(100), 
	DWH varchar(20), 
	PXLX varchar(200), 
	PXCS varchar(255), 
	PXRC varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-4-2 ��ʦ��ѵ���ޡ��������

  CREATE TABLE TBL_JSPXJXJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	PXJXJLLX varchar(20), 
	KSSJ varchar(20), 
	JSSJ varchar(20), 
	BZ varchar(1000), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-1 ��ʦ���ֿ�����Ŀ���

  CREATE TABLE TBL_JSZCKYXMQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	XMMC varchar(200), 
	XMXZ varchar(20), 
	ZXXMLB varchar(50), 
	LXDWPX varchar(10), 
	GNXMJF bigint, 
	GJXMJF bigint, 
	LXSJ varchar(20), 
	LXBH varchar(50), 
	JTYSHJDSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-2 ��ʦ��ÿ��н������

  CREATE TABLE TBL_JSHDKYJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	CGMC varchar(200), 
	HJDWPM varchar(10), 
	HJRPM varchar(10), 
	HJLB varchar(50), 
	HJDJ varchar(10), 
	HJSJ varchar(20), 
	HJZSBH varchar(200), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-3 ��ʦ������������

  CREATE TABLE TBL_JSFBLWS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	ZZLB varchar(20), 
	LWMC varchar(1000), 
	LWLB varchar(20), 
	FBQK varchar(200), 
	SLQK varchar(100), 
	FBSJ varchar(20), 
	TYCS varchar(255), 
	SFYHYLHFB varchar(10), 
	SFYDFLHFB varchar(10), 
	SFYGJLHFB varchar(10), 
	SFSKXKLW varchar(10), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-4 ��ʦ�����������

  CREATE TABLE TBL_JSCBZZQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	ZZMC varchar(200), 
	ISBN varchar(100), 
	ZZLB varchar(20), 
	CBS varchar(100), 
	CBSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-5 ��ʦר��������Ȩ����Ȩ���

  CREATE TABLE TBL_JSHZLSHQQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	MC varchar(200), 
	LX varchar(20), 
	SQH varchar(20), 
	HPSJ varchar(20), 
	SFYY varchar(10), 
	SFHYLHZL varchar(10), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-6 ��ʦ���౾רҵ�̲����

  CREATE TABLE TBL_JSZBBZYJCQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	GH varchar(20), 
	JSXM varchar(50), 
	JCMC varchar(100), 
	CBS varchar(100), 
	ISBN varchar(200), 
	CBSJ varchar(20), 
	JCRXQK varchar(50), 
	RXSJ varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-5-7 ��ʦ���гɹ�ת�����

  CREATE TABLE TBL_JSKYCGZHQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	GH varchar(100), 
	JSXM varchar(100), 
	XMCGMC varchar(100), 
	ZHFS varchar(100), 
	ZHJEWY bigint, 
	CYXSS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��3-6 ���´�ҵ��ʦ���

  CREATE TABLE TBL_CXCYJSQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	CXCYJYZZJS varchar(100), 
	JYZDZZJS varchar(100), 
	CXCYJZDSS varchar(100), 
	ZZJSCXCYZXPXCC varchar(100), 
	ZZJSCXCYZXPXCXJSRC varchar(100), 
	ZZJSCXCYZXPXXWJSCXRC varchar(100), 
	ZYJSDHYQYGZDLDWS varchar(100), 
	ZYJSDHYQYGZDLRS varchar(100), 
	BXJSJZCYRS varchar(100), 
	BXJSLGCYRS varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-1-1 ѧ�ƽ���

  CREATE TABLE TBL_XKJS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	BSHLDZ varchar(255), 
	BSXWSQYJXKD varchar(255), 
	BSXWSQEJXKD varchar(255), 
	SSXWSQYJXKD varchar(255), 
	SSXWSQEJXKD varchar(255), 
	BKZYZS varchar(255), 
	BKXZYS varchar(255), 
	ZKZY varchar(255), 
	BSZYXWSQLB varchar(255), 
	SSZYXWSQLB varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-1-2 ��ʿ������վ

  CREATE TABLE TBL_BSHLDZ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	DWH varchar(20), 
	DWMC varchar(200), 
	BSHLDZMC varchar(200), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-1-3 ��ʿ��˶ʿ��

  CREATE TABLE TBL_BSDSSD 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	MC varchar(100), 
	XKDM varchar(20), 
	DWMC varchar(200), 
	DWH varchar(20), 
	LX varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-1-4 �ص�ѧ��

  CREATE TABLE TBL_ZDXK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZDXKMC varchar(200), 
	XKDM varchar(20), 
	SSDW varchar(2000), 
	DWH varchar(2000), 
	XKML varchar(20), 
	JB varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-2 רҵ�����ƻ���

  CREATE TABLE TBL_ZYPYJHB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYDM varchar(20), 
	XNZYMC varchar(50), 
	PYMB CLOB, 
	ZYYQ CLOB, 
	XSZS bigint, 
	BXKXS bigint, 
	XXKXS bigint, 
	KNJXXS bigint, 
	SYJXXS bigint, 
	ZXFS bigint, 
	BXKXF bigint, 
	XXKXF bigint, 
	JZXSJJXHJXF bigint, 
	KNJXJXF bigint, 
	SYJXXF bigint, 
	KWKJHDXF bigint, 
	CXCYJYXF bigint, 
	ZYDTRGH varchar(20), 
	ZYDTRXM varchar(50), 
	ZYRCPYTS varchar(2000), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��4-3 ����רҵ���

  CREATE TABLE TBL_YSZYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDLDM varchar(100), 
	XNZYDLMC varchar(100), 
	YSZYLX varchar(100), 
	YSZYHPSJ varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-1-1 �������

  CREATE TABLE TBL_KKQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	KKH varchar(50), 
	KCMC varchar(100), 
	KCH varchar(20), 
	KCXZ varchar(50), 
	KCLB varchar(20), 
	KHFS varchar(20), 
	XS varchar(255), 
	KKDW varchar(200), 
	DWH varchar(20), 
	SKJS varchar(2000), 
	SKJSGH varchar(2000), 
	BKXSS varchar(255), 
	JCSYQK varchar(20), 
	JCLX varchar(20), 
	SKFS varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-1-2 רҵ�ν�ѧʵʩ���

  CREATE TABLE TBL_ZYJXSSQKB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	KKH varchar(50), 
	KCXZ varchar(20), 
	XF bigint, 
	NJ varchar(500), 
	KCMC varchar(100), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-1-3 רҵ���Ŀγ����

  CREATE TABLE TBL_ZYHXKCQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	KCH varchar(20), 
	KCLB varchar(20), 
	KCXZ varchar(50), 
	KCSZZSYQ varchar(2000), 
	KCSZNLYQ varchar(2000), 
	KCDCMB varchar(2000), 
	JBPFKC varchar(50), 
	XF bigint, 
	ZXS bigint, 
	LLJXXS bigint, 
	SJJXXS bigint, 
	SYJXXS bigint, 
	TASK_VER_ bigint, 
	KCMC varchar(50),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-1-4 ��רҵ�����ࣩ רҵʵ������

  CREATE TABLE TBL_FZYDLZYSYKQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	KCH varchar(20), 
	KCMC varchar(100), 
	SYSYCSMC varchar(100), 
	SYCSDM varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-1-5 �йؿγ������

  CREATE TABLE TBL_YGKCQKB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	KCH varchar(100), 
	KCMC varchar(100), 
	KCLB varchar(100), 
	KCLX varchar(100), 
	XF varchar(100), 
	XS varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-2-1 ��רҵ��ҵ�ۺ�ѵ�����

  CREATE TABLE TBL_FZYBYZHXLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	BYZHXLKTZS varchar(255), 
	SHSJZWCS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-2-2 ��רҵ��ʦָ��ѧ����ҵ�ۺ�ѵ���������ҽѧ��רҵ���

  CREATE TABLE TBL_FZYJSZDXSBYZHXLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYMC varchar(50), 
	XNZYDM varchar(20), 
	JSXM varchar(50), 
	GH varchar(20), 
	ZDZYXSSL varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-2-3 ҽѧרҵѧ����ҵ�ۺ�ѵ�����

  CREATE TABLE TBL_LCYXZYXSBYZHXLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	YYDWH varchar(20), 
	YYMC varchar(200), 
	CWS varchar(255), 
	DNJSBYSXXSS varchar(255), 
	TASK_VER_ bigint, 
	YYJB varchar(50), 
	SZCS varchar(50),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-3-1 �˲�����ģʽ����ʵ����Ŀ

  CREATE TABLE TBL_RCPYMSCXSYXM 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	MC varchar(100), 
	LX varchar(50), 
	SLSJ varchar(20), 
	CYRS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-3-2 ���ƽ�ѧ��Ϣ��

  CREATE TABLE TBL_BKJXXXHH 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	KCMC varchar(50), 
	KCH varchar(20), 
	XMLX varchar(50), 
	XMJB varchar(20), 
	TASK_VER_ bigint, 
	JSFS varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-4-1 ���´�ҵ�������

  CREATE TABLE TBL_CXCYJYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JYJG varchar(255), 
	ZZJS varchar(255), 
	DS varchar(255), 
	PXRCS varchar(255), 
	JDS varchar(255), 
	SJPTS varchar(255), 
	SFJDS varchar(255), 
	XMS varchar(255), 
	KC varchar(255), 
	YZKC varchar(255), 
	ZDKCS varchar(255), 
	JZ varchar(255), 
	JXJ bigint, 
	CXCYZXZJTRWY bigint, 
	CXCYJYJCSM varchar(255), 
	CYCXCYXLXMBKZXXSSR varchar(255), 
	CYCXCYJSQRZBKZXXSSR varchar(255), 
	TASK_VER_ bigint, 
	SFCLCXCYJYGZLDXZ varchar(100), 
	SFKSCXCYXY varchar(100), 
	CXCYJYGZQTSW varchar(100), 
	CXCYJYMBYQXD varchar(100), 
	ZXXSCYXMSX varchar(255), 
	ZXXSCYXMCYXSSR varchar(255), 
	ZXXSCYXMHDZZJEWY bigint, 
	XSXXCYXMSX varchar(255), 
	XSXXCYXMCYXSRSR varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-4-2 ��У���´�ҵ����ʵ������

  CREATE TABLE TBL_GXSJYRCXCYJD 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(20), 
	JDMC varchar(200), 
	JDJB varchar(20), 
	JDLX varchar(50), 
	SYJS varchar(20), 
	PZNF varchar(20), 
	TASK_VER_ bigint, 
	JSHJ varchar(100), 
	TRJFWY bigint, 
	JFLY varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-4-3 ���´�ҵ�ƶȽ���

  CREATE TABLE TBL_CXCYZDJS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XFJLYZHSFZDSS varchar(100), 
	XFJLYZHNR varchar(100), 
	XFJLYZHNR_URL varchar(500), 
	GZJXKHSFZDSS varchar(100), 
	GZJXKHNR varchar(100), 
	GZJXKHNR_URL varchar(500), 
	JZDSGLSFZDSS varchar(100), 
	JZDSGLNR varchar(100), 
	JZDSGLNR_URL varchar(500), 
	XSGLXGSFZDSS varchar(100), 
	XSGLXGNR varchar(100), 
	XSGLXGNR_URL varchar(500), 
	JZLGCXCYXGSFZDSS varchar(100), 
	JZLGCXCYXGNR varchar(100), 
	JZLGCXCYXGNR_URL varchar(500),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��5-5 ����������

  CREATE TABLE TBL_KWHDJZ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JZZS varchar(255), 
	HDXMZS varchar(255), 
	GJCXCYXLJHXM varchar(255), 
	SBJCXCYXLJHXM varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-1 ѧ�������������

  CREATE TABLE TBL_XSSLJBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	PTBKXSS varchar(255), 
	LHPYXSS varchar(255), 
	PTGZXSS varchar(255), 
	SSYJSS varchar(255), 
	QRZSSYJSS varchar(255), 
	FQRZSSYJSS varchar(255), 
	BSYJSS varchar(255), 
	QRZBSYJSS varchar(255), 
	FQRZBSYJSS varchar(255), 
	LXSS varchar(255), 
	PTYKSS varchar(255), 
	JXSS varchar(255), 
	CRTCXSS varchar(255), 
	YDXSS varchar(255), 
	HSXSS varchar(255), 
	WLXSS varchar(255), 
	ZKXSS varchar(255), 
	TASK_VER_ bigint, 
	SSMZYJSXSSR varchar(255), 
	SSMZBKSXSSR varchar(255), 
	SSMZZKSXSSR varchar(255), 
	SYBSXWDLXSS varchar(255), 
	SSMZXSSR varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-2-1 ������תרҵ���

  CREATE TABLE TBL_BKSZZYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(20), 
	ZCXNZYDM varchar(20), 
	ZCXNZYMC varchar(50), 
	ZRXNZYDM varchar(20), 
	ZRXNZYMC varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-2-2 ���������ޡ�˫ѧλ���

  CREATE TABLE TBL_BKSFXSXWQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	XXLX varchar(20), 
	FXXNZYDM varchar(20), 
	FXXNZYMC varchar(50), 
	TASK_VER_ BIGINT,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-3-1 ��һ������������������

  CREATE TABLE TBL_JYJBKZSLBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZSJHS varchar(255), 
	SJLQS varchar(255), 
	SJBDS varchar(255), 
	ZZZSS varchar(255), 
	ZSTCSS varchar(255), 
	ZSBSXSS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-3-2 �����������⣩ ���

  CREATE TABLE TBL_BKSJWQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	BKSJWZSS varchar(255), 
	BKSJWZXSS varchar(255), 
	BKSJWBYSS varchar(255), 
	BKSJWSYXWS varchar(255), 
	BKSGWZSS varchar(255), 
	BKSGWZXSS varchar(255), 
	BKSGWBYSS varchar(255), 
	BKSGWSYXWS varchar(255), 
	BKSXGZSS varchar(255), 
	BKSXGZXSS varchar(255), 
	BKSXGBYSS varchar(255), 
	BKSXGSYXWS varchar(255), 
	BKSAMZSS varchar(255), 
	BKSAMZXSS varchar(255), 
	BKSAMBYSS varchar(255), 
	BKSAMSYXWS varchar(255), 
	BKSTWZSS varchar(255), 
	BKSTWZXSS varchar(255), 
	BKSTWBYSS varchar(255), 
	BKSTWSYXWS varchar(255), 
	BKSHQZSS bigint, 
	BKSHQZXSS bigint, 
	BKSHQBJYSS bigint, 
	BKSHQSYXWS bigint, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-3-3 ��һ��������¼ȡ��׼������

  CREATE TABLE TBL_YIJBKSLQBZRS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	SF varchar(50), 
	PC varchar(50), 
	WKLQRS varchar(255), 
	LKLQRS varchar(255), 
	WKPCZDKZX bigint, 
	LKPCZDKZX bigint, 
	BFWLKPCZDKZX bigint, 
	WKDNLQPJFS bigint, 
	LKDNLQPJFS bigint, 
	BFWLKDNLQPJFS bigint, 
	SM varchar(2000), 
	BFWLLQS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-3-4 ��һ����רҵ�����ࣩ�����������

  CREATE TABLE TBL_JYJGZYDLZSBDQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZYMC varchar(50), 
	ZYDM varchar(20), 
	SF varchar(50), 
	PC varchar(50), 
	ZSJHS varchar(255), 
	SJLQS varchar(255), 
	DYZYZYLQS varchar(255), 
	SJBDRS varchar(255), 
	XXWKLQPJFS bigint, 
	XXLKLQPJFS bigint, 
	XXLBFWKLQPJFS bigint, 
	DNBZYWKLQPJF bigint, 
	DNBZYLKLQPJF bigint, 
	DNBZYBFWLKLQPJF bigint, 
	SM varchar(2000), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-4 ������������

  CREATE TABLE TBL_BKSJDB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	ZZJEZJ bigint, 
	ZZXSSZJ varchar(255), 
	ZFZZJE bigint, 
	ZFZZXSS varchar(255), 
	SHZZJE bigint, 
	SHZZXSS varchar(255), 
	XXZZJE bigint, 
	XXZZXSS varchar(255), 
	GJZZJE bigint, 
	GJZZXSS varchar(255), 
	QGZXZZJE bigint, 
	QGZXZZXXS varchar(255), 
	JMXFZZJE bigint, 
	JMXFZZXSS varchar(255), 
	LSKNBZZZJE bigint, 
	LSKNBZZZXSS varchar(255), 
	TASK_VER_ bigint, 
	QTJZXJZZJE varchar(100), 
	QTJZXJZZXSS bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-5-1 Ӧ�챾�Ʊ�ҵ����ҵ���

  CREATE TABLE TBL_YJBKBYSJYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	MSTJYJS varchar(255), 
	KYLQZS varchar(255), 
	KYLQBXS varchar(255), 
	KYLQWXS varchar(255), 
	CGLXS varchar(255), 
	XXSZQYZS varchar(255), 
	XXSZQYZFJGZS varchar(255), 
	XXSZQYSYDWZS varchar(255), 
	XXSZQYQYZS varchar(255), 
	XXSZQYBDZS varchar(255), 
	XXSZQYDFXMZS varchar(255), 
	XXSZQYSXZS varchar(255), 
	XXSZQYLHJYZS varchar(255), 
	XXSZQYZZCYZS varchar(255), 
	XXSZQYQTZS varchar(255), 
	XXFSZQYZS varchar(255), 
	XXFSZQYZFJGZS varchar(255), 
	XXFSZQYSYDWZS varchar(255), 
	XXFSZQYQYZS varchar(255), 
	XXFSZQYBDZS varchar(255), 
	XXFSZQYDFXMZS varchar(255), 
	XXFSZQYSXZS varchar(255), 
	XXFSZQYLHJYZS varchar(255), 
	XXFSZQYZZCYZS varchar(255), 
	XXFSZQYQTZS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-5-2 Ӧ�챾�Ʊ�ҵ����רҵ��ҵ��ҵ���

  CREATE TABLE TBL_YJBKBYSFZYBYJY 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYDM varchar(50), 
	XNZYMC varchar(50), 
	YJBYSS varchar(255), 
	YJSZWASBYS varchar(255), 
	SYXWS varchar(255), 
	YJJYRS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6 ������ѧϰ��Ч

  CREATE TABLE TBL_BKSXXCX 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XKJSHJZS varchar(255), 
	XKJSHGJJJS varchar(255), 
	XKJSHGJIAJJS varchar(255), 
	XKJSHSBJJS varchar(255), 
	CXJNJSHJZS varchar(255), 
	CXJNJSHGJJJS varchar(255), 
	CXJNJSHGJIAJJS varchar(255), 
	CXJNJSHSBJJS varchar(255), 
	WYTYJSHJZS varchar(255), 
	WYTYJSHGJJJS varchar(255), 
	WYTYJSHGJIAJJS varchar(255), 
	WYTYJSHSBJJS varchar(255), 
	FBLW varchar(255), 
	FBZPS varchar(255), 
	HZZLS varchar(255), 
	YYSJGGL bigint, 
	YYLJGGL bigint, 
	TZHGL bigint, 
	CJGJHY varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-1 ѧ���μӴ�ѧ�����´�ҵѵ���ƻ����

  CREATE TABLE TBL_XSCJDXSCXCYXLJHQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	XMMC varchar(200), 
	XMJB varchar(20), 
	XMLB varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-2 ѧ�������ʦ������Ŀ���

  CREATE TABLE TBL_XSCYJSKYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	CYKYXMMC varchar(200), 
	XMFZR varchar(50), 
	GH varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-3 ѧ����ʡ�������ϸ��ྺ���������

  CREATE TABLE TBL_XSHDSJYSGLJSJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	JSMC varchar(100), 
	HJSJ varchar(20), 
	HJLB varchar(20), 
	HJDJ varchar(20), 
	SM CLOB, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
-- ��6-6-4 ѧ����רҵ�������������������רҵ�ã�

  CREATE TABLE TBL_XAHZYBSJLQKYSLZYY 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	BSMC varchar(100), 
	SSLB varchar(20), 
	HJDJ varchar(100), 
	HJSJ varchar(20), 
	ZBDW varchar(100), 
	XSPM varchar(255), 
	SM CLOB, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
-- ��6-6-5 ѧ����רҵ�������������������רҵ�ã�

  CREATE TABLE TBL_XSHZYBSJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XH varchar(100), 
	XSXM varchar(100), 
	BSMC varchar(100), 
	SSLB varchar(100), 
	HJMC varchar(100), 
	HJSJ varchar(100), 
	ZBDW varchar(10), 
	XSPM varchar(255), 
	SM varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-6 ѧ������ѧ���������

  CREATE TABLE TBL_XSFBXSLWQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	LWMC varchar(500), 
	FBQK varchar(100), 
	FBSJ varchar(20), 
	SLQK varchar(50), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-7 ѧ�����������ݵĴ�������Ʒ��������ѧ��רҵ�������������רҵ�ã�

  CREATE TABLE TBL_XSCZBYDDBXZP 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	ZPMC varchar(100), 
	LB varchar(20), 
	LX varchar(20), 
	FBSJ varchar(20), 
	FBCH varchar(100), 
	ZBDW varchar(100), 
	YXFW varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-6-8 ѧ��ר��������Ȩ�� ��Ȩ���

  CREATE TABLE TBL_XSZLZZQSQQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XH varchar(20), 
	XSXM varchar(50), 
	MC varchar(100), 
	LB varchar(20), 
	SQH varchar(50), 
	HPSJ varchar(20), 
	SFDYFMR varchar(20), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-7 �������������

  CREATE TABLE TBL_BKSJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XNZYDM varchar(20), 
	XNZYMC varchar(50), 
	BZYWCJWRS varchar(255), 
	BZYWCJNRS varchar(255), 
	JWDBZYRS varchar(255), 
	JNDBZYRS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��6-8 ѧ������

  CREATE TABLE TBL_XSST 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	STZS varchar(255), 
	KJLST varchar(255), 
	RWLST varchar(255), 
	TYLST varchar(255), 
	WYLST varchar(255), 
	QTST varchar(255), 
	CYRSZS varchar(255), 
	KJLCYRS varchar(255), 
	RWLCYRS varchar(255), 
	TYLCYRS varchar(255), 
	WYLCYRS varchar(255), 
	QTSTCYRS varchar(255), 
	TASK_VER_ bigint, 
	STCXCYL varchar(255), 
	CXCYLCYRCS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��7-1 ��ѧ������Ա�ɹ�

  CREATE TABLE TBL_JXGLRYCG 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JXCGJZS varchar(255), 
	GJJJXCGJS varchar(255), 
	SBJJXCGJS varchar(255), 
	JXLWZS varchar(255), 
	JXYJJXLWS varchar(255), 
	JXGLJXLWS varchar(255), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��7-2 ��ѧ��������ͳ�Ʊ�

  CREATE TABLE TBL_BKJXXXH 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XSPJFGBL bigint, 
	XSPJY bigint, 
	XSPJLH bigint, 
	XSPJZ bigint, 
	XSPJC bigint, 
	THPJFGBL bigint, 
	THPJY bigint, 
	THPJLH bigint, 
	THPJZ bigint, 
	THPJC bigint, 
	LDPJFGBL bigint, 
	LDPJY bigint, 
	LDPJLH bigint, 
	LDPJZ bigint, 
	LDPJC bigint, 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��7-3-1 ������ѧ�о���ĸ���Ŀ

  CREATE TABLE TBL_JYJXYJYGGXM 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XMMC varchar(500), 
	ZCR varchar(50), 
	ZCRGH varchar(20), 
	JB varchar(20), 
	LXSJ varchar(20), 
	JTYSSJ varchar(20), 
	JF bigint, 
	CYJSS varchar(255), 
	LXBH varchar(200), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��7-3-2 ��ѧ�ɹ�������һ�죩

  CREATE TABLE TBL_JXCGJ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	JSGH varchar(20), 
	JSXM varchar(50), 
	HJCGMC varchar(500), 
	BRPM varchar(255), 
	WCDWPM varchar(255), 
	JB varchar(20), 
	HJSJ varchar(20), 
	HJZSBH varchar(20), 
	SYDW varchar(100), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��7-3-3 ʡ�������ϱ��ƽ�ѧ������Ŀ���

  CREATE TABLE TBL_SJJYSBKJXGCXMQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	TJSJ varchar(20), 
	XMMC varchar(500), 
	XMLB varchar(50), 
	XMJB varchar(20), 
	ZCRGH varchar(20), 
	ZCRXM varchar(50), 
	HPSJ varchar(20), 
	PZWH varchar(500), 
	TASK_VER_ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-10��ʦ��������γ�

  CREATE TABLE TBL_SFJNLKC 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	TGSYSFJNLKCDSFSRS varchar(255), 
	SJSFJNLKCKSDSFSZRS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-11������ʵ�����

  CREATE TABLE TBL_JYSJQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	ZDJSGH varchar(100), 
	ZDJSXM varchar(100), 
	NJ varchar(100), 
	ZS varchar(255), 
	ZDBZYCJJYSJSFSS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-12��ʦ����רҵ�Ǳ���ѧ�������������

  CREATE TABLE TBL_SFLZYFBKXSSLJBQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	PTGZHZKSS varchar(255), 
	SSYJSS varchar(255), 
	BSYJSS varchar(255), 
	LXSS varchar(255), 
	PTYKSS varchar(255), 
	JXSS varchar(255), 
	CRTCXSS varchar(255), 
	YDYYXSS varchar(255), 
	HSXSS varchar(255), 
	WLJYXSS varchar(255), 
	ZKXSS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-13��ʦ�����ܾ����������

  CREATE TABLE TBL_SFJNJSJLQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XH varchar(100), 
	XSXM varchar(100), 
	JSMC varchar(100), 
	JSFW varchar(100), 
	MC varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-14��ʦ����רҵӦ���ҵ�����

  CREATE TABLE TBL_SFLZYYJBYSQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	PTHSPCSHGDJRSYJJD varchar(255), 
	PTHSPCSHGDJRSYJYD varchar(255), 
	PTHSPCSHGDJRSEJJD varchar(255), 
	PTHSPCSHGDJRSEJYD varchar(255), 
	PTHSPCSHGDJRSSJJD varchar(255), 
	PTHSPCSHGDJRSSJYD varchar(255), 
	TGJSZGZKSRS varchar(255), 
	BYCSJYGZRS varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-1��ѧ����չ�ɳ�ָ����ʦ���

  CREATE TABLE TBL_XSFZCZZDJSQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	JSXM varchar(100), 
	GH varchar(100), 
	ZDZYXSSL varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-2����ʦ�������о���ĸ���Ŀ���

  CREATE TABLE TBL_JSJYLYJYGGXMQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XMMC varchar(100), 
	LXBHHPZWH varchar(100), 
	ZCR varchar(100), 
	ZCRGH varchar(100), 
	JB varchar(100), 
	LXSJ varchar(50), 
	JTYSHJDSJ varchar(50), 
	JFWY bigint, 
	SYJSSR varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-3����ʦ���ֻ���������������о���Ŀ���

  CREATE TABLE TBL_JSZCJCJYLYHXYJXMQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	LXBH varchar(100), 
	XMMC varchar(100), 
	JB varchar(100), 
	WTDW varchar(100), 
	ZCR varchar(100), 
	ZCRGH varchar(100), 
	LXSJ varchar(50), 
	JTYSHJDSJ varchar(50),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-4����ʦ������������γ̲̽����

  CREATE TABLE TBL_JSZBJCJYKCJCQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	GH varchar(100), 
	JSXM varchar(100), 
	JCMC varchar(100), 
	CBS varchar(100), 
	ISBN varchar(100), 
	CBSJ varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-5����ʦ�������������������

  CREATE TABLE TBL_JSJWNJCJYFWJL 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	GH varchar(100), 
	XM varchar(100), 
	FWLB varchar(100), 
	KSRQ varchar(100), 
	JSRQ varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-6��ʦ����רҵ��ѧ��������

  CREATE TABLE TBL_SFLZYBXJBTJ 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	JXRCYXZCJFWY bigint, 
	JYSJJFWY bigint, 
	SYBKZE bigint, 
	XFSRJFWY bigint, 
	JYLZZTSZSC varchar(255), 
	JYLZZZWTSSC varchar(255), 
	JCHJSJCSKST varchar(255), 
	JYLDZTSZSC varchar(255), 
	JYJXALSLG varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-7��ʦ����רҵ��ѧ��ʩ

  CREATE TABLE TBL_SFLZYJXSS 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	JXSSMC varchar(100), 
	SSLB varchar(100), 
	ZDMJ bigint,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-8��ʦ����רҵ�������

  CREATE TABLE TBL_SFLZYPYQK 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(50), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	ZXF bigint, 
	XKZYKCXF bigint, 
	RWSHYSYKCXF bigint, 
	JSJYKCXF bigint, 
	JSJYKCBX bigint, 
	SDJYLKCXF bigint, 
	XXSYLKCXF bigint, 
	CJJYSJSFSZSR varchar(255), 
	CJJYSJSFSXSSR varchar(255),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��SF-9����ʦ�����γ������

  CREATE TABLE TBL_JSJYKCQKB 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	BATCH_ID bigint, 
	TJSJ varchar(100), 
	XNZYDM varchar(100), 
	XNZYMC varchar(100), 
	KKH varchar(100), 
	KCMC varchar(100), 
	KCXZ varchar(100), 
	XF bigint, 
	NJ varchar(100), 
	SKJS varchar(100), 
	SKJSGH varchar(100),
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ��ͨ�ߵ�ѧУ����רҵĿ¼�����в������

  CREATE TABLE TBL_PTGDXXBKZYML 
   (ID bigint not null auto_increment,  
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS bigint, 
	TASK_ID bigint, 
	ZYDM varchar(100), 
	ZYMC varchar(100), 
	DMBB varchar(100), 
	XKML varchar(100), 
	TASK_VER_ BIGINT,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   
