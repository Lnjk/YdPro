package com.example.bean;

import java.util.Date;


import com.example.ydshoa.LoginMainActivity;

public class URLS {

    public static final String base_url = LoginMainActivity.getMainActivity()
            .Url();
    //	public static final String base_url = "http://192.168.2.178:8080";
//	public static final String base_url = "http://oa.ydshce.com:8080";
    // -----------------------登录
    // 登录
    public static final String login_url = base_url
            + "/InfManagePlatform/LoginAction.action";
    // 验证码
    public static final String yzm_url = base_url
            + "/InfManagePlatform/IdentityServlet.do?ts="
            + new Date().getTime();
    // -----------------------root构建
    // root构建("http://192.168.2.178:8080/InfManagePlatform/rootcreatDatabase.action")
    public static final String creatRoot_url = base_url
            + "/InfManagePlatform/rootcreatDatabase.action";
    // root构建,测试连接("http://192.168.2.178:8080/InfManagePlatform/rootgetConnection.action")
    public static final String conRoot_url = base_url
            + "/InfManagePlatform/rootgetConnection.action";
    // -----------------------个人信息
    // 个人信息("http://192.168.2.178:8080/InfManagePlatform/LoginUserInf.action")
    public static final String userInfo_url = base_url
            + "/InfManagePlatform/LoginUserInf.action";
    // -----------------------账套管理
    // 获取账套("http://192.168.2.178:8080/InfManagePlatform/AccountDBqueryAccountDB.action")
    public static final String account_url = base_url
            + "/InfManagePlatform/AccountDBqueryAccountDB.action";
    // 连接账套("http://192.168.2.178:8080/InfManagePlatform/AccountDBgetConnectionTest.action")
    public static final String account_con_url = base_url
            + "/InfManagePlatform/AccountDBConnectionTest.action";
    // 新增账套("http://192.168.2.178:8080/InfManagePlatform/AccountDBinsertAccountDB.action")
    public static final String account_add_url = base_url
            + "/InfManagePlatform/AccountDBinsertAccountDB.action";
    // 删除账套("http://192.168.2.178:8080/InfManagePlatform/AccountDBdeleteAccountDB.action")
    public static final String account_del_url = base_url
            + "/InfManagePlatform/AccountDBdeleteAccountDB.action";
    // 更新账套("http://192.168.2.178:8080/InfManagePlatform/AccountDBupdateAccountDB.action")
    public static final String account_updata_url = base_url
            + "/InfManagePlatform/AccountDBupdateAccountDB.action";
    // -----------------------用户管理
    // 获取用户("http://192.168.2.178:8080/InfManagePlatform/UsergetUsers.action")
    public static final String all_user_url = base_url
            + "/InfManagePlatform/UsergetUsers.action";
    // 新增用户("http://192.168.2.178:8080/InfManagePlatform/UseraddUser.action")
    public static final String add_user_url = base_url
            + "/InfManagePlatform/UseraddUser.action";
    // 修改用户("http://192.168.2.178:8080/InfManagePlatform/UserupdateUser.action")
    public static final String reset_user_url = base_url
            + "/InfManagePlatform/UserupdateUser.action";
    // 删除单用户("http://192.168.2.178:8080/InfManagePlatform/UserdelUser.action")
    public static final String del_one_user_url = base_url
            + "/InfManagePlatform/UserdelUser.action";
    // 删除多用户("http://192.168.2.178:8080/InfManagePlatform/UserdelUser.action")
    public static final String del_more_user_url = base_url
            + "/InfManagePlatform/UserdelUsers.action";
    // 获取ERP账套("http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryAccountDB.action")
    public static final String ERP_db_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryAccountDB.action";
    // 获取ERP部门("http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryDepartment.action")
    public static final String ERP_dep_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryDepartment.action";
    // 获取ERP客户，供应商("http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryCustomer.action")
    public static final String ERP_cust_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryCustomer.action";
    // 获取ERP用户("http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryErpUser.action")
    public static final String ERP_user_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryErpUser.action";
    // 获取ERP核算单位"http://localhost:8080/InfManagePlatform/QueryErpInfqueryCompDep?accountNo=DB_BJ15")
    public static final String ERP_hs_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryCompDep";
    // 获取模块列表("http://192.168.2.178:8080/InfManagePlatform/ModuleListgetModules.action")
    public static final String mod_list_url = base_url
            + "/InfManagePlatform/ModuleListgetModules.action";
    // id获取模块列表("http://192.168.2.178:8080/InfManagePlatform/ModuleListgetModule.action")
    public static final String mod_id_list_url = base_url
            + "/InfManagePlatform/ModuleListgetModule.action";
    // name获取模块列表("http://192.168.2.178:8080/InfManagePlatform/ModuleListgetmodulesWithName.action")
    public static final String mod_name_list_url = base_url
            + "/InfManagePlatform/ModuleListgetmodulesWithName.action";
    // level获取模块列表("http://192.168.2.178:8080/InfManagePlatform/ModuleListgetSubModules.action")
    public static final String mod_level_list_url = base_url
            + "/InfManagePlatform/ModuleListgetSubModules.action";
    // ------------决策报表
    // 制表（"http://192.168.2.178:8080/InfManagePlatform/Report.action"）
    public static final String sale_url = base_url
            + "/InfManagePlatform/Report.action";
    // 分支机构（"http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryAffiliate.action"）
    public static final String sale_fzjg = base_url
            + "/InfManagePlatform/QueryErpInfqueryAffiliate.action";
    // 销售渠道（""http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryArea.action""）
    public static final String area_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryArea.action";
    // 销售人员（"http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryEmployee.action"）
    public static final String employee_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryEmployee.action";
    // 品号（"http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryPrdt.action"）
    public static final String prdNo_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryPrdt.action";
    // 货品中类（"http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryprdIndx.action"）
    public static final String prdIndex = base_url
            + "/InfManagePlatform/QueryErpInfqueryprdIndx.action";
    // 货品库区（"http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryprdWh.action"）
    public static final String prdWh = base_url
            + "/InfManagePlatform/QueryErpInfqueryprdWh.action";

//

    //--------定价单号------(临时单号)http://localhost:8080/InfManagePlatform/BilngetBiln_no.action?bn_Type=dj&db_Id=DB_BJ15&bn_Date=2016-06-16
    public static final String price_num_ls = base_url
            + "/InfManagePlatform/BilngetBiln_no.action";
    ///** 用户新增保存票据生成票据单号。 */http://localhost:8080/InfManagePlatform/BilncreateBiln_No.action?bn_Type=dj&db_Id=DB_BJ15&bn_Date=2016-06-16
    public static final String price_num_zs = base_url
            + "/InfManagePlatform/BilncreateBiln_No.action";
    //	/** 用户删除票据将票据号添加到删除票据库 http://localhost:8080/InfManagePlatform/BilndelBiln_No.action?bn_Type=dj&db_Id=DB_BJ15&bn_Date=2016-06-16&bn_No=dj1606160001
    public static final String price_del_sjk = base_url
            + "/InfManagePlatform/BilndelBiln_No.action";
    ///** 用户查询删除的单据号 */http://localhost:8080/InfManagePlatform/BilnqueryBilnOfDel.action?bn_Type=dj&db_Id=DB_BJ15&beginDate=2016-06-16&endDate=2016-06-16
    public static final String price_del_query = base_url
            + "/InfManagePlatform/BilnqueryBilnOfDel.action";
    //获取所有定价政策 主表  http://localhost:8080/InfManagePlatform/+Price+方法名+.action+参数
    public static final String price_getPrices = base_url
            + "/InfManagePlatform/PricegetPrices.action";
    //新增定价政策主表 (单号不传，自动生成！ )
    public static final String price_addPrice = base_url
            + "/InfManagePlatform/PriceaddPrice.action";
    //修改定价政策主表(传单号和数据库 ！ 如果已经执行了的不能修改！)
    public static final String price_setPrice = base_url
            + "/InfManagePlatform/PricesetPrice.action";
    //删除定价政策主表(传单号和数据库 ！ )
    public static final String price_delPrice = base_url
            + "/InfManagePlatform/PricedelPrice.action";
    // 执行定价政策
    public static final String price_executePrice = base_url
            + "/InfManagePlatform/PriceexecutePrice.action";
    // 获取指定单号的定价政策 主表和明细表
    public static final String price_getPrice = base_url
            + "/InfManagePlatform/PricegetPrice.action";
    // 依据账套编号查和定价政策编号获取所有定价政策产品明细
    public static final String price_getPrdtUpList = base_url
            + "/InfManagePlatform/PricegetPrdtUpList.action";
    // 货品加载  http://localhost:8080/InfManagePlatform/PrdtgetPrdtList.action?db_Id=DB_BJ18&query_Sup=CC001,CJ001
    public static final String price_load = base_url
            + "/InfManagePlatform/PrdtgetPrdtList.action";
    //设计师查询
    // http://oa.ydshce.com:8080/InfManagePlatform/VipqueryDepco_Vip.action?db_Id=DB_BJ15
    public static final String design_query = base_url
            + "/InfManagePlatform/VipqueryDepco_Vip.action";
    //设计师添加
    // http://oa.ydshce.com:8080/InfManagePlatform/addDepco_Vip.action?db_Id=DB_BJ15
    public static final String design_add = base_url
            + "/InfManagePlatform/VipaddDepco_Vip.action";
    //设计师删除
    // http://oa.ydshce.com:8080/InfManagePlatform/delDepco_Vip.action?db_Id=DB_BJ15
    public static final String design_del = base_url
            + "/InfManagePlatform/VipdelDepco_Vip.action";
    //设计师更新
    // http://oa.ydshce.com:8080/InfManagePlatform/updateDepco_Vip.action?db_Id=DB_BJ15
    public static final String design_updata = base_url
            + "/InfManagePlatform/VipupdateDepco_Vip.action";
    //vip查询
    // http://oa.ydshce.com:8080/InfManagePlatform/ObjqueryObjectType.action?db_Id=DB_BJ15&biln_Type=VP
    public static final String vip_query = base_url
            + "/InfManagePlatform/ObjqueryObjectType.action";
    //vip添加
    // http://oa.ydshce.com:8080/InfManagePlatform/ObjaddObjectType.action?db_Id=DB_BJ15&biln_Type=VP&Type_ID=1&Type_Name=ln
    public static final String vip_add = base_url
            + "/InfManagePlatform/ObjaddObjectType.action";
    //vip删除
    // http://oa.ydshce.com:8080/InfManagePlatform/ObjdelObjectType.action?db_Id=DB_BJ15&biln_Type=VP&Type_ID=1&Type_Name=ln
    public static final String vip_del = base_url
            + "/InfManagePlatform/ObjdelObjectType.action";
    //vip更新
    // http://oa.ydshce.com:8080/InfManagePlatform/ObjupdateObjectType.action?db_Id=DB_BJ15&biln_Type=VP&Type_ID=1&Type_Name=ln
    public static final String vip_updata = base_url
            + "/InfManagePlatform/ObjupdateObjectType.action";
    //配送信息查询
    // http://oa.ydshce.com:8080/InfManagePlatform/CustqueryconnInfList.action?db_Id=DB_BJ15&biln_Type=CT
    public static final String psxx_query = base_url
            + "/InfManagePlatform/CustqueryconnInfList.action";
    //配送信息添加
    // oa.ydshce.com:8080/InfManagePlatform/CustaddCust_Conn.action?Cust_Acc=DB_BJ15&Cust_No=KH1811040005&iTM=3&Con_Per=李宁
    // &Con_Tel=18511403631&Con_Crt=河北省&Con_Spa=张家口市&Con_Add=河北省张家口市&User_ID=ln123&Def=true
    public static final String psxx_add = base_url
            + "/InfManagePlatform/CustaddCust_Conn.action";
    //配送信息删除
    // http://oa.ydshce.com:8080/InfManagePlatform/CustdelCust_Conn.action?db_Id=DB_BJ15
    public static final String psxx_del = base_url
            + "/InfManagePlatform/CustdelCust_Conn.action";
    //配送信息更新
    // http://oa.ydshce.com:8080/InfManagePlatform/CustupdateCust_Conn.action?db_Id=DB_BJ15
    public static final String psxx_updata = base_url
            + "/InfManagePlatform/CustupdateCust_Conn.action";
    //客户信息查询
    // http://oa.ydshce.com:8080/InfManagePlatform/CustqueryCusts.action?Cust_Acc=DB_BJ15
    public static final String cust_z_query = base_url
            + "/InfManagePlatform/CustqueryCusts.action";
    //客户信息添加
    // oa.ydshce.com:8080/InfManagePlatform/CustaddCust_Conn.action?Cust_Acc=DB_BJ15&Cust_No=CT1810310001&iTM=3&Con_Per=李宁
    // &Con_Tel=18511403631&Con_Crt=河北省&Con_Spa=张家口市&Con_Add=河北省张家口市&User_ID=ln123&Def=是
    public static final String cust_z_add = base_url
            + "/InfManagePlatform/CustaddCust.action";
    //客户信息删除
    // http://oa.ydshce.com:8080/InfManagePlatform/CustdelCust_Conn.action?db_Id=DB_BJ15
    public static final String cust_z_del = base_url
            + "/InfManagePlatform/CustdelCust.action";
    //客户信息更新
    // http://oa.ydshce.com:8080/InfManagePlatform/CustupdateCust_Conn.action?db_Id=DB_BJ15
    public static final String cust_z_updata = base_url
            + "/InfManagePlatform/CustupdateCust.action";
    //    报表（客户完成以后）
//    http://oa.ydshce.com:8080/InfManagePlatform/Report.action?reporyBusiness=ARP&reprotNo=ArpTG&beginDate=2018-1-1&endDate=2018-10-1
//跟踪信息查询
    public static final String cust_gzxx_query = base_url
            + "/InfManagePlatform/CustqueryCust_FollList.action";
    //跟踪信息添加
    // oa.ydshce.com:8080/InfManagePlatform/CustaddCust_Foll.action?Cust_Acc=DB_BJ15&Cust_No=KH1810310007&Foll_DD=2018-11-6&Foll_Way=其他
    // &Foll_Them=销售&Foll_Case=开始&Stag_Class=意向客户&Foll_Per=李宁&User_ID=ln123&Foll_No=CF1811070001

    public static final String cust_gzxx_add = base_url
            + "/InfManagePlatform/CustaddCust_Foll.action";
    //跟踪信息删除
    public static final String cust_gzxx_del = base_url
            + "/InfManagePlatform/CustdelCust_Foll.action";
    //跟踪信息更新
    public static final String cust_gzxx_updata = base_url
            + "/InfManagePlatform/CustupdateCust_Foll.action";
    //报价信息(查询)
    public static final String cust_bjxx_qyery = base_url
            + "/InfManagePlatform/QuotationqueryQuotation.action";
    //报价信息(增加)
    public static final String cust_bjxx_add = base_url
            + "/InfManagePlatform/QuotationaddQuotation.action";
    //报价信息(删除)
    public static final String cust_bjxx_del = base_url
            + "/InfManagePlatform/QuotationdelQuotation.action";
    //报价信息(更新)
    public static final String cust_bjxx_update = base_url
            + "/InfManagePlatform/QuotationupdateQuotation.action";
    //    单据类别
    //http://oa.ydshce.com:8080/InfManagePlatform/QueryErpInfqueryBilnType.action?accountNo=DB_BJ15&&bil_Id=SA
    public static final String djlb_url = base_url
            + "/InfManagePlatform/QueryErpInfqueryBilnType.action";
    //    收款单
//    http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=300&&clientRows=0&&rp_ID=1&&rp_NO=RT201805230051
//            &&rp_DD=2018-05-23&&usr_NO=L022&&dep=2023&&rem=孙黎明&&bil_NO=SO201805230097&&irp_ID=T&&CLS_ID=T&&cus_NO=KH031&&bil_TYPE=06&&usr=Z002&&chk_MAN=Z002
//   &&cus_NO_OS=123(查询,新增，删除，更新)
    public static final String skd_url_query = base_url
            + "/InfManagePlatform/PaymentsqueryPayment.action";
    public static final String skd_url_add = base_url
            + "/InfManagePlatform/PaymentsqaddPayment.action";
    public static final String skd_url_del= base_url
            + "/InfManagePlatform/PaymentsdelPayment.action";
    public static final String skd_url_reset = base_url
            + "/InfManagePlatform/PaymentsupdatePayment.action";
    //明细表(通过irp_ID判断预收还是应收)
    //    oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryMf_ARP.action?db_Id=DB_BJ18&&cus_NO=KH347
    public static final String skd_url_mx = base_url
            + "/InfManagePlatform/PaymentsqueryMf_ARP.action";
    //    http://oa.ydshce.com:8080/InfManagePlatform/QueryErpInfqueryBacc.action?accountNo=DB_BJ18&&id=null&&name=null
//现金账户，银行账户(区分时根据客户类型)custType=1(银行)2，现金
    public static final String prd_obj_zh = base_url
            + "/InfManagePlatform/QueryErpInfqueryBacc.action";
    //设计师积分
    //登记单
    public static final String design_jfdj_add = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsaddDeco_Vip_Points.action";
    public static final String design_jfrw_add = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsaddDeco_Vip_Task.action";
    public static final String design_jfsp_add = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsaddDeco_Vip_Prdt.action";
    public static final String design_jfdj_del = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsdelDeco_Vip_Points.action";
    public static final String design_jfrw_del = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsdelDeco_Vip_Task.action";
    public static final String design_jfsp_del = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsdelPoints_Prdt.action";
    public static final String design_jfdj_set = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointssetDeco_Vip_Points.action";
    public static final String design_jfrw_set = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointssetDeco_Vip_Task.action";
    public static final String design_jfsp_set = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointssetPoints_Prdt.action";
    //统计所有
    public static final String design_jfdj_query = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsquerySum_Deco_Vip_points.action";
    public static final String design_jfrw_query = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsquerySum_Deco_Vip_Task.action";
    public static final String design_jfsp_query = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsquerySumPoints_Prdt.action";
    //明细
    public static final String design_jfdjquery_mx = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsqueryDeco_Vip_points.action";
    public static final String design_jfrwquery_mx = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsqueryDeco_Vip_Task.action";
    public static final String design_jfspquery_mx = base_url
            + "/InfManagePlatform/Deco_Vip_Of_PointsqueryPoints_Prdt.action";
}
