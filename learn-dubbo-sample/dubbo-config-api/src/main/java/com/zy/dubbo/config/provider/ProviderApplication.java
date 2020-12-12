package com.zy.dubbo.config.provider;

import com.alibaba.dubbo.config.*;
import com.zy.dubbo.config.provider.service.impl.DubboServiceImpl;
import com.zy.dubbo.service.DubboService;

import java.io.IOException;

/**
 * @author: jogin
 * @date: 2020/9/12 17:35
 */
public class ProviderApplication {

    public static void main(String[] args) throws IOException, IOException {
        // 应用信息
        ApplicationConfig application = new ApplicationConfig();
        application.setName("provider");
        //协议信息
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20890);
        //注册地址信息
        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setRegister(false);
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:2181");
        //提供者信息
        ProviderConfig provider = new ProviderConfig();
        provider.setThreads(3);
//        provider.setHost("127.0.0.1"); //设置直连的 ip 地址
//        provider.setPort(20890); //设置直连的 port
//        provider.setPath("/say"); // 设置直连的 path
        //服务信息
        ServiceConfig<DubboService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setProtocol(protocol);
        service.setProvider(provider);
        service.setRegistry(registryConfig);
        service.setInterface(DubboService.class);
        service.setRef(new DubboServiceImpl());
        service.setVersion("1.0.0");
        // 发布服务
        service.export();

        System.in.read();
    }
}
