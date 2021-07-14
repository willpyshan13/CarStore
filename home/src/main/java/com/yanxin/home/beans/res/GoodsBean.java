package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/14
 */
public class GoodsBean extends BaseBean {


    /**
     amt (number, optional): 商品金额 ,
     commentNum (integer, optional): 商品评论数量 ,
     createdTime (string, optional): 创建时间 ,
     detailList (Array[GoodsDetailRes], optional): 物料列表 ,
     goodsDescribe (string, optional): 商品描述 ,
     goodsName (string, optional): 商品名称 ,
     goodsType (string, optional): 商品类型 ,
     goodsTypeUuid (string, optional): 商品类型uuid ,
     imgList (Array[GoodsImgRes], optional): 图片列表 ,
     levelOne (string, optional): 一级分类 ,
     levelOneUuid (string, optional): 一级分类uuid ,
     levelTwo (string, optional): 二级分类 ,
     levelTwoUuid (string, optional): 二级分类uuid ,
     manHourCost (number, optional): 工时费 ,
     materialsExpenses (number, optional): 材料费 ,
     salesNum (integer, optional): 销量 ,
     score (number, optional): 商品评分 ,
     sellSts (integer, optional): 销售状态:0 库存 1 在售 ,
     storeName (string, optional): 店铺名称 ,
     storeUuid (string, optional): 店铺主键 ,
     surplusNum (integer, optional): 库存 ,
     uuid (string, optional): uuid
     */

    private BigDecimal amt;
    private int commentNum;
    private String createdTime;
    private String goodsDescribe;
    private String goodsName;
    private String goodsType;
    private String goodsTypeUuid;
    private String levelOne;
    private String levelOneUuid;
    private String levelTwo;
    private String levelTwoUuid;
    private BigDecimal manHourCost;
    private BigDecimal materialsExpenses;
    private int salesNum;
    private double score;
    private int sellSts;
    private String storeName;
    private String storeUuid;
    private int surplusNum;
    private String uuid;
    private List<DetailListBean> detailList;
    private List<ImgListBean> imgList;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeUuid() {
        return goodsTypeUuid;
    }

    public void setGoodsTypeUuid(String goodsTypeUuid) {
        this.goodsTypeUuid = goodsTypeUuid;
    }

    public String getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(String levelOne) {
        this.levelOne = levelOne;
    }

    public String getLevelOneUuid() {
        return levelOneUuid;
    }

    public void setLevelOneUuid(String levelOneUuid) {
        this.levelOneUuid = levelOneUuid;
    }

    public String getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(String levelTwo) {
        this.levelTwo = levelTwo;
    }

    public String getLevelTwoUuid() {
        return levelTwoUuid;
    }

    public void setLevelTwoUuid(String levelTwoUuid) {
        this.levelTwoUuid = levelTwoUuid;
    }

    public BigDecimal getManHourCost() {
        return manHourCost;
    }

    public void setManHourCost(BigDecimal manHourCost) {
        this.manHourCost = manHourCost;
    }

    public BigDecimal getMaterialsExpenses() {
        return materialsExpenses;
    }

    public void setMaterialsExpenses(BigDecimal materialsExpenses) {
        this.materialsExpenses = materialsExpenses;
    }

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSellSts() {
        return sellSts;
    }

    public void setSellSts(int sellSts) {
        this.sellSts = sellSts;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public int getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public static class DetailListBean extends BaseBean {
        /**
         * actAmt : 0
         * name : string
         * uuid : string
         */

        private BigDecimal actAmt;
        private String name;
        private String uuid;

        public BigDecimal getActAmt() {
            return actAmt;
        }

        public void setActAmt(BigDecimal actAmt) {
            this.actAmt = actAmt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public static class ImgListBean  extends BaseBean {
        /**
         * goodsUuid : string
         * imgPath : string
         * imgType : 0
         * uuid : string
         */

        private String goodsUuid;
        private String imgPath;
        private int imgType;
        private String uuid;

        public String getGoodsUuid() {
            return goodsUuid;
        }

        public void setGoodsUuid(String goodsUuid) {
            this.goodsUuid = goodsUuid;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getImgType() {
            return imgType;
        }

        public void setImgType(int imgType) {
            this.imgType = imgType;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
