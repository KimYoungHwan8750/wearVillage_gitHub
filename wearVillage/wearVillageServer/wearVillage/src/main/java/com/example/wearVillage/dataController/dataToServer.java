package com.example.wearVillage.dataController;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class dataToServer {
    private String ADDRESS = "3.39.167.250";
    private int PORT = 22;  //포트번호
    private String USERNAME = "ubuntu";
    private String PASSWORD = "";
    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    public void sshAccess() throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(USERNAME, ADDRESS, PORT);  //세션 오픈!
        session.setPassword(PASSWORD);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }
    public void sendFileToOtherServer(String sourcePath, String destinationPath) throws Exception {
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.put("C:\\upload/2023/09/09/test.png", "home/ubuntu/upload");  //파일을 전송하는 메소드
        channelSftp.disconnect();
        channel.disconnect();
    }
}