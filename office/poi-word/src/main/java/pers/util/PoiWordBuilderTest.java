package pers.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilderTest {

    public final static String TEMPLATE_TITLE = "TEMPLATE_TITLE";
    public final static String TEMPLATE_HEAD_1 = "TEMPLATE_HEAD_1";
    public final static String TEMPLATE_HEAD_2 = "TEMPLATE_HEAD_2";
    public final static String TEMPLATE_HEAD_3 = "TEMPLATE_HEAD_3";
    public final static String TEMPLATE_HEAD_4 = "TEMPLATE_HEAD_4";
    public final static String TEMPLATE_BODY_1 = "TEMPLATE_BODY_1";
    public final static String TEMPLATE_BODY_2 = "TEMPLATE_BODY_2";

    public static void main(String[] args) throws IOException {

        String basePath = System.getProperty("user.dir") + "/office/poi-word/";
        String templatePath = basePath + "src/main/resources/wordStyle.docx";
        String savePath = basePath + "poiWord.docx";
        FileOutputStream out = new FileOutputStream(savePath);

        PoiWordBuilder builder = new PoiWordBuilder(templatePath, out);

        builder.addTextWithStyle("xxxx科技接口文档", TEMPLATE_TITLE)
                .addBlankLine()
                .addTextWithStyle("店铺信息批量查询 oms.shop.batch.query", TEMPLATE_HEAD_1)
                .addTextWithStyle("接口版本：2022年12月05日-2", TEMPLATE_BODY_1);

        builder.addTextWithStyle("请求参数", TEMPLATE_HEAD_2)
                .addTable(
                        new String[]{"参数名","参数语义","类型","是否必填","字段长度限制","默认值"},
                        Arrays.asList(
                                new String[]{"shopCodes","店铺编码","LIST<STRING>","否","0",""},
                                new String[]{"tenantId","租户id","STRING","否","0","1"},
                                new String[]{"status","状态","STRING","否","0",""},
                                new String[]{"viewCode","模型请求唯一码","STRING","否","0","shop_base_manage"}),
                        1);

        builder.addTextWithStyle("响应参数", TEMPLATE_HEAD_2)
                .addTable(
                        new String[]{"参数名","参数语义","类型","是否必填","字段长度限制","默认值"},
                        Arrays.asList(
                                new String[]{"success","BOOLEAN","否","0","",""},
                                new String[]{"data","LIST<OBJECT>","否","0","",""},
                                new String[]{"status","状态","STRING","否","0",""},
                                new String[]{"viewCode","模型请求唯一码","STRING","否","0","shop_base_manage"}),
                        1);

        builder.addTextWithStyle("报文示例", TEMPLATE_HEAD_2)
                .addTextWithStyle("请求报文示例", TEMPLATE_HEAD_3)
                .addTextWithStyle("JSON 格式", TEMPLATE_HEAD_4)
                .addTextWithStyle(
                        new String[]{
                                "{",
                                "    \"shopCodes\": [",
                                "        \"array data\",",
                                "        \"array data\"",
                                "    ],",
                                "    \"tenantId\": \"租户id\",",
                                "    \"status\": \"状态\",",
                                "    \"viewCode\": \"模型请求唯一码\"",
                                "}"
                        },
                        TEMPLATE_BODY_2);

        builder.addTextWithStyle("......", TEMPLATE_HEAD_2);
        builder.finish();
    }

}
