package pers.util;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilderTest {

    public static void main(String[] args) {

        String basePath = System.getProperty("user.dir") + "/office/poi-word/";
        String templatePath = basePath + "src/main/resources/wordStyle.docx";
        String savePath = basePath + "poiWord.docx";
        PoiWordBuilder builder = new PoiWordBuilder(templatePath);

        builder.addTextUseTemplateStyle("菜鸟物流科技接口文档", PoiWordBuilder.TEMPLATE_TITLE)
                .addBlankLine()
                .addTextUseTemplateStyle("店铺信息批量查询 oms.shop.batch.query", PoiWordBuilder.TEMPLATE_HEAD_1)
                .addTextUseTemplateStyle("接口版本：2022年12月05日-2", PoiWordBuilder.TEMPLATE_BODY)
                .addTextUseTemplateStyle("请求参数", PoiWordBuilder.TEMPLATE_HEAD_2);

        builder.write(savePath);
    }

}
