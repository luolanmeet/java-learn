package pers.util;

import java.util.Arrays;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilderTest {

    public static void main(String[] args) {

        String basePath = System.getProperty("user.dir") + "/office/poi-word/";
        String templatePath = basePath + "src/main/resources/wordStyle2.docx";
        String savePath = basePath + "poiWord.docx";
        PoiWordBuilder builder = new PoiWordBuilder(templatePath);

        builder.addTextUseTemplateStyle("xxxx科技接口文档", PoiWordBuilder.TEMPLATE_TITLE)
                .addBlankLine()
                .addTextUseTemplateStyle("店铺信息批量查询 oms.shop.batch.query", PoiWordBuilder.TEMPLATE_HEAD_1)
                .addTextUseTemplateStyle("接口版本：2022年12月05日-2", PoiWordBuilder.TEMPLATE_BODY)
                .addTextUseTemplateStyle("请求参数", PoiWordBuilder.TEMPLATE_HEAD_2);

        builder.addTable(
                new String[]{"参数名","参数语义","类型","是否必填","字段长度限制","默认值"},
                Arrays.asList(
                        new String[]{"shopCodes","店铺编码","LIST<STRING>","否","0",""},
                        new String[]{"tenantId","租户id","STRING","否","0","1"},
                        new String[]{"status","状态","STRING","否","0",""},
                        new String[]{"viewCode","模型请求唯一码","STRING","否","0","shop_base_manage"}));

        builder.write(savePath);
    }

}
