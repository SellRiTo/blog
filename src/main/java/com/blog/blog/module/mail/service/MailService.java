package com.blog.blog.module.mail.service;

import javax.mail.MessagingException;

/**
 * @program: blog
 * @description: 邮箱
 * @author: txr
 * @create: 2020-05-29 17:03
 */
public interface MailService {

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);
    public void sendSimpleMail(String to, String subject, String content, String... cc);
    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;
    public void sendHtmlMail(String to, String subject, String content, String... cc);
    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws Exception
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException;
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc);
    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws Exception
     */
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException;
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc);
}
