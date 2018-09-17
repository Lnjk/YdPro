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
}
