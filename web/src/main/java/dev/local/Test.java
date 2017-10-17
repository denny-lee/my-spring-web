package dev.local;

import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.TMS_WAYBILL_GET.*;
import com.taobao.pac.sdk.cp.dataobject.request.TMS_WAYBILL_SUBSCRIPTION_QUERY.TmsWaybillSubscriptionQueryRequest;
import com.taobao.pac.sdk.cp.dataobject.response.TMS_WAYBILL_GET.TmsWaybillGetResponse;
import com.taobao.pac.sdk.cp.dataobject.response.TMS_WAYBILL_GET.WaybillCloudPrintResponse;
import com.taobao.pac.sdk.cp.dataobject.response.TMS_WAYBILL_SUBSCRIPTION_QUERY.TmsWaybillSubscriptionQueryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * On 2017/10/12 15:53 created by LW
 */
public class Test {
    /**
     * 测试环境请求url
     */
    private final static String dailyUrl = "http://linkdaily.tbsandbox.com/gateway/link.do";
    /**
     * 测试环境appKey
     */
    private final static String dailyAppKey = "766654";
    /**
     * 测试环境secretKey
     */
    private final static String dailySecretKey = "F53eqq903jQySV100Z8w06f9g914A13Z";
    /**
     * 测试环境token，token是商家在物流云平台授权ISV后生成的授权码，目前需要商家配置在ISV的软件中
     */
    private final static String dailyToken = "TmpFU1ZOUGoyRnoybDZmT3lyaW9hWGR4VFNad0xNYTBUek9QZk9kamt2Z1hJMytsVkVHK0FjVW55T25wcUR1Qw==";

    /**
     * 线上环境请求url
     */
    private final static String onlineUrl = ":http://link.cainiao.com/gateway/link.do";
    /**
     * 线上环境appKey，物流云控制台查看
     */
    private final static String onlineAppKey = "yourAppKey";
    /**
     * 线上环境secretKey,物流云控制台查看
     */
    private final static String onlineSecretKey = "yourSecretKey";
    /**
     * 线上环境token，token是商家在物流云平台授权ISV后生成的授权码，目前需要商家配置在ISV的软件中
     */
    private final static String onlineToken = "yourOnlineToken";

    /**
     * 通过物流云获取电子面单
     * @param args
     */
    public static void main(String[] args) {
        PacClient client = new PacClient(dailyAppKey,dailySecretKey, dailyUrl);
        SendSysParams params = new SendSysParams();
        params.setFromCode(dailyToken);

        TmsWaybillGetRequest request = new TmsWaybillGetRequest();
        //配送公司编码
        request.setCpCode("EMS");

        //发件人信息
        UserInfoDto sender = new UserInfoDto();
        request.setSender(sender);

        //发件人姓名
        sender.setName("寄件人姓名");
        //发件人固定电话
        sender.setPhone("02512345678");
        //发件人手机号
        sender.setMobile("13000000000");

        //寄件地址，这里的地址需要是卖家订购电子面单服务时使用的订购地址，具体可以通过TMS_WAYBILL_SUBSCRIPTION_QUERY接口获取
        AddressDto sendAddress = new AddressDto();
        sender.setAddress(sendAddress);
        sendAddress.setProvince("江苏省");
        sendAddress.setCity("南京市");
        sendAddress.setDistrict("江宁区");
        sendAddress.setDetail("将军大道1号");

        String objectId = "A";
        //收件信息
        ArrayList<TradeOrderInfoDto> tradeOrderInfoDtos = new ArrayList<TradeOrderInfoDto>();
        request.setTradeOrderInfoDtos(tradeOrderInfoDtos);

        TradeOrderInfoDto tradeOrderInfoDto = new TradeOrderInfoDto();
        tradeOrderInfoDto.setObjectId(objectId);
        String emsTemplateUrl = "http://cloudprint.daily.taobao.net/template/standard/137411/1";
        //打印模板的url,参见白皮书
        tradeOrderInfoDto.setTemplateUrl(emsTemplateUrl);
        tradeOrderInfoDtos.add(tradeOrderInfoDto);

        OrderInfoDto orderInfoDto = new OrderInfoDto();
        tradeOrderInfoDto.setOrderInfo(orderInfoDto);

        orderInfoDto.setOrderChannelsType("OTHERS");
        ArrayList<String> orderList = new ArrayList<String>();
        orderInfoDto.setTradeOrderList(orderList);

        //订单列表，这里的场景是一个订单获取一个面单号
        orderList.add("1234567890");

        PackageInfoDto packageInfoDto = new PackageInfoDto();
        tradeOrderInfoDto.setPackageInfo(packageInfoDto);
        ArrayList<Item> items = new ArrayList<Item>();
        packageInfoDto.setItems(items);
        Item item = new Item();
        items.add(item);
        item.setName("衣服");
        item.setCount(1);

        UserInfoDto receiver = new UserInfoDto();
        tradeOrderInfoDto.setRecipient(receiver);
        receiver.setName("收件人姓名");
        receiver.setMobile("13100000000");
        AddressDto receiveAddress = new AddressDto();
        receiver.setAddress(receiveAddress);

        receiveAddress.setProvince("浙江省");
        receiveAddress.setCity("杭州市");
        receiveAddress.setDistrict("余杭区");
        receiveAddress.setDetail("文一西路969号");

        TmsWaybillGetResponse response = client.send(request, params);
        if(!response.isSuccess()){
            System.out.println("errorCode:" + response.getErrorCode() + ",errorMessage:" + response.getErrorMsg());
            return;
        }
        List<WaybillCloudPrintResponse> waybillCloudPrintResponseList = response.getWaybillCloudPrintResponseList();
        for (WaybillCloudPrintResponse waybillCloudPrintResponse : waybillCloudPrintResponseList) {
            if(objectId.equals(waybillCloudPrintResponse.getObjectId())){
                System.out.println("get waybillCode:" + waybillCloudPrintResponse.getWaybillCode() + ",printData:" + waybillCloudPrintResponse.getPrintData());
                //使用printData和templateUrl调用本地云打印组件打印面单
            }
        }

    }
}
