package com.example.wearVillage.dataController;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class dataToServer {
    public dataToServer(){}
     static String ADDRESS = "3.39.167.250";
     static int PORT = 22;  //포트번호
     static String USERNAME = "ubuntu";
     static String PASSWORD = "";
     static Session session = null;
     static Channel channel = null;
     static ChannelSftp channelSftp = null;

    public static void sshAccess() throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(USERNAME, ADDRESS, PORT);  //세션 오픈!
        session.setPassword(PASSWORD);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }
    public static void sendFileToOtherServer(String sourcePath, String destinationPath) throws Exception {
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.put("C:\\upload\\2023\\09\\11\\test.jpg", "/home/ubuntu/upload/test.jpg");  //파일을 전송하는 메소드
        channelSftp.disconnect();
        channel.disconnect();
    }
}